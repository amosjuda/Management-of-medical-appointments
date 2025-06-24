package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.AppointmentsMapper;
import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentsRepository appointmentsRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentsMapper appointmentsMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Patient testPatient;
    private Doctor testDoctor;
    private Appointments testAppointment;
    private UUID testAppointmentId;
    private LocalDateTime testAppointmentDate;

    @BeforeEach
    void setUp() {
        testPatient = new Patient();
        testPatient.setIdPatient(UUID.randomUUID());
        testPatient.setName("John Doe");

        testDoctor = new Doctor();
        testDoctor.setIdDoctor(UUID.randomUUID());
        testDoctor.setName("Dr. Smith");

        testAppointmentId = UUID.randomUUID();
        testAppointmentDate = LocalDateTime.now();

        testAppointment = new Appointments();
        testAppointment.setIdAppointments(testAppointmentId);
        testAppointment.setPatient(testPatient);
        testAppointment.setDoctor(testDoctor);
        testAppointment.setDateTime(testAppointmentDate);
        testAppointment.setStatus(AppointmentStatus.SCHEDULED);
        testAppointment.setNotes("Initial consultation");
    }

    @Test
    void saveAppointment() {

    }

    @Test
    void getALLAppointments() {
    }

    @Test
    void getOneAppointmentById() {
    }

    @Test
    void updateAppointment() {
    }

    @Test
    void deleteAppointment() {
    }

    @Test
    void cancelAppointment() {
    }

}