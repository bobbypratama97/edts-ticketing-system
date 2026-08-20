package com.edts.edts_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookingRequest {
    private Long ticketCategoryId;
    private String userId;
    private Integer quantity;
}
