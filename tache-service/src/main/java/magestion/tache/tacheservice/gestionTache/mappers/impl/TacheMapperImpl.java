package magestion.tache.tacheservice.gestionTache.mappers.impl;

import magestion.tache.tacheservice.gestionTache.dtos.CreerTacheDto;
import magestion.tache.tacheservice.gestionTache.dtos.TacheDto;
import magestion.tache.tacheservice.gestionTache.entities.Tache;
import magestion.tache.tacheservice.gestionTache.mappers.ints.TacheMapper;
import org.springframework.stereotype.Component;

@Component
public class TacheMapperImpl implements TacheMapper {

    @Override
    public Tache toEntity(CreerTacheDto dto) {
        if (dto == null){
            return null;
        }

        return Tache.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .priorite(dto.getPriorite())
                .statut(dto.getStatut())
                .assignedId(dto.getAssignedId())
                .typeTache(dto.getTypeTache())
                .build();
    }

    @Override
    public TacheDto toDto(Tache tache) {
        if (tache == null){
            return null;
        }

        return TacheDto.builder()
                .id(tache.getId())
                .titre(tache.getTitre())
                .description(tache.getDescription())
                .dateDebut(tache.getDateDebut())
                .dateFin(tache.getDateFin())
                .creeParID(tache.getCreeParID())
                .assignedId(tache.getAssignedId())
                .statut(tache.getStatut())
                .priorite(tache.getPriorite())
                .typeTache(tache.getTypeTache())
                .categorieId(
                        tache.getCategorie() != null
                ? tache.getCategorie().getId()
                        : null)
                .dateCreation(tache.getDateCreation())
                .dateModification(tache.getDateModification())
                .build();
    }
}
