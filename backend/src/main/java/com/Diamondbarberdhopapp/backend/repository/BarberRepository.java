package com.Diamondbarberdhopapp.backend.repository;


import com.Diamondbarberdhopapp.backend.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
}