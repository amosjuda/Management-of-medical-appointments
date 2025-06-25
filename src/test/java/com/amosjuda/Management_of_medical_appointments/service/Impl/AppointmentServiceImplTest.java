package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.AppointmentsMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.request.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.AppointmentStatus;
import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.AppointmentsRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;
/*
 * Unit test class for {@link AppointmentServiceImpl}.
 *
 * This class tests all the functionalities of the appointment service,
 * ensuring correct behavior in both success and failure scenarios.
 *
 * Patterns used:
 * - AAA (Arrange-Act-Assert)
 * - Builder Pattern for object creation
 * - Test Data Builder for data organization
 * - Nested classes for logical grouping
 *
 * @author Amos Juda
 * @version 1.0
 * @since 2025
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AppointmentServiceImpl - Unitary Tests")
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

    private TestFixture fixture;

    @BeforeEach
    void setUp() {
        fixture = new TestFixture();
    }

    @Test
    @DisplayName("SaveAppointments tests")
    void saveAppointment() {

    }

    @Test
    @DisplayName("GetALLAppointments tests")
    void getALLAppointments() {
    }

    @Test
    @DisplayName("getOneAppointmentById tests")
    void getOneAppointmentById() {
    }

    @Test
    @DisplayName("UpdateAppointment tests")
    void updateAppointment() {
    }

    @Test
    @DisplayName("DaveAppointments tests")
    void deleteAppointment() {
    }

    @Test
    @DisplayName("CancelAppointment tests")
    void cancelAppointment() {
    }

    private static class TestFixture {
        // Constants for test data
        final UUID APPOINTMENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        final UUID DOCTOR_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        final UUID PATIENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        final LocalDateTime APPOINTMENT_DATE = LocalDateTime.of(2026, 12, 25, 10, 30, 0);
        final String DOCTOR_NAME = "Dr. Jhon Santos";
        final String PATIENT_NAME = "Victor Silva";
        final String APPOINTMENT_NOTES = "Routine consultation - general checkup";

        // Domain objects
        final Patient patient;
        final Doctor doctor;
        final Appointments appointment;
        final AppointmentsResponseDto responseDto;

        TestFixture() {
            this.patient = buildPatient();
            this.doctor = buildDoctor();
            this.appointment = buildAppointment();
            this.responseDto = buildResponseDto();
        }

        // Cria um DTO de request válido para testes de sucesso.
        AppointmentsRequestDto createValidRequestDto() {
            AppointmentsRequestDto dto = new AppointmentsRequestDto();
            dto.setDoctorId(DOCTOR_ID);
            dto.setPatientId(PATIENT_ID);
            dto.setDateTime(APPOINTMENT_DATE);
            return dto;
        }

        // Cria um DTO de request com doctor ID inválido.
        AppointmentsRequestDto createRequestDtoWithInvalidDoctorId() {
            AppointmentsRequestDto dto = new AppointmentsRequestDto();
            dto.setDoctorId(UUID.randomUUID()); // ID don't exists
            dto.setPatientId(PATIENT_ID);
            dto.setDateTime(APPOINTMENT_DATE);
            return dto;
        }

        // Cria um DTO de request com patient ID inválido.
        AppointmentsRequestDto createRequestDtoWithInvalidPatientId() {
            AppointmentsRequestDto dto = new AppointmentsRequestDto();
            dto.setDoctorId(DOCTOR_ID);
            dto.setPatientId(UUID.randomUUID()); // ID don't exists
            dto.setDateTime(APPOINTMENT_DATE);
            return dto;
        }

        private Patient buildPatient() {
            Patient p = new Patient();
            p.setIdPatient(PATIENT_ID);
            p.setName(PATIENT_NAME);
            p.setEmail("jhon.silva@email.com");
            p.setPhone("(85) 99999-9999");
            return p;
        }

        private Doctor buildDoctor() {
            Doctor d = new Doctor();
            d.setIdDoctor(DOCTOR_ID);
            d.setName(DOCTOR_NAME);
            d.setSpecialty("Cardiology");
            return d;
        }

        private Appointments buildAppointment() {
            Appointments a = new Appointments();
            a.setIdAppointments(APPOINTMENT_ID);
            a.setPatient(patient);
            a.setDoctor(doctor);
            a.setDateTime(APPOINTMENT_DATE);
            a.setStatus(AppointmentStatus.SCHEDULED);
            a.setNotes(APPOINTMENT_NOTES);
            return a;
        }

        private AppointmentsResponseDto buildResponseDto() {
            AppointmentsResponseDto dto = new AppointmentsResponseDto();
            dto.setId(APPOINTMENT_ID);
            dto.setDoctor(this.doctor);
            dto.setPatient(this.patient);
            dto.setDateTime(APPOINTMENT_DATE);
            dto.setStatus(AppointmentStatus.SCHEDULED);
            dto.setNotes(APPOINTMENT_NOTES);
            return dto;
        }
    }
}