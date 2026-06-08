package com.flightbooking.enums;

/**
 * 支付方式枚举
 */
public enum PaymentMethod {

    ALIPAY("支付宝"),
    WECHAT("微信支付"),
    UNIONPAY("银联支付"),
    BALANCE("余额支付");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
