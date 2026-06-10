package com.skylark.service;

import com.skylark.dto.ManualOverrideDTO;
import com.skylark.entity.ManualOverride;
import com.skylark.entity.QuizAttempt;
import com.skylark.repository.ManualOverrideRepository;
import com.skylark.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ManualOverrideService {

    @Autowired
    private ManualOverrideRepository manualOverrideRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    public List<ManualOverride> getAll() {
        return manualOverrideRepository.findAll();
    }

    public List<ManualOverride> getByAttemptId(Long attemptId) {
        return manualOverrideRepository.findByAttemptId(attemptId);
    }

    public List<ManualOverride> getByTeacherId(Long teacherId) {
        return manualOverrideRepository.findByTeacherId(teacherId);
    }

    public Optional<ManualOverride> getById(Long id) {
        return manualOverrideRepository.findById(id);
    }

    @Transactional
    public ManualOverride createOverride(ManualOverrideDTO dto) {
        QuizAttempt attempt = quizAttemptRepository.findById(dto.getAttemptId())
            .orElseThrow(() -> new RuntimeException("答题记录不存在"));

        attempt.setIsPassed(true);
        quizAttemptRepository.save(attempt);

        ManualOverride override = new ManualOverride();
        override.setAttemptId(dto.getAttemptId());
        override.setTeacherId(dto.getTeacherId());
        override.setTeacherName(dto.getTeacherName());
        override.setReason(dto.getReason());

        return manualOverrideRepository.save(override);
    }
}
