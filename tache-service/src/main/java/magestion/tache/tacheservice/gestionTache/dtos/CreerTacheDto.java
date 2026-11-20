package magestion.tache.tacheservice.gestionTache.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import magestion.tache.tacheservice.gestionTache.enums.Priorite;
import magestion.tache.tacheservice.gestionTache.enums.Statut;
import magestion.tache.tacheservice.gestionTache.enums.TypeTache;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreerTacheDto {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 200, message = "Le titre ne doit pas dépasser 200 caractères")
    private String titre;

    private String description;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @NotNull(message = "La priorité est obligatoire")
    private Priorite priorite;

    private Statut statut;

    private UUID assignedId;

    private UUID categorieId;

    private TypeTache typeTache;
}

