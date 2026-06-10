package com.skylark.controller;

import com.skylark.dto.ManualOverrideDTO;
import com.skylark.entity.ManualOverride;
import com.skylark.service.ManualOverrideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manual-overrides")
public class ManualOverrideController {

    @Autowired
    private ManualOverrideService manualOverrideService;

    @GetMapping
    public List<ManualOverride> getAll() {
        return manualOverrideService.getAll();
    }

    @GetMapping("/attempt/{attemptId}")
    public List<ManualOverride> getByAttemptId(@PathVariable Long attemptId) {
        return manualOverrideService.getByAttemptId(attemptId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<ManualOverride> getByTeacherId(@PathVariable Long teacherId) {
        return manualOverrideService.getByTeacherId(teacherId);
    }

    @GetMapping("/{id}")
    public Optional<ManualOverride> getById(@PathVariable Long id) {
        return manualOverrideService.getById(id);
    }

    @PostMapping
    public ManualOverride create(@RequestBody ManualOverrideDTO dto) {
        return manualOverrideService.createOverride(dto);
    }
}
