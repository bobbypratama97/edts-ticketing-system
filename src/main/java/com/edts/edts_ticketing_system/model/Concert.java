package com.edts.edts_ticketing_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "concerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    private String artist;
    private String location;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)

    private List<TicketCategory> ticketCategories;
}
