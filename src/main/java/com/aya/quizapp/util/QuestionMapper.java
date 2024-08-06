package com.aya.quizapp.util;

import com.aya.quizapp.model.dto.QuestionDto;
import com.aya.quizapp.model.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDto toDto(Question question);

    Question toEntity(QuestionDto questionDto);

    void updateEntityFromDto(@MappingTarget Question question, QuestionDto dto);
}
