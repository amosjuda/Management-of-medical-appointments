package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.models.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveDoctor(Doctor Doctor);

    List<Doctor> getALLDoctors();

    Optional<Doctor> getOneDoctorById(Long id);

    Doctor updateDoctor(Long id, Doctor Doctor);

    void deleteDoctor(Long id);
}