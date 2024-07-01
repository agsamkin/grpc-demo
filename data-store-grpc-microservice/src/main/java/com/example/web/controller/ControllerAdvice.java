package com.example.web.controller;

import com.example.model.exception.SensorNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(SensorNotFoundException.class)
    public String sensorNotFound(SensorNotFoundException e) {
        return "Sensor not found";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        e.printStackTrace();
        return "Something happened.";
    }

}
