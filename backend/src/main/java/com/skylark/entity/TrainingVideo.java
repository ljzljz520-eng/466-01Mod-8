package com.skylark.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "training_video")
public class TrainingVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;

    private String title;

    @Column(name = "video_url")
    private String videoUrl;

    private Integer duration;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "teacher_id")
    private Long teacherId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
