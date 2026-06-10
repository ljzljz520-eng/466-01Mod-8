package com.skylark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionWrongStatsDTO {
    private Long questionId;
    private String segmentName;
    private String questionText;
    private String questionType;
    private String typeName;
    private Long attemptCount;
    private Long wrongCount;
    private Double wrongRate;
}
