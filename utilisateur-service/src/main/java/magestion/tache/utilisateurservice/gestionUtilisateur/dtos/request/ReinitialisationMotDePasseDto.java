package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReinitialisationMotDePasseDto {

    private String token;

    private String nouveauMotDePasse;

    private String confirmationMotDePasse;

}
