package com.edts.edts_ticketing_system.service;

import com.edts.edts_ticketing_system.model.Booking;
import com.edts.edts_ticketing_system.model.TicketCategory;
import com.edts.edts_ticketing_system.repository.BookingRepository;
import com.edts.edts_ticketing_system.repository.TicketCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.edts.edts_ticketing_system.dto.BookingResponse;

@Service
public class BookingService {
    private final TicketCategoryRepository ticketCategoryRepository;
    private final BookingRepository bookingRepository;

    public BookingService(TicketCategoryRepository ticketCategoryRepository, BookingRepository bookingRepository) {
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Handles ticket booking with pessimistic locking to prevent race conditions and overselling.
     */

    @Transactional
    public Booking bookTicket(Long ticketCategoryId, String userId, Integer quantity) {

        // 1. Fetch ticket category
        TicketCategory category = ticketCategoryRepository.findByIdWithLock(ticketCategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket category not found"));

        // 2. Validate booking time window
        LocalDateTime now = LocalDateTime.now();
        if (category.getBookingStartTime() != null && now.isBefore(category.getBookingStartTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking has not started yet");
        }
        if (category.getBookingEndTime() != null && now.isAfter(category.getBookingEndTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking window has closed");
        }

        // 3. Validate available quota
        if (category.getAvailableQuota() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient ticket quota available");
        }

        // 4. Deduct quota
        category.setAvailableQuota(category.getAvailableQuota() - quantity);
        ticketCategoryRepository.save(category);

        // 5. Create and save booking record
        Booking booking = Booking.builder()
                .userId(userId)
                .ticketCategory(category)
                .quantity(quantity)
                .bookingTime(now)
                .status("SUCCESS")
                .build();

        return bookingRepository.save(booking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getId())
                        .userId(booking.getUserId())
                        .concertName(booking.getTicketCategory().getConcert().getName())
                        .categoryName(booking.getTicketCategory().getName())
                        .quantity(booking.getQuantity())
                        .bookingTime(booking.getBookingTime())
                        .status(booking.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
