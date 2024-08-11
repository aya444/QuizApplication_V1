package com.aya.quizapp.service;

import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;

import java.util.List;

public interface QuestionService {
    List<QuestionOutputDto> getAllQuestions();

    List<QuestionOutputDto> getQuestionsByCategory(String category);

    QuestionOutputDto createQuestion(QuestionInputDto questionInputDto);

    QuestionOutputDto editQuestion(Integer id, QuestionInputDto updatedQuestionInputDto);

    void deleteQuestion(Integer id);
}
