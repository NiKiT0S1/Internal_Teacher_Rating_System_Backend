package com.university.teacherreviewsystem.controller;

import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/test")
    public String teacherAccess() {
        return "Hello, TEACHER! You have access to teacher section.";
    }
}
