package com.aya.quizapp.exception;

public class ResultsNotFoundException extends RuntimeException{
    public ResultsNotFoundException(String message) {
        super(message);
    }
}
