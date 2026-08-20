package com.edts.edts_ticketing_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
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

    private String name;
    private BigDecimal price;
    private Integer totalQuota;
    private Integer availableQuota;

    // Defines the valid time window for ticket purchasing
    @Column(name = "booking_start_time")
    private LocalDateTime bookingStartTime;

    @Column(name = "booking_end_time")
    private LocalDateTime bookingEndTime;

}