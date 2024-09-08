package com.aya.quizapp.repository;

import com.aya.quizapp.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT q FROM Question q ORDER BY q.id ASC ")
    List<Question> findAllQuestionsSorted();

     List<Question> findByCategory(String category);
}
