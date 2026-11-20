package magestion.tache.tacheservice.gestionTache.services.inter;

import magestion.tache.tacheservice.gestionTache.dtos.CategorieDto;
import magestion.tache.tacheservice.gestionTache.entities.Categorie;

import java.util.List;
import java.util.UUID;

public interface CategorieService {

    Categorie creerCategorie(CategorieDto dto);
    List<CategorieDto> listerCategories();
    CategorieDto trouverCategorieParId(UUID id);
    Categorie modifierCategorie(UUID id, CategorieDto nouvelleCategorie);
    void supprimerCategorie(UUID id);
}
