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
public class Operacion {

    private Long idOperacion;               // Maps to B02_ID_OPERACION (NUMBER(10))
    private String idEntidad;               // Maps to B01_ID_ENTIDAD (VARCHAR2(50))
    private Long idConexion;                // Maps to B07_ID_CONEXION (NUMBER(10))
    private String descripcion;             // Maps to B02_DESCRIPCION (VARCHAR2(250))
    private Integer requiereBodyEnvio;      // Maps to B02_REQUIERE_BODY_ENVIO (NUMBER(1))
    private String codigoOperacion;         // Maps to B02_COD_OPERACION (VARCHAR2(4)) - NUEVO
    private String usuarioCreacion;         // Maps to B02_USU_CRE
    private Date fechaCreacion;             // Maps to B02_FEC_CRE
    private String usuarioModificacion;     // Maps to B02_USU_MOD
    private Date fechaModificacion;         // Maps to B02_FEC_MOD

    // Lombok @Data, @AllArgsConstructor and @NoArgsConstructor handle the generation
    // of getters, setters, and constructors automatically.
    // No need to define manual constructors unless specific logic is required.
}
