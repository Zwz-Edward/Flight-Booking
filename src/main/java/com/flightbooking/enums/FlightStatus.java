package com.flightbooking.enums;

/**
 * 航班状态枚举
 */
public enum FlightStatus {

    SCHEDULED("计划中"),
    DELAYED("延误"),
    CANCELLED("已取消"),
    COMPLETED("已完成");

    private final String description;

    FlightStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
