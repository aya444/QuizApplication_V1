package com.aya.quizapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class QuestionInputDto {

    private Integer id;

    @NotBlank(message = "Question title cannot be blank")
    private String questionTitle;

    @NotBlank(message = "Option1 cannot be blank")
    private String option1;

    @NotBlank(message = "Option2 cannot be blank")
    private String option2;

    @NotBlank(message = "Option3 cannot be blank")
    private String option3;

    @NotBlank(message = "Option4 cannot be blank")
    private String option4;

    @NotBlank(message = "Right Answer cannot be blank")
    private String rightAnswer;

    @NotBlank(message = "Difficulty Level cannot be blank")
    private String difficultyLevel;

    @NotBlank(message = "Category cannot be blank")
    private String category;

}
