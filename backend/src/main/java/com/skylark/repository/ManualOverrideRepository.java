package com.skylark.repository;

import com.skylark.entity.ManualOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManualOverrideRepository extends JpaRepository<ManualOverride, Long> {
    List<ManualOverride> findByAttemptId(Long attemptId);
    List<ManualOverride> findByTeacherId(Long teacherId);
}
