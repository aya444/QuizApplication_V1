package com.aya.quizapp.exception;

public class QuizNotFoundException extends RuntimeException{
    public QuizNotFoundException(String message){
        super(message);
    }
}
