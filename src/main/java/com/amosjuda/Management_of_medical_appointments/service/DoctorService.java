package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.dtos.request.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.DoctorResponseDto;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorResponseDto saveDoctor(DoctorRequestDto dto);

    List<DoctorResponseDto> getALLDoctors();

    DoctorResponseDto getOneDoctorById(UUID id);

    DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto dto);

    void deleteDoctor(UUID id);
}