package com.example.Management_of_medical_appointments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_DOCTOR")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idDoctor;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialty;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Appointments> appointments;

    public UUID getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(UUID idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointments> appointments) {
        this.appointments = appointments;
    }
}