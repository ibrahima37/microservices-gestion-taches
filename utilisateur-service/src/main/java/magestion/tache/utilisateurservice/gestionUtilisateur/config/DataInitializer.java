package magestion.tache.utilisateurservice.gestionUtilisateur.config;

import lombok.RequiredArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Personne;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Profil;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.PersonneRepository;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.ProfilRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProfilRepository profilRepository;
    private final PersonneRepository personneRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        // Créer ou mettre à jour le profil SUPER_ADMIN
        Profil superAdminProfil = profilRepository.findByNomProfil("SUPER_ADMIN")
                .orElseGet(() -> {
                    Profil profil = Profil.builder()
                            .nomProfil("SUPER_ADMIN")
                            .description("Super administrateur du systeme")
                            .build();
                    return profilRepository.save(profil);
                });

        // Créer la personne Super Admin si elle n’existe pas
        String emailSuperAdmin = "superadmin@system.com";

        if (personneRepository.findByEmail("superadmin@system.com").isEmpty()) {
            Personne superAdmin = Personne.builder()
                    .nom("Super")
                    .prenoms("Admin")
                    .email(emailSuperAdmin)
                    .adresse("Dakar")
                    .telephone("773855062")
                    .motDePasse(passwordEncoder.encode("superadmin123"))
                    .statut(true)
                    .profils(Set.of(superAdminProfil))
                    .build();

            personneRepository.save(superAdmin);
        }
    }
}


