package com.example.service.impl;

import com.example.grpc.common.GRPCData;
import com.example.model.Data;
import com.example.service.GRPCDataService;
import com.example.service.SummeryService;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.grpc.common.AnalyticsServerGrpc;
import com.example.grpc.common.GRPCAnalyticsRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class GRPCDataServiceImpl implements GRPCDataService {

    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();

    private final SummeryService summeryService;

    @GrpcClient(value = "data-store-async")
    private AnalyticsServerGrpc.AnalyticsServerStub asyncStub;

    @Value("${fetch.batch-size}")
    private int batchSize;

    @PostConstruct
    public void init() {
        fetchMessages();
    }

    @Override
    public void fetchMessages() {
        executorService.scheduleAtFixedRate(
                () -> asyncStub.askForData(
                        GRPCAnalyticsRequest.newBuilder()
                                .setBatchSize(batchSize).build(),
                        new StreamObserver<GRPCData>() {
                            @Override
                            public void onNext(GRPCData grpcData) {
                                summeryService.handle(new Data(grpcData));
                            }

                            @Override
                            public void onError(Throwable throwable) {
                            }

                            @Override
                            public void onCompleted() {
                                log.info("Batch was handled.");
                            }
                        })
                , 0, 10, TimeUnit.SECONDS);
    }

}
