package com.amosjuda.Management_of_medical_appointments.dtos;

import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
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
    LocalDateTime dateTime;

    @NotNull
    UUID patientId;

    @NotNull
    UUID doctorId;

    @NotNull
    AppointmentStatus status;

    @NotNull
    String notes;
}
