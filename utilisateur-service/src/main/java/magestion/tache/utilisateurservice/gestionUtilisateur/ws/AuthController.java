package magestion.tache.utilisateurservice.gestionUtilisateur.ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.ChangerMotDePasseDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.LoginRequest;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.ReinitialisationMotDePasseDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.AuthResponse;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.PersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.services.ints.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> inscription(
            @Valid @RequestBody CreerPersonneDto dto) {

        return ResponseEntity.ok(authService.inscription(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> connexion(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.connexion(request));
    }

    @GetMapping("personnes")
    public ResponseEntity<List<PersonneDto>> listerPersonne(){
        return ResponseEntity.ok(authService.listePersonne());
    }

    @GetMapping("/personnes/{id}")
    public ResponseEntity<PersonneDto> trouverPersonneParId(@PathVariable UUID id) {
        return ResponseEntity.ok(authService.trouverPersonneParId(id));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> motDePasseOublie(
            @RequestParam String email) {

        authService.motDePasseOublie(email);

        return ResponseEntity.ok(
                "Un email de réinitialisation a été envoyé."
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> reinitialiserMotDePasse(
            @Valid @RequestBody ReinitialisationMotDePasseDto dto) {

        authService.reinitialiserMotDePasse(dto);

        return ResponseEntity.ok(
                "Mot de passe réinitialisé avec succès."
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> deconnexion() {

        authService.deconnexion();

        return ResponseEntity.ok(
                "Déconnexion effectuée."
        );
    }

    @PutMapping("/change-password/{personneId}")
    public ResponseEntity<String> changerMotDePasse(
            @Valid @RequestBody ChangerMotDePasseDto dto) {

        authService.changerMotDePasse(dto);

        return ResponseEntity.ok(
                "Mot de passe modifié avec succès."
        );
    }
}
