package com.flightbooking.controller;

import com.flightbooking.dto.request.PaymentRequest;
import com.flightbooking.dto.response.ApiResponse;
import com.flightbooking.entity.Payment;
import com.flightbooking.entity.User;
import com.flightbooking.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 支付订单
     */
    @PostMapping
    public ApiResponse<Payment> pay(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PaymentRequest request) {
        return ApiResponse.success("支付成功", paymentService.processPayment(user.getId(), request));
    }

    /**
     * 申请退款
     */
    @PostMapping("/refund/{orderId}")
    public ApiResponse<Payment> refund(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId) {
        return ApiResponse.success("退款成功", paymentService.requestRefund(user.getId(), orderId));
    }

    /**
     * 查询支付记录
     */
    @GetMapping("/order/{orderId}")
    public ApiResponse<Payment> getPaymentByOrder(@PathVariable Long orderId) {
        return ApiResponse.success(paymentService.getPaymentByOrderId(orderId));
    }
}
