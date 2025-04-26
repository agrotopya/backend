package com.agrotopya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private Integer totalFields;
    private Integer totalSensors;
    private Integer activeSensors;
    private Integer activeSchedules;
    private Double averageSoilMoisture;
    private Double averageTemperature;
    private Integer scheduledIrrigationsToday;
    private Integer completedIrrigationsToday;
}
