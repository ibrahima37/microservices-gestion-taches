package magestion.tache.tacheservice.gestionTache.models;

import magestion.tache.tacheservice.gestionTache.config.FeignConfig;
import magestion.tache.tacheservice.gestionTache.dtos.PersonneDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "utilisateur-service",
        path = "/api/auth/personnes",
        configuration = FeignConfig.class
)
public interface UtilisateurClient {

    @GetMapping("/{id}")
    PersonneDto trouverPersonneParId(@PathVariable("id") UUID id);
}
