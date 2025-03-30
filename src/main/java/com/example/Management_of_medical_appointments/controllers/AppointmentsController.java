package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.AppointmentsRecordDto;
import com.example.Management_of_medical_appointments.models.Appointments;
import com.example.Management_of_medical_appointments.models.Doctor;
import com.example.Management_of_medical_appointments.models.Patient;
import com.example.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.example.Management_of_medical_appointments.repositories.DoctorRepository;
import com.example.Management_of_medical_appointments.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class AppointmentsController {
    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @PostMapping("/appointment")
    public ResponseEntity<Object> saveAppointment(@RequestBody @Valid AppointmentsRecordDto appointmentsRecordDto){
            try {
                Patient patient = patientRepository.findById(appointmentsRecordDto.patientId())
                        .orElseThrow(() -> new NoSuchElementException("Patient not found"));

                Doctor doctor = doctorRepository.findById(appointmentsRecordDto.doctorId())
                        .orElseThrow(() -> new NoSuchElementException("Doctor not found"));

                var appointment = new Appointments();
                BeanUtils.copyProperties(appointmentsRecordDto, appointment);
                appointment.setPatientId(patient);
                appointment.setDoctorId(doctor);
                Appointments savedAppointment = appointmentsRepository.save(appointment);

                AppointmentsRecordDto appointmentRecord = new AppointmentsRecordDto(
                        savedAppointment.getDateTimeAppointment(),
                        savedAppointment.getPatientId().getIdPatient(),
                        savedAppointment.getDoctorId().getIdDoctor(),
                        savedAppointment.getStatus(),
                        savedAppointment.getNotes()
                );

                return ResponseEntity.status(HttpStatus.CREATED).body(appointmentRecord);
            } catch (NoSuchElementException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving appointment: " + e.getMessage());
            }


    }
}
