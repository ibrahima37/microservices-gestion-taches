package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ModifierStatutPersonneDto {

    @NotNull(message = "Le statut est obligatoire")
    private Boolean statut;
}
