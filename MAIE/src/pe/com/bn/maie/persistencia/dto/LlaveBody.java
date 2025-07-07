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
public class LlaveBody {

    private Long idLlaveBody;             // Mapea a B09_ID_LLAVE_BODY (NUMBER(10))
    private Long idOperacion;             // Mapea a B02_ID_OPERACION (NUMBER(10))
    private String idEntidad;             // Mapea a B01_ID_ENTIDAD (VARCHAR2(50))
    private String tagName;               // Mapea a B09_TAG_NAME (VARCHAR2(100))
    private Integer tipoOrigen;           // Mapea a B09_TIPO_ORIGEN (NUMBER(1) -> 1: Request, 2: Response)
    private Long idLlavePadre;            // Mapea a B09_ID_LLAVE_PADRE (NUMBER(10), puede ser NULL)
    private Integer tipoDatoEsperado;     // Mapea a B09_TIPO_DATO_ESPERADO (NUMBER(1) -> 1: String, 2: Integer, etc.)
    private Integer esPadre;              // Mapea a B09_ES_PADRE (NUMBER(1) -> 0: Hijo, 1: Padre)
    private String valorDefecto;          // Mapea a B09_VALOR_DEFECTO (VARCHAR2(255))
    private Integer orden;                // Mapea a B09_ORDEN (NUMBER(5))
    private Integer indObligatorio;       // Mapea a B09_IND_OBLIGATORIO (NUMBER(1) -> 0: No, 1: Sí)
    private String descripcion;           // Mapea a B09_DESCRIPCION (VARCHAR2(250))
    private Integer indMapeable;          // Mapea a B09_IND_MAPEABLE (NUMBER(1) -> 0: No, 1: Sí)
    private String usuarioCreacion;       // Mapea a B09_USU_CRE
    private Date fechaCreacion;           // Mapea a B09_FEC_CRE
    private String usuarioModificacion;   // Mapea a B09_USU_MOD
    private Date fechaModificacion;       // Mapea a B09_FEC_MOD

    // Lombok @Data, @AllArgsConstructor and @NoArgsConstructor handle the generation
    // of getters, setters, and constructors automatically.
    // No need to define manual constructors unless specific logic is required.
}

