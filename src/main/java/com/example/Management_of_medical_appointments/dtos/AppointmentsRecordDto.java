package com.example.Management_of_medical_appointments.dtos;

import com.example.Management_of_medical_appointments.models.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentsRecordDto(@NotNull @Future LocalDateTime dateTimeAppointment,
                                    @NotNull UUID patientId,
                                    @NotNull UUID doctorId,
                                    @NotNull AppointmentStatus status,
                                    @NotNull String notes) {
}
