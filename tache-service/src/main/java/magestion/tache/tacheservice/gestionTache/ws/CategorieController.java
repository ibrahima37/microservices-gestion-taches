package magestion.tache.tacheservice.gestionTache.ws;

import lombok.AllArgsConstructor;
import magestion.tache.tacheservice.gestionTache.dtos.CategorieDto;
import magestion.tache.tacheservice.gestionTache.entities.Categorie;
import magestion.tache.tacheservice.gestionTache.services.inter.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieService categorieService;

    @PostMapping
    public ResponseEntity<Categorie> creerCategorie(
            @RequestBody CategorieDto dto) {

        Categorie categorie = categorieService.creerCategorie(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(categorie);
    }

    @GetMapping
    public ResponseEntity<List<CategorieDto>> listerCategories() {
        return ResponseEntity.ok(categorieService.listerCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDto> trouverCategorieParId(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                categorieService.trouverCategorieParId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> modifierCategorie(
            @PathVariable UUID id,
            @RequestBody CategorieDto dto) {

        return ResponseEntity.ok(
                categorieService.modifierCategorie(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCategorie(
            @PathVariable UUID id) {

        categorieService.supprimerCategorie(id);

        return ResponseEntity.noContent().build();
    }
}
