package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.AppointmentsMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.request.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.exceptions.ResourceNotFoundException;
import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import com.amosjuda.Management_of_medical_appointments.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentsRepository appointmentsRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentsMapper appointmentsMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentsRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentsMapper appointmentsMapper) {
        this.appointmentsRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentsMapper = appointmentsMapper;
    }

    @Override
    public AppointmentsResponseDto saveAppointment(AppointmentsRequestDto dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Appointments appointment = appointmentsMapper.toEntity(dto, doctor, patient);
        Appointments saved = appointmentsRepository.save(appointment);
        return appointmentsMapper.toResponseDto(saved);
    }

    @Override
    public List<AppointmentsResponseDto> getALLAppointments() {
        return appointmentsRepository.findAll().stream()
                .map(appointmentsMapper::toResponseDto)
                .toList();
    }

    @Override
    public AppointmentsResponseDto getOneAppointmentById(UUID id) {
        Appointments appointments = appointmentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return appointmentsMapper.toResponseDto(appointments);
    }

    @Override
    public AppointmentsResponseDto updateAppointment(UUID id, AppointmentsRequestDto dto) {
        Appointments existing = appointmentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        existing.setDoctor(doctor);
        existing.setPatient(patient);
        existing.setDateTime(dto.getDateTime());

        Appointments updated = appointmentsRepository.save(existing);
        return appointmentsMapper.toResponseDto(updated);
    }

    @Override
    public void deleteAppointment(UUID id) {
        if (!appointmentsRepository.existsById(id)) {
            throw new EntityNotFoundException("Appointment not found");
        }
        appointmentsRepository.deleteById(id);
    }

    @Override
    public void cancelAppointment(UUID id) {
        Appointments appointment = appointmentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentsRepository.save(appointment);
    }
}
