package com.skylark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingProgressDTO {
    private Long videoId;
    private Integer totalQuestions;
    private Integer passedQuestions;
    private Integer failedQuestions;
    private Boolean allPassed;
}
