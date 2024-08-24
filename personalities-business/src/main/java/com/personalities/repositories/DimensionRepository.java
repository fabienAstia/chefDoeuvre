package com.personalities.repositories;

import com.personalities.entities.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DimensionRepository extends JpaRepository<Dimension, Long> {

    Dimension findByNameIgnoreCase(String dimensionName);
}
