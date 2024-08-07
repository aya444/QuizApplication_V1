package com.aya.quizapp.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;
    @Column(name = "question_title", nullable = false)
    private String questionTitle;
    @Column(name = "option1", nullable = false)
    private String option1;
    @Column(name = "option2", nullable = false)
    private String option2;
    @Column(name = "option3", nullable = false)
    private String option3;
    @Column(name = "option4", nullable = false)
    private String option4;
    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;
    @Column(name = "right_answer", nullable = false)
    private String rightAnswer;
    @Column(name = "category", nullable = false)
    private String category;

}
