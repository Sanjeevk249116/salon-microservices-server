package com.payment.server.repository;

import com.payment.server.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentLinkId(String paymentLinkId);

}
