package com.personalities.repositories;

import com.personalities.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllProjectedBy();

    @Override
    void deleteById(Long aLong);

    boolean existsByNameIgnoreCase(String value);

    boolean existsByNameIgnoreCaseAndIdNot(String value, Long id);
}
