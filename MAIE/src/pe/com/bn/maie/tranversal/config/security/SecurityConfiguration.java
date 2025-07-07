package pe.com.bn.maie.tranversal.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import pe.com.bn.maie.tranversal.util.constantes.ConstantesPagina;
import pe.com.bn.maie.tranversal.util.constantes.ConstantesSeguridad;
 
//Indica que esta clase contiene configuraciones para el contenedor de Spring.
@Configuration
//Habilita la seguridad web de Spring en el proyecto. Es el interruptor principal.
@EnableWebSecurity
//Habilita la seguridad a nivel de método (ej. @PreAuthorize, @PostAuthorize).
//prePostEnabled = true activa las anotaciones @PreAuthorize y @PostAuthorize.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

 // Inyecta tu proveedor de autenticación personalizado.
 // Spring buscará un Bean con el nombre "customAuthenticationProvider".
 @Autowired
 @Qualifier("customAuthenticationProvider")
 private CustomAuthenticationProvider customAuthenticationProvider;

 /**
  * Configura los recursos que Spring Security debe ignorar por completo.
  * Estos recursos no pasarán por la cadena de filtros de seguridad,
  * lo que mejora el rendimiento para contenido estático.
  */
 @Override
 public void configure(WebSecurity webSecurity) throws Exception {
     webSecurity.ignoring()
         // Ignora todos los recursos estáticos como CSS, JS, imágenes, etc.
         .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**")
         // Ignora las páginas de error para evitar bucles de redirección.
         .antMatchers("/error/**")
         // Ignora el ícono de la aplicación.
         .antMatchers("/favicon.ico")
         // Ignora los recursos generados por JavaServer Faces (JSF). Es crucial para que JSF funcione.
         .antMatchers("/javax.faces.resource/**");
 }

 /**
  * Configura el gestor de autenticación (AuthenticationManager).
  * Le dice a Spring Security CÓMO autenticar a los usuarios.
  */
 @Override
 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     // Aquí registras tu proveedor de autenticación personalizado.
     // Toda la lógica de validación de usuario y contraseña se delega a `CustomAuthenticationProvider`.
     // Alternativa: Podrías usar un proveedor en memoria (auth.inMemoryAuthentication()) para pruebas
     // o un proveedor JDBC (auth.jdbcAuthentication()) para autenticar contra una base de datos.
     auth.authenticationProvider(customAuthenticationProvider);
 }

 /**
  * El corazón de la configuración de seguridad. Define las reglas de protección
  * para las peticiones HTTP (URLs).
  */
 @Override
 protected void configure(HttpSecurity http) throws Exception {
     http
         // 1. Protección CSRF (Cross-Site Request Forgery)
         // Se deshabilita porque JSF 2.2+ tiene su propio mecanismo de protección CSRF (ViewState).
         // Habilitarlo podría causar conflictos. Si usaras otro framework como Thymeleaf,
         // lo ideal sería mantenerlo habilitado: .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
         .csrf().disable()
         
         // 2. Gestión de Sesiones
         .sessionManagement()
             // Define cuándo crear una sesión. IF_REQUIRED crea una sesión solo si es necesario (ej. al loguearse).
             // Alternativas: ALWAYS (siempre crea), NEVER (nunca crea), STATELESS (sin sesión, para APIs REST).
             .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
             // Permite solo una sesión activa por usuario.
             .maximumSessions(1)
             // Si un usuario intenta iniciar una segunda sesión, la primera es invalidada (el usuario anterior es "expulsado").
             // Si fuera `true`, se impediría el nuevo inicio de sesión. `false` es más amigable.
             .maxSessionsPreventsLogin(false)
             // URL a la que se redirige al usuario cuya sesión ha expirado por un nuevo login.
             .expiredUrl(ConstantesPagina.PAGINA_INDEX + "?expired=true")
             // Registro para llevar la cuenta de las sesiones activas. Necesario para `maximumSessions`.
             .sessionRegistry(sessionRegistry())
             .and()
             // Protección contra Session Fixation. `migrateSession` crea una nueva sesión al loguearse
             // y copia los atributos de la antigua, invalidando la anterior. Es la opción recomendada.
             // Alternativa: `newSession()` crea una sesión limpia sin copiar atributos.
             .sessionFixation().migrateSession()
             // URL a la que se redirige si se intenta usar una sesión inválida.
             .invalidSessionUrl(ConstantesPagina.PAGINA_INDEX + "?invalid=true")
             .and()
         
         // 3. Cabeceras de Seguridad (Security Headers)
         // Ayudan a proteger contra ataques como Clickjacking, XSS, etc.
         .headers()
             // X-Frame-Options: DENY. Evita que tu página sea cargada en un <iframe>, <frame>, etc.
             // previene ataques de Clickjacking. Alternativa: `sameOrigin()` para permitir iframes del mismo dominio.
             .frameOptions().deny()
             // X-Content-Type-Options: nosniff. Evita que el navegador intente adivinar (MIME-sniffing) el tipo de contenido.
             .contentTypeOptions().and()
             // HTTP Strict Transport Security (HSTS). Obliga al navegador a usar HTTPS.
             .httpStrictTransportSecurity(hstsConfig -> hstsConfig
                 .maxAgeInSeconds(31536000) // 1 año. El navegador recordará usar HTTPS.
                 .includeSubDomains(true)   // Aplica la regla a todos los subdominios.
                 .preload(true)             // Permite que tu sitio sea pre-cargado en las listas HSTS de los navegadores.
             )
             // Content-Security-Policy (CSP). Define de dónde se pueden cargar recursos (scripts, imágenes, etc).
             // Es una defensa muy potente contra ataques XSS.
             .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy", 
                 "default-src 'self'; " + // Por defecto, solo permite recursos del mismo origen ('self').
                 "img-src 'self' data: https:; " + // Imágenes del mismo origen, data URIs y cualquier fuente HTTPS.
                 "script-src 'self' 'unsafe-inline' 'unsafe-eval' ; " + // Scripts del mismo origen. 'unsafe-*' son necesarios para JSF, pero menos seguros.
                 "style-src 'self' 'unsafe-inline'  https://cdnjs.cloudflare.com; " + // Estilos del mismo origen y en línea. También es por compatibilidad con JSF.
                 "font-src 'self' data:  https://cdnjs.cloudflare.com; " + // Fuentes del mismo origen y data URIs.
                 "object-src 'none'; " + // No permite plugins como Flash (<object>, <embed>). Muy recomendado.
                 "base-uri 'self'")) // Restringe la etiqueta <base> al propio origen.
             // Referrer-Policy. Controla qué información de "referencia" se envía al navegar a otros sitios.
             .addHeaderWriter(new ReferrerPolicyHeaderWriter(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
             // X-Permitted-Cross-Domain-Policies. Restringe las peticiones de dominios cruzados de Adobe Flash/Acrobat.
             .addHeaderWriter(new StaticHeadersWriter("X-Permitted-Cross-Domain-Policies", "none"))
             // Permissions-Policy. Controla qué características del navegador puede usar la página (cámara, micro, etc.).
             .addHeaderWriter(new StaticHeadersWriter("Permissions-Policy", "geolocation=(), microphone=(), camera=()"))
             .and()
         
         // 4. Autorización de Peticiones
         // Define los permisos necesarios para acceder a las URLs. Se procesan en orden.
         .authorizeRequests()
             // Permite el acceso a todos (autenticados o no) a la página de inicio y sus recursos JSF.
             .antMatchers(ConstantesPagina.PAGINA_INDEX, "/index.jsf").permitAll()
             .antMatchers("/javax.faces.resource/**").permitAll()
             // Solo los usuarios con la autoridad 'ACCESO_OPERACIONES_BLOQUEO_TARJETA' pueden acceder.
             .antMatchers(ConstantesPagina.PAGINA_OPERACIONES_BLOQUEO_TARJETA)
                 .hasAnyAuthority(ConstantesSeguridad.ACCESO_OPERACIONES_BLOQUEO_TARJETA)
             // Similar para las otras páginas, cada una con su permiso específico.
             .antMatchers(ConstantesPagina.PAGINA_CONSULTAS_CONSULTA)
                 .hasAnyAuthority(ConstantesSeguridad.ACCESO_CONSULTAS_CONSULTA)
             .antMatchers(ConstantesPagina.PAGINA_AUDITORIA_CONSULTA)
                 .hasAnyAuthority(ConstantesSeguridad.ACCESO_AUDITORIA_CONSULTA)
             // El usuario debe estar autenticado para acceder a la página principal.
             .antMatchers(ConstantesPagina.PAGINA_PRINCIPAL).authenticated()
             // Cualquier otra petición no especificada anteriormente requiere que el usuario esté autenticado.
             .anyRequest().authenticated()
             .and()
         
         // 5. Configuración del Formulario de Login
         .formLogin()
             // URL de la página de inicio de sesión personalizada.
             .loginPage(ConstantesPagina.PAGINA_INDEX)
             // Nombres de los parámetros en el formulario HTML para usuario y contraseña.
             .usernameParameter(ConstantesPagina.LOGIN_PARAMETRO_USUARIO)
             .passwordParameter(ConstantesPagina.LOGIN_PARAMETRO_CONTRASENIA)
             // URL que procesará el envío del formulario de login. Spring Security la intercepta.
             .loginProcessingUrl(ConstantesPagina.LOGIN_URL_AUTENTICACION)
             // Manejador personalizado para acciones después de un login exitoso (ej. redireccionar según el rol).
             .successHandler(authenticationSuccessHandler())    
             // URL a la que se redirige si el login falla (ej. contraseña incorrecta).
             .failureUrl(ConstantesPagina.PAGINA_INDEX + "?error=true")
             // Permite el acceso a todos a la página de login.
             .permitAll()
             .and()
         
         // 6. Configuración del Logout
         .logout()
             // URL que activa el proceso de logout.
             .logoutUrl(ConstantesPagina.LOGIN_URL_CERRAR_SESION)
             // URL a la que se redirige después de un logout exitoso.
             .logoutSuccessUrl(ConstantesPagina.PAGINA_INDEX + "?logout=true")
             // Borra la cookie de sesión del navegador.
             .deleteCookies("JSESSIONID")
             // Invalida la sesión HTTP en el servidor.
             .invalidateHttpSession(true)
             // Limpia el contexto de seguridad para que el usuario ya no esté autenticado.
             .clearAuthentication(true)
             .permitAll()
             .and()
         
         // 7. Manejo de Excepciones de Seguridad
         .exceptionHandling()
             // Página a la que se redirige si un usuario autenticado intenta acceder a un recurso sin permiso (Error 403 Forbidden).
             .accessDeniedPage(ConstantesPagina.PAGINA_ACCESO_DENEGADO)
             // Punto de entrada para usuarios no autenticados que intentan acceder a recursos protegidos.
             // Aquí se personaliza para redirigir a la página de inicio con un parámetro de timeout.
             .authenticationEntryPoint((request, response, authException) -> {
                 response.sendRedirect(request.getContextPath()  + ConstantesPagina.PAGINA_INDEX + "?timeout=true");
             });
 }

 /**
  * Define un Bean para tu manejador de éxito de login personalizado.
  * Permite ejecutar lógica extra tras una autenticación exitosa.
  */
 @Bean
 public AuthenticationSuccessHandler authenticationSuccessHandler() {
     return new CustomSuccessLoginHandler();
 }

 /**
  * Crea el registro de sesiones. Es fundamental para que la funcionalidad
  * de `maximumSessions(1)` funcione correctamente.
  */
 @Bean
 public SessionRegistry sessionRegistry() {
     return new SessionRegistryImpl();
 }

 /**
  * Este Bean es crucial. Vincula los eventos de sesión de Spring Security
  * con los eventos del contenedor de servlets (como Tomcat). Sin él,
  * Spring Security no se enteraría de cuándo una sesión es creada o destruida,
  * y la limpieza de sesiones expiradas no funcionaría.
  */
 @Bean
 public HttpSessionEventPublisher httpSessionEventPublisher() {
     return new HttpSessionEventPublisher();
 }
}