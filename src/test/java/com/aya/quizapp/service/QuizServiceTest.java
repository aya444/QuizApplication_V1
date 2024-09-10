package com.aya.quizapp.service;

import com.aya.quizapp.exception.InvalidQuizDataException;
import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.exception.QuizNotFoundException;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.dto.QuizDto;
import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.model.entity.Quiz;
import com.aya.quizapp.model.entity.Response;
import com.aya.quizapp.repository.QuizRepository;
import com.aya.quizapp.service.impl.QuizServiceImpl;
import com.aya.quizapp.util.QuizMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuizServiceTest {

    // Which services we need to test and inject the dependencies that it needs
    @InjectMocks
    private QuizService quizService = new QuizServiceImpl();

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizMapper quizMapper;

    // Tell the mockito to start mocking the current class
    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldSuccessfullyCreateQuiz() {
        // Given
        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question2 = new Question(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null", "Easy", "FALSE", "Java");
        List<Question> questionList = List.of(question1, question2);

        Quiz quiz = Quiz.builder()
                .id(1)
                .title("Java Quiz")
                .questions(questionList)
                .build();
        QuizDto quizDto = new QuizDto(1, "Java Quiz", questionList);

        String category = "Java";
        String title = "Java Quiz";
        int numOfQuestions = 2;

        // Mock repository and mapper behavior
        when(quizRepository.findRandomQuestionsByCategory(category, numOfQuestions)).thenReturn(questionList);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.fromEntityToDto(quiz)).thenReturn(quizDto); // Mock the mapper to return quizDto

        // When
        QuizDto result = quizService.createQuiz(category, numOfQuestions, title);

        // Then
        assertEquals(result.getTitle(), title);
        assertEquals(result.getQuestions(), questionList);
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenInvalidNumberOfQuestions() {
        // Given
        String category = "Java";
        String title = "Java Quiz";
        int numOfQuestions = 0;  // Invalid number of questions

        // When
        InvalidQuizDataException exception = assertThrows(
                InvalidQuizDataException.class,
                () -> quizService.createQuiz(category, numOfQuestions, title)
        );

        // Then
        assertEquals("Number of questions must be greater than 0!", exception.getMessage());
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenNoQuestionFoundInCategory() {
        // Given
        String category = "Java"; // Invalid category
        String title = "Java Quiz";
        int numOfQuestions = 1;

        // When
        QuestionNotFoundException exception = assertThrows(
                QuestionNotFoundException.class,
                () -> quizService.createQuiz(category, numOfQuestions, title)
        );

        // Then
        assertEquals("No questions found for category: " + category, exception.getMessage());
    }

    @Test
    void shouldSuccessfullyGetQuizQuestions() {
        // Given
        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question2 = new Question(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null", "Easy", "FALSE", "Java");
        List<Question> questionList = List.of(question1, question2);

        QuestionOutputDto questionOutputDto1 = new QuestionOutputDto(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements");
        QuestionOutputDto questionOutputDto2 = new QuestionOutputDto(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null");
        List<QuestionOutputDto> questionOutputDtoList = List.of(questionOutputDto1, questionOutputDto2);

        Integer quizId = 1;
        Quiz quiz = Quiz.builder()
                .id(quizId)
                .title("Java Quiz")
                .questions(questionList)
                .build();

        // Mock the repository to return the quiz
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        // When
        List<QuestionOutputDto> result = quizService.getQuizQuestions(quizId);

        // Then
        assertThat(result).isEqualTo(questionOutputDtoList);  // Verify the result matches the expected DTO list
        verify(quizRepository).findById(quizId);  // Verify the findById interaction occurred
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenInvalidQuizIdInGetQuizQuestions() {
        // Given
        int quizId = 1;

        // When
        QuizNotFoundException exception = assertThrows(
                QuizNotFoundException.class,
                () -> quizService.getQuizQuestions(quizId)
        );

        // Then
        assertEquals("Quiz Id not found!", exception.getMessage());
    }


    @Test
    void shouldSuccessfullyCalculateResults() {
        // Give
        int quizId = 1;
        Response response1 = new Response(18, "extends");
        Response response2 = new Response(19, "FALSE");
        List<Response> responseList = List.of(response1, response2);

        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        Question question2 = new Question(19, "In Java, what is the default value of an uninitialized boolean variable?", "TRUE", "FALSE", "0", "null", "Easy", "FALSE", "Java");
        List<Question> questionList = List.of(question1, question2);

        Quiz quiz = Quiz.builder()
                .id(quizId)
                .title("Java Quiz")
                .questions(questionList)
                .build();

        // Mock the repository to return the quiz
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        // When
        Integer result = quizService.calculateResults(quizId, responseList);

        // Then
        assertEquals(result, 2);

    }

    @Test
    void shouldSuccessfullyThrowErrorWhenInvalidQuizIdInCalculateResults() {
        // Given
        int quizId = 1;
        Response response1 = new Response(18, "extends");
        Response response2 = new Response(19, "FALSE");
        List<Response> responseList = List.of(response1, response2);

        // When
        QuizNotFoundException exception = assertThrows(
                QuizNotFoundException.class,
                () -> quizService.calculateResults(quizId, responseList)
        );

        // Then
        assertEquals("Quiz Id not found!", exception.getMessage());
    }

    @Test
    void shouldSuccessfullyThrowErrorWhenInvalidNumberOfResponsesWithQuestions() {
        // Given
        int quizId = 1;
        Response response1 = new Response(18, "extends");
        Response response2 = new Response(19, "FALSE");
        List<Response> responseList = List.of(response1, response2);

        Question question1 = new Question(18, "Which Java keyword is used to create a subclass?", "class", "interface", "extends", "implements", "Easy", "extends", "Java");
        List<Question> questionList = List.of(question1);

        Quiz quiz = Quiz.builder()
                .id(quizId)
                .title("Java Quiz")
                .questions(questionList)
                .build();

        // Mock the repository to return the quiz
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        // When
        InvalidQuizDataException exception = assertThrows(
                InvalidQuizDataException.class,
                () -> quizService.calculateResults(quizId, responseList)
        );

        // Then
        assertEquals("Mismatch between the number of responses and questions!", exception.getMessage());
    }

}