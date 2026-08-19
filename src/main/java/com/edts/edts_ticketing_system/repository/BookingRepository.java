package com.edts.edts_ticketing_system.repository;

import com.edts.edts_ticketing_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
