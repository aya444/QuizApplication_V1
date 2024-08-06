package com.aya.quizapp.model.dto;

import com.aya.quizapp.model.entity.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {

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

    @JsonIgnore
    @NotBlank(message = "Right Answer cannot be blank")
    private String rightAnswer;

    @JsonIgnore
    @NotBlank(message = "Difficulty Level cannot be blank")
    private String difficultyLevel;

    @JsonIgnore
    @NotBlank(message = "Category cannot be blank")
    private String category;


//    public static QuestionDto toDto(Question question) {
//        return QuestionDto.builder()
//                .id(question.getId())
//                .questionTitle(question.getQuestionTitle())
//                .option1(question.getOption1())
//                .option2(question.getOption2())
//                .option3(question.getOption3())
//                .option4(question.getOption4())
//                .difficultyLevel(question.getDifficultyLevel())
//                .category(question.getCategory())
//                .rightAnswer(question.getRightAnswer())
//                .build();
//    }
}
