package com.edts.edts_ticketing_system.repository;

import com.edts.edts_ticketing_system.model.TicketCategory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
    /**
     * Uses Pessimistic Write Lock (SELECT ... FOR UPDATE) to prevent race conditions
     * and overselling during high concurrency booking requests.
     * */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM TicketCategory t WHERE t.id = :id")
    Optional<TicketCategory> findByIdWithLock(@Param("id") Long id);
}
