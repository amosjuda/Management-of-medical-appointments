package com.amosjuda.Management_of_medical_appointments.dtos;

import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
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
    private UUID id;
    private LocalDateTime dateTime;
    private Patient patient;
    private Doctor doctor;
    private AppointmentStatus status;
    private String notes;
}
