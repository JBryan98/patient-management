package com.jbryan98.patientservice.mapper;

import com.jbryan98.patientservice.dto.PatientRequest;
import com.jbryan98.patientservice.dto.PatientResponse;
import com.jbryan98.patientservice.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PatientMapper {
    public PatientResponse toDto(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getDateOfBirth()
        );
    }

    public Patient toEntity(PatientRequest request) {
        return Patient
                .builder()
                .name(request.name())
                .address(request.address())
                .email(request.email())
                .dateOfBirth(LocalDate.parse(request.dateOfBirth()))
                .registeredDate(LocalDate.parse(request.registeredDate()))
                .build();
    }

    public void updateEntityFromDto(PatientRequest request, Patient patient) {
        patient.setName(request.name());
        patient.setAddress(request.address());
        patient.setEmail(request.email());
        patient.setDateOfBirth(LocalDate.parse(request.dateOfBirth()));
    }
}
