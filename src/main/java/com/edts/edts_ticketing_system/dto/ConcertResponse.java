package com.edts.edts_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ConcertResponse {
    private Long id;
    private String name;
    private String artist;
    private String venue;
    private LocalDateTime concertDate;
    private List<TicketCategoryDto> ticketCategories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketCategoryDto {
        private Long id;
        private String name;
        private BigDecimal price;
        private Integer availableQuota;
        private LocalDateTime bookingStartTime;
        private LocalDateTime bookingEndTime;
    }
}
