package com.agrotopya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadingDto {
    private Long id;
    private Long sensorId;
    private String sensorName;
    private String sensorType;
    private Double value;
    private String unit;
    private LocalDateTime timestamp;
}
