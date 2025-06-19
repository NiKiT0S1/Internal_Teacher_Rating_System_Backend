/**
 * Назначение: Хранит критерии, по которым студенты оценивают преподавателей
 */

package com.university.teacherreviewsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "criteria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Criteria {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
