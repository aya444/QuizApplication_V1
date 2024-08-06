package com.aya.quizapp.controller;


import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.model.dto.QuestionDto;
import com.aya.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("question")
@Validated
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // TODO make a Global Exception Handler
    @GetMapping("all")
    public ResponseEntity<List<QuestionDto>> getAllQuetions(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getAllQuestions());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @GetMapping("category/{cat}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable("cat") @NotNull String category){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsByCategory(category));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping("add")
    public ResponseEntity<QuestionDto> addQuestions(@RequestBody @Valid QuestionDto questionDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(questionDto));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editQuestion(@PathVariable @NotNull Integer id, @RequestBody @Valid QuestionDto updatedQuestionDto) {
        try {
            QuestionDto updatedQuestion = questionService.editQuestion(id, updatedQuestionDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);
        } catch (QuestionNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable @NotNull Integer id){
        try{
            questionService.deleteQuestion(id);
            return ResponseEntity.status(HttpStatus.OK).body("Question with id "+ id +" is successfully deleted!");
        }catch (QuestionNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
