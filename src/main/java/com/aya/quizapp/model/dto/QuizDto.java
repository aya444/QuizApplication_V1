package com.aya.quizapp.model.dto;

import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.model.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {
    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "List cannot be blank")
    private List<Question> questions;

//    public static QuizDto toDto(Quiz quiz){
//        return QuizDto.builder()
//                .id(quiz.getId())
//                .title(quiz.getTitle())
//                .questions(quiz.getQuestions())
//                .build();
//    }
}
