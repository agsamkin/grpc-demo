package com.example.service.impl;

import com.example.model.Data;
import com.example.model.Summary;
import com.example.model.exception.SensorNotFoundException;

import com.example.repository.SummaryRepository;

import com.example.service.SummeryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SummeryServiceImpl implements SummeryService {

    private final SummaryRepository summaryRepository;

    @Override
    public Summary getSummery(
            long sensorId,
            Set<Data.MeasurementType> measurementTypes,
            Set<Summary.SummaryType> summaryTypes) {

        return summaryRepository.findBySensorId(
                sensorId,
                measurementTypes == null ? Set.of(Data.MeasurementType.values()) : measurementTypes,
                summaryTypes == null ? Set.of(Summary.SummaryType.values()) : summaryTypes
                ).orElseThrow(SensorNotFoundException::new);

    }

    @Override
    public void handle(Data data) {
        summaryRepository.handle(data);
    }

}
