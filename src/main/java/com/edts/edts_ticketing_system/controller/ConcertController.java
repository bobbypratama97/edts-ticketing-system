package com.edts.edts_ticketing_system.controller;

import com.edts.edts_ticketing_system.dto.ConcertResponse;
import com.edts.edts_ticketing_system.service.ConcertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")

public class ConcertController {
    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping
    public ResponseEntity<List<ConcertResponse>> searchConcerts(
            @RequestParam(required = false) String keyword) {
        List<ConcertResponse> response = concertService.searchConcerts(keyword);
        return ResponseEntity.ok(response);
    }
}
