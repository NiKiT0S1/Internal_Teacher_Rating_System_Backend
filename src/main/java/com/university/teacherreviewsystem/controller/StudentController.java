package com.university.teacherreviewsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class StudentController {

    @GetMapping("/test")
    public String studentAccess() {
        return "Hello, STUDENT! You have access to reviews.";
    }
}
