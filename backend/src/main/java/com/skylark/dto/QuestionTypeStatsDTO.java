package com.skylark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionTypeStatsDTO {
    private String questionType;
    private String typeName;
    private Long totalCount;
    private Long wrongCount;
    private Double wrongRate;
}
