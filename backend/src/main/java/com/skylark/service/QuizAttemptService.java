package com.skylark.service;

import com.skylark.dto.QuizAnswerDTO;
import com.skylark.dto.QuizResultDTO;
import com.skylark.dto.TrainingProgressDTO;
import com.skylark.entity.QuizAttempt;
import com.skylark.entity.QuizQuestion;
import com.skylark.repository.QuizAttemptRepository;
import com.skylark.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuizAttemptService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public List<QuizAttempt> getAll() {
        return quizAttemptRepository.findAll();
    }

    public List<QuizAttempt> getByStudentId(Long studentId) {
        return quizAttemptRepository.findByStudentId(studentId);
    }

    public List<QuizAttempt> getByQuestionId(Long questionId) {
        return quizAttemptRepository.findByQuestionId(questionId);
    }

    public Optional<QuizAttempt> getLatestAttempt(Long studentId, Long questionId) {
        return quizAttemptRepository.findFirstByStudentIdAndQuestionIdOrderByCreatedAtDesc(studentId, questionId);
    }

    public boolean hasPassedQuestion(Long studentId, Long questionId) {
        return quizAttemptRepository.hasPassedQuestion(studentId, questionId);
    }

    public boolean hasPassedAllQuestions(Long studentId, Long videoId) {
        return quizAttemptRepository.hasPassedAllQuestionsForVideo(studentId, videoId);
    }

    public TrainingProgressDTO getTrainingProgress(Long studentId, Long videoId) {
        List<QuizQuestion> questions = quizQuestionRepository.findByVideoIdOrderByTimePointAsc(videoId);
        int totalQuestions = questions.size();
        int passedQuestions = 0;
        int failedQuestions = 0;

        for (QuizQuestion q : questions) {
            Optional<QuizAttempt> attempt = quizAttemptRepository.findFirstByStudentIdAndQuestionIdOrderByCreatedAtDesc(studentId, q.getId());
            if (attempt.isPresent()) {
                if (attempt.get().getIsPassed()) {
                    passedQuestions++;
                } else {
                    failedQuestions++;
                }
            }
        }

        boolean allPassed = quizAttemptRepository.hasPassedAllQuestionsForVideo(studentId, videoId);
        return new TrainingProgressDTO(videoId, totalQuestions, passedQuestions, failedQuestions, allPassed);
    }

    @Transactional
    public QuizResultDTO submitAnswer(QuizAnswerDTO answerDTO) {
        QuizQuestion question = quizQuestionRepository.findById(answerDTO.getQuestionId())
            .orElseThrow(() -> new RuntimeException("题目不存在"));

        Optional<QuizAttempt> existingAttempt = quizAttemptRepository.findFirstByStudentIdAndQuestionIdOrderByCreatedAtDesc(
            answerDTO.getStudentId(), answerDTO.getQuestionId());

        QuizAttempt attempt;
        int attemptCount;

        if (existingAttempt.isPresent() && !existingAttempt.get().getIsPassed()) {
            attempt = existingAttempt.get();
            attemptCount = attempt.getAttemptCount() + 1;

            if (attemptCount > attempt.getMaxRetries()) {
                QuizResultDTO result = new QuizResultDTO();
                result.setAttemptId(attempt.getId());
                result.setQuestionId(question.getId());
                result.setIsCorrect(false);
                result.setIsPassed(false);
                result.setCanRetry(false);
                result.setAttemptCount(attempt.getAttemptCount());
                result.setMaxRetries(attempt.getMaxRetries());
                result.setExplanation(question.getExplanation());
                result.setMessage("已达到最大补考次数，请联系老师人工放行");
                return result;
            }

            attempt.setAttemptCount(attemptCount);
        } else {
            attempt = new QuizAttempt();
            attempt.setQuestionId(answerDTO.getQuestionId());
            attempt.setStudentId(answerDTO.getStudentId());
            attempt.setStudentName(answerDTO.getStudentName());
            attemptCount = 1;
            attempt.setAttemptCount(1);
            attempt.setMaxRetries(3);
        }

        attempt.setSelectedAnswer(answerDTO.getSelectedAnswer());
        boolean isCorrect = answerDTO.getSelectedAnswer().equals(question.getCorrectAnswer());
        attempt.setIsCorrect(isCorrect);

        if (!isCorrect && answerDTO.getWrongReason() != null) {
            attempt.setWrongReason(answerDTO.getWrongReason());
        }

        attempt.setIsPassed(isCorrect);

        quizAttemptRepository.save(attempt);

        QuizResultDTO result = new QuizResultDTO();
        result.setAttemptId(attempt.getId());
        result.setQuestionId(question.getId());
        result.setIsCorrect(isCorrect);
        result.setIsPassed(isCorrect);
        result.setCanRetry(!isCorrect && attemptCount < attempt.getMaxRetries());
        result.setAttemptCount(attemptCount);
        result.setMaxRetries(attempt.getMaxRetries());
        result.setExplanation(question.getExplanation());

        if (isCorrect) {
            result.setMessage("回答正确！可以继续学习");
        } else {
            if (attemptCount < attempt.getMaxRetries()) {
                result.setMessage("回答错误，请查看解析后重试");
            } else {
                result.setMessage("回答错误，已达到最大补考次数，请联系老师人工放行");
            }
        }

        return result;
    }

    @Transactional
    public QuizResultDTO retryAnswer(Long attemptId, QuizAnswerDTO answerDTO) {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId)
            .orElseThrow(() -> new RuntimeException("答题记录不存在"));

        if (attempt.getIsPassed()) {
            throw new RuntimeException("该题目已通过，无需重试");
        }

        if (attempt.getAttemptCount() >= attempt.getMaxRetries()) {
            throw new RuntimeException("已达到最大补考次数，请联系老师人工放行");
        }

        QuizQuestion question = quizQuestionRepository.findById(attempt.getQuestionId())
            .orElseThrow(() -> new RuntimeException("题目不存在"));

        answerDTO.setQuestionId(attempt.getQuestionId());
        answerDTO.setStudentId(attempt.getStudentId());
        answerDTO.setStudentName(attempt.getStudentName());

        return submitAnswer(answerDTO);
    }
}
