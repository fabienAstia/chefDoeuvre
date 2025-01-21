package com.personalities.repositories;

import com.personalities.entities.Constraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstraintRepository extends JpaRepository<Constraint, Long> {
    Constraint findByMinAndMax(Integer min, Integer max);
}
