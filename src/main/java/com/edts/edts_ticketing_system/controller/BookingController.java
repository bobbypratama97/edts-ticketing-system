package com.edts.edts_ticketing_system.controller;

import com.edts.edts_ticketing_system.dto.BookingRequest;
import com.edts.edts_ticketing_system.dto.BookingResponse;
import com.edts.edts_ticketing_system.model.Booking;
import com.edts.edts_ticketing_system.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookTicket(@RequestBody BookingRequest request) {
        Booking booking = bookingService.bookTicket(
                request.getTicketCategoryId(),
                request.getUserId(),
                request.getQuantity()
        );

        BookingResponse response = BookingResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .concertName(booking.getTicketCategory().getConcert().getName())
                .categoryName(booking.getTicketCategory().getName())
                .quantity(booking.getQuantity())
                .bookingTime(booking.getBookingTime())
                .status(booking.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> response = bookingService.getAllBookings();
        return ResponseEntity.ok(response);
    }
}
