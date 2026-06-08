package com.flightbooking.repository;

import com.flightbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 支付记录数据访问层
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentNo(String paymentNo);

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByOrderNo(String orderNo);

    Optional<Payment> findByOrderIdAndStatus(Long orderId, String status);
}
