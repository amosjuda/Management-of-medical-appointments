package com.example.Management_of_medical_appointments.dtos;

import com.example.Management_of_medical_appointments.models.AppointmentStatus;
import com.example.Management_of_medical_appointments.models.Doctor;
import com.example.Management_of_medical_appointments.models.Patient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentsRecordDto(@NotNull @Future LocalDateTime dateTime,
                                    @NotNull Patient patientId,
                                    @NotNull Doctor doctorId,
                                    @NotNull AppointmentStatus status,
                                    @NotNull String notes) {
}
