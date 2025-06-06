package com.agrotopya.repository;

import com.agrotopya.model.SensorReading;
import org.springframework.data.domain.Pageable; // <-- Eklendi
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    List<SensorReading> findBySensorId(Long sensorId);

    List<SensorReading> findBySensorIdAndTimestampBetween(
        Long sensorId, LocalDateTime startTime, LocalDateTime endTime);

    // @Query("SELECT sr FROM SensorReading sr WHERE sr.sensor.id = :sensorId ORDER BY sr.timestamp DESC LIMIT 1") // <-- Eski Hali
    // SensorReading findLatestBySensorId(Long sensorId); // <-- Eski Hali

    // --- YENİ HALİ ---
    @Query("SELECT sr FROM SensorReading sr WHERE sr.sensor.id = :sensorId ORDER BY sr.timestamp DESC")
    List<SensorReading> findLatestBySensorId(Long sensorId, Pageable pageable);
    // --- /YENİ HALİ ---

    @Query("SELECT AVG(sr.value) FROM SensorReading sr WHERE sr.sensor.id = :sensorId AND sr.timestamp BETWEEN :startTime AND :endTime")
    Double findAverageValueBySensorIdAndTimeRange(Long sensorId, LocalDateTime startTime, LocalDateTime endTime);

    SensorReading findLatestBySensorId(Long sensorId);
}