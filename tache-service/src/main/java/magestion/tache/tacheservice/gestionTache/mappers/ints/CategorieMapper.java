package magestion.tache.tacheservice.gestionTache.mappers.ints;

import magestion.tache.tacheservice.gestionTache.dtos.CategorieDto;
import magestion.tache.tacheservice.gestionTache.entities.Categorie;

public interface CategorieMapper {

    Categorie toEntity(CategorieDto dto);
    CategorieDto toDto(Categorie categorie);
}
