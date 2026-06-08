package com.flightbooking.service;

import com.flightbooking.dto.request.FlightSearchRequest;
import com.flightbooking.dto.response.FlightVO;
import com.flightbooking.dto.response.PageResponse;
import com.flightbooking.entity.Flight;

import java.util.List;

/**
 * 航班服务接口
 */
public interface FlightService {

    /**
     * 搜索航班
     */
    PageResponse<FlightVO> searchFlights(FlightSearchRequest request);

    /**
     * 获取航班详情
     */
    FlightVO getFlightDetail(Long id);

    /**
     * 获取热门航班
     */
    List<FlightVO> getHotFlights(int limit);

    /**
     * 管理员添加航班
     */
    Flight createFlight(Flight flight);

    /**
     * 管理员更新航班
     */
    Flight updateFlight(Long id, Flight flight);

    /**
     * 管理员取消航班
     */
    void cancelFlight(Long id);

    /**
     * 获取航班实体（内部使用）
     */
    Flight getFlightEntity(Long id);
}
