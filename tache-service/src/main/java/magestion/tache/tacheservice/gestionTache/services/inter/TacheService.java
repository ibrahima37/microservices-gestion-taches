package magestion.tache.tacheservice.gestionTache.services.inter;

import magestion.tache.tacheservice.gestionTache.dtos.CreerTacheDto;
import magestion.tache.tacheservice.gestionTache.dtos.TacheDto;
import magestion.tache.tacheservice.gestionTache.entities.Tache;
import magestion.tache.tacheservice.gestionTache.enums.Statut;

import java.util.List;
import java.util.UUID;

public interface TacheService {

    TacheDto creerTache(CreerTacheDto dto);

    TacheDto attribuerTache(UUID id, UUID personneId);

    List<TacheDto> listerTaches();

    TacheDto trouverTacheParId(UUID id);

    List<TacheDto> listerTachesParPersonne(UUID personneId);

    TacheDto modifierTache(UUID id, CreerTacheDto dto);

    TacheDto modifierStatut(UUID id, Statut statut);

    void supprimerTache(UUID id);
}
