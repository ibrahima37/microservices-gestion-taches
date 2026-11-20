package magestion.tache.tacheservice.gestionTache.entities;

import jakarta.persistence.*;
import lombok.*;
import magestion.tache.tacheservice.gestionTache.enums.StatutRapport;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Column(nullable = false)
    private UUID auteurId;

    private String nomFichier;

    private String cheminFichier;

    private LocalDateTime dateRapport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRapport statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tache_id")
    @ToString.Exclude
    private Tache tache;

    @PrePersist
    protected void avantCreation() {
        if (dateRapport == null) {
            dateRapport = LocalDateTime.now();
        }

        if (statut == null) {
            statut = StatutRapport.SOUMIS;
        }
    }
}
