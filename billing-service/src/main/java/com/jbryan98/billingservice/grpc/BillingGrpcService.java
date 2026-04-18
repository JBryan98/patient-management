package com.jbryan98.billingservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request {}", request);
        // Business logic - e.g save to database, perform calculations, etc.
        var response = BillingResponse.newBuilder().setAccountId("123").setStatus("ACTIVE").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
