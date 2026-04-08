package com.booking.booking.server.service.impl;

import com.booking.booking.server.domain.BookingStatus;
import com.booking.booking.server.dto.*;
import com.booking.booking.server.entity.BookingService;
import com.booking.booking.server.globalExceptionHandler.CustomException;
import com.booking.booking.server.repository.ServiceBookingRepository;
import com.booking.booking.server.service.BookingServices;
import com.booking.booking.server.serviceHelper.BookingServiceHelperFn;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingServices {

    private final ServiceBookingRepository serviceBookingRepository;
    private final ModelMapper modelMapper;
    private final BookingServiceHelperFn bookingServiceHelperFn;


    @Override
    public BookingService createNewBookingService(BookingServiceDto newBookingServiceData, Long userId, SalonDto salon, Set<ServiceOfferDto> serviceDtoSet) {
        int totalDuration = serviceDtoSet.stream().mapToInt(ServiceOfferDto::getDuration).sum();

        LocalDateTime bookingStartTime = newBookingServiceData.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);
        List<BookingService> existingBooking = getAllBookingBySalon(salon.getId());

        Boolean isSlotAvailable = bookingServiceHelperFn.checkTimeSlot(salon, bookingStartTime, bookingEndTime, existingBooking);

        int totalPrice = serviceDtoSet.stream().mapToInt(ServiceOfferDto::getPrice).sum();
        Set<Long> idList = serviceDtoSet.stream().map(ServiceOfferDto::getId).collect(Collectors.toSet());

        BookingService newBookingService = new BookingService();
        newBookingService.setCustomerId(userId);
        newBookingService.setSalonId(salon.getId());
        newBookingService.setServicesIds(idList);
        newBookingService.setBookingStatus(BookingStatus.PENDING);
        newBookingService.setStartTime(bookingStartTime);
        newBookingService.setEndTime(bookingEndTime);
        newBookingService.setTotalPrice(totalPrice);

        return serviceBookingRepository.save(newBookingService);
    }

    @Override
    public List<BookingService> getAllBookingByCustomer(Long customerId) {
        return serviceBookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<BookingService> getAllBookingBySalon(Long salonId) {
        return serviceBookingRepository.findBySalonId(salonId);
    }

    @Override
    public BookingService readSingleBookingByBookingId(Long bookingId) {
        return serviceBookingRepository.findById(bookingId).orElseThrow(() -> new CustomException("Booking not found."));
    }

    @Override
    public BookingService updateSingleBookingServiceStatusByBookingId(Long bookingId, BookingStatus updateBookingStatus) {
        BookingService bookingDetail = serviceBookingRepository.findById(bookingId).orElseThrow(() -> new CustomException("Booking not found"));
        bookingDetail.setBookingStatus(updateBookingStatus);
        return serviceBookingRepository.save(bookingDetail);
    }

    @Override
    public List<BookingService> RealAllBookingServiceByDate(Long salonId, LocalDate date) {
        List<BookingService> allBookingList = getAllBookingBySalon(salonId);
        if (date == null) {
            return allBookingList;
        }

        return allBookingList.stream().filter((item) -> {
            return bookingServiceHelperFn.isSameDate(item.getStartTime(), date) || bookingServiceHelperFn.isSameDate(item.getEndTime(), date);
        }).toList();
    }


    @Override
    public SalonReport readSalonReport(Long salonId) {
        List<BookingService> bookingsList = getAllBookingBySalon(salonId);
        Double totalEarnings = bookingsList.stream().mapToDouble(BookingService::getTotalPrice).sum();

        Integer totalBooking = bookingsList.size();
        List<BookingService> cancelledBooking = bookingsList.stream().filter(item -> item.getBookingStatus().equals(BookingStatus.CANCELLED)).toList();
        Double totalRefund = cancelledBooking.stream().mapToDouble(BookingService::getTotalPrice).sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setTotalBookings(totalBooking);
        salonReport.setCancelledBookings(cancelledBooking.size());
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setTotalRefunds(totalRefund);
        salonReport.setSalonName("Not available for now.");

        return salonReport;
    }

}
