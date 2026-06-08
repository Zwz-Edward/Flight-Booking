package com.flightbooking.controller;

import com.flightbooking.dto.response.ApiResponse;
import com.flightbooking.entity.Flight;
import com.flightbooking.entity.User;
import com.flightbooking.repository.UserRepository;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final FlightService flightService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    /**
     * 获取所有用户
     */
    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.success(userRepository.findAll());
    }

    /**
     * 添加航班
     */
    @PostMapping("/flights")
    public ApiResponse<Flight> createFlight(@RequestBody Flight flight) {
        return ApiResponse.success("添加成功", flightService.createFlight(flight));
    }

    /**
     * 更新航班
     */
    @PutMapping("/flights/{id}")
    public ApiResponse<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return ApiResponse.success("更新成功", flightService.updateFlight(id, flight));
    }

    /**
     * 取消航班
     */
    @PostMapping("/flights/{id}/cancel")
    public ApiResponse<Void> cancelFlight(@PathVariable Long id) {
        flightService.cancelFlight(id);
        return ApiResponse.success("已取消", null);
    }

    /**
     * 手动触发超时订单处理
     */
    @PostMapping("/orders/process-timeout")
    public ApiResponse<Void> processTimeoutOrders() {
        orderService.processTimeoutOrders();
        return ApiResponse.success("处理完成", null);
    }
}
