package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findByUser(User user);
}
