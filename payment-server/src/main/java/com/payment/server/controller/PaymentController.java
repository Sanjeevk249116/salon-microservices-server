package com.payment.server.controller;

import com.payment.server.domain.PaymentMethod;
import com.payment.server.dto.BookingDto;
import com.payment.server.dto.PaymentRequestDto;
import com.payment.server.dto.UserDto;
import com.payment.server.entity.Payment;
import com.payment.server.payloadResponseStructure.PaymentLinkResponse;
import com.payment.server.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create/new-payment-link")
    public ResponseEntity<PaymentLinkResponse> createOrderAndGetPaymentLink(@RequestBody BookingDto booking, @RequestParam PaymentMethod paymentMethod) {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setPhone("9044011112");
        user.setEmail("priyanshuharrdy@gmail.com");
        user.setFullName("Priyanshu dewidi");

        PaymentLinkResponse paymentResponseLink = paymentService.createOrderAndGenerateNewPayment(user, booking, paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponseLink);
    }

    @GetMapping("/read/single-payment/{id}")
    public ResponseEntity<Payment> getPaymentOrderById(@PathVariable Long id) {
        Payment getSinglePaymentOrder = paymentService.getSinglePaymentById(id);
        return ResponseEntity.ok(getSinglePaymentOrder);
    }

    @GetMapping("/read/single-payment-by-paymentId/{paymentId}")
    public ResponseEntity<Payment> getPaymentOrderByPaymentId(@PathVariable String paymentId) {
        Payment getSinglePaymentOrderByPaymentId = paymentService.getSinglePaymentByPaymentId(paymentId);
        return ResponseEntity.ok(getSinglePaymentOrderByPaymentId);
    }

    @PatchMapping("/proceed-payment")
    public ResponseEntity<Boolean> proceedPaymentProcess(@RequestParam String paymentId, @RequestParam String paymentLinkId) throws RazorpayException {

        Payment payment = paymentService.getSinglePaymentByPaymentId(paymentLinkId);
        Boolean res = paymentService.proceedPayment(payment, paymentId, paymentLinkId);
        return ResponseEntity.ok(res);
    }


}
