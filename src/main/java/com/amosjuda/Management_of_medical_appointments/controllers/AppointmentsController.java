package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import com.amosjuda.Management_of_medical_appointments.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentService appointmentService;

    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentsResponseDto> saveAppointment(@RequestBody @Valid AppointmentsRequestDto dto){
        return ResponseEntity.ok(appointmentService.saveAppointment(dto));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentsResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getALLAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentsResponseDto> getOneAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getOneAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentsResponseDto> updateAppointment(@PathVariable UUID id,
                                                                     @Valid @RequestBody AppointmentsRequestDto dto){
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable UUID id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}