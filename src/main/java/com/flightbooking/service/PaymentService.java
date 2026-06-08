package com.flightbooking.service;

import com.flightbooking.dto.request.PaymentRequest;
import com.flightbooking.entity.Payment;

/**
 * 支付服务接口
 */
public interface PaymentService {

    /**
     * 发起支付
     */
    Payment processPayment(Long userId, PaymentRequest request);

    /**
     * 申请退款
     */
    Payment requestRefund(Long userId, Long orderId);

    /**
     * 查询支付记录
     */
    Payment getPaymentByOrderId(Long orderId);

    /**
     * 查询支付记录
     */
    Payment getPaymentByPaymentNo(String paymentNo);
}
