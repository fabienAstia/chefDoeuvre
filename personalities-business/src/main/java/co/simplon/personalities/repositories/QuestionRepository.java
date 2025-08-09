package co.simplon.personalities.repositories;

import co.simplon.personalities.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllProjectedBy();

    Page<Question> findAllByOrderByOrderValue(Pageable pageable);

    @Override
    void deleteById(Long aLong);

    boolean existsByLabelIgnoreCase(String label);

    boolean existsByLabelIgnoreCaseAndIdNot(String label, Long id);

    OnlyOrderValue findTopByOrderByOrderValueDesc();

    interface OnlyOrderValue {
        Integer getOrderValue();
    }
}
