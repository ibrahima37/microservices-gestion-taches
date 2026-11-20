package magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints;

import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.ModifierPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.PersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Personne;

public interface PersonneMapper {

    Personne toEntity(CreerPersonneDto dto);
    PersonneDto toDto(Personne personne);
    void updateEntity(ModifierPersonneDto dto, Personne personne);
}
