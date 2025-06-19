/**
 * Назначение:
 * Хранит оценки студентов
 * Использует JSONB для гибкого хранения оценок по критериям
 * Предотвращает повторные оценки (один студент = один отзыв на преподавателя за семестр)
 */

package com.university.teacherreviewsystem.model;

import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.annotations.Type;
//import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
//import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher; // Какого преподавателя оценивают

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student; // Какой студент оставил отзыв

    private String semester; // В каком семестре

    /**
     * JSON field, format: { "criteria_id": score, ... }
     * Saved in the database as JSONB
     */
//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    @Convert(converter = ScoresConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<UUID, Integer> scores;

    private String comment;

    @Column(name = "is_hidden")
    private boolean hidden = false;
}
