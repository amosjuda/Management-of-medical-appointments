package com.amosjuda.Management_of_medical_appointments.config;

import com.amosjuda.Management_of_medical_appointments.dtos.request.PatientRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.PatientResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "idPatient", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Patient toEntity(PatientRequestDto dto);

    @Mapping(target = "id", source = "idPatient")
    PatientResponseDto toDto(Patient entity);
}
