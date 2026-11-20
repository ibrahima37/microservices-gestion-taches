package magestion.tache.tacheservice.gestionTache.dtos;

import lombok.*;

import java.util.UUID;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class CategorieDto {

    private UUID id;
    private String nomCategorie;
}
