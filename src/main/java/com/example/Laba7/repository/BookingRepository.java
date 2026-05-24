package com.example.Laba7.repository;

import com.example.Laba7.model.Booking;
import com.example.Laba7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByClient(User client);
    List<Booking> findByPropertyId(Long propertyId);
    List<Booking> findByStatus(String status);
}