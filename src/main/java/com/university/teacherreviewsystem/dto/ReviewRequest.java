package com.university.teacherreviewsystem.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class ReviewRequest {
    private UUID teacherId;
    private String semester;
    private Map<UUID, Integer> scores;
    private String comment;
}
