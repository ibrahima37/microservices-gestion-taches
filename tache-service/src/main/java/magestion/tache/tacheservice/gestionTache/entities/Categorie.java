package magestion.tache.tacheservice.gestionTache.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nomCategorie;

    @OneToMany(mappedBy = "categorie")
    @ToString.Exclude
    private List<Tache> tacheList;
}
