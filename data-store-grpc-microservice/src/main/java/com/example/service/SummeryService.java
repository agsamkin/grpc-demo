package com.example.service;

import com.example.model.Data;
import com.example.model.Summary;

import java.util.Set;

public interface SummeryService {

    Summary getSummery(
            long sensorId,
            Set<Data.MeasurementType> measurementTypes,
            Set<Summary.SummaryType> summaryTypes);

    void handle(Data data);

}
