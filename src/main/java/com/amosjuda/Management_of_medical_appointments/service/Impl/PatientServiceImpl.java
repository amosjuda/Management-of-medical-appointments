package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import com.amosjuda.Management_of_medical_appointments.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getALLPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getOnePatientById(UUID id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient updatePatient(UUID id, Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            Patient patientToUpdate = existingPatient.get();
            patientToUpdate.setName(patient.getName());
            patientToUpdate.setEmail(patient.getEmail());
            return patientRepository.save(patientToUpdate);
        } else {
            throw new RuntimeException("Query not found with id: " + id);
        }
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
