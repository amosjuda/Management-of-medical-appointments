package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import com.amosjuda.Management_of_medical_appointments.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getALLDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getOneDoctorById(UUID id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor updateDoctor(UUID id, Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(id);
        if(existingDoctor.isPresent()){
            Doctor doctorToUpdate = existingDoctor.get();
            doctorToUpdate.setName(doctor.getName());
            doctorToUpdate.setSpecialty(doctor.getSpecialty());
            doctorToUpdate.setEmail(doctor.getEmail());
            return doctorRepository.save(doctorToUpdate);
        } else {
            throw new RuntimeException("Query not found with id: " + id);
        }
    }

    @Override
    public void deleteDoctor(UUID id) {
        doctorRepository.deleteById(id);
    }
}
