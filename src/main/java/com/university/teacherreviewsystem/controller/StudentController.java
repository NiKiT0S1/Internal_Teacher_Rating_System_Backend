/**
 * Назначение: Функции студента - API Endpoints
 * Возвращает список всех преподавателей для выбора
 * Возвращает все критерии оценки
 */

package com.university.teacherreviewsystem.controller;

import com.university.teacherreviewsystem.model.Criteria;
import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.repository.CriteriaRepository;
import com.university.teacherreviewsystem.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class StudentController {

    private final TeacherRepository teacherRepository;
    private final CriteriaRepository criteriaRepository;

    // Show all teachers for Student
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Show all criteria
    @GetMapping("/criteria")
    public List<Criteria> getAllCriteria() {
        return criteriaRepository.findAll();
    }

    // Just Test Access
    @GetMapping("/test")
    public String studentAccess() {
        return "Hello, STUDENT! You have access to reviews.";
    }
}
