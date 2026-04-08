package com.booking.booking.server.service;

import com.booking.booking.server.domain.BookingStatus;
import com.booking.booking.server.dto.*;
import com.booking.booking.server.entity.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface BookingServices {
    BookingService createNewBookingService(BookingServiceDto newBookingServiceData, Long userId, SalonResponseDto salon, Set<ServiceOfferingResponseDto> serviceDtoSet);

    List<BookingService> getAllBookingByCustomer(Long customerId);

    List<BookingService> getAllBookingBySalon(Long salonId);

    BookingService readSingleBookingByBookingId(Long bookingId);

    BookingService updateSingleBookingServiceStatusByBookingId(Long bookingId, BookingStatus updateBookingStatus);

    List<BookingService> RealAllBookingServiceByDate(Long salonId, LocalDate date);

    SalonReport readSalonReport(Long salonId);
}
