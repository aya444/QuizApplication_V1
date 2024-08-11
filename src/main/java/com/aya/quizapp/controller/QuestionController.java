package com.aya.quizapp.controller;


import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    private QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("all")
    public ResponseEntity<List<QuestionOutputDto>> getAllQuestions() {
        List<QuestionOutputDto> questions = questionService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("category/{cat}")
    public ResponseEntity<List<QuestionOutputDto>> getQuestionsByCategory(@PathVariable("cat") @NotNull String category) {
        List<QuestionOutputDto> questions = questionService.getQuestionsByCategory(category);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<QuestionOutputDto> addQuestions(@RequestBody @Valid QuestionInputDto questionInputDto) {
        QuestionOutputDto question = questionService.createQuestion(questionInputDto);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<QuestionOutputDto> editQuestion(@PathVariable @NotNull Integer id, @RequestBody @Valid QuestionInputDto updatedQuestionInputDto) {
        QuestionOutputDto updatedQuestion = questionService.editQuestion(id, updatedQuestionInputDto);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable @NotNull Integer id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>("Question with id " + id + " is successfully deleted!", HttpStatus.OK);
    }
}
