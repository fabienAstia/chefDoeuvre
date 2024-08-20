package com.personalities.repositories;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionView;
import com.personalities.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    //@Override
    List<QuestionView> findAllProjectedBy();

    @Override
    void deleteById(Long aLong);
}
