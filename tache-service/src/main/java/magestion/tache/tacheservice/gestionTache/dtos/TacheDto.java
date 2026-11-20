package magestion.tache.tacheservice.gestionTache.dtos;

import lombok.*;
import magestion.tache.tacheservice.gestionTache.enums.Priorite;
import magestion.tache.tacheservice.gestionTache.enums.Statut;
import magestion.tache.tacheservice.gestionTache.enums.TypeTache;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class TacheDto {

    private UUID id;

    private String titre;

    private String description;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    private UUID creeParID;

    private UUID assignedId;

    private Priorite priorite;

    private Statut statut;

    private TypeTache typeTache;

    private UUID categorieId;

    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;
}
