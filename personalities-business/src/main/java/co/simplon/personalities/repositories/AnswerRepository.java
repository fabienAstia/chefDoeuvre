package co.simplon.personalities.repositories;

import co.simplon.personalities.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByQuestionIdAndUserId(Long questionId, Long userId);

    List<Answer> findAllByUserId(Long userId);
}
