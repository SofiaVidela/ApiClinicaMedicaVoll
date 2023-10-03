package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author sofia
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//se le indica a spring el tipo de sesion
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()  
                //RELES DIFERENTES
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
/**
 * ROLES DIFERENTES en la app aca aclararemos a que url tendran acceso
                 .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
                 .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
 * SINO SE PUEDE ANOTAR DIRECTAMENTE EN LOS METODOS
                   @GetMapping("/{id}")
                   @Secured("ROLE_ADMIN")
                    public ResponseEntity detallar(@PathVariable Long id) {
                        var medico = repository.getReferenceById(id);
                         return ResponseEntity.ok(new DatosDetalladoMedico(medico));
                            }
 *SIEMPRE Y CUANDO EN LA CLASE  Securityconfigurations 
 * @EnableMethodSecurity(securedEnabled = true)
                 
 */
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
