package com.flightbooking.service.impl;

import com.flightbooking.dto.request.PaymentRequest;
import com.flightbooking.entity.Order;
import com.flightbooking.entity.Payment;
import com.flightbooking.entity.User;
import com.flightbooking.exception.BusinessException;
import com.flightbooking.exception.ErrorCode;
import com.flightbooking.repository.OrderRepository;
import com.flightbooking.repository.PaymentRepository;
import com.flightbooking.service.PaymentService;
import com.flightbooking.service.UserService;
import com.flightbooking.util.OrderNoGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 支付服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Payment processPayment(Long userId, PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权支付该订单");
        }

        if (!"PENDING_PAY".equals(order.getStatus())) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_PAY);
        }

        String payMethod = request.getPayMethod();
        Payment payment = Payment.builder()
                .paymentNo(OrderNoGenerator.generatePaymentNo())
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .amount(order.getTotalPrice())
                .payMethod(payMethod)
                .status("PENDING")
                .build();

        // 根据支付方式处理
        switch (payMethod) {
            case "BALANCE" -> processBalancePayment(userId, order, payment);
            case "ALIPAY", "WECHAT", "UNIONPAY" -> processThirdPartyPayment(payment);
            default -> throw new BusinessException(ErrorCode.BAD_REQUEST, "不支持的支付方式");
        }

        payment = paymentRepository.save(payment);
        log.info("支付成功: paymentNo={}, orderNo={}, amount={}, method={}",
                payment.getPaymentNo(), order.getOrderNo(), payment.getAmount(), payMethod);

        return payment;
    }

    @Override
    @Transactional
    public Payment requestRefund(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        if (!"PAID".equals(order.getStatus())) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_REFUND);
        }

        Payment payment = paymentRepository.findByOrderIdAndStatus(orderId, "SUCCESS")
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));

        // 退款处理 - 余额支付直接退款
        if ("BALANCE".equals(payment.getPayMethod())) {
            User user = userService.getUserById(userId);
            user.setBalance(user.getBalance() + payment.getAmount());
            log.info("余额退款: userId={}, amount={}", userId, payment.getAmount());
        }

        payment.setStatus("REFUNDED");
        order.setStatus("REFUNDED");

        paymentRepository.save(payment);
        orderRepository.save(order);

        log.info("退款成功: paymentNo={}, orderNo={}", payment.getPaymentNo(), order.getOrderNo());
        return payment;
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    @Override
    public Payment getPaymentByPaymentNo(String paymentNo) {
        return paymentRepository.findByPaymentNo(paymentNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    /**
     * 余额支付处理
     */
    private void processBalancePayment(Long userId, Order order, Payment payment) {
        User user = userService.getUserById(userId);
        if (user.getBalance() < order.getTotalPrice()) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        // 扣减余额
        user.setBalance(user.getBalance() - order.getTotalPrice());
        payment.setStatus("SUCCESS");
        payment.setPayTime(LocalDateTime.now());
        order.setStatus("PAID");
        order.setPayTime(LocalDateTime.now());
        orderRepository.save(order);
    }

    /**
     * 第三方支付处理（模拟）
     */
    private void processThirdPartyPayment(Payment payment) {
        // TODO: 集成第三方支付SDK（支付宝/微信）
        // 模拟支付成功
        payment.setStatus("SUCCESS");
        payment.setTransactionId("MOCK_" + System.currentTimeMillis());
        payment.setPayTime(LocalDateTime.now());

        Order order = orderRepository.findById(payment.getOrderId()).orElse(null);
        if (order != null) {
            order.setStatus("PAID");
            order.setPayTime(LocalDateTime.now());
            orderRepository.save(order);
        }
    }
}
