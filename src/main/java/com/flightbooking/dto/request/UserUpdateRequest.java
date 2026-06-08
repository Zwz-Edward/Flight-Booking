package com.flightbooking.dto.request;

import lombok.Data;

/**
 * 用户信息更新请求
 */
@Data
public class UserUpdateRequest {

    private String email;
    private String phone;
    private String realName;
    private String idCard;
}
