package com.amosjuda.Management_of_medical_appointments.service.Impl;

import com.amosjuda.Management_of_medical_appointments.config.DoctorMapper;
import com.amosjuda.Management_of_medical_appointments.dtos.request.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

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

    @Test
    @Order(1)
    @DisplayName("SaveDoctor tests")
    void saveDoctor() {
    }

    @Test
    @Order(2)
    @DisplayName("GetALLDoctor tests")
    void getALLDoctors() {
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