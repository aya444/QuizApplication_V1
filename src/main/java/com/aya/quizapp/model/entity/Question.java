package com.aya.quizapp.model.entity;


import com.aya.quizapp.model.dto.QuestionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;
    @Column(name = "questiontitle", nullable = false)
    private String questionTitle;
    @Column(name = "option1", nullable = false)
    private String option1;
    @Column(name = "option2", nullable = false)
    private String option2;
    @Column(name = "option3", nullable = false)
    private String option3;
    @Column(name = "option4", nullable = false)
    private String option4;
    @Column(name = "difficultylevel", nullable = false)
    private String difficultyLevel;
    @Column(name = "rightanswer", nullable = false)
    private String rightAnswer;
    @Column(name = "category", nullable = false)
    private String category;

//    public static Question toEntity(QuestionDto questiondto){
//        return Question.builder()
//                .id(questiondto.getId())
//                .questionTitle(questiondto.getQuestionTitle())
//                .option1(questiondto.getOption1())
//                .option2(questiondto.getOption2())
//                .option3(questiondto.getOption3())
//                .option4(questiondto.getOption4())
//                .difficultyLevel(questiondto.getDifficultyLevel())
//                .rightAnswer(questiondto.getRightAnswer())
//                .category(questiondto.getCategory())
//                .build();
//    }



}
