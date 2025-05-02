package com.amosjuda.Management_of_medical_appointments.dtos;

import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppointmentsRequestDto {

    @NotNull @Future
    @Schema(description = "Appointment date and time", example = "2025-06-01T14:00:00")
    LocalDateTime dateTime;

    @NotNull
    @Schema(description = "Patient UUID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    UUID patientId;

    @NotNull
    @Schema(description = "Doctor UUID", example = "c9a64639-e823-4b44-8439-7f0e9dcfd980")
    UUID doctorId;

    @NotNull
    @Schema(description = "Current scheduling status", example = "SCHEDULED")
    AppointmentStatus status;

    @NotNull
    @Schema(description = "Notes or observations about scheduling", example = "Patient asked for a follow-up after 30 days")
    String notes;
}
