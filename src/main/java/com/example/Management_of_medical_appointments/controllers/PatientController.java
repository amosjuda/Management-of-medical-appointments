package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.PatientRecordDto;
import com.example.Management_of_medical_appointments.models.Patient;
import com.example.Management_of_medical_appointments.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/patient")
    public ResponseEntity<Patient> savePatient(@RequestBody @Valid PatientRecordDto patientRecordDto){
        var patient = new Patient();
        BeanUtils.copyProperties(patientRecordDto, patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientRepository.save(patient));
    }

    @GetMapping("/patient")
    public ResponseEntity<List<Patient>> getALLPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientRepository.findAll());
    }
}
