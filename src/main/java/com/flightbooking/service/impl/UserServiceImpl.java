package com.flightbooking.service.impl;

import com.flightbooking.dto.request.LoginRequest;
import com.flightbooking.dto.request.RegisterRequest;
import com.flightbooking.dto.request.UserUpdateRequest;
import com.flightbooking.dto.response.LoginVO;
import com.flightbooking.entity.User;
import com.flightbooking.exception.BusinessException;
import com.flightbooking.exception.ErrorCode;
import com.flightbooking.repository.UserRepository;
import com.flightbooking.security.JwtUtil;
import com.flightbooking.service.UserService;
import com.flightbooking.util.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }
        // 检查邮箱是否已注册
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .realName(request.getRealName())
                .idCard(request.getIdCard())
                .balance(0L)
                .role("USER")
                .build();

        user = userRepository.save(user);
        log.info("新用户注册: id={}, username={}", user.getId(), user.getUsername());
        return user;
    }

    @Override
    public LoginVO login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        log.info("用户登录: id={}, username={}", user.getId(), user.getUsername());

        return LoginVO.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = getUserById(userId);
        BeanCopyUtil.copyNonNullProperties(request, user);
        user = userRepository.save(user);
        log.info("用户信息更新: id={}", userId);
        return user;
    }

    @Override
    @Transactional
    public User rechargeBalance(Long userId, Long amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "充值金额必须大于0");
        }
        User user = getUserById(userId);
        user.setBalance(user.getBalance() + amount);
        user = userRepository.save(user);
        log.info("用户充值: id={}, amount={}, balance={}", userId, amount, user.getBalance());
        return user;
    }
}
