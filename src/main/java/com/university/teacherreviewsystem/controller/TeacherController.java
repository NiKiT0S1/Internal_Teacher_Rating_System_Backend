package com.university.teacherreviewsystem.controller;

import com.university.teacherreviewsystem.dto.AverageScoreResponse;
import com.university.teacherreviewsystem.dto.ReviewResponse;
import com.university.teacherreviewsystem.model.Criteria;
import com.university.teacherreviewsystem.model.Review;
import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.model.User;
import com.university.teacherreviewsystem.repository.CriteriaRepository;
import com.university.teacherreviewsystem.repository.ReviewRepository;
import com.university.teacherreviewsystem.repository.TeacherRepository;
import com.university.teacherreviewsystem.repository.UserRepository;
import com.university.teacherreviewsystem.util.ReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CriteriaRepository criteriaRepository;
    private final ReviewUtils reviewUtils;

//    @GetMapping
//    public List<Teacher> getAllTeachers() {
//        return teacherRepository.findAll();
//    }


    // Get all reviews for current Teacher
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviews(Authentication authentication) {
        String username = authentication.getName();
        User teacherUser = userRepository.findByUsername(username).orElseThrow();

        Teacher teacher = teacherRepository.findByUser(teacherUser).orElseThrow();

        List<Review> reviews = reviewRepository.findByTeacherAndHiddenFalse(teacher);

        List<ReviewResponse> response = reviews.stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getSemester(),
                        reviewUtils.mapCriteriaNames(review.getScores()),
                        review.getComment()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Get average scores by review's criteria
    @GetMapping("/averages")
    public ResponseEntity<List<AverageScoreResponse>> getAverageScores(Authentication authentication) {
        String username = authentication.getName();
        User teacherUser = userRepository.findByUsername(username).orElseThrow();

        Teacher teacher = teacherRepository.findByUser(teacherUser).orElseThrow();

        List<Review> reviews = reviewRepository.findByTeacherAndHiddenFalse(teacher);

        Map<UUID, List<Integer>> scoresMap = new HashMap<>();

        for (Review review : reviews) {
            review.getScores().forEach((criteriaId, score) -> {
                scoresMap.computeIfAbsent(criteriaId, k -> new ArrayList<>()).add(score);
            });
        }

        // Counting average scores based on review's criteria
        List<AverageScoreResponse> averages = scoresMap.entrySet().stream()
                .map(entry -> {
                    UUID criteriaId = entry.getKey();
                    String criteriaName = criteriaRepository.findById(criteriaId)
                            .map(Criteria::getName)
                            .orElse("Unknown Criteria");

                    List<Integer> scores = entry.getValue();
                    double average = scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                    double roundedAverage = Math.round(average * 100.0) / 100.0;
                    return new AverageScoreResponse(criteriaName, roundedAverage);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(averages);
    }

//    // Convertation criteria from ID(UUID) to Name(String)(NO NEED)
//    private Map<String, Integer> mapCriteriaNames(Map<UUID, Integer> scores) {
//        Map<String, Integer> namedScores = new HashMap<>();
//        scores.forEach((criteriaId, score) -> {
//            String criteriaName = criteriaRepository.findById(criteriaId)
//                    .map(Criteria::getName)
//                    .orElse("Unknown Criteria");
//            namedScores.put(criteriaName, score);
//        });
//
//        return namedScores;
//    }

    // Just Test Access
    @GetMapping("/test")
    public String teacherAccess() {
        return "Hello, TEACHER! You have access to teacher section.";
    }
}
