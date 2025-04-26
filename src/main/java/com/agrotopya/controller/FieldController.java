package com.agrotopya.controller;

import com.agrotopya.model.Field;
import com.agrotopya.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fields")
@CrossOrigin(origins = "*")
public class FieldController {

    private final FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    public ResponseEntity<List<Field>> getAllFields() {
        List<Field> fields = fieldService.getAllFields();
        return new ResponseEntity<>(fields, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Field>> getFieldsByUserId(@PathVariable Long userId) {
        List<Field> fields = fieldService.getFieldsByUserId(userId);
        return new ResponseEntity<>(fields, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> getFieldById(@PathVariable Long id) {
        Optional<Field> field = fieldService.getFieldById(id);
        return field.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Field> createField(@Valid @RequestBody Field field) {
        Field createdField = fieldService.createField(field);
        return new ResponseEntity<>(createdField, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Field> updateField(@PathVariable Long id, @Valid @RequestBody Field field) {
        Optional<Field> existingField = fieldService.getFieldById(id);
        if (existingField.isPresent()) {
            field.setId(id);
            Field updatedField = fieldService.updateField(field);
            return new ResponseEntity<>(updatedField, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        Optional<Field> existingField = fieldService.getFieldById(id);
        if (existingField.isPresent()) {
            fieldService.deleteField(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
