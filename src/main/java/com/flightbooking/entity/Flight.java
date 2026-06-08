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
 * 航班实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fb_flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 航班号 */
    @Column(name = "flight_no", nullable = false, unique = true, length = 20)
    private String flightNo;

    /** 航空公司 */
    @Column(nullable = false, length = 50)
    private String airline;

    /** 出发城市 */
    @Column(name = "departure_city", nullable = false, length = 50)
    private String departureCity;

    /** 到达城市 */
    @Column(name = "arrival_city", nullable = false, length = 50)
    private String arrivalCity;

    /** 出发机场 */
    @Column(name = "departure_airport", length = 100)
    private String departureAirport;

    /** 到达机场 */
    @Column(name = "arrival_airport", length = 100)
    private String arrivalAirport;

    /** 出发时间 */
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    /** 到达时间 */
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    /** 票价(分) */
    @Column(nullable = false)
    private Long price;

    /** 经济舱票价(分) */
    @Column(name = "economy_price")
    private Long economyPrice;

    /** 商务舱票价(分) */
    @Column(name = "business_price")
    private Long businessPrice;

    /** 头等舱票价(分) */
    @Column(name = "first_class_price")
    private Long firstClassPrice;

    /** 经济舱余座 */
    @Column(name = "economy_seats")
    @Builder.Default
    private Integer economySeats = 0;

    /** 商务舱余座 */
    @Column(name = "business_seats")
    @Builder.Default
    private Integer businessSeats = 0;

    /** 头等舱余座 */
    @Column(name = "first_class_seats")
    @Builder.Default
    private Integer firstClassSeats = 0;

    /** 总余座 */
    @Column(name = "remaining_seats")
    @Builder.Default
    private Integer remainingSeats = 0;

    /** 航班状态: SCHEDULED / DELAYED / CANCELLED / COMPLETED */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "SCHEDULED";

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
