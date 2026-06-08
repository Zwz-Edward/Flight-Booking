package com.flightbooking.enums;

/**
 * 座位类型枚举
 */
public enum SeatType {

    ECONOMY("经济舱"),
    BUSINESS("商务舱"),
    FIRST_CLASS("头等舱");

    private final String description;

    SeatType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
