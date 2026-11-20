package magestion.tache.tacheservice.gestionTache.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import magestion.tache.tacheservice.gestionTache.dtos.CreerTacheDto;
import magestion.tache.tacheservice.gestionTache.dtos.PersonneDto;
import magestion.tache.tacheservice.gestionTache.dtos.TacheDto;
import magestion.tache.tacheservice.gestionTache.entities.Categorie;
import magestion.tache.tacheservice.gestionTache.entities.Tache;
import magestion.tache.tacheservice.gestionTache.enums.Statut;
import magestion.tache.tacheservice.gestionTache.mappers.ints.TacheMapper;
import magestion.tache.tacheservice.gestionTache.models.UtilisateurClient;
import magestion.tache.tacheservice.gestionTache.repository.CategorieRepository;
import magestion.tache.tacheservice.gestionTache.repository.TacheRepository;
import magestion.tache.tacheservice.gestionTache.services.inter.TacheService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepository;
    private final CategorieRepository categorieRepository;
    private final TacheMapper tacheMapper;
    private final UtilisateurClient utilisateurClient;

    @Override
    public TacheDto creerTache(CreerTacheDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données de la tâche sont obligatoires."
            );
        }

        UUID utilisateurId = obtenirUtilisateurConnecteId();

        Tache tache = tacheMapper.toEntity(dto);

        // L'identité du créateur vient de l'authentification
        tache.setCreeParID(utilisateurId);

        if(dto.getStatut() != null){
            tache.setStatut(dto.getStatut());
        } else {
            tache.setStatut(Statut.EN_ATTENTE);
        }

        if (dto.getCategorieId() != null) {
            tache.setCategorie(trouverCategorie(dto.getCategorieId()));
        }

        return tacheMapper.toDto(tacheRepository.save(tache));
    }

    @Override
    @Transactional
    public TacheDto attribuerTache(UUID id, UUID personneId) {

        if (id == null || personneId == null){
            throw new IllegalArgumentException(
                    "L'identifiant de la tâche et celui de l'employé sont obligatoires."
            );
        }
        Tache tache = trouverTache(id);

        PersonneDto personneDto = utilisateurClient.trouverPersonneParId(personneId);

        if (personneDto == null || !Boolean.TRUE.equals(personneDto.statut())) {
            throw new EntityNotFoundException(
                    "Personne introuvable ou inactif : " + personneId
            );
        }

        tache.setAssignedId(personneId);

        return tacheMapper.toDto(tacheRepository.save(tache));

    }

    // READ : toutes les tâches
    @Override
    @Transactional(readOnly = true)
    public List<TacheDto> listerTaches() {

        return tacheRepository.findAll()
                .stream()
                .map(tacheMapper::toDto)
                .toList();
    }

    // READ : une tâche
    @Override
    @Transactional(readOnly = true)
    public TacheDto trouverTacheParId(UUID id) {

        return tacheMapper.toDto(trouverTache(id));
    }

    // READ : tâches d'un employé
    @Override
    @Transactional(readOnly = true)
    public List<TacheDto> listerTachesParPersonne(UUID personneId) {

        if (personneId == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de l'employé est obligatoire."
            );
        }

        UUID utilisateurConnecteId = obtenirUtilisateurConnecteId();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // 2. Vérifier les droits
        boolean administrateur = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN")
                                || a.getAuthority().equals("ROLE_SUPER_ADMIN")
                                || a.getAuthority().equals("ADMIN")
                                || a.getAuthority().equals("SUPER_ADMIN")
                );

        if (!utilisateurConnecteId.equals(personneId) && !administrateur) {
            throw new EntityNotFoundException(
                    "Vous ne pouvez consulter que vos propres tâches."
            );
        }

        // 3. Vérifier que l'employé existe dans utilisateur-service
        PersonneDto employe = utilisateurClient
                .trouverPersonneParId(personneId);

        if (employe == null || !Boolean.TRUE.equals(employe.statut())) {
            throw new EntityNotFoundException(
                    "Employé introuvable ou inactif : " + personneId
            );
        }

        return tacheRepository.findByAssignedId(personneId)
                .stream()
                .map(tacheMapper::toDto)
                .toList();
    }

    // UPDATE : modifier une tâche
    @Override
    public TacheDto modifierTache(UUID id, CreerTacheDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données de la tâche sont obligatoires."
            );
        }

        Tache tache = trouverTache(id);

        tache.setTitre(dto.getTitre());
        tache.setDescription(dto.getDescription());
        tache.setDateDebut(dto.getDateDebut());
        tache.setDateFin(dto.getDateFin());
        tache.setPriorite(dto.getPriorite());

        if (dto.getCategorieId() != null) {
            tache.setCategorie(trouverCategorie(dto.getCategorieId()));
        }

        return tacheMapper.toDto(tacheRepository.save(tache));
    }

    // UPDATE : modifier le statut
    @Override
    public TacheDto modifierStatut(UUID id, Statut nouveauStatut) {

        if (nouveauStatut == null) {
            throw new IllegalArgumentException(
                    "Le nouveau statut est obligatoire."
            );
        }

        Tache tache = trouverTache(id);
        UUID utilisateurId = obtenirUtilisateurConnecteId();

        if (!utilisateurId.equals(tache.getAssignedId())) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Vous ne pouvez modifier que le statut de vos tâches."
            );
        }

        tache.setStatut(nouveauStatut);

        return tacheMapper.toDto(tacheRepository.save(tache));
    }

    // DELETE
    @Override
    public void supprimerTache(UUID id) {

        Tache tache = trouverTache(id);

        tacheRepository.delete(tache);
    }

    // Méthodes privées
    private Tache trouverTache(UUID id) {

        return tacheRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tâche introuvable : " + id
                ));
    }

    private Categorie trouverCategorie(UUID categorieId) {

        return categorieRepository.findById(categorieId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Catégorie introuvable : " + categorieId
                ));
    }

    private UUID obtenirUtilisateurConnecteId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName() == null
                || authentication.getName().equals("anonymousUser")) {

            throw new org.springframework.security.access.AccessDeniedException(
                    "Vous devez être connecté."
            );
        }

        try {
            return UUID.fromString(authentication.getName());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "L'identité authentifiée ne contient pas un UUID valide.", e
            );
        }
    }
}
