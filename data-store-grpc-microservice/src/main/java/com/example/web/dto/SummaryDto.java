package com.example.web.dto;

import com.example.model.Data;
import com.example.model.Summary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SummaryDto {

    private Long sensorId;

    private Map<Data.MeasurementType, List<Summary.SummaryEntry>> values;

}
