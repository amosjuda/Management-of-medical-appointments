package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.DoctorMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.request.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("DoctorServiceImpl - Unitary Tests")
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private DoctorServiceImplTest.TestFixture fixture;

    @BeforeEach
    void setUp() { fixture = new DoctorServiceImplTest.TestFixture(); }

    @AfterEach
    void tearDown() { reset(doctorRepository, doctorMapper); }

    @Nested
    @Order(1)
    @DisplayName("SaveDoctor tests")
    class saveDoctor {
        @Test
        @Tag("happy-path")
        @DisplayName("Should save save doctor successfully when all data is valid")
        void shouldSaveDoctorSuccessfully_WhenAllDataIsValid() {
            //Arrange
            DoctorRequestDto requestDto = fixture.createValidRequestDto();
            mockSuccessfulSave();

            //Act
            DoctorResponseDto result = doctorService.saveDoctor(requestDto);

            //Assert
            assertThat(result).isEqualTo(fixture.responseDto);
            verifySuccessfulSaveFlow(requestDto);
        }

        @Test
        @Tag("integration")
        @DisplayName("Should handle mapper conversion correctly during save")
        void shouldHandleMapperConversionCorrectly_DuringSave() {
            // Arrange
            DoctorRequestDto requestDto = fixture.createValidRequestDto();
            when(doctorMapper.toEntity(requestDto)).thenReturn(fixture.doctor);
            when(doctorRepository.save(fixture.doctor)).thenReturn(fixture.doctor);
            when(doctorMapper.toDto(fixture.doctor)).thenReturn(fixture.responseDto);

            // Act
            DoctorResponseDto result = doctorService.saveDoctor(requestDto);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(fixture.responseDto);

            verify(doctorMapper).toEntity(requestDto);
            verify(doctorRepository).save(fixture.doctor);
            verify(doctorMapper).toDto(fixture.doctor);
        }

        private void mockSuccessfulSave() {
            when(doctorMapper.toEntity(any(DoctorRequestDto.class))).thenReturn(fixture.doctor);
            when(doctorRepository.save(fixture.doctor)).thenReturn(fixture.doctor);
            when(doctorMapper.toDto(fixture.doctor)).thenReturn(fixture.responseDto);
        }

        private void verifySuccessfulSaveFlow(DoctorRequestDto requestDto) {
            verify(doctorMapper).toEntity(requestDto);
            verify(doctorRepository).save(fixture.doctor);
            verify(doctorMapper).toDto(fixture.doctor);
        }
    }

    @Nested
    @Order(2)
    @DisplayName("GetALLDoctor tests")
    class getALLDoctors {
        @Test
        @Tag("Happy-path")
        @DisplayName("Should return doctor list when data exists")
        void shouldReturnDoctorList_WhenDataExists() {
            //Arrange
            mockRepositoryFindAll(List.of(fixture.doctor));
            when(doctorMapper.toDto(fixture.doctor)).thenReturn(fixture.responseDto);

            //Act
            List<DoctorResponseDto> result = doctorService.getALLDoctors();

            //Assert
            assertThat(result).hasSize(1).containsExactly(fixture.responseDto);
            verify(doctorRepository).findAll();
            verify(doctorMapper).toDto(fixture.doctor);
        }

        @Test
        @Tag("edge-case")
        @DisplayName("Should return empty list when there are no doctors")
        void ShouldReturnEmptyList_WhenNoDoctorsExist() {
            //Arrange
            mockRepositoryFindAll(Collections.emptyList());

            //Act & Assert
            assertThat(doctorService.getALLDoctors()).isEmpty();
            verify(doctorRepository).findAll();
            verifyNoInteractions(doctorMapper);
        }

        @Test
        @Tag("happy-path")
        @DisplayName("Should return multiple doctors when multiple exist")
        void shouldReturnMultipleDoctors_WhenMultipleExist() {
            // Arrange
            Doctor secondDoctor = fixture.createSecondDoctor();
            DoctorResponseDto secondResponseDto = fixture.createSecondResponseDto();

            mockRepositoryFindAll(List.of(fixture.doctor, secondDoctor));
            when(doctorMapper.toDto(fixture.doctor)).thenReturn(fixture.responseDto);
            when(doctorMapper.toDto(secondDoctor)).thenReturn(secondResponseDto);

            // Act
            List<DoctorResponseDto> result = doctorService.getALLDoctors();

            // Assert
            assertThat(result)
                    .hasSize(2)
                    .containsExactly(fixture.responseDto, secondResponseDto);

            verify(doctorRepository).findAll();
            verify(doctorMapper).toDto(fixture.doctor);
            verify(doctorMapper).toDto(secondDoctor);
        }
        private void mockRepositoryFindAll(List<Doctor> doctor) {
            when(doctorRepository.findAll()).thenReturn(doctor);
        }
    }

    @Test
    @Order(3)
    @DisplayName("GetOneDoctorById tests")
    void getOneDoctorById() {
    }

    @Test
    @Order(4)
    @DisplayName("UpdateDoctor tests")
    void updateDoctor() {
    }

    @Test
    @Order(5)
    @DisplayName("DeleteDoctor tests")
    void deleteDoctor() {
    }

    private void assertRuntimeException(String expectedMessage, Runnable executable) {
        RuntimeException exception = assertThrows(RuntimeException.class, executable::run);
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    public static class TestFixture {
        final UUID DOCTOR_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        final UUID SECOND_DOCTOR_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        final String DOCTOR_NAME = "John Silva";
        final String DOCTOR_SPECIALTY = "pediatrician";
        final String DOCTOR_EMAIL = "john@email.com";

        final Doctor doctor;
        final DoctorResponseDto responseDto;

        TestFixture() {
            this.doctor = createMockDoctor();
            this.responseDto = createMockResponseDto();
        }

        DoctorRequestDto createValidRequestDto() {
            DoctorRequestDto dto = new DoctorRequestDto();
            dto.setName(DOCTOR_NAME);
            dto.setEmail(DOCTOR_EMAIL);
            dto.setSpecialty(DOCTOR_SPECIALTY);
            return dto;
        }

        DoctorRequestDto createValidUpdateRequestDto() {
            DoctorRequestDto dto = new DoctorRequestDto();
            dto.setName("John Silva Updated");
            dto.setEmail("john.updated@email.com");
            dto.setSpecialty("pediatrician");
            return dto;
        }

        Doctor createMockDoctor() {
            Doctor d = new Doctor();
            d.setIdDoctor(DOCTOR_ID);
            d.setName(DOCTOR_NAME);
            d.setEmail(DOCTOR_EMAIL);
            d.setSpecialty(DOCTOR_SPECIALTY);
            return d;
        }

        Doctor createSecondDoctor() {
            Doctor d = new Doctor();
            d.setIdDoctor(SECOND_DOCTOR_ID);
            d.setName("Joe Santos");
            d.setEmail("joe@email.com");
            d.setSpecialty("orthopedist");
            return d;
        }

        DoctorResponseDto createMockResponseDto() {
            DoctorResponseDto dto = new DoctorResponseDto();
            dto.setName(DOCTOR_NAME);
            dto.setEmail(DOCTOR_EMAIL);
            dto.setSpecialty(DOCTOR_SPECIALTY);
            return dto;
        }

        DoctorResponseDto createSecondResponseDto() {
            DoctorResponseDto dto = new DoctorResponseDto();
            dto.setId(DOCTOR_ID);
            dto.setName("Joe Santos");
            dto.setEmail("joe@email.com");
            dto.setSpecialty("orthopedist");
            return dto;
        }
    }
}