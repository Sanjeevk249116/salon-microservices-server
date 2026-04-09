package com.payment.server.dto;

import lombok.Data;

@Data
public class BookingDto {
    private Long id;
    private int totalPrice;
    private Long salonId;
}
