package com.jbryan98.patientservice.controller;

import com.jbryan98.patientservice.dto.PatientRequest;
import com.jbryan98.patientservice.dto.PatientResponse;
import com.jbryan98.patientservice.dto.validators.CreatePatientValidationGroup;
import com.jbryan98.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

//2:13:54
@RestController
@RequestMapping("/patients")
@Tag(name = "Patient API", description = "Endpoints for managing patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService service;

    @GetMapping
    @Operation(summary = "Get paginated list of patients", description = "Returns a paginated list of patients. Supports pagination parameters like page, size, and sorting.")
    public ResponseEntity<Page<PatientResponse>> getPatients(Pageable pageable) {
        return ResponseEntity.ok(service.getPatients(pageable));
    }

    /**
     * Creates a new patient
     * Validation groups:
     * - Default: validates common fields (name, email, address, dateOfBirth)
     * - CreatePatientValidationGroup: validates fields required only on creation
     *   (e.g. registeredDate)
     */
    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponse> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody @Valid PatientRequest request) {
        return new ResponseEntity<>(service.createPatient(request), HttpStatus.CREATED);
    }


    /**
     * Updates an existing patient.
     * Only Default validation group is applied.
     * Fields specific to creation (e.g. registeredDate) are NOT validated here.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing patient")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable UUID id, @RequestBody @Validated({Default.class}) PatientRequest request) {
        return ResponseEntity.ok(service.updatePatient(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
