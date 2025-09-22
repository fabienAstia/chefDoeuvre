package co.simplon.personalities.repositories;

import co.simplon.personalities.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Answer a where a.user.id =:userId")
    int deleteAllByUserId(Long userId);
}
