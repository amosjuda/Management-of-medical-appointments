package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.PatientResponseDto;
import com.amosjuda.Management_of_medical_appointments.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> savePatient(@Valid @RequestBody PatientRequestDto dto){
        PatientResponseDto saveThePatient = service.savePatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveThePatient);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getALLPatients() {
        return ResponseEntity.ok(service.getALLPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getOnePatient(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOnePatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
                                                            @Valid @RequestBody PatientRequestDto dto){
        return ResponseEntity.ok(service.updatePatient(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}