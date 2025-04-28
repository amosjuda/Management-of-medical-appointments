package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.models.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorService {
    Doctor saveDoctor(Doctor Doctor);

    List<Doctor> getALLDoctors();

    Optional<Doctor> getOneDoctorById(UUID id);

    Doctor updateDoctor(UUID id, Doctor Doctor);

    void deleteDoctor(UUID id);
}