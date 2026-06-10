package com.skylark.service;

import com.skylark.dto.QuestionTypeStatsDTO;
import com.skylark.dto.QuestionWrongStatsDTO;
import com.skylark.entity.QuizQuestion;
import com.skylark.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizQuestionService {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    private static final Map<String, String> TYPE_NAMES = new HashMap<>();
    static {
        TYPE_NAMES.put("DANGER_STEP", "危险步骤");
        TYPE_NAMES.put("CLEANING_REQUIREMENT", "清洁要求");
        TYPE_NAMES.put("SHUTDOWN_SEQUENCE", "关机顺序");
    }

    public List<QuizQuestion> getAll() {
        return quizQuestionRepository.findAll();
    }

    public List<QuizQuestion> getByVideoId(Long videoId) {
        return quizQuestionRepository.findByVideoIdOrderByTimePointAsc(videoId);
    }

    public List<QuizQuestion> getByVideoIdAndType(Long videoId, String questionType) {
        return quizQuestionRepository.findByVideoIdAndQuestionType(videoId, questionType);
    }

    public Optional<QuizQuestion> getById(Long id) {
        return quizQuestionRepository.findById(id);
    }

    public QuizQuestion save(QuizQuestion question) {
        return quizQuestionRepository.save(question);
    }

    public void delete(Long id) {
        quizQuestionRepository.deleteById(id);
    }

    public List<QuestionTypeStatsDTO> getQuestionTypeStats(Long videoId) {
        List<Object[]> results = quizQuestionRepository.getQuestionTypeStatsByVideoId(videoId);
        return results.stream()
            .map(row -> {
                String type = (String) row[0];
                Long totalCount = ((Number) row[1]).longValue();
                Long wrongCount = ((Number) row[2]).longValue();
                double wrongRate = totalCount > 0 ? (wrongCount.doubleValue() / totalCount.doubleValue()) * 100 : 0.0;
                return new QuestionTypeStatsDTO(
                    type,
                    TYPE_NAMES.getOrDefault(type, type),
                    totalCount,
                    wrongCount,
                    Math.round(wrongRate * 100.0) / 100.0
                );
            })
            .collect(Collectors.toList());
    }

    public List<QuestionWrongStatsDTO> getQuestionWrongStats(Long videoId) {
        List<Object[]> results = quizQuestionRepository.getQuestionWrongStatsByVideoId(videoId);
        return results.stream()
            .map(row -> {
                Long questionId = ((Number) row[0]).longValue();
                String segmentName = (String) row[1];
                String questionText = (String) row[2];
                String questionType = (String) row[3];
                Long attemptCount = ((Number) row[4]).longValue();
                Long wrongCount = ((Number) row[5]).longValue();
                double wrongRate = attemptCount > 0 ? (wrongCount.doubleValue() / attemptCount.doubleValue()) * 100 : 0.0;
                return new QuestionWrongStatsDTO(
                    questionId,
                    segmentName,
                    questionText,
                    questionType,
                    TYPE_NAMES.getOrDefault(questionType, questionType),
                    attemptCount,
                    wrongCount,
                    Math.round(wrongRate * 100.0) / 100.0
                );
            })
            .collect(Collectors.toList());
    }

    public Map<String, String> getQuestionTypeNames() {
        return TYPE_NAMES;
    }
}
