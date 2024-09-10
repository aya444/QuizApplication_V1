package com.aya.quizapp.service;

import com.aya.quizapp.exception.InvalidQuestionDataException;
import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.exception.ResultsNotFoundException;
import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.repository.QuestionRepository;
import com.aya.quizapp.service.impl.QuestionServiceImpl;
import com.aya.quizapp.util.QuestionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService = new QuestionServiceImpl();

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSuccessfullyGetAllQuestions (){
        // Given
        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question2 = new Question(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null", "Easy", "FALSE", "Java");
        List<Question> questionList = List.of(question1, question2);

        QuestionOutputDto questionOutputDto1 = new QuestionOutputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements");
        QuestionOutputDto questionOutputDto2 = new QuestionOutputDto(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null");
        List<QuestionOutputDto> questionOutputDtoList = List.of(questionOutputDto1, questionOutputDto2);

        // Mock QuestionRepository and QuestionMapper's behavior for any question passed
        when(questionRepository.findAllQuestionsSorted()).thenReturn(questionList);
        when(questionMapper.fromEntityToDto(any(Question.class))).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            return new QuestionOutputDto(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
        });

        // When
        List<QuestionOutputDto> result = questionService.getAllQuestions();

        // Then
        Assertions.assertEquals(result, questionOutputDtoList);
    }

    @Test
    void shouldSuccessfullyGetQuestionsByCategory(){
        // Given
        String category = "Java";
        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question2 = new Question(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null", "Easy", "FALSE", "Java");
        List<Question> questionList = List.of(question1, question2);

        QuestionOutputDto questionOutputDto1 = new QuestionOutputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements");
        QuestionOutputDto questionOutputDto2 = new QuestionOutputDto(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null");
        List<QuestionOutputDto> questionOutputDtoList = List.of(questionOutputDto1, questionOutputDto2);

        // Mock QuestionRepository and QuestionMapper's behavior for any question passed
        when(questionRepository.findByCategory(category)).thenReturn(questionList);
        when(questionMapper.fromEntityToDto(any(Question.class))).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            return new QuestionOutputDto(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
        });

        // When
        List<QuestionOutputDto> result = questionService.getQuestionsByCategory(category);

        // Then
        Assertions.assertEquals(result, questionOutputDtoList);
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenNoQuestionsFoundForGetQuestionByCategory(){
        // Given
        String category = "Java"; // Invalid category

        // When
        ResultsNotFoundException exception = assertThrows(
                ResultsNotFoundException.class,
                () -> questionService.getQuestionsByCategory(category)
        );

        // Then
        assertEquals("No questions found for category: " + category, exception.getMessage());
    }

    @Test
    void shouldSuccessfullyCreateQuestion(){
        // Given
        QuestionInputDto questionInputDto = new QuestionInputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        QuestionOutputDto questionOutputDto = new QuestionOutputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements");

        // Mock
        when(questionMapper.fromDtoToEntity(questionInputDto)).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.fromEntityToDto(question)).thenReturn(questionOutputDto);

        // When
        QuestionOutputDto result = questionService.createQuestion(questionInputDto);

        // Then
        assertEquals(result, questionOutputDto);
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenQuestionDataIsNullForCreateQuestion(){
        // Given
        QuestionInputDto questionInputDto = null;

        // When
        InvalidQuestionDataException exception = assertThrows(
                InvalidQuestionDataException.class,
                () -> questionService.createQuestion(questionInputDto)
        );

        // Then
        assertEquals("Question data cannot be null!", exception.getMessage());
    }

    @Test
    void shouldSuccessfullyEditQuestion() {
        // Given
        int questionId = 18;
        QuestionInputDto questionInputDto = new QuestionInputDto(18, "Updated Question?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question beforeUpdateQuestion = new Question(18, "Original Question", "option1", "option2", "option3", "option4", "Easy", "extends", "Java");
        Question afterUpdateQuestion = new Question(18, "Updated Question?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        QuestionOutputDto questionOutputDto = new QuestionOutputDto(18, "Updated Question?", "class", "interface", "extends", "implements");

        // Mock
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(beforeUpdateQuestion));
        when(questionMapper.updateEntityFromDto(beforeUpdateQuestion, questionInputDto)).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            QuestionInputDto dto = invocation.getArgument(1);
            // Update properties
            question.setQuestionTitle(dto.getQuestionTitle());
            question.setOption1(dto.getOption1());
            question.setOption2(dto.getOption2());
            question.setOption3(dto.getOption3());
            question.setOption4(dto.getOption4());
            return question; // Return the updated question
        });
        when(questionRepository.save(afterUpdateQuestion)).thenReturn(afterUpdateQuestion);
        when(questionMapper.fromEntityToDto(afterUpdateQuestion)).thenReturn(questionOutputDto);

        // When
        QuestionOutputDto result = questionService.editQuestion(questionId, questionInputDto);

        // Then
        assertEquals(result, questionOutputDto);
    }


    @Test
    void shouldSuccessfullyThrowErrorWhenQuestionDataIsNullForUpdateQuestion(){
        // Given
        int questionId = 18;
        QuestionInputDto questionInputDto = null;

        // When
        InvalidQuestionDataException exception = assertThrows(
                InvalidQuestionDataException.class,
                () -> questionService.editQuestion(questionId, questionInputDto)
        );

        // Then
        assertEquals("Updated question data cannot be null!", exception.getMessage());
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenNoQuestionsFoundForUpdateQuestion(){
        // Given
        int questionId = 18;

        // Mock
        when(questionRepository.findById(questionId)).thenReturn(null);

        // When
        QuestionNotFoundException exception = assertThrows(
                QuestionNotFoundException.class,
                () -> questionService.deleteQuestion(questionId)
        );

        // Then
        assertEquals("Question Id not found!", exception.getMessage());
    }

    @Test
    void shouldSuccessfullyDeleteQuestion(){
        // Given
        int questionId = 18;

        // Mock
        when(questionRepository.existsById(questionId)).thenReturn(true);

        // When
        questionService.deleteQuestion(questionId);

        // Then verify that the delete method was called
        verify(questionRepository).deleteById(questionId);
    }


    @Test
    void shouldSuccessfullyThrowErrorWhenNoQuestionsFoundForDeleteQuestionById(){
        // Given
        int questionId = 18;

        // Mock
        when(questionRepository.existsById(questionId)).thenReturn(false);

        // When
        QuestionNotFoundException exception = assertThrows(
                QuestionNotFoundException.class,
                () -> questionService.deleteQuestion(questionId)
        );

        // Then
        assertEquals("Question Id not found!", exception.getMessage());
    }

}