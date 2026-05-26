package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import com.Diamondbarberdhopapp.backend.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // 💈 BOOK
    @PostMapping
    public Appointment book(@RequestBody Appointment appointment,
                            HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");
        return service.book(appointment, email);
    }

    // 📅 GET ALL
    @GetMapping
    public List<Appointment> getAll() {
        return service.getAll();
    }

    // ⏰ BOOKED TIMES (FOR FRONTEND)
    @GetMapping("/booked")
    public List<String> getBookedTimes(
            @RequestParam Long barberId,
            @RequestParam String date) {
    
        return service.getBookedTimes(barberId, date);
    }

    // ❌ CANCEL
    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        service.cancel(id);
    }
}