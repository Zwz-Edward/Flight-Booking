package com.flightbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 支付请求
 */
@Data
public class PaymentRequest {

    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotBlank(message = "支付方式不能为空")
    private String payMethod;

    /** 支付密码 - 余额支付时需要 */
    private String payPassword;
}
