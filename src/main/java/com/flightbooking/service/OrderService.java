package com.flightbooking.service;

import com.flightbooking.dto.request.CreateOrderRequest;
import com.flightbooking.dto.response.OrderVO;
import com.flightbooking.dto.response.PageResponse;
import com.flightbooking.entity.Order;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 创建订单
     */
    OrderVO createOrder(Long userId, CreateOrderRequest request);

    /**
     * 获取订单详情
     */
    OrderVO getOrderDetail(Long userId, Long orderId);

    /**
     * 根据订单号获取订单
     */
    OrderVO getOrderByOrderNo(String orderNo);

    /**
     * 获取用户订单列表
     */
    PageResponse<OrderVO> getUserOrders(Long userId, int page, int size);

    /**
     * 取消订单
     */
    void cancelOrder(Long userId, Long orderId);

    /**
     * 完成订单（确认行程）
     */
    void completeOrder(Long orderId);

    /**
     * 处理超时未支付订单
     */
    void processTimeoutOrders();
}
