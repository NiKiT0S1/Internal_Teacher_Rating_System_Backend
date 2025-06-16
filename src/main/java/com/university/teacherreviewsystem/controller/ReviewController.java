package com.university.teacherreviewsystem.controller;

import com.university.teacherreviewsystem.dto.ReviewRequest;
import com.university.teacherreviewsystem.model.Review;
import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.model.User;
import com.university.teacherreviewsystem.repository.ReviewRepository;
import com.university.teacherreviewsystem.repository.TeacherRepository;
import com.university.teacherreviewsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> submitReview(@RequestBody ReviewRequest request, Authentication authentication) {
        String username = authentication.getName();
        User student = userRepository.findByUsername(username).orElseThrow();

        // Verification: the student has already left a review to this teacher this semester
        boolean exists = reviewRepository.existsByStudentAndTeacherIdAndSemester(student, request.getTeacherId(), request.getSemester());
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You have already submitted a review for this teacher in this semester.");
        }

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElse(null);

        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Teacher not found.");
        }

        Review review = Review.builder()
                .teacher(teacher)
                .student(student)
                .semester(request.getSemester())
                .scores(request.getScores())
                .comment(request.getComment())
                .hidden(false)
                .build();

        reviewRepository.save(review);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Review submitted successfully");
    }
}
