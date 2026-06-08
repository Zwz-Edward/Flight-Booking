package com.flightbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 订单号 */
    @Column(name = "order_no", nullable = false, unique = true, length = 32)
    private String orderNo;

    /** 用户ID */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** 航班ID */
    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    /** 订单状态: PENDING_PAY / PAID / CANCELLED / REFUNDING / REFUNDED / COMPLETED / TIMEOUT */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING_PAY";

    /** 订单总金额(分) */
    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    /** 乘机人数 */
    @Column(name = "passenger_count")
    @Builder.Default
    private Integer passengerCount = 1;

    /** 舱位类型: ECONOMY / BUSINESS / FIRST_CLASS */
    @Column(name = "seat_type", length = 20)
    private String seatType;

    /** 联系人姓名 */
    @Column(name = "contact_name", length = 50)
    private String contactName;

    /** 联系人手机 */
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    /** 联系人邮箱 */
    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    /** 支付时间 */
    @Column(name = "pay_time")
    private LocalDateTime payTime;

    /** 取消时间 */
    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    /** 订单明细 */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
