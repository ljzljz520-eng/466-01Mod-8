package com.skylark.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequestDTO {
    private Long equipmentId;
    private Long studentId;
    private String studentName;
    private LocalDate bookingDate;
    private String timeSlot;
}
