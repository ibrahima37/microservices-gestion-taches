package magestion.tache.utilisateurservice.gestionUtilisateur.repository;

import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PersonneRepository extends JpaRepository<Personne, UUID> {

    Optional<Personne> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT p FROM Personne p LEFT JOIN FETCH p.profils WHERE p.id = :id")
    Optional<Personne> findByIdWithProfils(@Param("id") UUID id);

    @Query("SELECT p FROM Personne p LEFT JOIN FETCH p.profils WHERE p.email = :email")
    Optional<Personne> findByEmailWithProfils(@Param("email") String email);
}
