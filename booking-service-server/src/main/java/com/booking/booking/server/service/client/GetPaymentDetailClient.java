package com.booking.booking.server.service.client;

import com.booking.booking.server.config.FeignConfig;
import com.booking.booking.server.dto.BookingDto;
import com.booking.booking.server.dto.PaymentLinkResponse;
import com.booking.booking.server.dto.PaymentMethod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVER", path = "/api/payment", configuration = FeignConfig.class, fallback = GetPaymentDetailClientCallback.class)
public interface GetPaymentDetailClient {

    @PostMapping("/create/new-payment-link")
    public ResponseEntity<PaymentLinkResponse> createOrderAndGetPaymentLink(@RequestBody BookingDto booking, @RequestParam PaymentMethod paymentMethod, @RequestHeader("Authorization") String authHeader);

}