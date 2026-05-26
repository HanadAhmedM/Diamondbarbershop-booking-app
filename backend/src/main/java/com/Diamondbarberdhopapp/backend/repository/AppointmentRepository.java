package com.Diamondbarberdhopapp.backend.repository;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByBarberIdAndDateTime(Long barberId, LocalDateTime dateTime);

    // 💈 for booked times (IMPORTANT FIX)
    @Query("SELECT a FROM Appointment a WHERE a.barber.id = :barberId AND DATE(a.dateTime) = :date")
    List<Appointment> findByBarberIdAndDateTimeBetween(
        Long barberId,
        LocalDateTime start,
        LocalDateTime end
);
}