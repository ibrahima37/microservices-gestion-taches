package magestion.tache.tacheservice.gestionTache.ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import magestion.tache.tacheservice.gestionTache.dtos.CreerTacheDto;
import magestion.tache.tacheservice.gestionTache.dtos.ModifierStatutTacheDto;
import magestion.tache.tacheservice.gestionTache.dtos.TacheDto;
import magestion.tache.tacheservice.gestionTache.services.inter.TacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
public class TacheController {

    private final TacheService tacheService;

    @PostMapping
    public ResponseEntity<TacheDto> creerTache(@Valid @RequestBody CreerTacheDto dto) {

        TacheDto tache = tacheService.creerTache(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(tache);
    }

    @PatchMapping("/{id}/attribution/{personneId}")
    public ResponseEntity<TacheDto> attribuerTache(
            @PathVariable UUID id,
            @PathVariable UUID personneId) {

        return ResponseEntity.ok(
                tacheService.attribuerTache(id, personneId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TacheDto>> listerTaches() {
        return ResponseEntity.ok(tacheService.listerTaches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacheDto> trouverTacheParId(@PathVariable UUID id) {

        return ResponseEntity.ok(tacheService.trouverTacheParId(id));
    }

    @GetMapping("/personne/{personeId}")
    public ResponseEntity<List<TacheDto>> listerTachesParPersonne(@PathVariable UUID personneId) {

        return ResponseEntity.ok(tacheService.listerTachesParPersonne(personneId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacheDto> modifierTache(
            @PathVariable UUID id,
            @RequestBody CreerTacheDto dto) {

        return ResponseEntity.ok(tacheService.modifierTache(id, dto));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<TacheDto> modifierStatut(
            @PathVariable UUID id,
            @Valid @RequestBody ModifierStatutTacheDto dto) {

        return ResponseEntity.ok(tacheService.modifierStatut(id, dto.getStatut()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTache(
            @PathVariable UUID id) {

        tacheService.supprimerTache(id);

        return ResponseEntity.noContent().build();
    }
}