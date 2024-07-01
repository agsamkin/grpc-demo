package com.example.service;

import com.example.model.Data;

import java.util.List;

public interface DataService {

    void handle(Data data);

    List<Data> getWithBatch(long batchSize);

}
