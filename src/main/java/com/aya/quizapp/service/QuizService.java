package com.aya.quizapp.service;

import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.dto.QuizDto;
import com.aya.quizapp.model.entity.Response;

import java.util.List;

public interface QuizService {
    QuizDto createQuiz(String category, Integer numOfQuestions, String title);

    List<QuestionOutputDto> getQuizQuestions(Integer id);

    Integer calculateResults(Integer id, List<Response> responseList);
}
