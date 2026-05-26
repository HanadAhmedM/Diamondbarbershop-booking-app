package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.entity.Appointment;
import com.Diamondbarberdhopapp.backend.entity.Barber;
import com.Diamondbarberdhopapp.backend.repository.AppointmentRepository;
import com.Diamondbarberdhopapp.backend.repository.BarberRepository;
import com.Diamondbarberdhopapp.backend.service.S3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final BarberRepository barberRepo;
    private final AppointmentRepository appointmentRepo;
    private final S3Service s3Service;

    public AdminController(
            BarberRepository barberRepo,
            AppointmentRepository appointmentRepo,
            S3Service s3Service
    ) {
        this.barberRepo = barberRepo;
        this.appointmentRepo = appointmentRepo;
        this.s3Service = s3Service;
    }

    // 💈 GET ALL BARBERS
    @GetMapping("/barbers")
    public List<Barber> getBarbers() {
        return barberRepo.findAll();
    }

    // ➕ CREATE BARBER WITH IMAGE
    @PostMapping("/barbers")
    public Barber createBarber(
            @RequestParam String name,
            @RequestParam String specialty,
            @RequestParam MultipartFile image
    ) {

        try {
            File file = convert(image);

            String imageUrl = s3Service.uploadFile(
                    image.getOriginalFilename(),
                    file
            );

            file.delete();

            Barber barber = new Barber();
            barber.setName(name);
            barber.setSpecialty(specialty);
            barber.setImageUrl(imageUrl);

            return barberRepo.save(barber);

        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    // ❌ DELETE BARBER
    @DeleteMapping("/barbers/{id}")
    public void deleteBarber(@PathVariable Long id) {
        barberRepo.deleteById(id);
    }

    // 📅 GET APPOINTMENTS
    @GetMapping("/appointments")
    public List<Appointment> getAppointments() {
        return appointmentRepo.findAll();
    }

    // ❌ DELETE APPOINTMENT
    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentRepo.deleteById(id);
    }

    // 🔥 helper method
    private File convert(MultipartFile file) throws Exception {

        File convFile = new File(file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }

        return convFile;
    }
}