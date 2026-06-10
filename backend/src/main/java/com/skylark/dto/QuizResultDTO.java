package com.skylark.dto;

import lombok.Data;

@Data
public class QuizResultDTO {
    private Long attemptId;
    private Long questionId;
    private Boolean isCorrect;
    private Boolean isPassed;
    private Boolean canRetry;
    private Integer attemptCount;
    private Integer maxRetries;
    private String explanation;
    private String message;
}
