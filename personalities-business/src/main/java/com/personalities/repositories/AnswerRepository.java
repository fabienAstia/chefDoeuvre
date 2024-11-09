package com.personalities.repositories;

import com.personalities.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository <Answer, Long> {

}
