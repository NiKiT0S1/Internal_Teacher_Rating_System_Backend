package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
