/**
 * Назначение: Данные регистрации
 *
 * DTO (Data Transfer Objects) - это объекты, предназначенные для передачи данных между подсистемами приложения.
 * Другими словами, он определяет структуру данных (поля),
 * которые нужно предоставить для успешной регистрации в какой-либо системе или приложении
 */

package com.university.teacherreviewsystem.dto;

import com.university.teacherreviewsystem.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private Role role;
    private String department;
}
