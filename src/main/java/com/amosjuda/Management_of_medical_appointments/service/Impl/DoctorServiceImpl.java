package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.DoctorMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.request.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import com.amosjuda.Management_of_medical_appointments.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper mapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper mapper) {
        this.doctorRepository = doctorRepository;
        this.mapper = mapper;
    }

    @Override
    public DoctorResponseDto saveDoctor(DoctorRequestDto dto) {
        Doctor doctor = mapper.toEntity(dto);
        return mapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public List<DoctorResponseDto> getALLDoctors() {
        return doctorRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public DoctorResponseDto getOneDoctorById(UUID id) {
        return doctorRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Override
    public DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

            doctor.setName(doctor.getName());
            doctor.setSpecialty(doctor.getSpecialty());
            doctor.setEmail(doctor.getEmail());

            return mapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public void deleteDoctor(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
