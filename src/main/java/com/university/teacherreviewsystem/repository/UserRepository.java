/**
 * Назначение: Интерфейс используется для работы с данными сущности User в базе данных,
 * и позволяет находить пользователя по его имени пользователя.
 * Взят из документации по Spring Data JPA
 */

package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
