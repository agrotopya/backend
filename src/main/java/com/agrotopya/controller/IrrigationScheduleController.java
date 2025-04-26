package com.agrotopya.controller;

import com.agrotopya.model.IrrigationSchedule;
import com.agrotopya.service.IrrigationScheduleService;
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
@RequestMapping("/api/irrigation-schedules")
@CrossOrigin(origins = "*")
public class IrrigationScheduleController {

    private final IrrigationScheduleService irrigationScheduleService;

    @Autowired
    public IrrigationScheduleController(IrrigationScheduleService irrigationScheduleService) {
        this.irrigationScheduleService = irrigationScheduleService;
    }

    @GetMapping
    public ResponseEntity<List<IrrigationSchedule>> getAllIrrigationSchedules() {
        List<IrrigationSchedule> irrigationSchedules = irrigationScheduleService.getAllIrrigationSchedules();
        return new ResponseEntity<>(irrigationSchedules, HttpStatus.OK);
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<IrrigationSchedule>> getIrrigationSchedulesByFieldId(@PathVariable Long fieldId) {
        List<IrrigationSchedule> irrigationSchedules = irrigationScheduleService.getIrrigationSchedulesByFieldId(fieldId);
        return new ResponseEntity<>(irrigationSchedules, HttpStatus.OK);
    }

    @GetMapping("/field/{fieldId}/active")
    public ResponseEntity<List<IrrigationSchedule>> getActiveIrrigationSchedulesByFieldId(@PathVariable Long fieldId) {
        List<IrrigationSchedule> irrigationSchedules = irrigationScheduleService.getActiveIrrigationSchedulesByFieldId(fieldId);
        return new ResponseEntity<>(irrigationSchedules, HttpStatus.OK);
    }

    @GetMapping("/field/{fieldId}/automatic")
    public ResponseEntity<List<IrrigationSchedule>> getAutomaticIrrigationSchedulesByFieldId(@PathVariable Long fieldId) {
        List<IrrigationSchedule> irrigationSchedules = irrigationScheduleService.getAutomaticIrrigationSchedulesByFieldId(fieldId);
        return new ResponseEntity<>(irrigationSchedules, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<IrrigationSchedule>> getUpcomingIrrigationSchedules(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        List<IrrigationSchedule> irrigationSchedules = irrigationScheduleService.getUpcomingIrrigationSchedules(dateTime);
        return new ResponseEntity<>(irrigationSchedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IrrigationSchedule> getIrrigationScheduleById(@PathVariable Long id) {
        Optional<IrrigationSchedule> irrigationSchedule = irrigationScheduleService.getIrrigationScheduleById(id);
        return irrigationSchedule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<IrrigationSchedule> createIrrigationSchedule(@Valid @RequestBody IrrigationSchedule irrigationSchedule) {
        IrrigationSchedule createdIrrigationSchedule = irrigationScheduleService.createIrrigationSchedule(irrigationSchedule);
        return new ResponseEntity<>(createdIrrigationSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IrrigationSchedule> updateIrrigationSchedule(
            @PathVariable Long id, @Valid @RequestBody IrrigationSchedule irrigationSchedule) {
        Optional<IrrigationSchedule> existingIrrigationSchedule = irrigationScheduleService.getIrrigationScheduleById(id);
        if (existingIrrigationSchedule.isPresent()) {
            irrigationSchedule.setId(id);
            IrrigationSchedule updatedIrrigationSchedule = irrigationScheduleService.updateIrrigationSchedule(irrigationSchedule);
            return new ResponseEntity<>(updatedIrrigationSchedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIrrigationSchedule(@PathVariable Long id) {
        Optional<IrrigationSchedule> existingIrrigationSchedule = irrigationScheduleService.getIrrigationScheduleById(id);
        if (existingIrrigationSchedule.isPresent()) {
            irrigationScheduleService.deleteIrrigationSchedule(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
