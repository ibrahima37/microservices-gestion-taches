package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@Builder
public class AuthResponse {

    private String token;

    private UUID personneId;

    private String nom;

    private String prenoms;

    private String email;

    private List<ProfilDto> profils;
}
