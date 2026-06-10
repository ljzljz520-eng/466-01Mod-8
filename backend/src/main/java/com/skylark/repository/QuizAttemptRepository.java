package com.skylark.repository;

import com.skylark.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByStudentIdAndQuestionId(Long studentId, Long questionId);
    List<QuizAttempt> findByStudentId(Long studentId);
    List<QuizAttempt> findByQuestionId(Long questionId);
    Optional<QuizAttempt> findFirstByStudentIdAndQuestionIdOrderByCreatedAtDesc(Long studentId, Long questionId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM QuizAttempt a " +
           "WHERE a.studentId = :studentId " +
           "AND a.questionId = :questionId " +
           "AND a.isPassed = true")
    boolean hasPassedQuestion(Long studentId, Long questionId);

    @Query("SELECT CASE WHEN COUNT(DISTINCT q.id) = COUNT(DISTINCT a.questionId) THEN true ELSE false END " +
           "FROM QuizQuestion q " +
           "LEFT JOIN QuizAttempt a ON q.id = a.questionId AND a.studentId = :studentId AND a.isPassed = true " +
           "WHERE q.videoId = :videoId")
    boolean hasPassedAllQuestionsForVideo(Long studentId, Long videoId);
}
