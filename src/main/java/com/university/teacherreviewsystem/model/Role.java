/**
 * Назначение: роли пользователей
 * Студент - может оставлять отзывы
 * Преподаватель - может просматривать свои отзывы и среднюю оценку по ним
 * Модератор - может управлять отзывами и критериями
 */

package com.university.teacherreviewsystem.model;

public enum Role {
    STUDENT,
    TEACHER,
    MODERATOR
}
