package com.jbryan98.patientservice.dto;

import com.jbryan98.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatientRequest(
        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must not exceed 100 characters.")
        String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email should be valid.")
        String email,

        @NotBlank(message = "Address is required.")
        String address,

        @NotBlank(message = "Date of Birth is required.")
        String dateOfBirth,

        /*
         * Required ONLY when creating a patient.
         * This constraint belongs exclusively to CreatePatientValidationGroup
         * and will not be validated during updates.
         */
        @NotNull(groups = CreatePatientValidationGroup.class, message = "Registered date is required")
        String registeredDate
) {
}
