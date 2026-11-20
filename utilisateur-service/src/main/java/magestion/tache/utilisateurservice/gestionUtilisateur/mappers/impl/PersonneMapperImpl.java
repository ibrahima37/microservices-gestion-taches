package magestion.tache.utilisateurservice.gestionUtilisateur.mappers.impl;

import lombok.AllArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.CreerPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.creation.ModifierPersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.PersonneDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.dtos.response.ProfilDto;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Personne;
import magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints.PersonneMapper;
import magestion.tache.utilisateurservice.gestionUtilisateur.mappers.ints.ProfilMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PersonneMapperImpl implements PersonneMapper {

    private final ProfilMapper profilMapper;

    @Override
    public Personne toEntity(CreerPersonneDto dto) {
        if (dto == null) {
            return null;
        }

        return Personne.builder()
                .nom(dto.getNom())
                .prenoms(dto.getPrenoms())
                .email(dto.getEmail())
                .motDePasse(dto.getMotDePasse())
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .statut(true)
                .profils(new HashSet<>())
                .build();
    }

    @Override
    public PersonneDto toDto(Personne personne) {
        if (personne == null) {
            return null;
        }

        Set<ProfilDto> profilsDto = personne.getProfils() == null
                ? Collections.emptySet()
                : personne.getProfils()
                .stream()
                .map(profilMapper::toDto)
                .collect(Collectors.toSet());

        return PersonneDto.builder()
                .id(personne.getId())
                .nom(personne.getNom())
                .prenoms(personne.getPrenoms())
                .email(personne.getEmail())
                .telephone(personne.getTelephone())
                .adresse(personne.getAdresse())
                .statut(personne.getStatut())
                .profils(profilsDto)
                .dateCreation(personne.getDateCreation())
                .dateModification(personne.getDateModification())
                .creePar(personne.getCreePar())
                .modifierPar(personne.getModifierPar())
                .build();
    }

    @Override
    public void updateEntity(
            ModifierPersonneDto dto,
            Personne personne
    ) {
        if (dto == null || personne == null) {
            return;
        }

        // Mise à jour partielle : ne pas écraser les valeurs par null.
        if (dto.getNom() != null) {
            personne.setNom(dto.getNom());
        }

        if (dto.getPrenoms() != null) {
            personne.setPrenoms(dto.getPrenoms());
        }

        if (dto.getEmail() != null) {
            personne.setEmail(dto.getEmail());
        }

        if (dto.getTelephone() != null) {
            personne.setTelephone(dto.getTelephone());
        }

        if (dto.getAdresse() != null) {
            personne.setAdresse(dto.getAdresse());
        }

    }
}
