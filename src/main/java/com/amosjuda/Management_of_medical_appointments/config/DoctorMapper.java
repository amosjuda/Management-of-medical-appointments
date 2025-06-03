package com.amosjuda.Management_of_medical_appointments.config;

import com.amosjuda.Management_of_medical_appointments.dtos.request.DoctorRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.DoctorResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "idDoctor", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Doctor toEntity(DoctorRequestDto dto);

    @Mapping(target = "id", source = "idDoctor")
    DoctorResponseDto toDto(Doctor entity);
}
