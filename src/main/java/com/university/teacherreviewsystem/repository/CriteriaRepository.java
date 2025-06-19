/**
 * Назначение: Интерфейс используется для работы с данными сущности Criteria в базе данных,
 * и позволяет находить критерии по его ID.
 * Взят из документации по Spring Data JPA
 *
 * JpaRepository - это интерфейс из Spring Data JPA,
 * который предоставляет стандартные CRUD (Create, Read, Update, Delete) операции
 * для работы с сущностями через JPA (Java Persistence API).
 */

package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CriteriaRepository extends JpaRepository<Criteria, UUID> {
}
