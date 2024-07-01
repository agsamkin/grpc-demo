package com.example.service.impl;

import com.example.grpc.common.AnalyticsServerGrpc;
import com.example.grpc.common.GRPCAnalyticsRequest;
import com.example.grpc.common.GRPCData;
import com.example.model.Data;
import com.example.service.DataService;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.ZoneOffset;
import java.util.List;

import com.example.grpc.common.MeasurementType;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class GRPCAnalyticsService extends AnalyticsServerGrpc.AnalyticsServerImplBase {

    private final DataService dataService;

    @Override
    public void askForData(GRPCAnalyticsRequest request, StreamObserver<GRPCData> responseObserver) {
        List<Data> data = dataService.getWithBatch(request.getBatchSize());
        for (Data d : data) {
            GRPCData dataRequest = GRPCData.newBuilder()
                    .setSensorId(d.getSensorId())
                    .setTimestamp(Timestamp.newBuilder()
                            .setSeconds(
                                    d.getTimestamp().toEpochSecond(ZoneOffset.UTC)
                            ).build())
                    .setMeasurement(d.getMeasurement())
                    .setMeasurementType(MeasurementType.valueOf(
                            d.getMeasurementType().name()
                    )).build();
            responseObserver.onNext(dataRequest);
        }
        log.info("Batch was send");
        responseObserver.onCompleted();
    }
}
