package com.edts.edts_ticketing_system.service;

import com.edts.edts_ticketing_system.model.TicketCategory;
import com.edts.edts_ticketing_system.repository.TicketCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookingServiceConcurrencyTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;

    @Test
    @DisplayName("Should handle concurrent booking requests without race conditions")
    void testConcurrentBookingSuccessAndQuotaLimit() throws InterruptedException {
        Long ticketCategoryId = 3L;
        int numberOfThreads = 100000;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(1);

        AtomicInteger successfulBookings = new AtomicInteger(0);
        AtomicInteger failedBookings = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final String userId = "user-" + i;
            executorService.execute(() -> {
                try {
                    latch.await(); // Hold execution until all threads are ready to launch simultaneously
                    bookingService.bookTicket(ticketCategoryId, userId, 1);
                    successfulBookings.incrementAndGet();
                } catch (Exception e) {
                    failedBookings.incrementAndGet(); // Expected failure once available quota is exhausted
                }
            });
        }

        latch.countDown(); // Release the latch to initiate concurrent execution across all threads
        executorService.shutdown();
        boolean completedInTime = executorService.awaitTermination(60, TimeUnit.SECONDS);
        assertTrue(completedInTime, "All booking threads should finish within the timeout");

        TicketCategory updatedCategory = ticketCategoryRepository.findById(ticketCategoryId).orElseThrow();

        // Verification: Out of 100,000 requests for 10,000 tickets, exactly 10,000 must succeed, the rest must fail
        assertEquals(10000, successfulBookings.get(), "Exactly 10,000 bookings should be successful");
        assertEquals(90000, failedBookings.get(), "Exactly 90,000 bookings should fail due to quota exhaustion");
        assertEquals(0, updatedCategory.getAvailableQuota(), "Remaining available quota in DB must be exactly 0");
    }

    @Test
    @DisplayName("Should reject booking attempts outside the designated time window")
    void testBookingOutsideTimeWindowShouldFail() {
        // Retrieve valid ticket category instance from database
        Long ticketCategoryId = 1L;
        TicketCategory category = ticketCategoryRepository.findById(ticketCategoryId).orElseThrow();

        // Temporarily shift the booking window to simulate an expired window scenario
        category.setBookingStartTime(LocalDateTime.now().minusHours(2));
        category.setBookingEndTime(LocalDateTime.now().minusHours(1));
        ticketCategoryRepository.save(category);

        // Assert that initiating a transaction outside the active window throws an exception
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> bookingService.bookTicket(ticketCategoryId, "user-expired-test", 1));

        assertEquals("Booking window has closed", exception.getMessage());
    }
}
