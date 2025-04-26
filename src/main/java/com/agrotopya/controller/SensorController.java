package com.agrotopya.controller;

import com.agrotopya.model.Sensor;
import com.agrotopya.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(origins = "*")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<Sensor>> getSensorsByFieldId(@PathVariable Long fieldId) {
        List<Sensor> sensors = sensorService.getSensorsByFieldId(fieldId);
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @GetMapping("/field/{fieldId}/type/{type}")
    public ResponseEntity<List<Sensor>> getSensorsByFieldIdAndType(
            @PathVariable Long fieldId, @PathVariable String type) {
        List<Sensor> sensors = sensorService.getSensorsByFieldIdAndType(fieldId, type);
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @GetMapping("/field/{fieldId}/active")
    public ResponseEntity<List<Sensor>> getActiveSensorsByFieldId(@PathVariable Long fieldId) {
        List<Sensor> sensors = sensorService.getActiveSensorsByFieldId(fieldId);
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        return sensor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@Valid @RequestBody Sensor sensor) {
        Sensor createdSensor = sensorService.createSensor(sensor);
        return new ResponseEntity<>(createdSensor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @Valid @RequestBody Sensor sensor) {
        Optional<Sensor> existingSensor = sensorService.getSensorById(id);
        if (existingSensor.isPresent()) {
            sensor.setId(id);
            Sensor updatedSensor = sensorService.updateSensor(sensor);
            return new ResponseEntity<>(updatedSensor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        Optional<Sensor> existingSensor = sensorService.getSensorById(id);
        if (existingSensor.isPresent()) {
            sensorService.deleteSensor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
