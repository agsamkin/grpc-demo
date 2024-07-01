package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.example.grpc.common.GRPCData;
import lombok.ToString;

@Entity
@Table(name = "data")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id")
    private Long sensorId;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "measurement")
    private Double measurement;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    public Data(GRPCData data) {
        this.id = data.getId();
        this.sensorId = data.getSensorId();
        this.timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(
                        data.getTimestamp().getSeconds(),
                        data.getTimestamp().getNanos()
                ),
                ZoneId.systemDefault()
        );
        this.measurement = data.getMeasurement();
        this.measurementType = MeasurementType.valueOf(
                data.getMeasurementType().name()
        );
    }

    public enum MeasurementType {
        TEMPERATURE, VOLTAGE, POWER
    }

}
