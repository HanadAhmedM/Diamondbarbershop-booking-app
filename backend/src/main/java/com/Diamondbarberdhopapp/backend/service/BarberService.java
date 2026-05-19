package com.Diamondbarberdhopapp.backend.service;

import com.Diamondbarberdhopapp.backend.entity.Barber;
import com.Diamondbarberdhopapp.backend.repository.BarberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberService {

    private final BarberRepository repo;

    public BarberService(BarberRepository repo) {
        this.repo = repo;
    }

    public Barber create(Barber barber) {
        return repo.save(barber);
    }

    public List<Barber> getAll() {
        return repo.findAll();
    }

    public Barber getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Barber update(Long id, Barber updatedBarber) {
        return repo.findById(id)
                .map(barber -> {
                    barber.setName(updatedBarber.getName());
                    barber.setSpecialty(updatedBarber.getSpecialty());
                    barber.setImageUrl(updatedBarber.getImageUrl());
                    return repo.save(barber);
                })
                .orElse(null);
    }
}