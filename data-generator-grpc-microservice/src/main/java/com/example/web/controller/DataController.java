package com.example.web.controller;

import com.example.model.Data;
import com.example.model.test.DataTestOptions;

import com.example.service.GRPCDataService;
import com.example.service.TestDataService;

import com.example.web.dto.DataDto;
import com.example.web.dto.DataTestOptionsDto;
import com.example.web.mapper.DataMapper;
import com.example.web.mapper.DataTestOptionsMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data")
public class DataController {

    private final DataMapper dataMapper;
    private final DataTestOptionsMapper dataTestOptionsMapper;

    private final GRPCDataService grpcDataService;
    private final TestDataService testDataService;

    @PostMapping("/send")
    public void send(@RequestBody DataDto dataDto) {
        Data data = dataMapper.toEntity(dataDto);
        grpcDataService.send(data);
    }

    @PostMapping("/test/send")
    public void testSend(@RequestBody DataTestOptionsDto testOptionsDto) {
        DataTestOptions testOptions = dataTestOptionsMapper.toEntity(testOptionsDto);
        testDataService.sendMessages(testOptions);
    }

}
