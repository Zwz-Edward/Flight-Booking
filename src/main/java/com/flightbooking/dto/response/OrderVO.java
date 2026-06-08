package com.flightbooking.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;


/**
 * 订单视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {

    private Long id;
    private String orderNo;
    private Long userId;
    private String username;
    private Long flightId;
    private String flightNo;
    private String airline;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;
    private Long totalPrice;
    private String seatType;
    private Integer passengerCount;
    private String contactName;
    private String contactPhone;
    private LocalDateTime payTime;
    private LocalDateTime createdAt;

    /** 订单明细 */
    private List<OrderItemVO> orderItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemVO {
        private Long id;
        private String passengerName;
        private String passengerIdCard;
        private String passengerPhone;
        private Long price;
        private String seatType;
    }
}
