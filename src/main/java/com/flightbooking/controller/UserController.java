package com.flightbooking.controller;

import com.flightbooking.dto.request.UserUpdateRequest;
import com.flightbooking.dto.response.ApiResponse;
import com.flightbooking.entity.User;
import com.flightbooking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/profile")
    public ApiResponse<User> getProfile(@AuthenticationPrincipal User user) {
        return ApiResponse.success(userService.getUserById(user.getId()));
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/profile")
    public ApiResponse<User> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserUpdateRequest request) {
        return ApiResponse.success("更新成功", userService.updateUser(user.getId(), request));
    }

    /**
     * 充值余额
     */
    @PostMapping("/recharge")
    public ApiResponse<User> recharge(
            @AuthenticationPrincipal User user,
            @RequestParam Long amount) {
        return ApiResponse.success("充值成功", userService.rechargeBalance(user.getId(), amount));
    }

    /**
     * 获取余额
     */
    @GetMapping("/balance")
    public ApiResponse<Long> getBalance(@AuthenticationPrincipal User user) {
        User fullUser = userService.getUserById(user.getId());
        return ApiResponse.success(fullUser.getBalance());
    }
}
