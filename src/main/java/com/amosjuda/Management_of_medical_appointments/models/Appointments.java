package com.amosjuda.Management_of_medical_appointments.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAppointments;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonManagedReference
    private Patient patient;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonManagedReference
    private Doctor doctor;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Getter
    @Setter
    private String notes;

}
