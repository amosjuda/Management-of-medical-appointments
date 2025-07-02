package com.amosjuda.Management_of_medical_appointments.config;

import com.amosjuda.Management_of_medical_appointments.dtos.request.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.PatientResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-02T12:21:01-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Ubuntu)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public Patient toEntity(PatientRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setName( dto.getName() );
        patient.setEmail( dto.getEmail() );
        patient.setPhone( dto.getPhone() );
        patient.setBirthdate( dto.getBirthdate() );

        return patient;
    }

    @Override
    public PatientResponseDto toDto(Patient entity) {
        if ( entity == null ) {
            return null;
        }

        PatientResponseDto.PatientResponseDtoBuilder patientResponseDto = PatientResponseDto.builder();

        patientResponseDto.id( entity.getIdPatient() );
        patientResponseDto.name( entity.getName() );
        patientResponseDto.email( entity.getEmail() );
        patientResponseDto.phone( entity.getPhone() );
        patientResponseDto.birthdate( entity.getBirthdate() );

        return patientResponseDto.build();
    }
}
