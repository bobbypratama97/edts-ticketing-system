package com.edts.edts_ticketing_system.repository;

import com.edts.edts_ticketing_system.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByNameContainingIgnoreCaseOrArtistContainingIgnoreCase(String name, String artist);
}