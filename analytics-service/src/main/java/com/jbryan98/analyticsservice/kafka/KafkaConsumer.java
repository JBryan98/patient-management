package com.jbryan98.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumerEvent(byte[] event) {
        try {
            var patientEvent = PatientEvent.parseFrom(event);
            // Perform any business logic related to analytics here, e.g., save to database, update metrics, etc.
            log.info("Received Patient Event: {}", patientEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing patient event: {}", e.getMessage());
        }
    }
}
