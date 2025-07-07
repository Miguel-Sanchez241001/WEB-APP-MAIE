package pe.com.bn.maie.persistencia.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Added for a no-argument constructor
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor // Added for a no-argument constructor, useful for frameworks
@ToString
public class Parametro {
    private Long idParametro;             // Mapea a B04_ID_PARA (NUMBER(10))
    private Integer codigoGrupo;          // Mapea a B04_COD_GRUP (NUMBER(10))
    private Integer codigoParametro;      // Mapea a B04_COD_PARA (NUMBER(10))
    private Integer indicadorActivo;      // Mapea a B04_IND_ACT (NUMBER(1))
    private String descripcion;           // Mapea a B04_DESCRIP (VARCHAR2(150))
    private Date fechaCreacion;           // Mapea a B04_FEC_CRE (DATE)
    private Date fechaModificacion;       // Mapea a B04_FEC_MOD (DATE)
    private String usuarioCreacion;       // Mapea a B04_USU_CRE (VARCHAR2(50))
    private String usuarioModificacion;   // Mapea a B04_USU_MOD (VARCHAR2(50))
    private String valor1;                // Mapea a B04_VAL_01 (VARCHAR2(255))
    private String valor2;                // Mapea a B04_VAL_02 (VARCHAR2(255))
    private String valor3;                // Mapea a B04_VAL_03 (CLOB, se mapea a String en Java)
    private Long codigoOperacion;         // Mapea a B04_COD_OPER (NUMBER(10))
    private String idEntidadFk;           // Mapea a B01_ID_ENTIDAD_FK (VARCHAR2(50))
}
