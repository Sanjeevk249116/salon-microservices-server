package com.booking.booking.server.controller;

import com.booking.booking.server.domain.BookingStatus;
import com.booking.booking.server.dto.*;
import com.booking.booking.server.entity.BookingService;
import com.booking.booking.server.service.BookingServices;
import com.booking.booking.server.service.client.GetPaymentDetailClient;
import com.booking.booking.server.service.client.GetSalonDetailClient;
import com.booking.booking.server.service.client.ServiceOfferingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking-service")
public class BookingServiceController {

    private final BookingServices bookingService;
    private final GetSalonDetailClient getSalonDetailClient;
    private final ServiceOfferingClient serviceOfferingClient;
    private final GetPaymentDetailClient getPaymentDetailClient;

    @PostMapping("/create-new/{salonId}")
    @Transactional
    public ResponseEntity<PaymentLinkResponse> createNewServiceBooking(@PathVariable Long salonId,
                                                                  @RequestBody BookingServiceDto newBookingServiceData,
                                                                  @RequestParam PaymentMethod paymentMethod,
                                                                  @AuthenticationPrincipal Jwt jwt) {

        SalonResponseDto salonResponseDto = getSalonDetailClient.readSingleSalon(salonId,
                "Bearer " + jwt.getTokenValue()).getBody();

        Set<ServiceOfferingResponseDto> serviceOfferingResponseDtos =
                serviceOfferingClient.readAllServiceOfferByIds(newBookingServiceData.getServicesIds(),
                        "Bearer " + jwt.getTokenValue()).getBody();

        BookingService createdService = bookingService
                .createNewBookingService(newBookingServiceData, jwt.getClaim("userId"),
                        salonResponseDto,
                        serviceOfferingResponseDtos);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(createdService.getId());
        bookingDto.setSalonId(createdService.getSalonId());
        bookingDto.setTotalPrice(createdService.getTotalPrice());

        PaymentLinkResponse res = getPaymentDetailClient.createOrderAndGetPaymentLink(bookingDto, paymentMethod, "Bearer " + jwt.getTokenValue()).getBody();

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/owner/read/list-customer")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<BookingService>> getBookingByCustomer(@AuthenticationPrincipal Jwt jwt) {

        List<BookingService> getALlBookingByCustomer = bookingService.getAllBookingByCustomer(jwt.getClaim("userId"));
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

    @PutMapping("/owner/update/single-booking/{bookingId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<BookingService> updateSingleBookingById(@PathVariable Long bookingId, @RequestBody BookingStatus updateBookingStatus) {
        BookingService updatedBookingService = bookingService.updateSingleBookingServiceStatusByBookingId(bookingId, updateBookingStatus);
        return ResponseEntity.ok(updatedBookingService);
    }

    @GetMapping("/read/list-byDate/{salonId}")
    public ResponseEntity<List<BookingService>> readAllListOfSalonByDate(@PathVariable Long salonId, @RequestBody LocalDate date) {
        List<BookingService> readAllSalonByDate = bookingService.RealAllBookingServiceByDate(salonId, date);
        return ResponseEntity.ok(readAllSalonByDate);
    }

    @GetMapping("/owner/read/salon-report/{salonId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<SalonReport> readSalonReport(@PathVariable Long salonId) {
        SalonReport salonReport = bookingService.readSalonReport(salonId);
        return ResponseEntity.ok(salonReport);
    }
}
