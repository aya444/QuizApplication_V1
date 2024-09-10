package com.aya.quizapp.util;

import com.aya.quizapp.model.dto.QuizDto;
import com.aya.quizapp.model.entity.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizDto fromEntityToDto(Quiz quiz);
}
