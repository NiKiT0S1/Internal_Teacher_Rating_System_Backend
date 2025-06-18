package com.university.teacherreviewsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class ReviewRequest {

    @NotNull(message = "Teacher ID is required")
    private UUID teacherId;

    @NotNull(message = "Semester is required")
    private String semester;

    @NotNull(message = "Scores cannot be null")
    @NotEmpty(message = "Scores cannot be empty. Please rate all criteria.")
    private Map<UUID, Integer> scores;

    private String comment;
}
