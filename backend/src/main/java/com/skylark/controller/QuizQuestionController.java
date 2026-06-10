package com.skylark.controller;

import com.skylark.dto.QuestionTypeStatsDTO;
import com.skylark.dto.QuestionWrongStatsDTO;
import com.skylark.entity.QuizQuestion;
import com.skylark.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz-questions")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @GetMapping
    public List<QuizQuestion> getAll() {
        return quizQuestionService.getAll();
    }

    @GetMapping("/video/{videoId}")
    public List<QuizQuestion> getByVideoId(@PathVariable Long videoId) {
        return quizQuestionService.getByVideoId(videoId);
    }

    @GetMapping("/video/{videoId}/type/{questionType}")
    public List<QuizQuestion> getByVideoIdAndType(@PathVariable Long videoId, @PathVariable String questionType) {
        return quizQuestionService.getByVideoIdAndType(videoId, questionType);
    }

    @GetMapping("/{id}")
    public Optional<QuizQuestion> getById(@PathVariable Long id) {
        return quizQuestionService.getById(id);
    }

    @GetMapping("/types")
    public Map<String, String> getQuestionTypes() {
        return quizQuestionService.getQuestionTypeNames();
    }

    @GetMapping("/video/{videoId}/stats/by-type")
    public List<QuestionTypeStatsDTO> getTypeStats(@PathVariable Long videoId) {
        return quizQuestionService.getQuestionTypeStats(videoId);
    }

    @GetMapping("/video/{videoId}/stats/by-question")
    public List<QuestionWrongStatsDTO> getWrongStats(@PathVariable Long videoId) {
        return quizQuestionService.getQuestionWrongStats(videoId);
    }

    @PostMapping
    public QuizQuestion create(@RequestBody QuizQuestion question) {
        return quizQuestionService.save(question);
    }

    @PutMapping("/{id}")
    public QuizQuestion update(@PathVariable Long id, @RequestBody QuizQuestion question) {
        question.setId(id);
        return quizQuestionService.save(question);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        quizQuestionService.delete(id);
    }
}
