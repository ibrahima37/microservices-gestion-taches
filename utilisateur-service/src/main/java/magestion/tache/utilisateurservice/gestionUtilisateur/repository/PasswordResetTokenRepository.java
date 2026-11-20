package magestion.tache.utilisateurservice.gestionUtilisateur.repository;

import magestion.tache.utilisateurservice.gestionUtilisateur.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByToken(String token);
}