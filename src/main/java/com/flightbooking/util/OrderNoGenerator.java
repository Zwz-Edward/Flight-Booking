package com.flightbooking.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 订单号生成器
 *
 * 格式: 前缀 + yyyyMMddHHmmss + 6位序列号
 * 线程安全, 后续可改用 Redis 生成分布式ID
 */
public class OrderNoGenerator {

    private static final String FLIGHT_ORDER_PREFIX = "FB";
    private static final String PAYMENT_PREFIX = "PAY";
    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final int MAX_SEQUENCE = 999999;

    /**
     * 生成订单号
     */
    public static String generateOrderNo() {
        return generate(FLIGHT_ORDER_PREFIX);
    }

    /**
     * 生成支付流水号
     */
    public static String generatePaymentNo() {
        return generate(PAYMENT_PREFIX);
    }

    private static String generate(String prefix) {
        long seq = SEQUENCE.incrementAndGet();
        if (seq > MAX_SEQUENCE) {
            synchronized (OrderNoGenerator.class) {
                if (SEQUENCE.get() > MAX_SEQUENCE) {
                    SEQUENCE.set(0);
                }
                seq = SEQUENCE.incrementAndGet();
            }
        }
        return prefix + LocalDateTime.now().format(FORMATTER) + String.format("%06d", seq);
    }
}
