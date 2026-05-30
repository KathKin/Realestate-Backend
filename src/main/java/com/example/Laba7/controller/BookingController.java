package com.example.Laba7.controller;

import com.example.Laba7.model.Booking;
import com.example.Laba7.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/client/{clientId}")
    public List<Booking> getClientBookings(@PathVariable Long clientId) {
        return bookingService.getBookingsByClient(clientId);
    }

    @GetMapping("/property/{propertyId}")
    public List<Booking> getPropertyBookings(@PathVariable Long propertyId) {
        return bookingService.getBookingsByProperty(propertyId);
    }

    @PutMapping("/{id}/confirm")
    public Booking confirmBooking(@PathVariable Long id) {
        return bookingService.confirmBooking(id);
    }

    @PutMapping("/{id}/cancel")
    public Booking cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}
