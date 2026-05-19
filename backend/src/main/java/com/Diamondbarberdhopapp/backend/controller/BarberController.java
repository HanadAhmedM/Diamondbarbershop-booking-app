package com.Diamondbarberdhopapp.backend.controller;

import com.Diamondbarberdhopapp.backend.entity.Barber;
import com.Diamondbarberdhopapp.backend.repository.BarberRepository;
import com.Diamondbarberdhopapp.backend.service.BarberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/barbers")
public class BarberController {

    private final BarberService service;

    public BarberController(BarberService service) {
        this.service = service;
    }

    @GetMapping
    public List<Barber> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Barber create(@RequestBody Barber barber) {
        return service.create(barber);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public Barber update(@PathVariable Long id,
                         @RequestBody Barber updatedBarber) {
        return service.update(id, updatedBarber);
    }
}