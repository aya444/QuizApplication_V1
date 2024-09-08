package com.aya.quizapp.util;

import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.entity.Question;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class QuestionMapperTest {

    // Service we want to test
    private static QuestionMapper questionMapper;

    @BeforeAll
    static void beforeAll() {
        questionMapper = new QuestionMapperImpl();
    }

    @Test
    public void shouldMapQuestionInputDtoToEntity() {
        // Given
        QuestionInputDto questionInputDto = new QuestionInputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");

        // When
        Question myQuestion = questionMapper.fromDtoToEntity(questionInputDto);

        // Then
        assertThat(myQuestion)
                .usingRecursiveComparison()
                .isEqualTo(questionInputDto);
    }

    @Test
    public void shouldMapFromEntityToQuestionOutputDto() {
        // Given
        Question question = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");

        // When
        QuestionOutputDto questionOutputDto = questionMapper.fromEntityToDto(question);

        // Then
        assertThat(questionOutputDto)
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Test
    public void shouldMapUpdatedEntityToQuestionInputDto() {
        // Given
        Question question = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "C++");
        QuestionInputDto questionInputDto = new QuestionInputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");

        // When
        Question updatedQuestion = questionMapper.updateEntityFromDto(question, questionInputDto);

        // Then
        assertNotNull(questionInputDto);
        assertThat(updatedQuestion)
                .usingRecursiveComparison()
                .isEqualTo(question);
    }


    // @AfterEach -> To reset the value of a specific attribute or re-initiate a value
    // @BeforeEach -> To initialize anything before each method
    // @BeforeAll -> To initialize specific attributes/services/instances before the execution of the whole class (Once)
    // @AfterAll -> To tear-down/remove anything after the execution of the whole class (Once)
    // @BeforeAll & @AfterAll -> are very helpful especially in cases as, inserting data and removing it so that if prevents the pollution of the DB
}