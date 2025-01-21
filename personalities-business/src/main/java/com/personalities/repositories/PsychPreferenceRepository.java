package com.personalities.repositories;

import com.personalities.entities.PsychPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsychPreferenceRepository extends JpaRepository<PsychPreference, Long> {

    PsychPreference findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

}
