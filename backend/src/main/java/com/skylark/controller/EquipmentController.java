package com.skylark.controller;

import com.skylark.entity.Equipment;
import com.skylark.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<Equipment> getAll() {
        return equipmentService.getAll();
    }

    @GetMapping("/available")
    public List<Equipment> getAvailable() {
        return equipmentService.getAvailable();
    }

    @GetMapping("/{id}")
    public Optional<Equipment> getById(@PathVariable Long id) {
        return equipmentService.getById(id);
    }

    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        return equipmentService.save(equipment);
    }

    @PutMapping("/{id}")
    public Equipment update(@PathVariable Long id, @RequestBody Equipment equipment) {
        equipment.setId(id);
        return equipmentService.save(equipment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipmentService.delete(id);
    }
}
