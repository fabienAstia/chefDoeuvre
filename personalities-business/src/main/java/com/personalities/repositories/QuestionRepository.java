package com.personalities.repositories;

import com.personalities.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllProjectedBy();

    Page<Question> findAll(Pageable pageable);

    @Override
    void deleteById(Long aLong);

    boolean existsByTextIgnoreCase(String name);

    boolean existsByTextIgnoreCaseAndIdNot(String name, Long id);
}
