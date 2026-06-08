package com.flightbooking.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航班视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightVO {

    private Long id;
    private String flightNo;
    private String airline;
    private String departureCity;
    private String arrivalCity;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    /** 时长(分钟) */
    private Long duration;

    private Long price;
    private Long economyPrice;
    private Long businessPrice;
    private Long firstClassPrice;
    private Integer economySeats;
    private Integer businessSeats;
    private Integer firstClassSeats;
    private Integer remainingSeats;
    private String status;
}
