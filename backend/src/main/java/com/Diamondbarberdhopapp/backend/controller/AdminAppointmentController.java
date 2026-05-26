package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import com.Diamondbarberdhopapp.backend.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/appointments")
@CrossOrigin(origins = "*")
public class AdminAppointmentController {

    private final AppointmentRepository repo;

    public AdminAppointmentController(AppointmentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Appointment> getAll() {
        return repo.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}