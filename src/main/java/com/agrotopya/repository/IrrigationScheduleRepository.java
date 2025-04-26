package com.agrotopya.repository;

import com.agrotopya.model.IrrigationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IrrigationScheduleRepository extends JpaRepository<IrrigationSchedule, Long> {
    List<IrrigationSchedule> findByFieldId(Long fieldId);
    List<IrrigationSchedule> findByFieldIdAndIsActive(Long fieldId, Boolean isActive);
    List<IrrigationSchedule> findByFieldIdAndIsAutomatic(Long fieldId, Boolean isAutomatic);
    List<IrrigationSchedule> findByNextRunBefore(LocalDateTime dateTime);
}
