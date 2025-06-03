package com.amosjuda.Management_of_medical_appointments.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoctorResponseDto {
    @Schema(description = "Doctor UUID", example = "2c4e59b2-5f45-455e-a64l-ec49968b3f5e")
    private UUID id;

    @Schema(description = "Doctor name", example = "Antoni Green")
    private String name;

    @Schema(description = "Doctor specialty", example = "Orthopedist")
    private String specialty;

    @Schema(description = "Doctor email", example = "antonigreen@gmail.com")
    private String email;
}
