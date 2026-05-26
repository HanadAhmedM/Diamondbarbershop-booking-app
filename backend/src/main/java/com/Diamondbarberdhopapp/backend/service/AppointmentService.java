package com.Diamondbarberdhopapp.backend.service;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import com.Diamondbarberdhopapp.backend.entity.User;
import com.Diamondbarberdhopapp.backend.repository.AppointmentRepository;
import com.Diamondbarberdhopapp.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final UserRepository userRepo;

    public AppointmentService(AppointmentRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // 💈 BOOK APPOINTMENT (FIXED & SAFE)
    public Appointment book(Appointment appointment, String userEmail) {

        User user = userRepo.findByEmail(userEmail);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Long barberId = appointment.getBarber().getId();
        LocalDateTime time = appointment.getDateTime();

        boolean exists = repo.findByBarberIdAndDateTime(barberId, time).isPresent();

        if (exists) {
            throw new RuntimeException("This time slot is already booked");
        }

        appointment.setUser(user);
        appointment.setStatus("BOOKED");

        return repo.save(appointment);
    }

    // 📅 GET ALL
    public List<Appointment> getAll() {
        return repo.findAll();
    }

    // ❌ CANCEL
    public void cancel(Long id) {
        repo.deleteById(id);
    }

    // ⏰ GET BOOKED TIMES (FIXED SAFE VERSION)
    public List<String> getBookedTimes(Long barberId, String date) {

        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.plusDays(1).atStartOfDay();

        List<Appointment> appointments =
                repo.findByBarberIdAndDateTimeBetween(barberId, start, end);

        return appointments.stream()
                .map(a -> a.getDateTime().toLocalTime().toString().substring(0, 5))
                .toList();
    }
}