package com.skylark.controller;

import com.skylark.dto.QuizAnswerDTO;
import com.skylark.dto.QuizResultDTO;
import com.skylark.dto.TrainingProgressDTO;
import com.skylark.entity.QuizAttempt;
import com.skylark.service.QuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptController {

    @Autowired
    private QuizAttemptService quizAttemptService;

    @GetMapping
    public List<QuizAttempt> getAll() {
        return quizAttemptService.getAll();
    }

    @GetMapping("/student/{studentId}")
    public List<QuizAttempt> getByStudentId(@PathVariable Long studentId) {
        return quizAttemptService.getByStudentId(studentId);
    }

    @GetMapping("/question/{questionId}")
    public List<QuizAttempt> getByQuestionId(@PathVariable Long questionId) {
        return quizAttemptService.getByQuestionId(questionId);
    }

    @GetMapping("/student/{studentId}/question/{questionId}/latest")
    public Optional<QuizAttempt> getLatestAttempt(@PathVariable Long studentId, @PathVariable Long questionId) {
        return quizAttemptService.getLatestAttempt(studentId, questionId);
    }

    @GetMapping("/student/{studentId}/question/{questionId}/passed")
    public boolean hasPassedQuestion(@PathVariable Long studentId, @PathVariable Long questionId) {
        return quizAttemptService.hasPassedQuestion(studentId, questionId);
    }

    @GetMapping("/student/{studentId}/video/{videoId}/all-passed")
    public boolean hasPassedAllQuestions(@PathVariable Long studentId, @PathVariable Long videoId) {
        return quizAttemptService.hasPassedAllQuestions(studentId, videoId);
    }

    @GetMapping("/student/{studentId}/video/{videoId}/progress")
    public TrainingProgressDTO getTrainingProgress(@PathVariable Long studentId, @PathVariable Long videoId) {
        return quizAttemptService.getTrainingProgress(studentId, videoId);
    }

    @PostMapping("/submit")
    public QuizResultDTO submitAnswer(@RequestBody QuizAnswerDTO answerDTO) {
        return quizAttemptService.submitAnswer(answerDTO);
    }

    @PostMapping("/{attemptId}/retry")
    public QuizResultDTO retryAnswer(@PathVariable Long attemptId, @RequestBody QuizAnswerDTO answerDTO) {
        return quizAttemptService.retryAnswer(attemptId, answerDTO);
    }
}
