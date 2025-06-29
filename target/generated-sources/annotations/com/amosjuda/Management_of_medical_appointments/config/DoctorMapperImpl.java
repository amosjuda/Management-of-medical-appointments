package com.amosjuda.Management_of_medical_appointments.config;

import com.amosjuda.Management_of_medical_appointments.dtos.request.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-29T17:46:26-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public Doctor toEntity(DoctorRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setName( dto.getName() );
        doctor.setSpecialty( dto.getSpecialty() );
        doctor.setEmail( dto.getEmail() );

        return doctor;
    }

    @Override
    public DoctorResponseDto toDto(Doctor entity) {
        if ( entity == null ) {
            return null;
        }

        DoctorResponseDto.DoctorResponseDtoBuilder doctorResponseDto = DoctorResponseDto.builder();

        doctorResponseDto.id( entity.getIdDoctor() );
        doctorResponseDto.name( entity.getName() );
        doctorResponseDto.specialty( entity.getSpecialty() );
        doctorResponseDto.email( entity.getEmail() );

        return doctorResponseDto.build();
    }
}
