package magestion.tache.tacheservice.gestionTache.services.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import magestion.tache.tacheservice.gestionTache.dtos.CategorieDto;
import magestion.tache.tacheservice.gestionTache.entities.Categorie;
import magestion.tache.tacheservice.gestionTache.mappers.ints.CategorieMapper;
import magestion.tache.tacheservice.gestionTache.repository.CategorieRepository;
import magestion.tache.tacheservice.gestionTache.services.inter.CategorieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    @Override
    public Categorie creerCategorie(CategorieDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données de la catégorie sont obligatoires"
            );
        }

        Categorie categorie = categorieMapper.toEntity(dto);

        return categorieRepository.save(categorie) ;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieDto> listerCategories() {
        return categorieRepository.findAll()
                .stream()
                .map(categorieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategorieDto trouverCategorieParId(UUID id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Catégorie introuvable avec l'identifiant : " + id
                ));
        return categorieMapper.toDto(categorie);
    }

    @Override
    public Categorie modifierCategorie(
            UUID id,
            CategorieDto nouvelleCategorie) {

        if (nouvelleCategorie == null) {
            throw new IllegalArgumentException(
                    "Les nouvelles données sont obligatoires"
            );
        }

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Catégorie introuvable avec l'identifiant : " + id
                ));

        categorie.setNomCategorie(nouvelleCategorie.getNomCategorie());

        return categorieRepository.save(categorie);
    }

    @Override
    public void supprimerCategorie(UUID id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Catégorie introuvable avec l'identifiant : " + id
                ));

        categorieRepository.delete(categorie);
    }
}
