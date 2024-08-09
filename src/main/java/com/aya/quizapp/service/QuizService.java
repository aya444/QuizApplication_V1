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
import com.aya.quizapp.util.QuizMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    private final QuizMapper quizMapper = QuizMapper.INSTANCE;

    public QuizDto createQuiz(String category, int numOfQuestions, String title) {
        if (numOfQuestions <= 0) {
            throw new InvalidQuizDataException("Number of questions must be greater than 0!");
        }

        List<Question> foundRandomQuestionsForCategory = quizRepository.findRandomQuestionsByCategory(category, numOfQuestions);
        if (foundRandomQuestionsForCategory.isEmpty()) {
            throw new QuestionNotFoundException("No questions found for category: " + category);
        }

        Quiz quiz = quizRepository.save(Quiz.builder()
                .title(title)
                .questions(foundRandomQuestionsForCategory)
                .build());
        return quizMapper.fromEntityToDto(quiz);
    }

    public List<QuestionOutputDto> getQuizQuestions(Integer id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isEmpty()) throw new QuizNotFoundException("Quiz Id not found!");

        Quiz quiz = quizOptional.get();
        List<Question> questionList = quiz.getQuestions();
        List<QuestionOutputDto> questionsOutputDto = new ArrayList<>();

        for (Question q : questionList) {
            questionsOutputDto.add(QuestionOutputDto.builder()
                    .id(q.getId())
                    .questionTitle(q.getQuestionTitle())
                    .option1(q.getOption1())
                    .option2(q.getOption2())
                    .option3(q.getOption3())
                    .option4(q.getOption4())
                    .build());
        }
        return questionsOutputDto;
    }

    public Integer calculateResults(Integer id, @Valid List<Response> responseList) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz Id not found!"));
        List<Question> questionsList = quiz.getQuestions();

        if (responseList.size() != questionsList.size()) {
            throw new InvalidQuizDataException("Mismatch between the number of responses and questions!");
        }

        Integer result = 0;
        for (int i = 0; i < responseList.size(); i++) {
            if (responseList.get(i).getResponse().equals(questionsList.get(i).getRightAnswer())) {
                result++;
            }
        }
        return result;
    }
}
