package com.agrotopya.service;

import com.agrotopya.model.SensorReading;
import com.agrotopya.repository.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest; // Pageable için gerekli import
import org.springframework.data.domain.Pageable;    // Pageable için gerekli import
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

    /**
     * Tüm sensör okumalarını getirir.
     * @return SensorReading listesi
     */
    public List<SensorReading> getAllSensorReadings() {
        return sensorReadingRepository.findAll();
    }

    /**
     * Belirli bir sensöre ait tüm okumaları getirir.
     * @param sensorId Sensör ID'si
     * @return Belirtilen sensöre ait SensorReading listesi
     */
    public List<SensorReading> getSensorReadingsBySensorId(Long sensorId) {
        return sensorReadingRepository.findBySensorId(sensorId);
    }

    /**
     * Belirli bir sensöre ait, verilen zaman aralığındaki okumaları getirir.
     * @param sensorId Sensör ID'si
     * @param startTime Başlangıç zamanı
     * @param endTime Bitiş zamanı
     * @return Belirtilen sensöre ve zaman aralığına ait SensorReading listesi
     */
    public List<SensorReading> getSensorReadingsBySensorIdAndTimeRange(
            Long sensorId, LocalDateTime startTime, LocalDateTime endTime) {
        return sensorReadingRepository.findBySensorIdAndTimestampBetween(sensorId, startTime, endTime);
    }

    /**
     * Belirli bir sensöre ait en son okumayı getirir.
     * @param sensorId Sensör ID'si
     * @return En son SensorReading nesnesi veya bulunamazsa null
     */
    public SensorReading getLatestSensorReading(Long sensorId) {
        // Sayfalama kullanarak sadece ilk sonucu almak için Pageable oluşturuyoruz.
        // (Repository'deki @Query'den LIMIT kaldırıldığı için bu gerekli)
        Pageable limitToOne = PageRequest.of(0, 1);
        List<SensorReading> results = sensorReadingRepository.findLatestBySensorId(sensorId, limitToOne);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Belirli bir sensöre ait, verilen zaman aralığındaki okumaların ortalamasını hesaplar.
     * @param sensorId Sensör ID'si
     * @param startTime Başlangıç zamanı
     * @param endTime Bitiş zamanı
     * @return Ortalama değer veya okuma yoksa null
     */
    public Double getAverageSensorReadingValue(Long sensorId, LocalDateTime startTime, LocalDateTime endTime) {
        return sensorReadingRepository.findAverageValueBySensorIdAndTimeRange(sensorId, startTime, endTime);
    }

    /**
     * Belirli bir ID'ye sahip sensör okumasını getirir.
     * @param id SensorReading ID'si
     * @return SensorReading nesnesini içeren Optional veya boş Optional
     */
    public Optional<SensorReading> getSensorReadingById(Long id) {
        return sensorReadingRepository.findById(id);
    }

    /**
     * Yeni bir sensör okuması oluşturur (kaydeder).
     * @param sensorReading Kaydedilecek SensorReading nesnesi
     * @return Kaydedilen SensorReading nesnesi
     */
    public SensorReading createSensorReading(SensorReading sensorReading) {
        // Gerekirse burada ek doğrulamalar veya işlemler yapılabilir.
        return sensorReadingRepository.save(sensorReading);
    }

    /**
     * Mevcut bir sensör okumasını günceller.
     * @param sensorReading Güncellenecek SensorReading nesnesi (ID'si set edilmiş olmalı)
     * @return Güncellenen SensorReading nesnesi
     */
    public SensorReading updateSensorReading(SensorReading sensorReading) {
        // Gerekirse burada ek doğrulamalar veya işlemler yapılabilir.
        // Örneğin, güncellenmek istenen ID'nin var olup olmadığı kontrol edilebilir.
        return sensorReadingRepository.save(sensorReading);
    }

    /**
     * Belirli bir ID'ye sahip sensör okumasını siler.
     * @param id Silinecek SensorReading ID'si
     */
    public void deleteSensorReading(Long id) {
        sensorReadingRepository.deleteById(id);
    }
}