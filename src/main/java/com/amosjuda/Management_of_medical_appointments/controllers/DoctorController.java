package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.dtos.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.service.DoctorService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/doctors")
@Tag(name = "Doctors", description = "Medical Doctors Management Endpoints")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Save new doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor saved successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate doctor")
    })
    public ResponseEntity<DoctorResponseDto> saveDoctor(@Valid @RequestBody DoctorRequestDto dto){
        DoctorResponseDto saveTheDoctor = service.saveDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveTheDoctor);
    }

    @GetMapping
    @Operation(summary = "List all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors list returned successfully")
    })
    public ResponseEntity<List<DoctorResponseDto>> getALLDoctors() {
       return ResponseEntity.ok(service.getALLDoctors());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for doctor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = @Content(schema = @Schema(implementation = AppointmentsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    public ResponseEntity<DoctorResponseDto> getOneDoctor(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOneDoctorById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable UUID id,
                                                          @Valid @RequestBody DoctorRequestDto dto){
        return ResponseEntity.ok(service.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    public ResponseEntity<DoctorResponseDto> deleteDoctor(@PathVariable UUID id) {
        service.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}