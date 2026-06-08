package com.flightbooking.dto.request;

import java.time.LocalDate;

import lombok.Data;

/**
 * 航班搜索请求
 */
@Data
public class FlightSearchRequest {

    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private String keyword;
    private Long minPrice;
    private Long maxPrice;
    private String seatType;

    private int page = 0;
    private int size = 10;
}
