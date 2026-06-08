package com.flightbooking.controller;

import com.flightbooking.dto.request.FlightSearchRequest;
import com.flightbooking.dto.response.ApiResponse;
import com.flightbooking.dto.response.FlightVO;
import com.flightbooking.dto.response.PageResponse;
import com.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 航班控制器 - 查询航班信息
 */
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    /**
     * 搜索航班
     */
    @GetMapping("/search")
    public ApiResponse<PageResponse<FlightVO>> searchFlights(FlightSearchRequest request) {
        return ApiResponse.success(flightService.searchFlights(request));
    }

    /**
     * 获取航班详情
     */
    @GetMapping("/{id}")
    public ApiResponse<FlightVO> getFlightDetail(@PathVariable Long id) {
        return ApiResponse.success(flightService.getFlightDetail(id));
    }

    /**
     * 获取热门航班
     */
    @GetMapping("/hot")
    public ApiResponse<List<FlightVO>> getHotFlights(@RequestParam(defaultValue = "5") int limit) {
        return ApiResponse.success(flightService.getHotFlights(limit));
    }
}
