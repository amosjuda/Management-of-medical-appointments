package com.amosjuda.Management_of_medical_appointments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tb_patient")
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    public Patient() {}

    public Patient(UUID idPatient, String name, String email, String phone, LocalDate birthdate) {
        this.idPatient = idPatient;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPatient;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Appointments> appointments;
}