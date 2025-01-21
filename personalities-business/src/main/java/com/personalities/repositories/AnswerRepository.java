package com.personalities.repositories;

import com.personalities.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByQuestionIdAndUserId(Long questionId, Long userId);

    List<Answer> findAllByUserId(Long userId);
}
