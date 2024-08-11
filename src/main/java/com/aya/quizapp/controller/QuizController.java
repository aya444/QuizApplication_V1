package com.aya.quizapp.controller;


import com.aya.quizapp.model.dto.QuestionOutputDto;
import com.aya.quizapp.model.dto.QuizDto;
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

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("create")
    public ResponseEntity<QuizDto> createQuiz(@RequestParam @NotNull String category, @RequestParam @NotNull int numOfQuestions, @RequestParam @NotNull String title) {
        QuizDto quiz = quizService.createQuiz(category, numOfQuestions, title);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }


    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionOutputDto>> getQuizQuestionsById(@PathVariable @NotNull Integer id) {
        List<QuestionOutputDto> questions = quizService.getQuizQuestions(id);
        return new ResponseEntity<>(questions, HttpStatus.OK);

    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> getResult(@PathVariable @NotNull Integer id, @RequestBody @Valid List<Response> responseList) {
        Integer result = quizService.calculateResults(id, responseList);
        return new ResponseEntity<>("The result is " + result + "!", HttpStatus.OK);
    }
}
