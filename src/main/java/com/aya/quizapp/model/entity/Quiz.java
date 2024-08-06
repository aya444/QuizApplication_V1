package com.aya.quizapp.model.entity;

import com.aya.quizapp.model.dto.QuizDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany
    private List<Question> questions;

//    public static Quiz toEntity(QuizDto quizDto){
//        return Quiz.builder()
//                .id(quizDto.getId())
//                .title(quizDto.getTitle())
//                .questions(quizDto.getQuestions())
//                .build();
//    }
}
