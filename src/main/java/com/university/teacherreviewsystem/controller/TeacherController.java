package com.university.teacherreviewsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping("/test")
    public String teacherAccess() {
        return "Hello, TEACHER! You have access to teacher section.";
    }
}
