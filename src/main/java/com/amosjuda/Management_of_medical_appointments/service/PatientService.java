package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.models.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientService {
    Patient savePatient(Patient patient);

    List<Patient> getALLPatients();

    Optional<Patient> getOnePatientById(UUID id);

    Patient updatePatient(UUID id, Patient patient);

    void deletePatient(UUID id);
}