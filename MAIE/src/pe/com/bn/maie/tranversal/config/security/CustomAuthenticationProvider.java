package pe.com.bn.maie.tranversal.config.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import pe.com.bn.maie.infraestructura.services.external.domain.wsautentica.AutenticaRegProxy;
import pe.com.bn.maie.persistencia.dto.Permiso;
import pe.com.bn.maie.tranversal.util.StringsUtils;
import pe.com.bn.maie.tranversal.util.constantes.ConstantesSeguridad;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LogManager.getLogger(CustomAuthenticationProvider.class);
    
    /**
     * Autentica el usuario contra el gateway de servicios web.
     * 
     * @param authentication Objeto de autenticación con las credenciales del usuario.
     * @return Un token de autenticación si las credenciales son válidas.
     * @throws AuthenticationException Si la autenticación falla.
     */
    @Override
    public Authentication authenticate(Authentication authentication) 
            throws AuthenticationException {
        
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        logger.info("authenticate username: " + username);
        logger.trace("authenticate password: " + password);
        
        // Validaciones básicas
        if (username == null || username.trim().isEmpty()) {
            throw new BadCredentialsException("Username cannot be empty");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new BadCredentialsException("Password cannot be empty");
        }
        
        String[] arrayDatos = null;
        String cadenaHost = null;
        
        try {
            AutenticaRegProxy proxy = new AutenticaRegProxy();
            
            cadenaHost = proxy.claveHost(StringsUtils.llenarEspaciosAlaDerecha(username, 4).toUpperCase() +
                                        StringsUtils.llenarEspaciosAlaDerecha(password, 8).toUpperCase() +
                                        "MGTT" +
                                        "00");
            
            // Validar que la respuesta no sea nula o vacía
            if (cadenaHost == null || cadenaHost.trim().isEmpty()) {
                throw new BadCredentialsException("Invalid response from authentication service");
            }
            
            arrayDatos = cadenaHost.split("\\|");
            
            // Validar que el array tenga al menos los elementos mínimos
            if (arrayDatos.length < 5) {
                throw new BadCredentialsException("Invalid response format from authentication service");
            }
            
        } catch (Exception e) {
            logger.error("Error during authentication service call", e);
            throw new BadCredentialsException("Authentication service error: " + e.getMessage());
        }
        
        // EXISTE UN ERROR EN LA RESPUESTA DEL SERVICIO WEB AUTENTICAREG
        if (!arrayDatos[0].equals("00")) {
            
            String mensaje = "";
            
            if (arrayDatos.length == 1) {
                mensaje = arrayDatos[0].toString();
            } else if (arrayDatos.length > 1) {
                mensaje = arrayDatos[1].toString();
            }
            
            logger.warn("Authentication failed for user: " + username + ", reason: " + mensaje);
            throw new BadCredentialsException("AutenticaReg: " + mensaje);
        }
        
        /** PARAMETROS COMP **/
        /**
         * 02S Bloqueo MA
         * 03S Bloqueo RA
         * 04S Bloqueo PF
         * 05S Bloqueo SO
         * 07S Auditoria
         **/
        String cadPermisos = arrayDatos[4].toString();
        
        int cant = cadPermisos.length();
        int grupos = cant / 3;
        
        List<Permiso> permisos = new ArrayList<Permiso>();
        
        for (int i = 0; i < grupos; i++) {
            
            String permiso = cadPermisos.substring(i * 3, (i + 1) * 3);
            
            permisos.add(new Permiso(permiso, ""));
            logger.trace("authenticate permiso: " + permiso);
        }
        
        // Obtener authorities de Spring Security
        List<GrantedAuthority> authorities = obtenerPermisosSeguridad(permisos);
        
        // Validar que el usuario tenga al menos un permiso
        if (authorities.isEmpty()) {
            throw new BadCredentialsException("User has no valid permissions");
        }
        
        // Extraer datos del usuario de forma segura
        String codigoEmpleado = arrayDatos.length > 3 ? arrayDatos[3].toString().trim() : "";
        String codigoArea = arrayDatos.length > 1 ? arrayDatos[1].toString().trim() : "";
        String nombreArea = arrayDatos.length > 6 ? arrayDatos[6].toString().trim() : "";
        String dni = arrayDatos.length > 9 ? arrayDatos[9].toString().trim() : "";
        String nombres = arrayDatos.length > 5 ? arrayDatos[5].toString().replace("/", " ").trim() : "";
        
        // Crear el objeto de usuario personalizado
        UsuarioSeguridad usuarioSeguridad = new UsuarioSeguridad(username, 
                                                                password,
                                                                authorities,
                                                                codigoEmpleado,
                                                                codigoArea, 
                                                                nombreArea,
                                                                "", 
                                                                "",
                                                                nombres, 
                                                                dni,
                                                                "", 
                                                                permisos);
        
        // Crear el token de autenticación
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, 
                null, // No pasar la contraseña por seguridad
                authorities
        );
        
        authToken.setDetails(usuarioSeguridad);

        return authToken;
    }

    /**
     * Verifica si el proveedor soporta el tipo de autenticación dado.
     * 
     * @param authentication Tipo de autenticación.
     * @return true si el proveedor soporta el tipo de autenticación, false en caso contrario.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<GrantedAuthority> obtenerPermisosSeguridad(List<Permiso> permisos) {
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for (Permiso permiso : permisos) {

            if (ConstantesSeguridad.OPCION_ACC.containsKey(permiso.getCodigo())) {
                
                authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get(permiso.getCodigo())));
            }
        }
        
        return authorities;
    }
}