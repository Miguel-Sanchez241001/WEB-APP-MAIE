package pe.com.bn.maie.persistencia.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Added for a no-argument constructor
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor // Added for a no-argument constructor, useful for frameworks
@ToString
public class Conexion {

    private Long idConexion;              // Maps to B07_ID_CONEXION (NUMBER(10))
    private String idEntidad;             // Maps to B01_ID_ENTIDAD (VARCHAR2(50))
    private Integer protocolo;             // Maps to B07_PROTOCOLO (VARCHAR2(10))
    private String host;                  // Maps to B07_HOST (VARCHAR2(255))
    private Integer puerto;               // Maps to B07_PUERTO (NUMBER(5))
    private Integer usaTls;               // Maps to B07_USA_TLS (NUMBER(1))
    private Integer indCredenciales;      // Maps to B07_IND_CREDENCIALES (NUMBER(1))
    private Integer timeoutConexionMs;    // Maps to B07_TIMEOUT_CONEXION_MS (NUMBER(6))
    private Integer timeoutLecturaMs;     // Maps to B07_TIMEOUT_LECTURA_MS (NUMBER(6))
    private Integer maxReintentos;        // Maps to B07_MAX_REINTENTOS (NUMBER(2))
    private Integer reintentoEsperaMs;    // Maps to B07_REINTENTO_ESPERA_MS (NUMBER(6))
    private String usuarioCreacion;       // Maps to B07_USU_CRE
    private Date fechaCreacion;           // Maps to B07_FEC_CRE
    private String usuarioModificacion;   // Maps to B07_USU_MOD
    private Date fechaModificacion;       // Maps to B07_FEC_MOD

    // Lombok @Data, @AllArgsConstructor and @NoArgsConstructor handle the generation
    // of getters, setters, and constructors automatically.
    // No need to define manual constructors unless specific logic is required.
}
