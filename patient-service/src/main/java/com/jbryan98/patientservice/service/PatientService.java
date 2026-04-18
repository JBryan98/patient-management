package com.jbryan98.patientservice.service;

import com.jbryan98.patientservice.dto.PatientResponse;
import com.jbryan98.patientservice.dto.PatientRequest;
import com.jbryan98.patientservice.exception.EmailAlreadyExistsException;
import com.jbryan98.patientservice.exception.PatientNotFoundException;
import com.jbryan98.patientservice.grpc.BillingServiceGrpcClient;
import com.jbryan98.patientservice.kafka.KafkaProducer;
import com.jbryan98.patientservice.mapper.PatientMapper;
import com.jbryan98.patientservice.model.Patient;
import com.jbryan98.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;
    private final PatientMapper mapper;
    private final BillingServiceGrpcClient grpcClient;
    private final KafkaProducer kafkaProducer;

    public Page<PatientResponse> getPatients(Pageable pageable) {
        log.info("findAll pageable {}", pageable);
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public Patient getPatientById(UUID id) {
        log.info("getPatientById id {}", id);
        return repository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
    }

    public PatientResponse createPatient(PatientRequest request) {
        log.info("createPatient request {}", request);
        var newPatient = mapper.toEntity(request);
        validateUqEmail(newPatient.getEmail());
        repository.save(newPatient);
        grpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());
        kafkaProducer.sendEvent(newPatient);
        return mapper.toDto(newPatient);
    }

    public PatientResponse updatePatient(UUID id, PatientRequest request) {
        log.info("updatePatient id {} request {}", id, request);
        var patient = getPatientById(id);
        if (!patient.getEmail().equals(request.email())) {
            validateUqEmail(request.email());
        }
        mapper.updateEntityFromDto(request, patient);
        repository.save(patient);
        return mapper.toDto(patient);
    }

    public void deletePatient(UUID id) {
        log.info("deletePatient id {}", id);
        var patient = getPatientById(id);
        repository.delete(patient);
    }

    private void validateUqEmail(String email) {
        log.info("validateUqEmail email {}", email);
        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists: " + email);
        }
    }
}
