/**
 * Назначение: Интерфейс используется для работы с данными сущности Review в базе данных,
 * и показывает отзывы о конкретном преподавателе (только видимые),
 * и проверяет существования отзыва (предотвращение дублирования).
 * Взят из документации по Spring Data JPA
 */

package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Review;
import com.university.teacherreviewsystem.model.User;
import com.university.teacherreviewsystem.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    // Reviews about a particular teacher (only not hidden)
    List<Review> findByTeacherAndHiddenFalse(Teacher teacher);

    // Search for feedback from a specific student in a specific semester
//    Optional<Review> findByStudentIdAndTeacherIdAndSemester(UUID studentId, UUID teacherId, String semester);

    // Check if student already submitted review
    boolean existsByStudentAndTeacherIdAndSemester(User student, UUID teacherId, String semester);

    // Get all the reviews (moderator)
    List<Review> findAll();
}
