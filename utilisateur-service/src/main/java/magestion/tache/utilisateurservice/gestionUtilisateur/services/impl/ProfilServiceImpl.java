package magestion.tache.utilisateurservice.gestionUtilisateur.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.ProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Profil;
import magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints.ProfilMapper;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.ProfilRepository;
import magestion.tache.utilisateurservice.gestionUtilisateur.services.ints.ProfilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfilServiceImpl implements ProfilService {

    private final ProfilRepository profilRepository;
    private final ProfilMapper profilMapper;

    @Override
    public ProfilDto creerProfil(CreerProfilDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données du profil sont obligatoires."
            );
        }

        if (profilRepository.existsByNomProfil(dto.getNomProfil())) {
            throw new IllegalArgumentException(
                    "Un profil portant ce nom existe déjà : "
                            + dto.getNomProfil()
            );
        }

        Profil profil = profilMapper.toEntity(dto);

        Profil profilEnregistre = profilRepository.save(profil);

        return profilMapper.toDto(profilEnregistre);
    }

    @Override
    public ProfilDto modifierProfil(UUID id, CreerProfilDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données du profil sont obligatoires."
            );
        }

        Profil profil = profilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profil introuvable avec l'identifiant : " + id
                ));

        if (profilRepository.existsByNomProfilAndIdNot(
                dto.getNomProfil(), id)) {
            throw new IllegalArgumentException(
                    "Un autre profil porte déjà ce nom : "
                            + dto.getNomProfil()
            );
        }

        profil.setNomProfil(dto.getNomProfil());
        profil.setDescription(dto.getDescription());

        Profil profilModifie = profilRepository.save(profil);

        return profilMapper.toDto(profilModifie);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfilDto trouverProfilParId(UUID id) {

        Profil profil = profilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profil introuvable avec l'identifiant : " + id
                ));

        return profilMapper.toDto(profil);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfilDto> listerProfils() {

        return profilRepository.findAll()
                .stream()
                .map(profilMapper::toDto)
                .toList();
    }

    @Override
    public void supprimerProfil(UUID id) {

        Profil profil = profilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profil introuvable avec l'identifiant : " + id
                ));

        profilRepository.delete(profil);
    }
}
