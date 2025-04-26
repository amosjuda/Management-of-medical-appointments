package com.amosjuda.Management_of_medical_appointments.service;

import com.amosjuda.Management_of_medical_appointments.models.Appointments;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointments saveAppointment(Appointments Appointments);

    List<Appointments> getALLAppointments();

    Optional<Appointments> getOneAppointmentById(Long id);

    Appointments updateAppointment(Long id, Appointments Appointments);

    void deleteAppointment(Long id);
}