package com.example.service.impl;

import com.example.grpc.common.DataServerGrpc;
import com.example.grpc.common.GRPCData;
import com.example.grpc.common.MeasurementType;
import com.example.model.Data;
import com.example.service.GRPCDataService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GRPCDataServiceImpl implements GRPCDataService {

    @GrpcClient(value = "data-generator-blocking")
    private DataServerGrpc.DataServerBlockingStub blockingStub;

    @GrpcClient(value = "data-generator-async")
    private DataServerGrpc.DataServerStub asyncStub;

    @Override
    public void send(Data data) {
        blockingStub.addData(toGRPCData(data));
    }

    @Override
    public void send(List<Data> data) {

        StreamObserver<Empty> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(Empty empty) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }
        };

        StreamObserver<GRPCData> requestObserver = asyncStub.addStreamOfData(responseObserver);
        for (Data d : data) {
            GRPCData request = toGRPCData(d);
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();

    }

    private GRPCData toGRPCData(Data data) {
        return GRPCData.newBuilder()
                .setSensorId(data.getSensorId())
                .setTimestamp(
                        Timestamp.newBuilder()
                                .setSeconds(
                                        data.getTimestamp()
                                                .toEpochSecond(ZoneOffset.UTC))
                                .build()
                )
                .setMeasurementType(
                        MeasurementType.valueOf(data.getMeasurementType().name())
                )
                .setMeasurement(data.getMeasurement())
                .build();
    }

}
