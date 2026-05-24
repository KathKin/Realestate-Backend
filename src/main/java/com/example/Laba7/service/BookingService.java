package com.example.Laba7.service;

import com.example.Laba7.model.Booking;
import com.example.Laba7.model.User;
import com.example.Laba7.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Booking booking) {
        booking.setStatus("PENDING");
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByClient(Long clientId) {
        User client = userService.findById(clientId);
        return bookingRepository.findByClient(client);
    }

    public List<Booking> getBookingsByProperty(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }
}