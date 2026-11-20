package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response;

import lombok.*;

import java.util.UUID;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ProfilDto {

    private UUID id;

    private String nomProfil;

    private String description;
}
