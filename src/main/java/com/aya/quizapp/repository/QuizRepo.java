package com.aya.quizapp.repository;

import com.aya.quizapp.model.entity.Question;
import com.aya.quizapp.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz, Integer> {

    @Query("SELECT q FROM Question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ")
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
