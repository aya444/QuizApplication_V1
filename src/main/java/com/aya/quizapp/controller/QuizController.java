package com.aya.quizapp.controller;


import com.aya.quizapp.exception.InvalidQuizDataException;
import com.aya.quizapp.exception.QuestionNotFoundException;
import com.aya.quizapp.exception.QuizNotFoundException;
import com.aya.quizapp.model.entity.Response;
import com.aya.quizapp.service.QuizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<?> createQuiz(@RequestParam @NotNull String category, @RequestParam @NotNull int numOfQuestions, @RequestParam @NotNull String title) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.createQuiz(category, numOfQuestions, title));
        } catch (QuestionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidQuizDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping("get/{id}")
    public ResponseEntity<?> getQuizQuestionsById(@PathVariable @NotNull Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.getQuizQuestions(id));
        } catch (QuizNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<?> getResult(@PathVariable @NotNull Integer id, @RequestBody @Valid List<Response> responseList) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.calculateResults(id, responseList));
        } catch (QuizNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidQuizDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
