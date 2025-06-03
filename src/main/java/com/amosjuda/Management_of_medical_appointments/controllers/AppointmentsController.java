package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.request.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointments", description = "Medical Appointment Management Endpoints")
public class AppointmentsController {

    private final AppointmentService appointmentService;

    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "Save new schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Patient or doctor not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate scheduling")
    })
    public ResponseEntity<AppointmentsResponseDto> saveAppointment(@RequestBody @Valid AppointmentsRequestDto dto){
        return ResponseEntity.ok(appointmentService.saveAppointment(dto));
    }

    @GetMapping
    @Operation(summary = "List all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment list returned successfully")
    })
    public ResponseEntity<List<AppointmentsResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getALLAppointments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found",
                    content = @Content(schema = @Schema(implementation = AppointmentsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<AppointmentsResponseDto> getOneAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getOneAppointmentById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<AppointmentsResponseDto> updateAppointment(@PathVariable UUID id,
                                                                     @Valid @RequestBody AppointmentsRequestDto dto){
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Schedule deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/cancel")
    @Operation(summary = "Cancel appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment successfully cancelled"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "400", description = "Appointment already cancelled or finalised")
    })
    public ResponseEntity<Void> cancelAppointment(@PathVariable UUID id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}