package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author sofia
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    //con java puro podriamos solo usar extend filter de la libreria javax

    @Autowired//siempre es mejor con un constructor pero aca esta atravez del campo(field)
    private TokenService tokenservice;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenservice.getSubject(token);//aca extraemos el usuario
            if (nombreUsuario != null) {
                //entonces token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,
                        null, usuario.getAuthorities());//Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);//setiamos manualmente esa autenticacion
            }
        }
        filterChain.doFilter(request, response);
    }

}
