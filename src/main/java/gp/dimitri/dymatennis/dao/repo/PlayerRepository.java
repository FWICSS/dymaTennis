package gp.dimitri.dymatennis.dao.repo;

import gp.dimitri.dymatennis.dao.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByLastNameIgnoreCase(String lastName);
}
