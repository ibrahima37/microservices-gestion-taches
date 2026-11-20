package magestion.tache.utilisateurservice.gestionUtilisateur.services.ints;

import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.ChangerMotDePasseDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.LoginRequest;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request.ReinitialisationMotDePasseDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.AuthResponse;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.PersonneDto;

import java.util.List;
import java.util.UUID;

public interface AuthService {

    AuthResponse inscription(CreerPersonneDto dto);

    AuthResponse connexion(LoginRequest dto);

    List<PersonneDto> listePersonne();

    void changerMotDePasse(ChangerMotDePasseDto dto);

    void motDePasseOublie(String email);

    void reinitialiserMotDePasse(ReinitialisationMotDePasseDto dto);

    void deconnexion();

    PersonneDto trouverPersonneParId(UUID id);
}
