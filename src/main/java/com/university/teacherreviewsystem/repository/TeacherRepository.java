/**
 * Назначение: Интерфейс используется для работы с данными сущности Teacher в базе данных,
 * и позволяет находить преподавателя по аккаунту.
 * Взят из документации по Spring Data JPA
 */

package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findByUser(User user);
}
