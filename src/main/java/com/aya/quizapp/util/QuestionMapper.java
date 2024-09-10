package com.aya.quizapp.util;

import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionOutputDto fromEntityToDto(Question question);

    Question fromDtoToEntity(QuestionInputDto questionInputDto);

    @Mapping(target = "id", ignore = true)
    Question updateEntityFromDto(@MappingTarget Question question, QuestionInputDto dto);
}
