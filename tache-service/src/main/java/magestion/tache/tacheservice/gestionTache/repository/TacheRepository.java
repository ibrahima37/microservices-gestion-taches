package magestion.tache.tacheservice.gestionTache.repository;

import magestion.tache.tacheservice.gestionTache.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TacheRepository extends JpaRepository<Tache, UUID> {

    List<Tache> findByAssignedId(UUID assignedId);
}
