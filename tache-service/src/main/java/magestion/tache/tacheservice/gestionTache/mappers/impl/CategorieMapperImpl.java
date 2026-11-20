package magestion.tache.tacheservice.gestionTache.mappers.impl;

import magestion.tache.tacheservice.gestionTache.dtos.CategorieDto;
import magestion.tache.tacheservice.gestionTache.entities.Categorie;
import magestion.tache.tacheservice.gestionTache.mappers.ints.CategorieMapper;
import org.springframework.stereotype.Component;

@Component
public class CategorieMapperImpl implements CategorieMapper {

    @Override
    public Categorie toEntity(CategorieDto dto) {
        if (dto == null){
            return null;
        }
        return Categorie.builder()
                .nomCategorie(dto.getNomCategorie())
                .build();
    }

    @Override
    public CategorieDto toDto(Categorie categorie) {
        if (categorie == null){
            return null;
        }
        return CategorieDto.builder()
                .id(categorie.getId())
                .nomCategorie(categorie.getNomCategorie())
                .build();
    }
}
