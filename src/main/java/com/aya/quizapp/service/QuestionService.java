package com.aya.quizapp.service;

import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.model.dto.QuestionDto;
import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.repository.QuestionRepo;
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
    private QuestionRepo questionRepo;

    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;

    public List<QuestionDto> getAllQuestions(){
        List<Question> questionList= this.questionRepo.findAllQuestionsSorted();
        return questionList.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<QuestionDto> getQuestionsByCategory(String category){
        List<Question> foundQuestionList = questionRepo.findByCategory(category);
        return foundQuestionList.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionDto createQuestion(@Valid QuestionDto questionDto){
        Optional.ofNullable(questionDto).orElseThrow(() -> new IllegalArgumentException("Question cannot be null"));
        Question questionEntity = questionMapper.toEntity(questionDto);
        Question savedQuestionEntity = questionRepo.save(questionEntity);
        return questionMapper.toDto(savedQuestionEntity);
    }

    public QuestionDto editQuestion(Integer id, @Valid QuestionDto updatedQuestionDto) {
        return questionRepo.findById(id)
                .map(question -> {
                    questionMapper.updateEntityFromDto(question, updatedQuestionDto);
                    Question updatedQuestion = questionRepo.save(question);
                    return questionMapper.toDto(updatedQuestion);
                })
                .orElseThrow(() -> new QuestionNotFoundException("Question Id not found!"));
    }

    public void deleteQuestion(Integer id) {
        if(questionRepo.existsById(id)){
            questionRepo.deleteById(id);
        }else {
            throw new QuestionNotFoundException("Question Id not found!");
        }
    }
}
