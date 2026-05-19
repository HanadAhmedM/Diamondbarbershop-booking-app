package com.Diamondbarberdhopapp.backend.service;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import com.Diamondbarberdhopapp.backend.entity.User;
import com.Diamondbarberdhopapp.backend.repository.AppointmentRepository;
import com.Diamondbarberdhopapp.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

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
    public List<Appointment> getAll() {
        return repo.findAll();
    }

    public void cancel(Long id) {
        repo.deleteById(id);
    }
}