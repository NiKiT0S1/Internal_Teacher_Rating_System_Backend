package com.university.teacherreviewsystem.repository;

import com.university.teacherreviewsystem.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CriteriaRepository extends JpaRepository<Criteria, UUID> {
}
