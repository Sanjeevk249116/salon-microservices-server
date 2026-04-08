package com.payment.server.service;

import com.payment.server.domain.PaymentMethod;
import com.payment.server.dto.BookingDto;
import com.payment.server.dto.PaymentRequestDto;
import com.payment.server.dto.UserDto;
import com.payment.server.dto.UserResponseClientDto;
import com.payment.server.entity.Payment;
import com.payment.server.payloadResponseStructure.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;


@Service
public interface PaymentService {
    PaymentLinkResponse createOrderAndGenerateNewPayment(UserResponseClientDto user, BookingDto booking, PaymentMethod paymentMethod);

    Payment getSinglePaymentById(Long id);

    Payment getSinglePaymentByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserResponseClientDto user, Long amount, Long orderId);

    String createStripePaymentLink(UserResponseClientDto user, Long amount, Long orderId);

    Boolean proceedPayment(Payment paymentOrder,String paymentId, String paymentLinkId) throws RazorpayException;
}
