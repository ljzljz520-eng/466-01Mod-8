package com.skylark.repository;

import com.skylark.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findByVideoIdOrderByTimePointAsc(Long videoId);
    List<QuizQuestion> findByVideoIdAndQuestionType(Long videoId, String questionType);

    @Query("SELECT q.questionType as type, COUNT(q) as count, " +
           "SUM(CASE WHEN a.isCorrect = false THEN 1 ELSE 0 END) as wrongCount " +
           "FROM QuizQuestion q LEFT JOIN QuizAttempt a ON q.id = a.questionId " +
           "WHERE q.videoId = :videoId " +
           "GROUP BY q.questionType")
    List<Object[]> getQuestionTypeStatsByVideoId(Long videoId);

    @Query("SELECT q.id, q.segmentName, q.questionText, q.questionType, " +
           "COUNT(a) as attemptCount, " +
           "SUM(CASE WHEN a.isCorrect = false THEN 1 ELSE 0 END) as wrongCount " +
           "FROM QuizQuestion q LEFT JOIN QuizAttempt a ON q.id = a.questionId " +
           "WHERE q.videoId = :videoId " +
           "GROUP BY q.id, q.segmentName, q.questionText, q.questionType " +
           "ORDER BY wrongCount DESC")
    List<Object[]> getQuestionWrongStatsByVideoId(Long videoId);
}
