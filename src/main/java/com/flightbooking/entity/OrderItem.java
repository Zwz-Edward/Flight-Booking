package com.flightbooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单明细实体 - 每个乘机人一条记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fb_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 所属订单 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    /** 乘机人姓名 */
    @Column(name = "passenger_name", nullable = false, length = 50)
    private String passengerName;

    /** 乘机人身份证号 */
    @Column(name = "passenger_id_card", length = 18)
    private String passengerIdCard;

    /** 乘机人手机号 */
    @Column(name = "passenger_phone", length = 20)
    private String passengerPhone;

    /** 票价(分) */
    @Column(nullable = false)
    private Long price;

    /** 座位类型: ECONOMY / BUSINESS / FIRST_CLASS */
    @Column(name = "seat_type", length = 20)
    private String seatType;
}
