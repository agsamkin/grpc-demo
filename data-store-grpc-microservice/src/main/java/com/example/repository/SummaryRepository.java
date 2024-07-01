package com.example.repository;

import com.example.model.Data;
import com.example.model.Summary;

import java.util.Optional;
import java.util.Set;

public interface SummaryRepository {

    Optional<Summary> findBySensorId(
            long sensorId,
            Set<Data.MeasurementType> measurementTypes,
            Set<Summary.SummaryType> summaryTypes);

    void handle(Data data);

}
