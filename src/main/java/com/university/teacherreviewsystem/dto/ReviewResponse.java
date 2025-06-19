/**
 * Назначение: Ответ с отзывом, который сервер отправляет клиенту в ответ
 */

package com.university.teacherreviewsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewResponse {
    private UUID id;
    private String semester;
    private Map<String, Integer> scores;
    private String comment;
}
