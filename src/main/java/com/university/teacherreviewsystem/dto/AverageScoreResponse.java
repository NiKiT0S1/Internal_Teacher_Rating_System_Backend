package com.university.teacherreviewsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AverageScoreResponse {
//    private UUID criteriaId;
    private String criteriaName;
    private double averageScore;
}
