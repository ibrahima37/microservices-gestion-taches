package magestion.tache.utilisateurservice.gestionUtilisateur.securites;

import lombok.RequiredArgsConstructor;
import magestion.tache.utilisateurservice.gestionUtilisateur.entities.Personne;
import magestion.tache.utilisateurservice.gestionUtilisateur.repository.PersonneRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final PersonneRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Personne personne;
        try {
            UUID id = UUID.fromString(username);
            personne = repository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        } catch (IllegalArgumentException e){
            personne = repository.findByEmailWithProfils(username)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(
                                    "Utilisateur introuvable")
                    );
        }

        List<GrantedAuthority> authorities =
                personne.getProfils()
                        .stream()
                        .map(profil ->
                                        new SimpleGrantedAuthority("ROLE_" + profil.getNomProfil())
                        )
                        .collect(Collectors.toList());

        return new User(
                personne.getId().toString(),
                personne.getMotDePasse(),
                personne.getStatut(),
                true,
                true,
                true,
                authorities
        );
    }
}