package com.university.teacherreviewsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;
import java.util.UUID;

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
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    private String semester;

    /**
     * JSON field, format: { "criteria_id": score, ... }
     * Saved in the database as JSONB
     */
    @Column(columnDefinition = "jsonb")
    @Convert(converter = ScoresConverter.class)
    private Map<UUID, Integer> scores;

    private String comment;

    @Column(name = "is_hidden")
    private boolean hidden = false;
}
