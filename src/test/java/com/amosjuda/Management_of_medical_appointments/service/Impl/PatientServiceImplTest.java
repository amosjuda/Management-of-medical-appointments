package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.PatientMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.request.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.PatientResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import com.amosjuda.Management_of_medical_appointments.repositories.PatientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/*
 * Unit test class for {@link PatientServiceImpl}.
 *
 * This class tests all the functionalities of the patient service,
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
@DisplayName("PatientServiceImpl - Unitary Tests")
class PatientServiceImplTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private TestFixture fixture;

    @BeforeEach
    void setUp() { fixture = new TestFixture();}

    @AfterEach
    void tearDown() { reset(patientRepository, patientMapper); }

    @Nested
    @Order(1)
    @DisplayName("SavePatient tests")
    class savePatient {
        @Test
        @Tag("happy-path")
        @DisplayName("Should save save patient successfully when all data is valid")
        void shouldSavePatientSuccessfully_WhenAllDataIsValid() {
            //Arrange
            PatientRequestDto requestDto = fixture.createValidRequestDto();
            mockSuccessfulSave();

            //Act
            PatientResponseDto result = patientService.savePatient(requestDto);

            //Assert
            assertThat(result).isEqualTo(fixture.responseDto);
            verifySuccessfulSaveFlow(requestDto);
        }

        @Test
        @Tag("integration")
        @DisplayName("Should handle mapper conversion correctly during save")
        void shouldHandleMapperConversionCorrectly_DuringSave() {
            // Arrange
            PatientRequestDto requestDto = fixture.createValidRequestDto();
            when(patientMapper.toEntity(requestDto)).thenReturn(fixture.patient);
            when(patientRepository.save(fixture.patient)).thenReturn(fixture.patient);
            when(patientMapper.toDto(fixture.patient)).thenReturn(fixture.responseDto);

            // Act
            PatientResponseDto result = patientService.savePatient(requestDto);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(fixture.responseDto);

            verify(patientMapper).toEntity(requestDto);
            verify(patientRepository).save(fixture.patient);
            verify(patientMapper).toDto(fixture.patient);
        }

        private void mockSuccessfulSave() {
            when(patientMapper.toEntity(any(PatientRequestDto.class))).thenReturn(fixture.patient);
            when(patientRepository.save(fixture.patient)).thenReturn(fixture.patient);
            when(patientMapper.toDto(fixture.patient)).thenReturn(fixture.responseDto);
        }

        private void verifySuccessfulSaveFlow(PatientRequestDto requestDto) {
            verify(patientMapper).toEntity(requestDto);
            verify(patientRepository).save(fixture.patient);
            verify(patientMapper).toDto(fixture.patient);
        }
    }

    @Nested
    @Order(2)
    class getALLPatients {
        @Test
        @Tag("Happy-path")
        @DisplayName("Should return patient list when data exists")
        void shouldReturnPatientList_WhenDataExists() {
            //Arrange
            mockRepositoryFindAll(List.of(fixture.patient));
            when(patientMapper.toDto(fixture.patient)).thenReturn(fixture.responseDto);

            //Act
            List<PatientResponseDto> result = patientService.getALLPatients();

            //Assert
            assertThat(result).hasSize(1).containsExactly(fixture.responseDto);
            verify(patientRepository).findAll();
            verify(patientMapper).toDto(fixture.patient);
        }

        @Test
        @Tag("edge-case")
        @DisplayName("Should return empty list when there are no patients")
        void ShouldReturnEmptyList_WhenNoPatientsExist() {
            //Arrange
            mockRepositoryFindAll(Collections.emptyList());

            //Act & Assert
            assertThat(patientService.getALLPatients()).isEmpty();
            verify(patientRepository).findAll();
            verifyNoInteractions(patientMapper);
        }

        @Test
        @DisplayName("Should return multiple patients when multiple exist")
        @Tag("happy-path")
        void shouldReturnMultiplePatients_WhenMultipleExist() {
            // Arrange
            Patient secondPatient = fixture.createSecondPatient();
            PatientResponseDto secondResponseDto = fixture.createSecondResponseDto();

            mockRepositoryFindAll(List.of(fixture.patient, secondPatient));
            when(patientMapper.toDto(fixture.patient)).thenReturn(fixture.responseDto);
            when(patientMapper.toDto(secondPatient)).thenReturn(secondResponseDto);

            // Act
            List<PatientResponseDto> result = patientService.getALLPatients();

            // Assert
            assertThat(result)
                    .hasSize(2)
                    .containsExactly(fixture.responseDto, secondResponseDto);

            verify(patientRepository).findAll();
            verify(patientMapper).toDto(fixture.patient);
            verify(patientMapper).toDto(secondPatient);
        }
        private void mockRepositoryFindAll(List<Patient> patients) {
            when(patientRepository.findAll()).thenReturn(patients);
        }
    }

    @Nested
    @Order(3)
    class getOnePatientById {
        @Test
        @Tag("happy-path")
        @DisplayName("Should return patient when ID exists")
        void shouldReturnPatient_WhenIdExists() {
            //Arrange
            mockPatientById(Optional.of(fixture.patient));
            when(patientMapper.toDto(fixture.patient)).thenReturn(fixture.responseDto);

            //Act
            assertThat(patientService.getOnePatientById(fixture.PATIENT_ID))
                    .isEqualTo(fixture.responseDto);

            //Assert
            verify(patientRepository).findById(fixture.PATIENT_ID);
            verify(patientMapper).toDto(fixture.patient);
        }

        @Test
        @Tag("error-handling")
        @DisplayName("Should throw RuntimeException when ID does not exist")
        void shouldThrowRuntimeException_WhenIdNotFound() {
            //Arrange
            mockPatientById(Optional.empty());

            //Act & Assert
            assertRuntimeException("Patient not found",
                    () -> patientService.getOnePatientById(fixture.PATIENT_ID));

            verify(patientRepository).findById(fixture.PATIENT_ID);
            verifyNoInteractions(patientMapper);
        }

        @RepeatedTest(value = 2, name = "Should handle repository calls consistently - Repetition {currentRepetition}")
        @Tag("reliability")
        @DisplayName("Should handle repository calls consistently")
        void shouldHandleRepositoryCallsConsistently(RepetitionInfo repetitionInfo) {
            // Arrange
            mockPatientById(Optional.of(fixture.patient));
            when(patientMapper.toDto(fixture.patient)).thenReturn(fixture.responseDto);

            // Act
            PatientResponseDto result = patientService.getOnePatientById(fixture.PATIENT_ID);

            // Assert
            assertThat(result).isEqualTo(fixture.responseDto);
            assertThat(repetitionInfo.getCurrentRepetition()).isLessThanOrEqualTo(2);
        }

        private void mockPatientById(Optional<Patient> patient){
            when(patientRepository.findById(fixture.PATIENT_ID)).thenReturn(patient);
        }
    }

    @Nested
    @Order(4)
    class updatePatient {
    }

    @Nested
    @Order(5)
    class deletePatient {
    }

    private void assertRuntimeException(String expectedMessage, Runnable executable) {
        RuntimeException exception = assertThrows(RuntimeException.class, executable::run);
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    public static class TestFixture {
        final UUID PATIENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        final UUID SECOND_PATIENT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        final String PATIENT_NAME = "John Silva";
        final String PATIENT_EMAIL = "john@email.com";
        final String PATIENT_PHONE = "(85) 99999-9999";
        final LocalDate PATIENT_BIRTHDATE = LocalDate.of(1990, 5, 15);

        final Patient patient;
        final PatientResponseDto responseDto;

        TestFixture() {
            this.patient = createMockPatient();
            this.responseDto = createMockResponseDto();
        }

        PatientRequestDto createValidRequestDto() {
            PatientRequestDto dto = new PatientRequestDto();
            dto.setName(PATIENT_NAME);
            dto.setEmail(PATIENT_EMAIL);
            dto.setPhone(PATIENT_PHONE);
            dto.setBirthdate(PATIENT_BIRTHDATE);
            return dto;
        }

        PatientRequestDto createValidUpdateRequestDto() {
            PatientRequestDto dto = new PatientRequestDto();
            dto.setName("John Silva Updated");
            dto.setEmail("john.updated@email.com");
            dto.setPhone("(85) 98888-8888");
            dto.setBirthdate(LocalDate.of(1990, 6, 20));
            return dto;
        }

        Patient createMockPatient() {
            Patient p = new Patient();
            p.setIdPatient(PATIENT_ID);
            p.setName(PATIENT_NAME);
            p.setEmail(PATIENT_EMAIL);
            p.setPhone(PATIENT_PHONE);
            p.setBirthdate(PATIENT_BIRTHDATE);
            return p;
        }

        Patient createSecondPatient() {
            Patient p = new Patient();
            p.setIdPatient(SECOND_PATIENT_ID);
            p.setName("Maria Santos");
            p.setEmail("maria@email.com");
            p.setPhone("(85) 77777-7777");
            p.setBirthdate(LocalDate.of(1985, 3, 10));
            return p;
        }

        PatientResponseDto createMockResponseDto() {
            PatientResponseDto dto = new PatientResponseDto();
            dto.setId(PATIENT_ID);
            dto.setName(PATIENT_NAME);
            dto.setEmail(PATIENT_EMAIL);
            dto.setPhone(PATIENT_PHONE);
            dto.setBirthdate(PATIENT_BIRTHDATE);
            return dto;
        }

        PatientResponseDto createSecondResponseDto() {
            PatientResponseDto dto = new PatientResponseDto();
            dto.setId(SECOND_PATIENT_ID);
            dto.setName("Maria Santos");
            dto.setEmail("maria@email.com");
            dto.setPhone("(85) 77777-7777");
            dto.setBirthdate(LocalDate.of(1985, 3, 10));
            return dto;
        }
    }
}