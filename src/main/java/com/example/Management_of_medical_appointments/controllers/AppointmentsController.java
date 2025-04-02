package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.AppointmentsRecordDto;
import com.example.Management_of_medical_appointments.models.Appointments;
import com.example.Management_of_medical_appointments.models.Doctor;
import com.example.Management_of_medical_appointments.models.Patient;
import com.example.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.example.Management_of_medical_appointments.repositories.DoctorRepository;
import com.example.Management_of_medical_appointments.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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

                // Check if there is already an appointment at the same time for the patient and doctor
                Optional<Appointments> existingAppointment = appointmentsRepository
                        .findByDateTimeAndPatientAndDoctor(appointmentsRecordDto.dateTime(), patient, doctor);

                if (existingAppointment.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Appointment already exists for this patient and doctor at the specified time.");
                }

                var appointment = new Appointments();
                appointment.setDateTime(appointmentsRecordDto.dateTime());
                appointment.setStatus(appointmentsRecordDto.status());
                appointment.setNotes(appointmentsRecordDto.notes());
                appointment.setPatient(patient);
                appointment.setDoctor(doctor);
                Appointments savedAppointment = appointmentsRepository.save(appointment);

                AppointmentsRecordDto appointmentRecord = new AppointmentsRecordDto(
                        savedAppointment.getDateTime(),
                        savedAppointment.getPatient().getIdPatient(),
                        savedAppointment.getDoctor().getIdDoctor(),
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

    @GetMapping("/appointment")
    public ResponseEntity<List<Appointments>> getAllAppointments() {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsRepository.findAll());
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<Object> getOneAppointment(@PathVariable(value="id") UUID id) {
        Optional<Appointments> appointmentO = appointmentsRepository.findById(id);
        if(appointmentO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(appointmentO.get());
    }

    @PutMapping("/appointment/{id}")
    public ResponseEntity<Object> updateAppointments(@PathVariable(value="id") UUID id,
                                                     @RequestBody AppointmentsRecordDto appointmentsRecordDto){
        Optional<Appointments> appointmentO = appointmentsRepository.findById(id);
        if(appointmentO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
        var appointment = appointmentO.get();

        // Update only non-null fields
        if (appointmentsRecordDto.dateTime() != null) {
            appointment.setDateTime(appointmentsRecordDto.dateTime());
        }
        if (appointmentsRecordDto.patientId() != null) {
            Optional<Patient> patient = patientRepository.findById(appointmentsRecordDto.patientId());
            patient.ifPresent(appointment::setPatient);  // Convert UUID to Patient
        }
        if (appointmentsRecordDto.doctorId() != null) {
            Optional<Doctor> doctor = doctorRepository.findById(appointmentsRecordDto.doctorId());
            doctor.ifPresent(appointment::setDoctor);  // Convert UUID to Doctor
        }
        if (appointmentsRecordDto.status() != null) {
            appointment.setStatus(appointmentsRecordDto.status());
        }
        if (appointmentsRecordDto.notes() != null) {
            appointment.setNotes(appointmentsRecordDto.notes());
        }
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsRepository.save(appointment));
    }

}
