package com.aya.quizapp.exception;

public class InvalidQuestionDataException extends RuntimeException{
    public InvalidQuestionDataException(String message) {
        super(message);
    }
}
