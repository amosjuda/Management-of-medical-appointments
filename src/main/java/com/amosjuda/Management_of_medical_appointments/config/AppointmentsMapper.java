package com.amosjuda.Management_of_medical_appointments.config;

import com.amosjuda.Management_of_medical_appointments.dtos.request.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.response.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AppointmentsMapper {

    @Mapping(target = "idAppointments", ignore = true)
    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "patient", source = "patient")
    Appointments toEntity(AppointmentsRequestDto dto, Doctor doctor, Patient patient);

    @Mapping(source = "idAppointments", target = "id")
    @Mapping(source = "doctor", target = "doctor")
    @Mapping(source = "patient", target = "patient")
    AppointmentsResponseDto toResponseDto(Appointments appointment);
}
