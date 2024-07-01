package com.example.service.impl;

import com.example.model.Data;
import com.example.repository.DataRepository;
import com.example.service.DataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    @Override
    public void handle(Data data) {
        log.info("Data object {} was saved", data);
        dataRepository.save(data);
    }

    @Transactional
    @Override
    public List<Data> getWithBatch(long batchSize) {
        List<Data> data = dataRepository.findAllWithOffset(batchSize);
        if (data.size() > 0) {
            dataRepository.incrementOffset(Math.min(batchSize, data.size()));
        }
        return data;
    }

}
