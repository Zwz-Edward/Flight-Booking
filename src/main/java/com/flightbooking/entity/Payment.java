package com.flightbooking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付记录实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 支付流水号 */
    @Column(name = "payment_no", nullable = false, unique = true, length = 32)
    private String paymentNo;

    /** 订单ID */
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /** 订单号 */
    @Column(name = "order_no", nullable = false, length = 32)
    private String orderNo;

    /** 支付金额(分) */
    @Column(nullable = false)
    private Long amount;

    /** 支付方式: ALIPAY / WECHAT / UNIONPAY / BALANCE */
    @Column(name = "pay_method", nullable = false, length = 20)
    private String payMethod;

    /** 支付状态: PENDING / SUCCESS / FAILED / REFUNDING / REFUNDED */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING";

    /** 第三方支付流水号 */
    @Column(name = "transaction_id", length = 64)
    private String transactionId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /** 支付完成时间 */
    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
