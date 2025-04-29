package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.PatientMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.PatientResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import com.amosjuda.Management_of_medical_appointments.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper mapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Override
    public PatientResponseDto savePatient(PatientRequestDto dto) {
        Patient patient = mapper.toEntity(dto);
        return mapper.toDto(patientRepository.save(patient));
    }

    @Override
    public List<PatientResponseDto> getALLPatients(){
        return patientRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public PatientResponseDto getOnePatientById(UUID id) {
        return patientRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @Override
    public PatientResponseDto updatePatient(UUID id, PatientRequestDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        patient.setBirthdate(dto.getBirthdate());

        return mapper.toDto(patientRepository.save(patient));
    }

    @Override
    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}
