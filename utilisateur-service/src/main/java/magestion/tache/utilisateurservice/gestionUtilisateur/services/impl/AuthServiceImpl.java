package magestion.tache.utilisateurservice.gestionUtilisateur.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.config.JwtService;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.ChangerMotDePasseDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.LoginRequest;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.ReinitialisationMotDePasseDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.AuthResponse;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.PersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.PasswordResetToken;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Personne;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Profil;
import magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints.PersonneMapper;
import magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints.ProfilMapper;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.PasswordResetTokenRepository;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.PersonneRepository;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.ProfilRepository;
import magestion.tache.utilisateurservice.gestionUtilisateur.securites.CustomUserDetailsService;
import magestion.tache.utilisateurservice.gestionUtilisateur.services.ints.AuthService;
import magestion.tache.utilisateurservice.gestionUtilisateur.services.ints.MailService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final PersonneRepository personneRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final MailService mailService;
    private final ProfilMapper profilMapper;
    private final PersonneMapper personneMapper;

    @Override
    public AuthResponse inscription(CreerPersonneDto dto) {

        if (personneRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                    "Cette adresse email est déjà utilisée."
            );
        }

        Set<Profil> profils = new HashSet<>();

        Personne personne = Personne.builder()
                .nom(dto.getNom())
                .prenoms(dto.getPrenoms())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .motDePasse(
                        passwordEncoder.encode(
                                dto.getMotDePasse()
                        )
                )
                .adresse(dto.getAdresse())
                .statut(true)
                .profils(profils)
                .build();

        personne = personneRepository.save(personne);

        String token = jwtService.generateToken(personne);

        return AuthResponse.builder()
                .token(token)
                .personneId(personne.getId())
                .nom(personne.getNom())
                .prenoms(personne.getPrenoms())
                .email(personne.getEmail())
                .profils(
                        personne.getProfils()
                                .stream()
                                .map(profilMapper::toDto)
                                .toList()
                )
                .build();
    }

    @Override
    public AuthResponse connexion(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
        );

        Personne personne = personneRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable."));

        if (!personne.getStatut()) {
            throw new IllegalStateException("Votre compte est désactivé.");
        }

        String token = jwtService.generateToken(personne);

        return AuthResponse.builder()
                .token(token)
                .personneId(personne.getId())
                .nom(personne.getNom())
                .prenoms(personne.getPrenoms())
                .email(personne.getEmail())
                .profils(personne.getProfils()
                        .stream()
                        .map(profilMapper::toDto)
                        .toList())
                .build();

    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonneDto> listePersonne() {
        return personneRepository.findAll()
                .stream()
                .map(personneMapper::toDto)
                .toList();
    }

    @Override
    public void changerMotDePasse(
            ChangerMotDePasseDto dto) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        Personne personne =
                personneRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Utilisateur introuvable"
                                ));

        if (!passwordEncoder.matches(
                dto.getAncienMotDePasse(),
                personne.getMotDePasse()
        )) {

            throw new IllegalArgumentException(
                    "Ancien mot de passe incorrect"
            );
        }

        if (!dto.getNouveauMotDePasse()
                .equals(dto.getConfirmationMotDePasse())) {

            throw new IllegalArgumentException(
                    "Confirmation du mot de passe incorrecte"
            );
        }

        personne.setMotDePasse(
                passwordEncoder.encode(
                        dto.getNouveauMotDePasse()
                )
        );

        personneRepository.save(personne);

    }

    @Override
    public void motDePasseOublie(String email) {

        Personne personne = personneRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Utilisateur introuvable."
                        ));

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .dateExpiration(
                        LocalDateTime.now().plusMinutes(30))
                .personne(personne)
                .build();

        passwordResetTokenRepository.save(resetToken);

        String lien = "http://localhost:8080/api/auth/reset-password?token=" + token;

        mailService.envoyerEmail(

                personne.getEmail(),

                "Réinitialisation de votre mot de passe",

                """
                <html>
                    <body>

                        <p>Bonjour,</p>

                        <p>Vous avez demandé la réinitialisation de votre mot de passe.</p>

                        <p>Cliquez sur le bouton :</p>

                        <a href="%s">Réinitialiser mon mot de passe </a>

                        <p> Ce lien expire dans 30 minutes. </p>

                        <p> Cordialement,<br> BARHAMOU-BUSINESS
                        </p>

                    </body>
                </html>
                """.formatted(lien)
        );
    }

    @Override
    public void reinitialiserMotDePasse(ReinitialisationMotDePasseDto dto) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository
                        .findByToken(dto.getToken())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Le lien de réinitialisation est invalide."
                                ));

        if (resetToken.getDateExpiration()
                .isBefore(LocalDateTime.now())) {

            passwordResetTokenRepository.delete(resetToken);

            throw new IllegalArgumentException(
                    "Le lien de réinitialisation a expiré."
            );
        }

        if (!dto.getNouveauMotDePasse()
                .equals(dto.getConfirmationMotDePasse())) {

            throw new IllegalArgumentException(
                    "La confirmation du mot de passe est incorrecte."
            );
        }

        Personne personne = resetToken.getPersonne();

        if (personne == null) {
            throw new IllegalStateException(
                    "Aucun utilisateur associé à ce token."
            );
        }

        personne.setMotDePasse(
                passwordEncoder.encode(
                        dto.getNouveauMotDePasse()
                )
        );
        personneRepository.save(personne);

        passwordResetTokenRepository.delete(resetToken);
    }

    @Override
    public void deconnexion() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return;
        }

        SecurityContextHolder.clearContext();
    }

    @Override
    public PersonneDto trouverPersonneParId(UUID id) {

        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personne introuvable : " + id));
        return personneMapper.toDto(personne);
    }
}
