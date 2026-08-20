-- Seed Concerts
INSERT INTO concerts (id, name, artist, location, concert_date)
VALUES (1, 'Coldplay Music of the Spheres World Tour', 'Coldplay', 'Gelora Bung Karno, Jakarta', '2026-11-15 19:30:00');

INSERT INTO concerts (id, name, artist, location, concert_date)
VALUES (2, 'aespa LIVE TOUR - SYNK : COMPLEXITY', 'aespa', 'ICE BSD Hall 5-6, Tangerang', '2026-08-24 18:30:00');

-- Seed Ticket Categories for Coldplay (Concert ID 1)
INSERT INTO ticket_categories (id, concert_id, name, price, total_quota, available_quota, booking_start_time, booking_end_time)
VALUES (1, 1, 'CAT 1 - VIP', 5000000.00, 100, 100, '2026-01-01 00:00:00', '2026-12-31 23:59:59');

INSERT INTO ticket_categories (id, concert_id, name, price, total_quota, available_quota, booking_start_time, booking_end_time)
VALUES (2, 1, 'CAT 2 - Festival', 2500000.00, 500, 500, '2026-01-01 00:00:00', '2026-12-31 23:59:59');

-- Seed Ticket Categories for aespa (Concert ID 2)
INSERT INTO ticket_categories (id, concert_id, name, price, total_quota, available_quota, booking_start_time, booking_end_time)
VALUES (3, 2, 'VIP Standing (Soundcheck)', 3200000.00, 10000, 10000, '2026-01-01 00:00:00', '2026-12-31 23:59:59');

INSERT INTO ticket_categories (id, concert_id, name, price, total_quota, available_quota, booking_start_time, booking_end_time)
VALUES (4, 2, 'CAT 1 Seated', 2100000.00, 200, 200, '2026-01-01 00:00:00', '2026-12-31 23:59:59');