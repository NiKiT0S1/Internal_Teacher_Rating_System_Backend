/**
 * Назначение: Данные входа
 */

package com.university.teacherreviewsystem.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
