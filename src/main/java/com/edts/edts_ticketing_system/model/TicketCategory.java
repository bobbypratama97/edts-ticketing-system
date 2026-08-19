package com.edts.edts_ticketing_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TicketCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    private String categoryName;
    private Double price;
    private Integer totalQuota;
    private Integer availableQuota;

    // Defines the valid time window for ticket purchasing
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
}
