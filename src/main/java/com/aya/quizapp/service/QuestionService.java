package com.aya.quizapp.service;

import com.aya.quizapp.exception.InvalidQuizDataException;
import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.repository.QuestionRepository;
import com.aya.quizapp.util.QuestionMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepo;

    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;

    public List<QuestionOutputDto> getAllQuestions() {
        List<Question> questionList = questionRepo.findAllQuestionsSorted();
        return questionList.stream()
                .map(questionMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public List<QuestionOutputDto> getQuestionsByCategory(String category) {
        List<Question> foundQuestionList = questionRepo.findByCategory(category);
        return foundQuestionList.stream()
                .map(questionMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public QuestionOutputDto createQuestion(@Valid QuestionInputDto questionInputDto) {
        Optional.ofNullable(questionInputDto).orElseThrow(() -> new InvalidQuizDataException("Question cannot be null!"));
        Question questionEntity = questionMapper.fromDtoToEntity(questionInputDto);
        Question savedQuestionEntity = questionRepo.save(questionEntity);
        return questionMapper.fromEntityToDto(savedQuestionEntity);
    }

    public QuestionOutputDto editQuestion(Integer id, @Valid QuestionInputDto updatedQuestionInputDto) {
        Optional.ofNullable(updatedQuestionInputDto).orElseThrow(() -> new InvalidQuizDataException("Updated question date cannot be null!"));
        return questionRepo.findById(id)
                .map(question -> {
                    questionMapper.updateEntityFromDto(question, updatedQuestionInputDto);
                    Question updatedQuestion = questionRepo.save(question);
                    return questionMapper.fromEntityToDto(updatedQuestion);
                })
                .orElseThrow(() -> new QuestionNotFoundException("Question Id not found!"));
    }

    public void deleteQuestion(Integer id) {
        if (questionRepo.existsById(id)) {
            questionRepo.deleteById(id);
        } else {
            throw new QuestionNotFoundException("Question Id not found!");
        }
    }
}
