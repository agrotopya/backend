package com.agrotopya.controller;

import com.agrotopya.dto.DashboardStatsDto;
import com.agrotopya.model.Field;
import com.agrotopya.model.IrrigationSchedule;
import com.agrotopya.model.Sensor;
import com.agrotopya.model.SensorReading;
import com.agrotopya.service.FieldService;
import com.agrotopya.service.IrrigationScheduleService;
import com.agrotopya.service.SensorReadingService;
import com.agrotopya.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final FieldService fieldService;
    private final SensorService sensorService;
    private final SensorReadingService sensorReadingService;
    private final IrrigationScheduleService irrigationScheduleService;

    @Autowired
    public DashboardController(
            FieldService fieldService,
            SensorService sensorService,
            SensorReadingService sensorReadingService,
            IrrigationScheduleService irrigationScheduleService) {
        this.fieldService = fieldService;
        this.sensorService = sensorService;
        this.sensorReadingService = sensorReadingService;
        this.irrigationScheduleService = irrigationScheduleService;
    }

    @GetMapping("/stats/user/{userId}")
    public ResponseEntity<DashboardStatsDto> getDashboardStats(@PathVariable Long userId) {
        // Get user's fields
        List<Field> fields = fieldService.getFieldsByUserId(userId);
        
        if (fields.isEmpty()) {
            return new ResponseEntity<>(new DashboardStatsDto(0, 0, 0, 0, 0.0, 0.0, 0, 0), HttpStatus.OK);
        }
        
        int totalFields = fields.size();
        int totalSensors = 0;
        int activeSensors = 0;
        int activeSchedules = 0;
        double totalSoilMoisture = 0.0;
        int soilMoistureSensorCount = 0;
        double totalTemperature = 0.0;
        int temperatureSensorCount = 0;
        int scheduledIrrigationsToday = 0;
        int completedIrrigationsToday = 0;
        
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        
        for (Field field : fields) {
            // Get sensors for this field
            List<Sensor> sensors = sensorService.getSensorsByFieldId(field.getId());
            totalSensors += sensors.size();
            
            for (Sensor sensor : sensors) {
                if (sensor.getIsActive()) {
                    activeSensors++;
                    
                    // Get latest reading for this sensor
                    SensorReading latestReading = sensorReadingService.getLatestSensorReading(sensor.getId());
                    if (latestReading != null) {
                        if ("SOIL_MOISTURE".equals(sensor.getType())) {
                            totalSoilMoisture += latestReading.getValue();
                            soilMoistureSensorCount++;
                        } else if ("TEMPERATURE".equals(sensor.getType())) {
                            totalTemperature += latestReading.getValue();
                            temperatureSensorCount++;
                        }
                    }
                }
            }
            
            // Get irrigation schedules for this field
            List<IrrigationSchedule> schedules = irrigationScheduleService.getIrrigationSchedulesByFieldId(field.getId());
            for (IrrigationSchedule schedule : schedules) {
                if (schedule.getIsActive()) {
                    activeSchedules++;
                    
                    // Check if scheduled for today
                    if (schedule.getNextRun() != null && 
                        schedule.getNextRun().isAfter(startOfDay) && 
                        schedule.getNextRun().isBefore(endOfDay)) {
                        scheduledIrrigationsToday++;
                    }
                    
                    // Check if completed today
                    if (schedule.getLastRun() != null && 
                        schedule.getLastRun().isAfter(startOfDay) && 
                        schedule.getLastRun().isBefore(endOfDay)) {
                        completedIrrigationsToday++;
                    }
                }
            }
        }
        
        double averageSoilMoisture = soilMoistureSensorCount > 0 ? totalSoilMoisture / soilMoistureSensorCount : 0.0;
        double averageTemperature = temperatureSensorCount > 0 ? totalTemperature / temperatureSensorCount : 0.0;
        
        DashboardStatsDto stats = new DashboardStatsDto(
            totalFields,
            totalSensors,
            activeSensors,
            activeSchedules,
            averageSoilMoisture,
            averageTemperature,
            scheduledIrrigationsToday,
            completedIrrigationsToday
        );
        
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
