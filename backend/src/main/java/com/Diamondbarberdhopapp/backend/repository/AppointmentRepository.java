package com.Diamondbarberdhopapp.backend.repository;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByBarberIdAndDateTime(Long barberId, LocalDateTime dateTime);
}