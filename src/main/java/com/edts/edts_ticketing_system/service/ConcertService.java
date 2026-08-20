package com.edts.edts_ticketing_system.service;

import com.edts.edts_ticketing_system.dto.ConcertResponse;
import com.edts.edts_ticketing_system.model.Concert;
import com.edts.edts_ticketing_system.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<ConcertResponse> searchConcerts(String keyword) {
        List<Concert> concerts;
        if (keyword != null && !keyword.trim().isEmpty()) {
            concerts = concertRepository.findByNameContainingIgnoreCaseOrArtistContainingIgnoreCase(keyword, keyword);
        } else {
            concerts = concertRepository.findAll();
        }

        return concerts.stream()
                .map(this::mapToConcertResponse)
                .collect(Collectors.toList());
    }

    private ConcertResponse mapToConcertResponse(Concert concert) {
        List<ConcertResponse.TicketCategoryDto> categories = concert.getTicketCategories() == null ? List.of() :
                concert.getTicketCategories().stream()
                        .map(cat -> ConcertResponse.TicketCategoryDto.builder()
                                .id(cat.getId())
                                .name(cat.getName())
                                .price(cat.getPrice())
                                .availableQuota(cat.getAvailableQuota())
                                .bookingStartTime(cat.getBookingStartTime())
                                .bookingEndTime(cat.getBookingEndTime())
                                .build())
                        .collect(Collectors.toList());

        return ConcertResponse.builder()
                .id(concert.getId())
                .name(concert.getName())
                .artist(concert.getArtist())
                .venue(concert.getLocation())
                .concertDate(concert.getConcertDate())
                .ticketCategories(categories)
                .build();
    }
}