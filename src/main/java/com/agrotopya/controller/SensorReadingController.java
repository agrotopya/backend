package com.agrotopya.controller;

import com.agrotopya.model.SensorReading;
import com.agrotopya.service.SensorReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor-readings")
@CrossOrigin(origins = "*")
public class SensorReadingController {

    private final SensorReadingService sensorReadingService;

    @Autowired
    public SensorReadingController(SensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @GetMapping
    public ResponseEntity<List<SensorReading>> getAllSensorReadings() {
        List<SensorReading> sensorReadings = sensorReadingService.getAllSensorReadings();
        return new ResponseEntity<>(sensorReadings, HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<List<SensorReading>> getSensorReadingsBySensorId(@PathVariable Long sensorId) {
        List<SensorReading> sensorReadings = sensorReadingService.getSensorReadingsBySensorId(sensorId);
        return new ResponseEntity<>(sensorReadings, HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}/timerange")
    public ResponseEntity<List<SensorReading>> getSensorReadingsBySensorIdAndTimeRange(
            @PathVariable Long sensorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<SensorReading> sensorReadings = sensorReadingService.getSensorReadingsBySensorIdAndTimeRange(
                sensorId, startTime, endTime);
        return new ResponseEntity<>(sensorReadings, HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}/latest")
    public ResponseEntity<SensorReading> getLatestSensorReading(@PathVariable Long sensorId) {
        SensorReading sensorReading = sensorReadingService.getLatestSensorReading(sensorId);
        if (sensorReading != null) {
            return new ResponseEntity<>(sensorReading, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sensor/{sensorId}/average")
    public ResponseEntity<Double> getAverageSensorReadingValue(
            @PathVariable Long sensorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Double average = sensorReadingService.getAverageSensorReadingValue(sensorId, startTime, endTime);
        if (average != null) {
            return new ResponseEntity<>(average, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorReading> getSensorReadingById(@PathVariable Long id) {
        Optional<SensorReading> sensorReading = sensorReadingService.getSensorReadingById(id);
        return sensorReading.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SensorReading> createSensorReading(@Valid @RequestBody SensorReading sensorReading) {
        SensorReading createdSensorReading = sensorReadingService.createSensorReading(sensorReading);
        return new ResponseEntity<>(createdSensorReading, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorReading> updateSensorReading(
            @PathVariable Long id, @Valid @RequestBody SensorReading sensorReading) {
        Optional<SensorReading> existingSensorReading = sensorReadingService.getSensorReadingById(id);
        if (existingSensorReading.isPresent()) {
            sensorReading.setId(id);
            SensorReading updatedSensorReading = sensorReadingService.updateSensorReading(sensorReading);
            return new ResponseEntity<>(updatedSensorReading, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensorReading(@PathVariable Long id) {
        Optional<SensorReading> existingSensorReading = sensorReadingService.getSensorReadingById(id);
        if (existingSensorReading.isPresent()) {
            sensorReadingService.deleteSensorReading(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
