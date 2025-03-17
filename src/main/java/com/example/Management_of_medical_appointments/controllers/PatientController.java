package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.PatientRecordDto;
import com.example.Management_of_medical_appointments.models.Patient;
import com.example.Management_of_medical_appointments.repositories.PatientRepository;
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

    @GetMapping("/patient/{id}")
    public ResponseEntity<Object> getOnePatient(@PathVariable(value="id") UUID id) {
        Optional<Patient> patientO = patientRepository.findById(id);
        if(patientO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(patientO.get());
    }
}
