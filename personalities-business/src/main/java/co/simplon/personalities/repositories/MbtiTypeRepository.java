package co.simplon.personalities.repositories;

import co.simplon.personalities.entities.MbtiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiTypeRepository extends JpaRepository<MbtiType, Long> {

    MbtiType findProjectedByCode(String code);
}
