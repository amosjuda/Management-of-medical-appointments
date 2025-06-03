package com.amosjuda.Management_of_medical_appointments.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientResponseDto {
    @Schema(description = "Patient UUID", example = "a2s49b2-7f45-366e-o64h-aj49968b3f5e")
    private UUID id;

    @Schema(description = "Patient name", example = "Zion Smith")
    private String name;

    @Schema(description = "Patient email", example = "zionsmith@gmail.com")
    private String email;

    @Schema(description = "Patient phone", example = "5588990663434")
    private String phone;

    @Schema(description = "Patient birthdate", example = "1997-07-15")
    private LocalDate birthdate;
}
