package com.agrotopya.repository;

import com.agrotopya.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByFieldId(Long fieldId);
    List<Sensor> findByFieldIdAndType(Long fieldId, String type);
    List<Sensor> findByFieldIdAndIsActive(Long fieldId, Boolean isActive);
}
