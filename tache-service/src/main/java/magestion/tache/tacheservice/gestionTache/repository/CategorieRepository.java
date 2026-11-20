package magestion.tache.tacheservice.gestionTache.repository;

import magestion.tache.tacheservice.gestionTache.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategorieRepository extends JpaRepository<Categorie, UUID> {
}
