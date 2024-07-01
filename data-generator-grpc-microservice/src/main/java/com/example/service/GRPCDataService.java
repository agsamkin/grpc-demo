package com.example.service;

import com.example.model.Data;

import java.util.List;

public interface GRPCDataService {

    void send(Data data);

    void send(List<Data> data);

}
