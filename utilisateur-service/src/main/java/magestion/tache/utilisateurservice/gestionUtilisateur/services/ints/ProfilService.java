package magestion.tache.utilisateurservice.gestionUtilisateur.services.ints;

import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.ProfilDto;

import java.util.List;
import java.util.UUID;

public interface ProfilService {

    ProfilDto creerProfil(CreerProfilDto dto);

    ProfilDto modifierProfil(UUID id, CreerProfilDto dto);

    ProfilDto trouverProfilParId(UUID id);

    List<ProfilDto> listerProfils();

    void supprimerProfil(UUID id);
}
