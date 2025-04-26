package com.agrotopya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrrigationScheduleDto {
    private Long id;
    private Long fieldId;
    private String fieldName;
    private String name;
    private LocalTime startTime;
    private Integer durationMinutes;
    private Boolean isActive;
    private Boolean isAutomatic;
    private Double moistureThreshold;
}
