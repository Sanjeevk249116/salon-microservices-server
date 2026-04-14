package com.booking.booking.server.dto;

import lombok.Data;

@Data
public class BookingDto {
    private Long id;
    private int totalPrice;
    private Long salonId;
}
