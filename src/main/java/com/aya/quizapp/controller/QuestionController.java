package com.aya.quizapp.controller;


import com.aya.quizapp.exception.InvalidQuizDataException;
import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.model.dto.QuestionInputDto;
import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
@Validated
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // TODO make a Global Exception Handler
    @GetMapping("all")
    public ResponseEntity<?> getAllQuestions() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getAllQuestions());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("category/{cat}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable("cat") @NotNull String category) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsByCategory(category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("add")
    public ResponseEntity<QuestionOutputDto> addQuestions(@RequestBody @Valid QuestionInputDto questionInputDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(questionInputDto));
        } catch (InvalidQuizDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editQuestion(@PathVariable @NotNull Integer id, @RequestBody @Valid QuestionInputDto updatedQuestionInputDto) {
        try {
            QuestionOutputDto updatedQuestion = questionService.editQuestion(id, updatedQuestionInputDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);
        } catch (QuestionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (InvalidQuizDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable @NotNull Integer id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.status(HttpStatus.OK).body("Question with id " + id + " is successfully deleted!");
        } catch (QuestionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
