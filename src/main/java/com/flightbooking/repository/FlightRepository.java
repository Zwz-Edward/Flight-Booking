package com.flightbooking.repository;

import com.flightbooking.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 航班数据访问层
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * 按起降城市搜索航班
     */
    @Query("SELECT f FROM Flight f WHERE " +
            "(:departureCity IS NULL OR f.departureCity LIKE %:departureCity%) AND " +
            "(:arrivalCity IS NULL OR f.arrivalCity LIKE %:arrivalCity%) AND " +
            "(:departureDateStart IS NULL OR f.departureTime >= :departureDateStart) AND " +
            "(:departureDateEnd IS NULL OR f.departureTime <= :departureDateEnd) AND " +
            "f.status = 'SCHEDULED' " +
            "ORDER BY f.departureTime ASC")
    Page<Flight> searchFlights(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("departureDateStart") LocalDateTime departureDateStart,
            @Param("departureDateEnd") LocalDateTime departureDateEnd,
            Pageable pageable);

    /**
     * 查找热门航线
     */
    @Query("SELECT f FROM Flight f WHERE f.status = 'SCHEDULED' ORDER BY f.remainingSeats DESC")
    List<Flight> findHotFlights(Pageable pageable);

    /**
     * 根据航班号查询
     */
    Flight findByFlightNo(String flightNo);

    /**
     * 查找某城市出发的所有航班
     */
    List<Flight> findByDepartureCityAndStatus(String departureCity, String status);
}
