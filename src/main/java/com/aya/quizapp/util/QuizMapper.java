package com.aya.quizapp.util;

import com.aya.quizapp.model.dto.QuizDto;
import com.aya.quizapp.model.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuizMapper {
    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    QuizDto fromEntityToDto(Quiz quiz);
}
