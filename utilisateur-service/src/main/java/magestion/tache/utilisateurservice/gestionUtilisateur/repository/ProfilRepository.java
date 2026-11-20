package magestion.tache.utilisateurservice.gestionUtilisateur.repository;

import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfilRepository extends JpaRepository<Profil, UUID> {

    Optional<Profil> findByNomProfil(String nomProfil);

    boolean existsByNomProfil(String nomProfil);

    boolean existsByNomProfilAndIdNot(String nomProfil, UUID id);
}
