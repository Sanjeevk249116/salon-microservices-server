package com.booking.booking.server.service.impl;

import com.booking.booking.server.dto.*;
import com.booking.booking.server.entity.BookingService;
import com.booking.booking.server.service.BookingServices;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingServices {
    @Override
    public BookingService createNewBookingService(BookingServiceDto newBookingServiceData, UserDto user, SalonDto salon, Set<ServiceOfferDto> serviceDtoSet) {
        return null;
    }

    @Override
    public List<BookingService> getAllBookingByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public List<BookingService> getAllBookingBySalon(Long salonId) {
        return List.of();
    }

    @Override
    public BookingService readSingleBookingByBookingId(Long bookingId) {
        return null;
    }

    @Override
    public BookingService updateSingleBookingServiceStatusByBookingId(Long bookingId, BookingServiceDto updateBookingStatus) {
        return null;
    }

    @Override
    public List<BookingService> RealAllBookingServiceByDate(Long salonId, LocalDate date) {
        return List.of();
    }

    @Override
    public SalonReport readSalonReport(Long salonId) {
        return null;
    }
}
