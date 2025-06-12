package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    // Reviews about a particular teacher (only not hidden)
    List<Review> findByTeacherIdAndHiddenFalse(UUID teacherId);

    // Search for feedback from a specific student in a specific semester
    Optional<Review> findByStudentIdAndTeacherIdAndSemester(UUID studentId, UUID teacherId, String semester);

    // Get all the reviews (moderator)
    List<Review> findAll();
}
