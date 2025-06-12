package com.university.teacherreviewsystem.config;

import com.university.teacherreviewsystem.model.*;
import com.university.teacherreviewsystem.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(UserRepository userRepo,
                                   TeacherRepository teacherRepo,
                                   CriteriaRepository criteriaRepo) {
        return args -> {

            // ✅ Проверка для users
            if (userRepo.count() == 0) {
                // Create users
                User student = userRepo.save(User.builder()
                        .username("student_aitu")
                        .email("student@astanait.edu.kz")
                        .password("1234") // hash will be later
                        .fullname("John Smith")
                        .role(Role.STUDENT)
                        .build());

                User teacherUser = userRepo.save(User.builder()
                        .username("teacher_aitu")
                        .email("teacher@astanait.edu.kz")
                        .password("12345")
                        .fullname("Emily Johnson")
                        .role(Role.TEACHER)
                        .build());

                User moderator = userRepo.save(User.builder()
                        .username("moderator_aitu")
                        .email("moderator@astanait.edu.kz")
                        .password("1324")
                        .fullname("Michael Brown")
                        .role(Role.MODERATOR)
                        .build());
            }

            // ✅ Проверка для teachers
            if (teacherRepo.count() == 0) {
                // Связываем только одного преподавателя с user
                User teacherUser = userRepo.findByUsername("teacher_aitu").orElseThrow();

                teacherRepo.saveAll(List.of(
                        Teacher.builder()
                                .fullname("Emily Johnson")
                                .department("Software Engineering")
                                .user(teacherUser)
                                .build(),
                        Teacher.builder()
                                .fullname("David Wilson")
                                .department("Cybersecurity")
                                .build(),
                        Teacher.builder()
                                .fullname("Sarah Davis")
                                .department("Big Data Analysis")
                                .build(),
                        Teacher.builder()
                                .fullname("James Miller")
                                .department("Computer Science")
                                .build(),
                        Teacher.builder()
                                .fullname("Olivia Taylor")
                                .department("Media Technologies")
                                .build()
                ));
            }

            // ✅ Проверка для criteria
            if (criteriaRepo.count() == 0) {
                criteriaRepo.saveAll(List.of(
                        Criteria.builder().name("Punctuality").build(),
                        Criteria.builder().name("Subject Expertise").build(),
                        Criteria.builder().name("Clarity of Explanation").build()
                ));
            }
        };
    }
}
