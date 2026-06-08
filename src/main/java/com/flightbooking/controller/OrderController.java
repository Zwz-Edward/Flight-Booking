package com.flightbooking.controller;

import com.flightbooking.dto.request.CreateOrderRequest;
import com.flightbooking.dto.response.ApiResponse;
import com.flightbooking.dto.response.OrderVO;
import com.flightbooking.dto.response.PageResponse;
import com.flightbooking.entity.User;
import com.flightbooking.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping
    public ApiResponse<OrderVO> createOrder(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateOrderRequest request) {
        return ApiResponse.success("下单成功", orderService.createOrder(user.getId(), request));
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{orderId}")
    public ApiResponse<OrderVO> getOrderDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId) {
        return ApiResponse.success(orderService.getOrderDetail(user.getId(), orderId));
    }

    /**
     * 根据订单号查询
     */
    @GetMapping("/no/{orderNo}")
    public ApiResponse<OrderVO> getOrderByOrderNo(@PathVariable String orderNo) {
        return ApiResponse.success(orderService.getOrderByOrderNo(orderNo));
    }

    /**
     * 获取用户订单列表
     */
    @GetMapping
    public ApiResponse<PageResponse<OrderVO>> getUserOrders(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(orderService.getUserOrders(user.getId(), page, size));
    }

    /**
     * 取消订单
     */
    @PostMapping("/{orderId}/cancel")
    public ApiResponse<Void> cancelOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId) {
        orderService.cancelOrder(user.getId(), orderId);
        return ApiResponse.success("取消成功", null);
    }

    /**
     * 完成订单
     */
    @PostMapping("/{orderId}/complete")
    public ApiResponse<Void> completeOrder(@PathVariable Long orderId) {
        orderService.completeOrder(orderId);
        return ApiResponse.success("已完成", null);
    }
}
