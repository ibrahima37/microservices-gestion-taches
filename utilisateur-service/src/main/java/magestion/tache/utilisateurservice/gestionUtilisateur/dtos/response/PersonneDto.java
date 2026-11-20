package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response;

import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class PersonneDto {

    private UUID id;

    private String nom;

    private String prenoms;

    private String email;

    private String telephone;

    private String adresse;

    private Boolean statut;

    private Set<ProfilDto> profils;

    private Date dateCreation;

    private Date dateModification;

    private String creePar;

    private String modifierPar;
}
