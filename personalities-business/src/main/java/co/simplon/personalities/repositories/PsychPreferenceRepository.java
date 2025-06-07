package co.simplon.personalities.repositories;

import co.simplon.personalities.entities.PsychPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsychPreferenceRepository extends JpaRepository<PsychPreference, Long> {

    PsychPreference findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

}
