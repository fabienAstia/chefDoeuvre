package com.personalities.repositories;

import com.personalities.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
