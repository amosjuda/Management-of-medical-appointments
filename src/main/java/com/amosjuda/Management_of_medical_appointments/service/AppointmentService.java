package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.models.Appointments;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentService {
    Appointments saveAppointment(Appointments Appointments);

    List<Appointments> getALLAppointments();

    Optional<Appointments> getOneAppointmentById(UUID id);

    Appointments updateAppointment(UUID id, Appointments Appointments);

    void deleteAppointment(UUID id);
}