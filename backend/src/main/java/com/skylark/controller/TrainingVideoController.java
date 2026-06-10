package com.skylark.controller;

import com.skylark.entity.TrainingVideo;
import com.skylark.service.TrainingVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/training-videos")
public class TrainingVideoController {

    @Autowired
    private TrainingVideoService trainingVideoService;

    @GetMapping
    public List<TrainingVideo> getAll() {
        return trainingVideoService.getAll();
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<TrainingVideo> getByEquipmentId(@PathVariable Long equipmentId) {
        return trainingVideoService.getByEquipmentId(equipmentId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<TrainingVideo> getByTeacherId(@PathVariable Long teacherId) {
        return trainingVideoService.getByTeacherId(teacherId);
    }

    @GetMapping("/{id}")
    public Optional<TrainingVideo> getById(@PathVariable Long id) {
        return trainingVideoService.getById(id);
    }

    @PostMapping
    public TrainingVideo create(@RequestBody TrainingVideo video) {
        return trainingVideoService.save(video);
    }

    @PutMapping("/{id}")
    public TrainingVideo update(@PathVariable Long id, @RequestBody TrainingVideo video) {
        video.setId(id);
        return trainingVideoService.save(video);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trainingVideoService.delete(id);
    }
}
