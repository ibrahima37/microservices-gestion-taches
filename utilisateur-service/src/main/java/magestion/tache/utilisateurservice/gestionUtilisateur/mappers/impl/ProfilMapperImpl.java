package magestion.tache.utilisateurservice.gestionUtilisateur.mappers.impl;

import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.ProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Profil;
import magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints.ProfilMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfilMapperImpl implements ProfilMapper {

    @Override
    public Profil toEntity(CreerProfilDto dto) {
        if (dto == null){
            return null;
        }
        return Profil.builder()
                .nomProfil(dto.getNomProfil())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public ProfilDto toDto(Profil profil) {
        if (profil == null){
            return null;
        }

        return ProfilDto.builder()
                .id(profil.getId())
                .nomProfil(profil.getNomProfil())
                .description(profil.getDescription())
                .build();
    }
}
