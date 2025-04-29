package com.amosjuda.Management_of_medical_appointments.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tb_appointments")
public class Appointments implements Serializable {
    private static final long serialVersionUID = 1L;

    public Appointments(){}

    public Appointments(UUID idAppointments, LocalDateTime dateTime, Patient patient, Doctor doctor, AppointmentStatus status, String notes) {
        this.idAppointments = idAppointments;
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.notes = notes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAppointments;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonManagedReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonManagedReference
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    private String notes;

}
