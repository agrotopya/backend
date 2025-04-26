package com.agrotopya.service;

import com.agrotopya.model.Sensor;
import com.agrotopya.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public List<Sensor> getSensorsByFieldId(Long fieldId) {
        return sensorRepository.findByFieldId(fieldId);
    }

    public List<Sensor> getSensorsByFieldIdAndType(Long fieldId, String type) {
        return sensorRepository.findByFieldIdAndType(fieldId, type);
    }

    public List<Sensor> getActiveSensorsByFieldId(Long fieldId) {
        return sensorRepository.findByFieldIdAndIsActive(fieldId, true);
    }

    public Optional<Sensor> getSensorById(Long id) {
        return sensorRepository.findById(id);
    }

    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Sensor updateSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
    }
}
