package com.skylark.service;

import com.skylark.entity.TrainingVideo;
import com.skylark.repository.TrainingVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingVideoService {

    @Autowired
    private TrainingVideoRepository trainingVideoRepository;

    public List<TrainingVideo> getAll() {
        return trainingVideoRepository.findAll();
    }

    public List<TrainingVideo> getByEquipmentId(Long equipmentId) {
        return trainingVideoRepository.findByEquipmentId(equipmentId);
    }

    public List<TrainingVideo> getByTeacherId(Long teacherId) {
        return trainingVideoRepository.findByTeacherId(teacherId);
    }

    public Optional<TrainingVideo> getById(Long id) {
        return trainingVideoRepository.findById(id);
    }

    public TrainingVideo save(TrainingVideo video) {
        return trainingVideoRepository.save(video);
    }

    public void delete(Long id) {
        trainingVideoRepository.deleteById(id);
    }
}
