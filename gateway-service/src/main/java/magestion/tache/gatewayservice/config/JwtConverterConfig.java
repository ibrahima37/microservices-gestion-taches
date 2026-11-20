//package magestion.tache.gatewayservice.config;
//
//import ch.qos.logback.core.pattern.Converter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//
//@Configuration
//public class JwtConverterConfig {
//
//    @Bean
//    public Converter<Jwt, Mono<AbstractAuthenticationToken>>
//    grantedAuthoritiesExtractor() {
//
//        JwtAuthenticationConverter converter =
//                new JwtAuthenticationConverter();
//
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//
//                    List<String> roles = jwt.getClaimAsStringList("roles");
//
//                    if (roles == null){
//                        return List.of();
//                    }
//
//                    return roles.stream()
//                            .map(role -> new SimpleGrantedAuthority(
//                                                    role.startsWith("ROLE_")
//                                    ? role
//                                                            : "ROLE_" + role
//                                            )
//                            );
//                            .toList();
//                }
//        );
//
//        return new ReactiveJwtAuthenticationConverterAdapter(converter);
//    }
//}
