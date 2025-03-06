package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.DoctorRecordDto;
import com.example.Management_of_medical_appointments.models.Doctor;
import com.example.Management_of_medical_appointments.repositories.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
