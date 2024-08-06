package com.aya.quizapp.controller;


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
    public ResponseEntity<?> createQuiz(@RequestParam @NotNull String category, @RequestParam @NotNull int numQ, @RequestParam @NotNull String title){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.createQuiz(category, numQ, title));
        }catch (QuestionNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable @NotNull Integer id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.getQuizQuestions(id));
        }catch (QuestionNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<?> getResult(@PathVariable @NotNull Integer id, @RequestBody @Valid List<Response> responseList){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.calculateResults(id, responseList));
        }catch (QuizNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
