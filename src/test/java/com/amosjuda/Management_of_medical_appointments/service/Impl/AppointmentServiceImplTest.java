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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
 * @since 2025
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @AfterEach
    void tearDown() { reset(appointmentsRepository, doctorRepository, patientRepository, appointmentsMapper); }

    @Nested
    @DisplayName("SaveAppointments tests")
    @Order(1)
    class saveAppointment {
        @Test
        @Tag("happy-path")
        @DisplayName("It should save appointment successfully when all data is valid")
        void shouldSaveAppointmentSuccessfully_WhenAllDataIsValid() {
            //Arrange
            AppointmentsRequestDto requestDto = fixture.createValidRequestDto();
            mockSuccessfulSave();

            //Act
            AppointmentsResponseDto result = appointmentService.saveAppointment(requestDto);

            //Assert
            assertThat(result).isEqualTo(fixture.responseDto);
            verifySuccessfulSaveFlow(requestDto);
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw EntityNotFoundException when doctor is not found")
        void shouldThrowEntityNotFoundException_WhenDoctorNotFound(){
            //Arrange
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.empty());

            //Act & Assert
            assertEntityNotFound("Doctor not found",
                    () -> appointmentService.saveAppointment(fixture.createValidRequestDto()));

            verify(doctorRepository).findById(fixture.DOCTOR_ID);
            verifyNoInteractions(patientRepository, appointmentsRepository, appointmentsMapper);
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw EntityNotFoundException when patient does not exist")
        void shouldThrowEntityNotFoundException_WhenPatientNotFound() {
            //Arrange
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.of(fixture.doctor));
            when(patientRepository.findById(fixture.PATIENT_ID)).thenReturn(Optional.empty());

            //Act & Assert
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

    @Nested
    @Order(2)
    @DisplayName("GetALLAppointments tests")
    class getALLAppointments {
        @Test
        @Tag("happy-path")
        @DisplayName("Should return an appointmentList when data exists")
        void shouldReturnAppointmentsList_WhenDataExists() {
            //Arrange
            mockRepositoryFindAll(List.of(fixture.appointment));
            when(appointmentsMapper.toResponseDto(fixture.appointment)).thenReturn(fixture.responseDto);

            //Act
            List<AppointmentsResponseDto> result = appointmentService.getALLAppointments();

            //Assert
            assertThat(result).hasSize(1).containsExactly(fixture.responseDto);
            verify(appointmentsRepository).findAll();
            verify(appointmentsMapper).toResponseDto(fixture.appointment);
        }

        @Test
        @Tag("edge-case")
        @DisplayName("Should return empty list when there are no appointments")
        void ShouldReturnEmptyList_WhenNoAppointmentsExist() {
            //Arrange
            mockRepositoryFindAll(Collections.emptyList());

            //Act & Assert
            assertThat(appointmentService.getALLAppointments()).isEmpty();
            verify(appointmentsRepository).findAll();
            verifyNoInteractions(appointmentsMapper);
        }

        private void mockRepositoryFindAll(List<Appointments> appointments) {
            when(appointmentsRepository.findAll()).thenReturn(appointments);
        }
    }

    @Nested
    @Order(3)
    @DisplayName("getOneAppointmentById tests")
    class getOneAppointmentById {
        @Test
        @Tag("happy-path")
        @DisplayName("Should return appointment when ID exists")
        void shouldReturnAppointment_WhenIdExists() {
            //Arrange
            mockAppointmentById(Optional.of(fixture.appointment));
            when(appointmentsMapper.toResponseDto(fixture.appointment)).thenReturn(fixture.responseDto);

            //Act
            assertThat(appointmentService.getOneAppointmentById(fixture.APPOINTMENT_ID))
                    .isEqualTo(fixture.responseDto);

            //Assert
            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verify(appointmentsMapper).toResponseDto(fixture.appointment);
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw RuntimeException when ID does not exist")
        void shouldThrowRuntimeException_WhenIdNotFound() {
            //Arrange
            mockAppointmentById(Optional.empty());

            //Act & Assert
            assertRuntimeException("Appointment not found",
                    () -> appointmentService.getOneAppointmentById(fixture.APPOINTMENT_ID));

            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verifyNoInteractions(appointmentsMapper);
        }

        private void mockAppointmentById(Optional<Appointments> appointments){
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(appointments);
        }
    }

    @Nested
    @Order(4)
    @DisplayName("UpdateAppointment tests")
    class updateAppointment {
        @Test
        @Tag("happy-path")
        @DisplayName("It should update appointment successfully when all data is valid")
        void shouldUpdateAppointmentSuccessfully_WhenAllDataIsValid() {
            //Arrange
            AppointmentsRequestDto requestDto = fixture.createValidRequestDto();
            mockSuccessfulUpdate();

            //Act
            AppointmentsResponseDto result = appointmentService.updateAppointment(fixture.APPOINTMENT_ID, requestDto);

            //Assert
            assertThat(result).isEqualTo(fixture.responseDto);
            assertAppointmentUpdated(requestDto);
            verifyUpdateInteractions();
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw EntityNotFoundException when appointment does not exist")
        void shouldThrowEntityNotFoundException_WhenAppointmentNotFound() {
            //Arrange
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(Optional.empty());

            //Act & Assert
            assertEntityNotFound("Appointment not found",
                    () -> appointmentService.updateAppointment(fixture.APPOINTMENT_ID, fixture.createValidRequestDto()));

            verifyNoInteractions(doctorRepository, patientRepository, appointmentsMapper);
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw EntityNotFoundException when doctor does not exist during update")
        void shouldThrowEntityNotFoundException_WhenDoctorNotFoundDuringUpdate() {
            //Arrange
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(Optional.of(fixture.appointment));
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.empty());

            //Act & Assert
            assertEntityNotFound("Doctor not found",
                    () -> appointmentService.updateAppointment(fixture.APPOINTMENT_ID, fixture.createValidRequestDto()));
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw EntityNotFoundException when patient does not exist during update")
        void shouldThrowEntityNotFoundException_WhenPatientNotFoundDuringUpdate() {
            //Arrange
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(Optional.of(fixture.appointment));
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.of(fixture.doctor));
            when(patientRepository.findById(fixture.PATIENT_ID)).thenReturn(Optional.empty());

            //Act & Assert
            assertEntityNotFound("Patient not found",
                    () -> appointmentService.updateAppointment(fixture.APPOINTMENT_ID, fixture.createValidRequestDto()));

            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verify(doctorRepository).findById(fixture.DOCTOR_ID);
            verify(patientRepository).findById(fixture.PATIENT_ID);
            verifyNoMoreInteractions(appointmentsRepository, doctorRepository, patientRepository);
        }

        private void mockSuccessfulUpdate() {
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(Optional.of(fixture.appointment));
            when(doctorRepository.findById(fixture.DOCTOR_ID)).thenReturn(Optional.of(fixture.doctor));
            when(patientRepository.findById(fixture.PATIENT_ID)).thenReturn(Optional.of(fixture.patient));
            when(appointmentsMapper.toResponseDto(fixture.appointment)).thenReturn(fixture.responseDto);;
            when(appointmentsRepository.save(fixture.appointment)).thenReturn(fixture.appointment);
        }
        private void assertAppointmentUpdated(AppointmentsRequestDto requestDto) {
            assertThat(fixture.appointment.getDoctor()).isEqualTo(fixture.doctor);
            assertThat(fixture.appointment.getPatient()).isEqualTo(fixture.patient);
            assertThat(fixture.appointment.getDateTime()).isEqualTo(requestDto.getDateTime());
        }
        private void verifyUpdateInteractions() {
            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verify(doctorRepository).findById(fixture.DOCTOR_ID);
            verify(patientRepository).findById(fixture.PATIENT_ID);
            verify(appointmentsRepository).save(fixture.appointment);
        }
    }

    @Nested
    @Order(5)
    @DisplayName("DaveAppointments tests")
    class deleteAppointment {
        @Test
        @Tag("happy-path")
        @DisplayName("Should delete appointment when ID exists")
        void shouldDeleteAppointment_WhenIdExists() {
            //Arrange
            mockAppointmentExists(true);

            //Act
            appointmentService.deleteAppointment(fixture.APPOINTMENT_ID);

            //Assert
            verifyDeleteFlow();
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw EntityNotFoundException when ID does not exist")
        void shouldThrowEntityNotFoundException_WhenIdNotFound() {
            //Arrange
            mockAppointmentExists(false);

            //Act & Assert
            assertEntityNotFound("Appointment not found",
                    () -> appointmentService.deleteAppointment(fixture.APPOINTMENT_ID));

            verify(appointmentsRepository).existsById(fixture.APPOINTMENT_ID);
            verify(appointmentsRepository, never()).deleteById(any());
        }

        private void mockAppointmentExists(boolean exists) {
            when(appointmentsRepository.existsById(fixture.APPOINTMENT_ID)).thenReturn(exists);
        }

        private void verifyDeleteFlow() {
            verify(appointmentsRepository).existsById(fixture.APPOINTMENT_ID);
            verify(appointmentsRepository).deleteById(fixture.APPOINTMENT_ID);
        }
    }

    @Nested
    @Order(6)
    @DisplayName("CancelAppointment tests")
    class cancelAppointment {
        @Test
        @Tag("happy-path")
        @DisplayName("You must cancel the appointment when it is in SCHEDULED status")
        void shouldCancelAppointment_WhenStatusIsScheduled() {
            //Arrange
            mockAppointmentWithStatus(AppointmentStatus.SCHEDULED);

            //Act
            appointmentService.cancelAppointment(fixture.APPOINTMENT_ID);

            //Assert
            assertThat(fixture.appointment.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
            verifyCancelFlow();
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw IllegalStateException when appointment is already cancelled")
        void shouldThrowIllegalStateException_WhenAlreadyCancelled() {
            //Arrange
            mockAppointmentWithStatus(AppointmentStatus.CANCELLED);

            //Act & Assert
            assertIllegalState("Appointment already cancelled",
                    () -> appointmentService.cancelAppointment(fixture.APPOINTMENT_ID));

            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verify(appointmentsRepository, never()).save(any());
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw ResourceNotFoundException when appointment does not exist")
        void shouldThrowResourceNotFoundException_WhenAppointmentNotFound() {
            //Arrange
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(Optional.empty());

            //Act & Assert
            assertResourceNotFound("Appointment not found with id: " + fixture.APPOINTMENT_ID,
                    () -> appointmentService.cancelAppointment(fixture.APPOINTMENT_ID));

            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verify(appointmentsRepository, never()).save(any());
        }

        private void mockAppointmentWithStatus(AppointmentStatus status) {
            fixture.appointment.setStatus(status);
            when(appointmentsRepository.findById(fixture.APPOINTMENT_ID)).thenReturn(Optional.of(fixture.appointment));
        }

        private void verifyCancelFlow() {
            verify(appointmentsRepository).findById(fixture.APPOINTMENT_ID);
            verify(appointmentsRepository).save(fixture.appointment);
        }
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
            p.setName("John Silva");
            p.setEmail("antonnisilva@email.com");
            p.setPhone("(88)988448844");
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