/**
 * Назначение: Запускает Spring Boot приложение и инициализирует все компоненты
 * Дефолтный файл, который создается при инициализации проекта на Spring Boot
*/
package com.university.teacherreviewsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeacherReviewSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherReviewSystemApplication.class, args);
    }

}
