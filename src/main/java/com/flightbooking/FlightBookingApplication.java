package com.flightbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 企业级机票预订系统 - 启动类
 *
 * 技术栈：
 * - Spring Boot 3.2.5 (基础框架)
 * - Spring Data JPA (持久层)
 * - Spring Security (安全认证)
 * - Spring Cache (缓存抽象)
 * - JWT (令牌认证)
 *
 * 后续扩展学习：
 * - Spring Cloud (微服务) → 可拆分为多个独立服务
 * - Redis (缓存/分布式锁) → 缓存热点数据、分布式锁防超卖
 * - 多线程/线程池 → 异步订单处理、短信通知
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class FlightBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightBookingApplication.class, args);
    }
}
