package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.amosjuda.Management_of_medical_appointments.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentsRepository appointmentsRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentsRepository appointmentRepository) {
        this.appointmentsRepository = appointmentRepository;
    }

    @Override
    public Appointments saveAppointment(Appointments appointment) {
        return appointmentsRepository.save(appointment);
    }

    @Override
    public List<Appointments> getALLAppointments() {
        return appointmentsRepository.findAll();
    }

    @Override
    public Optional<Appointments> getOneAppointmentById(UUID id) {
        return appointmentsRepository.findById(id);
    }

    @Override
    public Appointments updateAppointment(UUID id, Appointments appointment) {
        Optional<Appointments> exitingAppointment = appointmentsRepository.findById(id);
        if(exitingAppointment.isPresent()){
            Appointments appointmentToUpdate = exitingAppointment.get();
            appointmentToUpdate.setDateTime(appointment.getDateTime());
            appointmentToUpdate.setPatient(appointment.getPatient());
            appointmentToUpdate.setDoctor(appointment.getDoctor());
            return appointmentsRepository.save(appointmentToUpdate);
        } else {
            throw new RuntimeException("Query not found with id: " + id);
        }
    }

    @Override
    public void deleteAppointment(UUID id) {
        appointmentsRepository.deleteById(id);
    }
}
