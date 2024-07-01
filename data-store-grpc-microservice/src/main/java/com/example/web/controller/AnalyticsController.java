package com.example.web.controller;

import com.example.model.Data;
import com.example.model.Summary;

import com.example.service.SummeryService;

import com.example.web.dto.SummaryDto;
import com.example.web.mapper.SummeryMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final SummeryService summeryService;
    private final SummeryMapper summeryMapper;

    @GetMapping("/summary/{sensorId}")
    public SummaryDto getSummary(
            @PathVariable long sensorId,
            @RequestParam(value = "mt", required = false) Set<Data.MeasurementType> measurementTypes,
            @RequestParam(value = "st", required = false) Set<Summary.SummaryType> summaryTypes) {

        Summary summary = summeryService.getSummery(sensorId, measurementTypes, summaryTypes);
        return summeryMapper.toDto(summary);

    }

}
