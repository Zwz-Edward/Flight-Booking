package com.flightbooking.service;

import com.flightbooking.dto.request.LoginRequest;
import com.flightbooking.dto.request.RegisterRequest;
import com.flightbooking.dto.request.UserUpdateRequest;
import com.flightbooking.dto.response.LoginVO;
import com.flightbooking.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    User register(RegisterRequest request);

    /**
     * 用户登录
     */
    LoginVO login(LoginRequest request);

    /**
     * 根据ID获取用户
     */
    User getUserById(Long id);

    /**
     * 更新用户信息
     */
    User updateUser(Long userId, UserUpdateRequest request);

    /**
     * 充值余额
     */
    User rechargeBalance(Long userId, Long amount);
}
