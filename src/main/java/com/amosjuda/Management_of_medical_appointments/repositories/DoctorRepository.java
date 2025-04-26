package com.amosjuda.Management_of_medical_appointments.repositories;

import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}