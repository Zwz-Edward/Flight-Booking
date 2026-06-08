package com.flightbooking.enums;

/**
 * 订单状态枚举
 */
public enum OrderStatus {

    PENDING_PAY("待支付"),
    PAID("已支付"),
    CANCELLED("已取消"),
    REFUNDING("退款中"),
    REFUNDED("已退款"),
    COMPLETED("已完成"),
    TIMEOUT("已超时");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
