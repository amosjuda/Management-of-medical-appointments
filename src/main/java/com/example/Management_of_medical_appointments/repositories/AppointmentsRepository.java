package com.example.Management_of_medical_appointments.repositories;

import com.example.Management_of_medical_appointments.models.Appointments;
import com.example.Management_of_medical_appointments.models.Doctor;
import com.example.Management_of_medical_appointments.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, UUID> {
    Optional<Appointments> findByDateTimeAndPatientAndDoctor(LocalDateTime dateTime, Patient patient, Doctor doctor);
}
