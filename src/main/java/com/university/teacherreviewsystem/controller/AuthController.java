/**
 * Назначение: Аутентификация - API Endpoints
 *
 * Порядок работы регистрации:
 * Получение данных из RegisterRequest
 * Проверка уникальности username
 * Валидация роли и обязательных полей
 * Хеширование пароля через BCrypt
 * Сохранение User в БД
 * Если роль TEACHER - создание связанной записи Teacher
 *
 * Порядок работы логина:
 * Аутентификация через AuthenticationManager
 * Поиск пользователя в БД
 * Генерация JWT токена
 * Возврат токена + информации о пользователе
 */

package com.university.teacherreviewsystem.controller;

import com.university.teacherreviewsystem.dto.LoginRequest;
import com.university.teacherreviewsystem.dto.RegisterRequest;
import com.university.teacherreviewsystem.model.Role;
import com.university.teacherreviewsystem.model.Teacher;
import com.university.teacherreviewsystem.model.User;
import com.university.teacherreviewsystem.repository.TeacherRepository;
import com.university.teacherreviewsystem.repository.UserRepository;
import com.university.teacherreviewsystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (request.getRole() == Role.TEACHER) {
            if (request.getDepartment() == null || request.getDepartment().isEmpty()) {
                return ResponseEntity.badRequest().body("Department is required for teachers");
            }
        }

        if (request.getRole() == Role.STUDENT || request.getRole() == Role.MODERATOR) {
            if (request.getDepartment() != null && !request.getDepartment().isEmpty()) {
                return ResponseEntity.badRequest().body("Students and Moderators should not provide a department");
            }
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullname(request.getFullName())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        if (request.getRole() == Role.TEACHER) {
//            if (request.getDepartment() == null || request.getDepartment().isEmpty()) {
//                return ResponseEntity.badRequest().body("Department is required for teachers");
//            }

            Teacher teacher = Teacher.builder()
                    .fullname(request.getFullName())
                    .department(request.getDepartment())
                    .user(savedUser)
                    .build();

            teacherRepository.save(teacher);
        }

        return ResponseEntity.ok("User registered successfully");
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user); // NEW
//        String jwtToken = jwtService.generateToken(user.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        response.put("fullname", user.getFullname());

        return ResponseEntity.ok(response);
    }
}
