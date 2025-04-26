package com.agrotopya.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "irrigation_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrrigationSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private LocalTime startTime;
    
    @Column(nullable = false)
    private Integer durationMinutes;
    
    @Column
    private Boolean isActive = true;
    
    @Column
    private Boolean isAutomatic = false; // true if controlled by AI, false if manually scheduled
    
    @Column
    private Double moistureThreshold; // only used if isAutomatic is true
    
    @Column
    private LocalDateTime lastRun;
    
    @Column
    private LocalDateTime nextRun;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;
}
