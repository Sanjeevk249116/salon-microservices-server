package com.payment.server.dto;

import com.payment.server.domain.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private PaymentMethod paymentMethod;
}
