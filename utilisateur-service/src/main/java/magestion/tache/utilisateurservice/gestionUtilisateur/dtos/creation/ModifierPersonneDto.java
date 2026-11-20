package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ModifierPersonneDto {

    @Size(max = 100)
    private String nom;

    @Size(max = 150)
    private String prenoms;

    @Email(message = "L'adresse email est invalide")
    @Size(max = 180)
    private String email;

    @Size(max = 30)
    private String telephone;

    @Size(max = 255)
    private String adresse;

    private Set<UUID> profilsIds;
}
