package com.flightbooking.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建订单请求
 */
@Data
public class CreateOrderRequest {

    @NotNull(message = "航班ID不能为空")
    private Long flightId;

    @NotBlank(message = "座位类型不能为空")
    private String seatType;

    @NotBlank(message = "联系人姓名不能为空")
    private String contactName;

    @NotBlank(message = "联系人手机不能为空")
    private String contactPhone;

    private String contactEmail;

    @NotEmpty(message = "乘机人信息不能为空")
    @Valid
    private List<PassengerInfo> passengers;

    @Data
    public static class PassengerInfo {

        @NotBlank(message = "乘机人姓名不能为空")
        private String name;

        @NotBlank(message = "乘机人身份证号不能为空")
        private String idCard;

        private String phone;
    }
}
