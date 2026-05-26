package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.entity.Barber;
import com.Diamondbarberdhopapp.backend.repository.BarberRepository;
import com.Diamondbarberdhopapp.backend.service.S3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/admin/barbers")
public class BarberController {

    private final BarberRepository barberRepo;
    private final S3Service s3Service;

    public BarberController(BarberRepository barberRepo,
                             S3Service s3Service) {
        this.barberRepo = barberRepo;
        this.s3Service = s3Service;
    }

    // ======================
    // CREATE BARBER
    // ======================
    @PostMapping
    public Barber createBarber(
            @RequestParam("name") String name,
            @RequestParam("specialty") String specialty,
            @RequestParam("image") MultipartFile image
    ) {

        try {
            // convert MultipartFile -> File
            File file = convertMultiPartToFile(image);

            String fileName = image.getOriginalFilename();

            // upload to S3
            String imageUrl = s3Service.uploadFile(fileName, file);

            // cleanup temp file
            file.delete();

            Barber barber = new Barber();
            barber.setName(name);
            barber.setSpecialty(specialty);
            barber.setImageUrl(imageUrl);

            return barberRepo.save(barber);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create barber: " + e.getMessage());
        }
    }

    // ======================
    // GET ALL BARBERS
    // ======================
    @GetMapping
    public List<Barber> getAllBarbers() {
        return barberRepo.findAll();
    }

    // ======================
    // DELETE BARBER
    // ======================
    @DeleteMapping("/{id}")
    public void deleteBarber(@PathVariable Long id) {
        barberRepo.deleteById(id);
    }

    // ======================
    // HELPER METHOD
    // ======================
    private File convertMultiPartToFile(MultipartFile file) throws Exception {
        File convFile = new File(file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }

        return convFile;
    }
}