package magestion.tache.tacheservice.gestionTache.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import magestion.tache.tacheservice.gestionTache.enums.Statut;

@Getter
@Setter
public class ModifierStatutTacheDto {

    @NotNull(message = "Le statut est obligatoire")
    private Statut statut;
}
