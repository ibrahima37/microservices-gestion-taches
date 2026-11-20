package magestion.tache.utilisateurservice.gestionUtilisateur.ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.ProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.services.ints.ProfilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profils")
@RequiredArgsConstructor
public class ProfilController {

    private final ProfilService profilService;

    @PostMapping
    public ResponseEntity<ProfilDto> creerProfil(
            @Valid @RequestBody CreerProfilDto dto) {

        ProfilDto profil = profilService.creerProfil(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profil);
    }

    @GetMapping
    public ResponseEntity<List<ProfilDto>> listerProfils() {

        return ResponseEntity.ok(
                profilService.listerProfils()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfilDto> trouverProfilParId(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                profilService.trouverProfilParId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfilDto> modifierProfil(
            @PathVariable UUID id,
            @Valid @RequestBody CreerProfilDto dto) {

        return ResponseEntity.ok(
                profilService.modifierProfil(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProfil(
            @PathVariable UUID id) {

        profilService.supprimerProfil(id);

        return ResponseEntity.noContent().build();
    }
}
