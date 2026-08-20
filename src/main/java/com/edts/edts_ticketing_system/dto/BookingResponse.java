package com.edts.edts_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookingResponse {
    private Long bookingId;
    private String userId;
    private String concertName;
    private String categoryName;
    private Integer quantity;
    private LocalDateTime bookingTime;
    private String status;
}
