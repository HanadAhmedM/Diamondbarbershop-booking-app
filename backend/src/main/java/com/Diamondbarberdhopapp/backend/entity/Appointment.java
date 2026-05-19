package com.Diamondbarberdhopapp.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Setter
    @Getter
    private String customerName;

    @Setter
    @Getter
    private String customerEmail;

    @Setter
    @Getter
    private LocalDateTime dateTime;

    @Setter
    @Getter
    private String status;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;


}