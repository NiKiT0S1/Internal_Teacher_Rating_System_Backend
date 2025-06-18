package com.university.teacherreviewsystem.util;

import com.university.teacherreviewsystem.model.Criteria;
import com.university.teacherreviewsystem.repository.CriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewUtils {

    private final CriteriaRepository criteriaRepository;

    // Convertation criteria from ID(UUID) to Name(String)
    public Map<String, Integer> mapCriteriaNames(Map<UUID, Integer> scores) {
        Map<String, Integer> namedScores = new HashMap<>();
        scores.forEach((criteriaId, score) -> {
            String criteriaName = criteriaRepository.findById(criteriaId)
                    .map(Criteria::getName)
                    .orElse("Unknown Criteria");
            namedScores.put(criteriaName, score);
        });

        return namedScores;
    }
}
