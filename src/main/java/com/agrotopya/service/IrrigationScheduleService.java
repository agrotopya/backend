package com.agrotopya.service;

import com.agrotopya.model.IrrigationSchedule;
import com.agrotopya.repository.IrrigationScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IrrigationScheduleService {

    private final IrrigationScheduleRepository irrigationScheduleRepository;

    @Autowired
    public IrrigationScheduleService(IrrigationScheduleRepository irrigationScheduleRepository) {
        this.irrigationScheduleRepository = irrigationScheduleRepository;
    }

    public List<IrrigationSchedule> getAllIrrigationSchedules() {
        return irrigationScheduleRepository.findAll();
    }

    public List<IrrigationSchedule> getIrrigationSchedulesByFieldId(Long fieldId) {
        return irrigationScheduleRepository.findByFieldId(fieldId);
    }

    public List<IrrigationSchedule> getActiveIrrigationSchedulesByFieldId(Long fieldId) {
        return irrigationScheduleRepository.findByFieldIdAndIsActive(fieldId, true);
    }

    public List<IrrigationSchedule> getAutomaticIrrigationSchedulesByFieldId(Long fieldId) {
        return irrigationScheduleRepository.findByFieldIdAndIsAutomatic(fieldId, true);
    }

    public List<IrrigationSchedule> getUpcomingIrrigationSchedules(LocalDateTime dateTime) {
        return irrigationScheduleRepository.findByNextRunBefore(dateTime);
    }

    public Optional<IrrigationSchedule> getIrrigationScheduleById(Long id) {
        return irrigationScheduleRepository.findById(id);
    }

    public IrrigationSchedule createIrrigationSchedule(IrrigationSchedule irrigationSchedule) {
        return irrigationScheduleRepository.save(irrigationSchedule);
    }

    public IrrigationSchedule updateIrrigationSchedule(IrrigationSchedule irrigationSchedule) {
        return irrigationScheduleRepository.save(irrigationSchedule);
    }

    public void deleteIrrigationSchedule(Long id) {
        irrigationScheduleRepository.deleteById(id);
    }
}
