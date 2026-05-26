package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.entity.Barber;
import com.Diamondbarberdhopapp.backend.repository.BarberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/barbers")
@CrossOrigin(origins = "*")
public class AdminBarberController {

    private final BarberRepository repo;

    public AdminBarberController(BarberRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Barber create(@RequestBody Barber barber) {
        return repo.save(barber);
    }

    @GetMapping
    public List<Barber> getAll() {
        return repo.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}