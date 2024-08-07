package com.aya.quizapp.model.dto;

import com.aya.quizapp.model.entity.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Data
public class QuizDto {
    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "List cannot be blank")
    private List<Question> questions;
}
