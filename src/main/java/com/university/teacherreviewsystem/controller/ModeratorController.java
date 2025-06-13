package com.university.teacherreviewsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moderation")
public class ModeratorController {

    @GetMapping("/test")
    public String moderatorAccess() {
        return "Hello, MODERATOR! You have access to moderator section.";
    }
}
