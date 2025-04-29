package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto> saveDoctor(@Valid @RequestBody DoctorRequestDto dto){
        DoctorResponseDto saveTheDoctor = service.saveDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveTheDoctor);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getALLDoctors() {
       return ResponseEntity.ok(service.getALLDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getOneDoctor(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOneDoctorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable UUID id,
                                                          @Valid @RequestBody DoctorRequestDto dto){
        return ResponseEntity.ok(service.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> deleteDoctor(@PathVariable UUID id) {
        service.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}