package com.skylark.dto;

import lombok.Data;

@Data
public class QuizAnswerDTO {
    private Long questionId;
    private Long studentId;
    private String studentName;
    private String selectedAnswer;
    private String wrongReason;
}
