package com.flightbooking.service.impl;

import com.flightbooking.dto.request.CreateOrderRequest;
import com.flightbooking.dto.response.OrderVO;
import com.flightbooking.dto.response.PageResponse;
import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Order;
import com.flightbooking.entity.OrderItem;
import com.flightbooking.entity.User;
import com.flightbooking.exception.BusinessException;
import com.flightbooking.exception.ErrorCode;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.repository.OrderItemRepository;
import com.flightbooking.repository.OrderRepository;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.OrderService;
import com.flightbooking.service.UserService;
import com.flightbooking.util.OrderNoGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.flightbooking.util.RedisDistributedLock;

/**
 * 订单服务实现
 *
 * 多线程/异步相关: 订单创建后的异步通知、超时取消等
 * 后续可引入 Redis 实现分布式锁防止并发超卖
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private final UserService userService;
    private final RedisDistributedLock redisDistributedLock;

    @Override
    @Transactional
    public OrderVO createOrder(Long userId, CreateOrderRequest request) {
        // 1. 获取航班并校验
        Flight flight = flightService.getFlightEntity(request.getFlightId());
        if (!"SCHEDULED".equals(flight.getStatus())) {
            throw new BusinessException(ErrorCode.FLIGHT_NOT_AVAILABLE);
        }

        // 2. 检查余座 (TODO: 后续引入Redis分布式锁防超卖)
        int passengerCount = request.getPassengers().size();
        String seatType = request.getSeatType();
        Long unitPrice;

        switch (seatType) {
            case "ECONOMY" -> {
                if (flight.getEconomySeats() < passengerCount) {
                    throw new BusinessException(ErrorCode.INSUFFICIENT_SEATS, "经济舱余座不足");
                }
                unitPrice = flight.getEconomyPrice() != null ? flight.getEconomyPrice() : flight.getPrice();
            }
            case "BUSINESS" -> {
                if (flight.getBusinessSeats() < passengerCount) {
                    throw new BusinessException(ErrorCode.INSUFFICIENT_SEATS, "商务舱余座不足");
                }
                unitPrice = flight.getBusinessPrice();
            }
            case "FIRST_CLASS" -> {
                if (flight.getFirstClassSeats() < passengerCount) {
                    throw new BusinessException(ErrorCode.INSUFFICIENT_SEATS, "头等舱余座不足");
                }
                unitPrice = flight.getFirstClassPrice();
            }
            default -> throw new BusinessException(ErrorCode.BAD_REQUEST, "无效的舱位类型");
        }

        // 3. 扣减余座
        switch (seatType) {
            case "ECONOMY" -> flight.setEconomySeats(flight.getEconomySeats() - passengerCount);
            case "BUSINESS" -> flight.setBusinessSeats(flight.getBusinessSeats() - passengerCount);
            case "FIRST_CLASS" -> flight.setFirstClassSeats(flight.getFirstClassSeats() - passengerCount);
        }
        flight.setRemainingSeats(flight.getRemainingSeats() - passengerCount);
        flightRepository.save(flight);

        // 4. 创建订单
        long totalPrice = unitPrice * passengerCount;
        String orderNo = OrderNoGenerator.generateOrderNo();

        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(userId)
                .flightId(flight.getId())
                .status("PENDING_PAY")
                .totalPrice(totalPrice)
                .passengerCount(passengerCount)
                .seatType(seatType)
                .contactName(request.getContactName())
                .contactPhone(request.getContactPhone())
                .contactEmail(request.getContactEmail())
                .build();

        // 5. 创建订单明细
        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderRequest.PassengerInfo passenger : request.getPassengers()) {
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .passengerName(passenger.getName())
                    .passengerIdCard(passenger.getIdCard())
                    .passengerPhone(passenger.getPhone())
                    .price(unitPrice)
                    .seatType(seatType)
                    .build();
            orderItems.add(item);
        }
        order.setOrderItems(orderItems);

        order = orderRepository.save(order);
        log.info("创建订单: orderNo={}, userId={}, flightNo={}, amount={}",
                orderNo, userId, flight.getFlightNo(), totalPrice);

        // 6. 异步发送通知
        sendOrderNotificationAsync(order);

        return convertToVO(order, flight);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderVO getOrderDetail(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        // 校验用户权限
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看该订单");
        }

        Flight flight = flightService.getFlightEntity(order.getFlightId());
        return convertToVO(order, flight);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderVO getOrderByOrderNo(String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        Flight flight = flightService.getFlightEntity(order.getFlightId());
        return convertToVO(order, flight);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderVO> getUserOrders(Long userId, int page, int size) {
        Page<Order> orderPage = orderRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));

        List<OrderVO> voList = orderPage.getContent().stream()
                .map(order -> convertToVO(order, flightService.getFlightEntity(order.getFlightId())))
                .collect(Collectors.toList());

        return PageResponse.of(voList, page, size, orderPage.getTotalElements());
    }

    @Override
    @Transactional
    public void cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权取消该订单");
        }

        if (!"PENDING_PAY".equals(order.getStatus())) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_CANCEL, "只有待支付订单可以取消");
        }

        // 恢复余座
        restoreSeats(order);

        order.setStatus("CANCELLED");
        order.setCancelTime(LocalDateTime.now());
        orderRepository.save(order);
        log.info("取消订单: orderNo={}", order.getOrderNo());
    }

    @Override
    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        if (!"PAID".equals(order.getStatus())) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_CANCEL, "只有已支付订单可以完成");
        }

        order.setStatus("COMPLETED");
        orderRepository.save(order);
        log.info("完成订单: orderNo={}", order.getOrderNo());
    }

    @Override
    @Transactional
    public void processTimeoutOrders() {
        // 查找超时未支付订单 (超过30分钟未支付)
        LocalDateTime timeout = LocalDateTime.now().minusMinutes(30);
        List<Order> timeoutOrders = orderRepository.findByStatus("PENDING_PAY");

        for (Order order : timeoutOrders) {
            if (order.getCreatedAt().isBefore(timeout)) {
                restoreSeats(order);
                order.setStatus("TIMEOUT");
                orderRepository.save(order);
                log.info("超时取消订单: orderNo={}", order.getOrderNo());
            }
        }
    }

    /**
     * 异步发送订单通知
     */
    @Async
    public void sendOrderNotificationAsync(Order order) {
        try {
            // TODO: 集成消息队列/邮件服务发送通知
            // 模拟耗时操作
            Thread.sleep(100);
            log.info("已发送订单通知: orderNo={}", order.getOrderNo());
        } catch (Exception e) {
            log.error("发送订单通知失败: orderNo={}", order.getOrderNo(), e);
        }
    }

    /**
     * 恢复扣减的座位
     */
    private void restoreSeats(Order order) {
        Flight flight = flightService.getFlightEntity(order.getFlightId());
        int count = order.getPassengerCount();

        switch (order.getSeatType()) {
            case "ECONOMY" -> flight.setEconomySeats(flight.getEconomySeats() + count);
            case "BUSINESS" -> flight.setBusinessSeats(flight.getBusinessSeats() + count);
            case "FIRST_CLASS" -> flight.setFirstClassSeats(flight.getFirstClassSeats() + count);
        }
        flight.setRemainingSeats(flight.getRemainingSeats() + count);
        flightRepository.save(flight);
    }

    /**
     * 转换为视图对象
     */
    private OrderVO convertToVO(Order order, Flight flight) {
        User user = userService.getUserById(order.getUserId());

        List<OrderVO.OrderItemVO> itemVOs = order.getOrderItems().stream()
                .map(item -> OrderVO.OrderItemVO.builder()
                        .id(item.getId())
                        .passengerName(item.getPassengerName())
                        .passengerIdCard(item.getPassengerIdCard())
                        .passengerPhone(item.getPassengerPhone())
                        .price(item.getPrice())
                        .seatType(item.getSeatType())
                        .build())
                .collect(Collectors.toList());

        return OrderVO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .username(user.getUsername())
                .flightId(flight.getId())
                .flightNo(flight.getFlightNo())
                .airline(flight.getAirline())
                .departureCity(flight.getDepartureCity())
                .arrivalCity(flight.getArrivalCity())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .seatType(order.getSeatType())
                .passengerCount(order.getPassengerCount())
                .contactName(order.getContactName())
                .contactPhone(order.getContactPhone())
                .payTime(order.getPayTime())
                .createdAt(order.getCreatedAt())
                .orderItems(itemVOs)
                .build();
    }
}
