package com.example.service.impl;

import com.example.model.Data;
import com.example.model.test.DataTestOptions;
import com.example.service.GRPCDataService;
import com.example.service.TestDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class TestDataServiceImpl implements TestDataService {

    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();

    private final GRPCDataService grpcDataService;

    @Value("${push.batch-size}")
    private int batchSize;

    @Override
    public void sendMessages(DataTestOptions testOptions) {
        List<Data> dataBatch = new ArrayList<>();
        if (testOptions.getMeasurementTypes().length > 0) {
            executorService.scheduleAtFixedRate(
                    () -> {
                        Data data = new Data();
                        data.setSensorId((long) getRandomNumber(1, 10));
                        data.setMeasurement(getRandomNumber(15, 20));
                        data.setMeasurementType(
                                getRandomMeasurementType(testOptions.getMeasurementTypes()));
                        data.setTimestamp(LocalDateTime.now());
                        dataBatch.add(data);
                        if (dataBatch.size() == batchSize) {
                            grpcDataService.send(dataBatch);
                            dataBatch.clear();
                        }
                    },0,
                    testOptions.getDelayInSeconds(),
                    TimeUnit.SECONDS);
        }
    }

    private double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    private Data.MeasurementType getRandomMeasurementType(Data.MeasurementType[] measurementTypes) {
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }

}
