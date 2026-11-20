package magestion.tache.utilisateurservice.gestionUtilisateur.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import magestion.tache.utilisateurservice.gestionUtilisateur.models.BaseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"profils", "motDePasse"})
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "personnes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_personnes_email",
                        columnNames = "email"
                )
        })
public class Personne extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 150)
    private String prenoms;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @JsonIgnore
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(length = 30)
    private String telephone;

    @Column(length = 255)
    private String adresse;

    //@Builder.Default
    @Column(nullable = false)
    private Boolean statut;

    //@Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "personnes_profils",
            joinColumns = @JoinColumn(name = "personne_id"),
            inverseJoinColumns = @JoinColumn(name = "profil_id")
    )
    private Set<Profil> profils = new HashSet<>();

}
