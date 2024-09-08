package com.aya.quizapp.util;

import com.aya.quizapp.model.dto.QuizDto;
import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.model.entity.Quiz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuizMapperTest {

    private static QuizMapper quizMapper;

    @BeforeAll
    static void beforeAll() {
        quizMapper = new QuizMapperImpl();
    }

    @Test
    public void shouldMapFromEntityToQuizDTO() {
        // Given
        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question2 = new Question(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null", "Easy", "FALSE", "Java");
        List<Question> questionList = new ArrayList<>() {
            {
                add(question1);
                add(question2);
            }
        };
        Quiz quiz = new Quiz(2, "Java Quiz", questionList);

        // When
        QuizDto quizDto = quizMapper.fromEntityToDto(quiz);

        // Then
        assertThat(quizDto)
                .usingRecursiveComparison()
                .isEqualTo(quiz);
    }
}