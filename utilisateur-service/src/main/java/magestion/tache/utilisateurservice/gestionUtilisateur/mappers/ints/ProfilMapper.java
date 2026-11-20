package magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints;

import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.ProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Profil;

public interface ProfilMapper {

    Profil toEntity(CreerProfilDto dto);
    ProfilDto toDto(Profil profil);
}
