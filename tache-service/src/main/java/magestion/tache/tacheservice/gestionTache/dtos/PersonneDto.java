package magestion.tache.tacheservice.gestionTache.dtos;

import java.util.Date;
import java.util.UUID;

public record PersonneDto(
        UUID id,
        String nom,
        String prenoms,
        String email,
        String motDePasse,
        String telephone,
        String adresse,
        Date dateInscription,
        Boolean statut
) {

}
