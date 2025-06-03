package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.dtos.request.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.PatientResponseDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    PatientResponseDto savePatient(PatientRequestDto dto);

    List<PatientResponseDto> getALLPatients();

    PatientResponseDto getOnePatientById(UUID id);

    PatientResponseDto updatePatient(UUID id, PatientRequestDto dto);

    void deletePatient(UUID id);
}