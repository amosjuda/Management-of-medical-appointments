package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.dtos.request.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.AppointmentsResponseDto;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentsResponseDto saveAppointment(AppointmentsRequestDto dto);

    List<AppointmentsResponseDto> getALLAppointments();

    AppointmentsResponseDto getOneAppointmentById(UUID id);

    AppointmentsResponseDto updateAppointment(UUID id, AppointmentsRequestDto dto);

    void deleteAppointment(UUID id);

    void cancelAppointment(UUID id);
}