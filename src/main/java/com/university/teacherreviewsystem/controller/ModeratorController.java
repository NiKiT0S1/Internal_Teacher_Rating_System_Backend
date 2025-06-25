/**
 * Назначение: Функции модератора - API Endpoints
 * Возвращает ВСЕ отзывы (включая скрытые)
 * Скрывает отзыв (hidden = true)
 * Показывает отзыв (hidden = false)
 * Добавляет новый критерий оценки
 * Удаляет критерий
 */

package com.university.teacherreviewsystem.controller;

import com.university.teacherreviewsystem.dto.ReviewResponse;
import com.university.teacherreviewsystem.model.Criteria;
import com.university.teacherreviewsystem.model.Review;
import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.model.User;
import com.university.teacherreviewsystem.repository.CriteriaRepository;
import com.university.teacherreviewsystem.repository.ReviewRepository;
import com.university.teacherreviewsystem.util.ReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/moderation")
@RequiredArgsConstructor
public class ModeratorController {

    private final ReviewRepository reviewRepository;
    private final CriteriaRepository criteriaRepository;
    private final ReviewUtils reviewUtils;

    // Get all the reviews
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        List<ReviewResponse> response = reviews.stream()
                .map(review -> {
                    Teacher teacher = review.getTeacher();
                    User teacherUser = (teacher != null) ? teacher.getUser() : null;

                    String fullname = (teacherUser != null) ? teacherUser.getFullname() : "Unknown";
                    String username = (teacherUser != null) ? teacherUser.getUsername() : "unknown";

                    return new ReviewResponse(
                            review.getId(),
                            review.getSemester(),
                            reviewUtils.mapCriteriaNames(review.getScores()),
                            review.getComment(),
                            review.isHidden(),
                            fullname,
                            username
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Hide the review
    @PatchMapping("/reviews/{id}/hide")
    public ResponseEntity<String> hideReview(@PathVariable UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setHidden(true);
        reviewRepository.save(review);

        return ResponseEntity.ok("Review hidden successfully");
    }

    // Show the review
    @PatchMapping("/reviews/{id}/show")
    public ResponseEntity<String> showReview(@PathVariable UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setHidden(false);
        reviewRepository.save(review);

        return ResponseEntity.ok("Review show successfully");
    }


    @GetMapping("/criteria")
    public ResponseEntity<List<Criteria>> getAllCriteria() {
        List<Criteria> all = criteriaRepository.findAll();
        return ResponseEntity.ok(all);
    }

    // Add criteria
    @PostMapping("/criteria")
    public ResponseEntity<String> addCriteria(@RequestBody Criteria criteria) {
        criteriaRepository.save(criteria);
        return ResponseEntity.ok("Criteria added successfully");
    }

    // Delete criteria
    @DeleteMapping("/criteria/{id}")
    public ResponseEntity<String> deleteCriteria(@PathVariable UUID id) {
        Criteria criteria = criteriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criteria not found"));

        criteriaRepository.delete(criteria);
        return ResponseEntity.ok("Criteria deleted successfully");
    }

    // Just Test Access
    @GetMapping("/test")
    public String moderatorAccess() {
        return "Hello, MODERATOR! You have access to moderator section.";
    }
}
