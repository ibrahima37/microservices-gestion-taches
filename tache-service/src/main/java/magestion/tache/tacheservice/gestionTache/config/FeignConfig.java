package magestion.tache.tacheservice.gestionTache.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    // On recopie le token du front vers le service appelé
                    requestTemplate.header("Authorization", authHeader);
                    System.out.println("FEIGN FORWARD TOKEN = " + authHeader.substring(0, 20) + "...");
                } else {
                    System.out.println("FEIGN : PAS DE TOKEN À FORWARD");
                }
            }
        };
    }
}
