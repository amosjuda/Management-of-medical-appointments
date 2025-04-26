package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.models.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient savePatient(Patient patient);

    List<Patient> getALLPatients();

    Optional<Patient> getOnePatientById(Long id);

    Patient updatePatient(Long id, Patient patient);

    void deletePatient(Long id);
}