package com.skylark.repository;

import com.skylark.entity.TrainingVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingVideoRepository extends JpaRepository<TrainingVideo, Long> {
    List<TrainingVideo> findByEquipmentId(Long equipmentId);
    List<TrainingVideo> findByTeacherId(Long teacherId);
}
