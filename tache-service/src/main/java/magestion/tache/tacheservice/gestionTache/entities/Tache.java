package magestion.tache.tacheservice.gestionTache.entities;

import jakarta.persistence.*;
import lombok.*;
import magestion.tache.tacheservice.gestionTache.enums.Priorite;
import magestion.tache.tacheservice.gestionTache.enums.Statut;
import magestion.tache.tacheservice.gestionTache.enums.TypeTache;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @Column(nullable = false)
    private UUID creeParID;

    private UUID assignedId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTache typeTache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    @ToString.Exclude
    private Categorie categorie;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;

    @PrePersist
    protected void avantCreation() {
        LocalDateTime maintenant = LocalDateTime.now();

        if (dateCreation == null) {
            dateCreation = maintenant;
        }

        if (dateModification == null) {
            dateModification = maintenant;
        }

        if (statut == null) {
            statut = Statut.EN_ATTENTE;
        }

        if (typeTache == null) {
            typeTache = TypeTache.ATTRIBUEE;
        }
    }

    @PreUpdate
    protected void avantModification() {
        dateModification = LocalDateTime.now();
    }
}
