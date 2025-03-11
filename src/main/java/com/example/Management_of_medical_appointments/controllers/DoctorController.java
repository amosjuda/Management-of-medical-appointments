package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.DoctorRecordDto;
import com.example.Management_of_medical_appointments.models.Doctor;
import com.example.Management_of_medical_appointments.repositories.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DoctorController {
    @Autowired
    DoctorRepository doctorRepository;

    @PostMapping("/doctor")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody @Valid DoctorRecordDto doctorRecordDto){
        var doctor = new Doctor();
        BeanUtils.copyProperties(doctorRecordDto, doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorRepository.save(doctor));
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<Doctor>> getALLDoctors() {
        return ResponseEntity.status(HttpStatus.OK).body(doctorRepository.findAll());
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<Object> getOneDoctor(@PathVariable(value="id") UUID id) {
        Optional<Doctor> doctorO = doctorRepository.findById(id);
        if(doctorO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(doctorO.get());
    }
}
