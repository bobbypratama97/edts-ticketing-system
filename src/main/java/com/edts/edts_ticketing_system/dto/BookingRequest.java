package com.edts.edts_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookingRequest {
    @Schema(example = "3")
    private Long ticketCategoryId;

    @Schema(example = "bobbypratama97")
    private String userId;

    @Schema(example = "1")
    private Integer quantity;
}
