package com.amosjuda.Management_of_medical_appointments.config;

import com.amosjuda.Management_of_medical_appointments.dtos.AppointmentsRequestDto;
import com.amosjuda.Management_of_medical_appointments.dtos.AppointmentsResponseDto;
import com.amosjuda.Management_of_medical_appointments.models.Appointments;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.models.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-29T16:12:45-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class AppointmentsMapperImpl implements AppointmentsMapper {

    @Override
    public Appointments toEntity(AppointmentsRequestDto dto, Doctor doctor, Patient patient) {
        if ( dto == null && doctor == null && patient == null ) {
            return null;
        }

        Appointments appointments = new Appointments();

        if ( dto != null ) {
            appointments.setDateTime( dto.getDateTime() );
            appointments.setStatus( dto.getStatus() );
            appointments.setNotes( dto.getNotes() );
        }
        appointments.setDoctor( doctor );
        appointments.setPatient( patient );

        return appointments;
    }

    @Override
    public AppointmentsResponseDto toResponseDto(Appointments appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentsResponseDto.AppointmentsResponseDtoBuilder appointmentsResponseDto = AppointmentsResponseDto.builder();

        appointmentsResponseDto.doctor( appointment.getDoctor() );
        appointmentsResponseDto.patient( appointment.getPatient() );
        appointmentsResponseDto.dateTime( appointment.getDateTime() );
        appointmentsResponseDto.status( appointment.getStatus() );
        appointmentsResponseDto.notes( appointment.getNotes() );

        return appointmentsResponseDto.build();
    }
}
