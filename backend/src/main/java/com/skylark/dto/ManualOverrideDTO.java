package com.skylark.dto;

import lombok.Data;

@Data
public class ManualOverrideDTO {
    private Long attemptId;
    private Long teacherId;
    private String teacherName;
    private String reason;
}
