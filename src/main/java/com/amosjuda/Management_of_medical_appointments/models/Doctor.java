package com.amosjuda.Management_of_medical_appointments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tb_doctor")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    public Doctor(){}

    public Doctor(UUID idDoctor, String name, String specialty, String email) {
        this.idDoctor = idDoctor;
        this.name = name;
        this.specialty = specialty;
        this.email = email;
    }

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

}