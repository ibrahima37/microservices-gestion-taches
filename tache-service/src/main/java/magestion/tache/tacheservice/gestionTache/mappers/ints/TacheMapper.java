package magestion.tache.tacheservice.gestionTache.mappers.ints;

import magestion.tache.tacheservice.gestionTache.dtos.CreerTacheDto;
import magestion.tache.tacheservice.gestionTache.dtos.TacheDto;
import magestion.tache.tacheservice.gestionTache.entities.Tache;

public interface TacheMapper {

    Tache toEntity(CreerTacheDto dto);

    TacheDto toDto(Tache tache);
}
