package com.booking.booking.server.serviceHelper;

import com.booking.booking.server.dto.SalonResponseDto;
import com.booking.booking.server.entity.BookingService;
import com.booking.booking.server.globalExceptionHandler.CustomException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingServiceHelperFn {

    public Boolean checkTimeSlot(SalonResponseDto salon, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime, List<BookingService> existingBookings) {

        LocalDateTime salonOpenTime = salon.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salon.getCloseTime().atDate(bookingEndTime.toLocalDate());

        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new CustomException("Booking time must be within salon's working hours");
        }

        for (BookingService existingBooking : existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();
            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)) {
                throw new CustomException("Slot not available, choose different time slot");
            }
            if (bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)) {
                throw new CustomException("Slot not available, choose different time slot");
            }

        }


        return true;
    }

    public Boolean isSameDate(LocalDateTime dateTime, LocalDate date) {

        return dateTime.toLocalDate().isEqual(date);
    }
}
