package com.aya.quizapp.service;

import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.exception.QuizNotFoundException;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.dto.QuizDto;
import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.model.entity.Quiz;
import com.aya.quizapp.model.entity.Response;
import com.aya.quizapp.repository.QuizRepo;
import com.aya.quizapp.util.QuizMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    private final QuizMapper quizMapper = QuizMapper.INSTANCE;

    public QuizDto createQuiz(String category, int numQ, String title) {
        if (numQ <= 0) {
            throw new IllegalArgumentException("Number of questions must be greater than 0");
        }

        List<Question> foundRandomQuestionsForCategory = quizRepo.findRandomQuestionsByCategory(category, numQ);
        if (foundRandomQuestionsForCategory.isEmpty()) {
            throw new QuestionNotFoundException("No questions found for the given category: " + category);
        }

        Quiz quiz = quizRepo.save(Quiz.builder()
                .title(title)
                .questions(foundRandomQuestionsForCategory)
                .build());
        return quizMapper.toDto(quiz);
    }

    public List<QuestionOutputDto> getQuizQuestions(Integer id) {
        Optional<Quiz> quizOptional = quizRepo.findById(id);
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

    public int calculateResults(Integer id, @Valid List<Response> responseList) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz Not Found!"));
        List<Question> questionsList = quiz.getQuestions();

        if (responseList.size() != questionsList.size()) {
            throw new QuizNotFoundException("Mismatch between the number of responses and questions");
        }

        int total=0;
        for (int i = 0; i < responseList.size(); i++) {
            if (responseList.get(i).getResponse().equals(questionsList.get(i).getRightAnswer())) {
                total++;
            }
        }
        return total;
    }
}
