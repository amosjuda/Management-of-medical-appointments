package com.amosjuda.Management_of_medical_appointments.dtos.response;

import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppointmentsResponseDto {
    @Schema(description = "Appointment ID", example = "8c1e28b2-5f45-455e-a64f-ec47768b3f2e")
    private UUID id;

    @Schema(description = "Appointment date and time", example = "2025-06-01T14:00:00")
    private LocalDateTime dateTime;

    @Schema(description = "Patient information")
    private Patient patient;

    @Schema(description = "Doctor information")
    private Doctor doctor;

    @Schema(description = "Current scheduling status", example = "CANCELED")
    private AppointmentStatus status;

    @Schema(description = "Scheduling notes or observations", example = "Appointment cancelled due to scheduling conflict")
    private String notes;
}
