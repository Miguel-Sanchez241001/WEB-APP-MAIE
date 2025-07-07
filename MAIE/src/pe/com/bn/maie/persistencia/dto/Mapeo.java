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
public class Mapeo {

    private Long idMapeo;                   // Mapea a B10_ID_MAPEO (NUMBER(10))
    private Long idOperacion;               // Mapea a B02_ID_OPERACION (NUMBER(10))
    private String idEntidad;               // Mapea a B01_ID_ENTIDAD (VARCHAR2(50))
    private Long idCampoTrama;              // Mapea a B08_ID_CAMPO_TRAMA (NUMBER(10))
    private Long idLlaveBody;               // Mapea a B09_ID_LLAVE_BODY (NUMBER(10))
    private Integer indTransformacion;      // Mapea a B10_IND_TRANSFORMACION (NUMBER(1))
    private String expresionTransform;      // Mapea a B10_EXPRESION_TRANSFORM (VARCHAR2(500))
    private Integer indTrim;                // Mapea a B10_IND_TRIM (NUMBER(1))
    private Integer indAutocompletar;       // Mapea a B10_IND_AUTOCOMPLETAR (NUMBER(1))
    private String valorAutocompletar;      // Mapea a B10_VALOR_AUTOCOMPLETAR (VARCHAR2(255))
    private String descripcion;             // Mapea a B10_DESCRIPCION (VARCHAR2(250))
    private String usuarioCreacion;         // Mapea a B10_USU_CRE
    private Date fechaCreacion;             // Mapea a B10_FEC_CRE
    private String usuarioModificacion;     // Mapea a B10_USU_MOD
    private Date fechaModificacion;         // Mapea a B10_FEC_MOD

    // Lombok @Data, @AllArgsConstructor and @NoArgsConstructor handle the generation
    // of getters, setters, and constructors automatically.
    // No need to define manual constructors unless specific logic is required.
}
