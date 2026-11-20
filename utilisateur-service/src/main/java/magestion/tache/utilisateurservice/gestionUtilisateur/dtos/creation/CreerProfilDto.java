package magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CreerProfilDto {

    @NotBlank(message = "Le nom du profil est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nomProfil;

    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;
}
