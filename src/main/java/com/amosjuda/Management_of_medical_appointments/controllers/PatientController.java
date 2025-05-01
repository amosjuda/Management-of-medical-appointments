package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.dtos.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.PatientResponseDto;
import com.amosjuda.Management_of_medical_appointments.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Medical Patients Management Endpoints")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service){
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Save new patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient saved successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate Patient")
    })
    public ResponseEntity<PatientResponseDto> savePatient(@Valid @RequestBody PatientRequestDto dto){
        PatientResponseDto saveThePatient = service.savePatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveThePatient);
    }

    @GetMapping
    @Operation(summary = "List all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients list returned successfully")
    })
    public ResponseEntity<List<PatientResponseDto>> getALLPatients() {
        return ResponseEntity.ok(service.getALLPatients());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found",
                    content = @Content(schema = @Schema(implementation = AppointmentsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<PatientResponseDto> getOnePatient(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOnePatientById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
                                                            @Valid @RequestBody PatientRequestDto dto){
        return ResponseEntity.ok(service.updatePatient(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}