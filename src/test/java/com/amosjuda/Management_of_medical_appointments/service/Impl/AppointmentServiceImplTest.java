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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
 * @author Amós Judá
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

    @Nested
    @DisplayName("SaveAppointments tests")
    class saveAppointment {
        @Test
        @DisplayName("It should save appointment successfully when all data is valid")
        void shouldSaveAppointmentSuccessfully_WhenAllDataIsValid() {
            AppointmentsRequestDto requestDto = fixture.createValidRequestDto();
            mockSuccessfulSave();

            AppointmentsResponseDto result = appointmentService.saveAppointment(requestDto);

            assertThat(result).isEqualTo(fixture.responseDto);
            verifySuccessfulSaveFlow(requestDto);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when doctor is not found")
        void shouldThrowEntityNotFoundException_WhenDoctorNotFound(){
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.empty());

            assertEntityNotFound("Doctor not found",
                    () -> appointmentService.saveAppointment(fixture.createValidRequestDto()));

            verify(doctorRepository).findById(fixture.DOCTOR_ID);
            verifyNoInteractions(patientRepository, appointmentsRepository, appointmentsMapper);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when patient does not exist")
        void shouldThrowEntityNotFoundException_WhenPatientNotFound() {
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.of(fixture.doctor));
            when(patientRepository.findById(fixture.PATIENT_ID)).thenReturn(Optional.empty());

            // Act & Assert
            assertEntityNotFound("Patient not found",
                    () -> appointmentService.saveAppointment(fixture.createValidRequestDto()));

            verify(doctorRepository).findById(fixture.DOCTOR_ID);
            verify(patientRepository).findById(fixture.PATIENT_ID);
            verifyNoInteractions(appointmentsRepository, appointmentsMapper);
        }

        private void mockSuccessfulSave() {
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.of(fixture.doctor));
            when(patientRepository.findById(fixture.PATIENT_ID)).thenReturn(Optional.of(fixture.patient));
            when(appointmentsMapper.toEntity(any(), any(), any())).thenReturn(fixture.appointment);
            when(appointmentsRepository.save(fixture.appointment)).thenReturn(fixture.appointment);
            when(appointmentsMapper.toResponseDto(fixture.appointment)).thenReturn(fixture.responseDto);
        }

        private void verifySuccessfulSaveFlow(AppointmentsRequestDto requestDto) {
            verify(doctorRepository).findById(fixture.DOCTOR_ID);
            verify(patientRepository).findById(fixture.PATIENT_ID);
            verify(appointmentsMapper).toEntity(requestDto, fixture.doctor, fixture.patient);
            verify(appointmentsRepository).save(fixture.appointment);
            verify(appointmentsMapper).toResponseDto(fixture.appointment);
        }
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

    private void assertEntityNotFound(String expectedMessage, Runnable executable) {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, executable::run);
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    private void assertRuntimeException(String expectedMessage, Runnable executable) {
        RuntimeException exception = assertThrows(RuntimeException.class, executable::run);
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    private void assertIllegalState(String expectedMessage, Runnable executable) {
        IllegalStateException exception = assertThrows(IllegalStateException.class, executable::run);
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    private void assertResourceNotFound(String expectedMessage, Runnable executable) {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, executable::run);
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    private static class TestFixture {
        final UUID APPOINTMENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        final UUID DOCTOR_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        final UUID PATIENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        final LocalDateTime APPOINTMENT_DATE = LocalDateTime.of(2024, 12, 25, 10, 30, 0);

        final Patient patient;
        final Doctor doctor;
        final Appointments appointment;
        final AppointmentsResponseDto responseDto;

        TestFixture() {
            this.patient = createMockPatient();
            this.doctor = createMockDoctor();
            this.appointment = createMockAppointment();
            this.responseDto = createMockResponseDto();
        }

        AppointmentsRequestDto createValidRequestDto() {
            AppointmentsRequestDto dto = new AppointmentsRequestDto();
            dto.setDoctorId(DOCTOR_ID);
            dto.setPatientId(PATIENT_ID);
            dto.setDateTime(APPOINTMENT_DATE);
            return dto;
        }

        private Patient createMockPatient() {
            Patient p = new Patient();
            p.setIdPatient(PATIENT_ID);
            p.setName("Jhon Silva");
            return p;
        }

        private Doctor createMockDoctor() {
            Doctor d = new Doctor();
            d.setIdDoctor(DOCTOR_ID);
            d.setName("Dr. Victor Santos");
            return d;
        }

        private Appointments createMockAppointment() {
            Appointments a = new Appointments();
            a.setIdAppointments(APPOINTMENT_ID);
            a.setPatient(patient);
            a.setDoctor(doctor);
            a.setDateTime(APPOINTMENT_DATE);
            a.setStatus(AppointmentStatus.SCHEDULED);
            return a;
        }

        private AppointmentsResponseDto createMockResponseDto() {
            AppointmentsResponseDto dto = new AppointmentsResponseDto();
            dto.setId(APPOINTMENT_ID);
            dto.setDateTime(APPOINTMENT_DATE);
            dto.setStatus(AppointmentStatus.SCHEDULED);
            return dto;
        }
    }
}