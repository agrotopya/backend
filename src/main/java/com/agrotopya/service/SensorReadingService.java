package com.agrotopya.service;

import com.agrotopya.model.SensorReading;
import com.agrotopya.repository.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    @Autowired
    public SensorReadingService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    public List<SensorReading> getAllSensorReadings() {
        return sensorReadingRepository.findAll();
    }

    public List<SensorReading> getSensorReadingsBySensorId(Long sensorId) {
        return sensorReadingRepository.findBySensorId(sensorId);
    }

    public List<SensorReading> getSensorReadingsBySensorIdAndTimeRange(
            Long sensorId, LocalDateTime startTime, LocalDateTime endTime) {
        return sensorReadingRepository.findBySensorIdAndTimestampBetween(sensorId, startTime, endTime);
    }

    public SensorReading getLatestSensorReading(Long sensorId) {
        return sensorReadingRepository.findLatestBySensorId(sensorId);
    }

    public Double getAverageSensorReadingValue(Long sensorId, LocalDateTime startTime, LocalDateTime endTime) {
        return sensorReadingRepository.findAverageValueBySensorIdAndTimeRange(sensorId, startTime, endTime);
    }

    public Optional<SensorReading> getSensorReadingById(Long id) {
        return sensorReadingRepository.findById(id);
    }

    public SensorReading createSensorReading(SensorReading sensorReading) {
        return sensorReadingRepository.save(sensorReading);
    }

    public SensorReading updateSensorReading(SensorReading sensorReading) {
        return sensorReadingRepository.save(sensorReading);
    }

    public void deleteSensorReading(Long id) {
        sensorReadingRepository.deleteById(id);
    }
}
