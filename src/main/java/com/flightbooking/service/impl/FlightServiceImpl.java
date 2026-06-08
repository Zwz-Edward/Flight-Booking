package com.flightbooking.service.impl;

import com.flightbooking.dto.request.FlightSearchRequest;
import com.flightbooking.dto.response.FlightVO;
import com.flightbooking.dto.response.PageResponse;
import com.flightbooking.entity.Flight;
import com.flightbooking.exception.BusinessException;
import com.flightbooking.exception.ErrorCode;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 航班服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    @Cacheable(value = "flights", key = "#request.hashCode()", unless = "#result.content.isEmpty()")
    public PageResponse<FlightVO> searchFlights(FlightSearchRequest request) {
        LocalDate departureDate = request.getDepartureDate();
        LocalDateTime dateStart = departureDate != null ? departureDate.atStartOfDay() : null;
        LocalDateTime dateEnd = departureDate != null ? departureDate.atTime(LocalTime.MAX) : null;

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Flight> flightPage = flightRepository.searchFlights(
                request.getDepartureCity(),
                request.getArrivalCity(),
                dateStart,
                dateEnd,
                pageable
        );

        List<FlightVO> voList = flightPage.getContent().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResponse.of(voList, request.getPage(), request.getSize(), flightPage.getTotalElements());
    }

    @Override
    @Cacheable(value = "flights", key = "'detail:' + #id")
    public FlightVO getFlightDetail(Long id) {
        Flight flight = getFlightEntity(id);
        return convertToVO(flight);
    }

    @Override
    @Cacheable(value = "flights", key = "'hot:' + #limit")
    public List<FlightVO> getHotFlights(int limit) {
        return flightRepository.findHotFlights(PageRequest.of(0, limit))
                .stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "flights", allEntries = true)
    public Flight createFlight(Flight flight) {
        if (flight.getPrice() == null || flight.getPrice() <= 0) {
            flight.setPrice(flight.getEconomyPrice() != null ? flight.getEconomyPrice() : 0L);
        }
        flight = flightRepository.save(flight);
        log.info("新增航班: id={}, flightNo={}", flight.getId(), flight.getFlightNo());
        return flight;
    }

    @Override
    @Transactional
    @CacheEvict(value = "flights", allEntries = true)
    public Flight updateFlight(Long id, Flight flight) {
        Flight existing = getFlightEntity(id);
        flight.setId(id);
        flight.setCreatedAt(existing.getCreatedAt());
        flight = flightRepository.save(flight);
        log.info("更新航班: id={}", id);
        return flight;
    }

    @Override
    @Transactional
    @CacheEvict(value = "flights", allEntries = true)
    public void cancelFlight(Long id) {
        Flight flight = getFlightEntity(id);
        flight.setStatus("CANCELLED");
        flightRepository.save(flight);
        log.info("取消航班: id={}, flightNo={}", id, flight.getFlightNo());
    }

    @Override
    public Flight getFlightEntity(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.FLIGHT_NOT_FOUND));
    }

    /**
     * 将 Flight 实体转换为 FlightVO
     */
    private FlightVO convertToVO(Flight flight) {
        long duration = ChronoUnit.MINUTES.between(flight.getDepartureTime(), flight.getArrivalTime());

        return FlightVO.builder()
                .id(flight.getId())
                .flightNo(flight.getFlightNo())
                .airline(flight.getAirline())
                .departureCity(flight.getDepartureCity())
                .arrivalCity(flight.getArrivalCity())
                .departureAirport(flight.getDepartureAirport())
                .arrivalAirport(flight.getArrivalAirport())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .duration(duration)
                .price(flight.getPrice())
                .economyPrice(flight.getEconomyPrice())
                .businessPrice(flight.getBusinessPrice())
                .firstClassPrice(flight.getFirstClassPrice())
                .economySeats(flight.getEconomySeats())
                .businessSeats(flight.getBusinessSeats())
                .firstClassSeats(flight.getFirstClassSeats())
                .remainingSeats(flight.getRemainingSeats())
                .status(flight.getStatus())
                .build();
    }
}
