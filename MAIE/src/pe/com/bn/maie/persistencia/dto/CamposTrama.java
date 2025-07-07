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
public class CamposTrama {

    private Long idCampoTrama;            // Mapea a B08_ID_CAMPO_TRAMA (NUMBER(10))
    private Long idOperacion;             // Mapea a B02_ID_OPERACION (NUMBER(10))
    private String idEntidad;             // Mapea a B01_ID_ENTIDAD (VARCHAR2(50))
    private String tagName;               // Mapea a B08_TAG_NAME (VARCHAR2(100))
    private Integer orden;                // Mapea a B08_ORDEN (NUMBER(5))
    private Integer tipoOrigen;           // Mapea a B08_TIPO_ORIGEN (NUMBER(1) -> 1: Request, 2: Response)
    private Integer tipoCampo;            // Mapea a B08_TIPO_CAMPO (NUMBER(1) -> 1: Cadena, 2: Entero, 3: Decimal)
    private String tipoDato;              // Mapea a B08_TIPO_DATO (VARCHAR2(10) -> N, A, AN, D)
    private Integer longitud;             // Mapea a B08_LONGITUD (NUMBER(5))
    private Integer indRelleno;           // Mapea a B08_IND_RELLENO (NUMBER(1) -> 0: No, 1: Sí)
    private String valorRelleno;          // Mapea a B08_VALOR_RELLENO (VARCHAR2(10))
    private String valorDefecto;          // Mapea a B08_VALOR_DEFECTO (VARCHAR2(255))
    private Integer alineacion;           // Mapea a B08_ALINEACION (NUMBER(1) -> 1: Derecha, 2: Izquierda)
    private String usuarioCreacion;       // Mapea a B08_USU_CRE
    private Date fechaCreacion;           // Mapea a B08_FEC_CRE
    private String usuarioModificacion;   // Mapea a B08_USU_MOD
    private Date fechaModificacion;       // Mapea a B08_FEC_MOD

    // Lombok @Data, @AllArgsConstructor and @NoArgsConstructor handle the generation
    // of getters, setters, and constructors automatically.
    // No need to define manual constructors unless specific logic is required.
}
