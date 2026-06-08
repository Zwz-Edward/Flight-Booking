package com.flightbooking.exception;

import lombok.Getter;

/**
 * 错误码枚举
 */
@Getter
public enum ErrorCode {

    // ========== 通用错误 ==========
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或令牌已过期"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    CONFLICT(409, "资源冲突"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // ========== 用户模块 ==========
    USER_NOT_FOUND(1001, "用户不存在"),
    USERNAME_EXISTS(1002, "用户名已存在"),
    EMAIL_EXISTS(1003, "邮箱已被注册"),
    PASSWORD_ERROR(1004, "密码错误"),
    USER_DISABLED(1005, "账号已被禁用"),
    INVALID_TOKEN(1006, "无效的令牌"),
    TOKEN_EXPIRED(1007, "令牌已过期"),

    // ========== 航班模块 ==========
    FLIGHT_NOT_FOUND(2001, "航班不存在"),
    FLIGHT_FULL(2002, "航班已满座"),
    FLIGHT_CANCELLED(2003, "航班已取消"),
    FLIGHT_NOT_AVAILABLE(2004, "航班当前不可预订"),
    INSUFFICIENT_SEATS(2005, "余座不足"),

    // ========== 订单模块 ==========
    ORDER_NOT_FOUND(3001, "订单不存在"),
    ORDER_CANNOT_PAY(3002, "订单当前不可支付"),
    ORDER_CANNOT_CANCEL(3003, "订单当前不可取消"),
    ORDER_ALREADY_PAID(3004, "订单已支付"),
    ORDER_TIMEOUT(3005, "订单已超时"),
    ORDER_CANNOT_REFUND(3006, "订单当前不可退款"),

    // ========== 支付模块 ==========
    PAYMENT_NOT_FOUND(4001, "支付记录不存在"),
    PAYMENT_FAILED(4002, "支付失败"),
    INSUFFICIENT_BALANCE(4003, "余额不足"),
    REFUND_FAILED(4004, "退款失败"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
