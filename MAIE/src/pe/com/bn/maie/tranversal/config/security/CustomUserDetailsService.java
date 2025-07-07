package pe.com.bn.maie.tranversal.config.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pe.com.bn.maie.persistencia.dto.Permiso;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

    // Suponiendo que tienes un servicio de usuario propio, reemplaza por el tuyo
    //@Autowired
    //private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Intentando autenticar usuario: " + username);

        // Aquí deberías buscar el usuario en tu base de datos o servicio externo
        // Usuario usuario = usuarioService.buscarPorUsuario(username);
        // if (usuario == null) {
        //     throw new UsernameNotFoundException("Usuario no encontrado");
        // }

        // Ejemplo genérico: usuario y clave quemados
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        String password = "admin123"; // En un caso real, obtén el hash de la base de datos

        // Asignar roles/permisos
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // Puedes poblar los datos de UsuarioSeguridad según tu modelo
        UsuarioSeguridad customUser = new UsuarioSeguridad(
            username,
            password,
            authorities,
            1L, // id
            "ApellidoPaterno",
            "ApellidoMaterno",
            "Nombres",
            "DNI",
            "12345678",
            "RUC",
            new ArrayList<Permiso>(),
            "ADMIN",
            "N"
        );
        return customUser;
    }
}
