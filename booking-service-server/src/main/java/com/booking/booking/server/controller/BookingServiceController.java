package com.booking.booking.server.controller;

import com.booking.booking.server.domain.BookingStatus;
import com.booking.booking.server.dto.*;
import com.booking.booking.server.entity.BookingService;
import com.booking.booking.server.service.BookingServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookig-service")
public class BookingServiceController {

    private final BookingServices bookingService;

    @PostMapping("/create-new")
    public ResponseEntity<BookingService> createNewServiceBooking(@RequestParam Long salonId, @RequestBody BookingServiceDto newBookingServiceData) {
        UserDto user = new UserDto();
        user.setId(1L);
        SalonDto salon = new SalonDto();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setCloseTime(LocalTime.now().plusHours(12));

        Set<ServiceOfferDto> serviceDtoSet = new HashSet<>();
        ServiceOfferDto serviceDto = new ServiceOfferDto();

        serviceDto.setId(1L);
        serviceDto.setPrice(399);
        serviceDto.setDuration(45);
        serviceDto.setServiceOfferingName("Hair cut for men");
        serviceDto.setDescription("Hair cutting");

        serviceDtoSet.add(serviceDto);

        BookingService createdService = bookingService.createNewBookingService(newBookingServiceData, user, salon, serviceDtoSet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }

    @GetMapping("/read/list-customer")
    public ResponseEntity<List<BookingService>> getBookingByCustomer() {
        UserDto user = new UserDto();
        user.setId(1L);
        List<BookingService> getALlBookingByCustomer = bookingService.getAllBookingByCustomer(user.getId());
        return ResponseEntity.ok(getALlBookingByCustomer);
    }

    @GetMapping("/read/list-salon/{salonId}")
    public ResponseEntity<List<BookingService>> getBookingBySalon(@PathVariable Long salonId) {
        List<BookingService> getALlBookingBySalon = bookingService.getAllBookingBySalon(salonId);
        return ResponseEntity.ok(getALlBookingBySalon);
    }

    @GetMapping("/read/single-booking/{bookingId}")
    public ResponseEntity<BookingService> readSingleBookingById(@PathVariable Long bookingId) {
        BookingService readSingleBooking = bookingService.readSingleBookingByBookingId(bookingId);
        return ResponseEntity.ok(readSingleBooking);
    }

    @PutMapping("/update/single-booking/{bookingId}")
    public ResponseEntity<BookingService> updateSingleBookingById(@PathVariable Long bookingId, @RequestBody BookingStatus updateBookingStatus) {
        BookingService updatedBookingService = bookingService.updateSingleBookingServiceStatusByBookingId(bookingId, updateBookingStatus);
        return ResponseEntity.ok(updatedBookingService);
    }

    @GetMapping("/read/list-byDate/{salonId}")
    public ResponseEntity<List<BookingService>> readAllListOfSalonByDate(@PathVariable Long salonId, @RequestBody LocalDate date) {
        List<BookingService> readAllSalonByDate = bookingService.RealAllBookingServiceByDate(salonId, date);
        return ResponseEntity.ok(readAllSalonByDate);
    }

    @GetMapping("/read/salon-report/{salonId}")
    public ResponseEntity<SalonReport> readSalonReport(@PathVariable Long salonId) {
        SalonReport salonReport = bookingService.readSalonReport(salonId);
        return ResponseEntity.ok(salonReport);
    }
}
