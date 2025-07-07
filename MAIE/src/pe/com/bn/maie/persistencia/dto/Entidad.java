package pe.com.bn.maie.persistencia.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Añadido para un constructor sin argumentos
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor // Añadido para un constructor sin argumentos, útil para frameworks
@ToString
public class Entidad {

    private String idEntidad;             // Mapea a B01_ID_ENTIDAD (VARCHAR2)
    private String descripcion;           // Mapea a B01_DESCRIPCION
    private Integer tipoComunicacion;     // Mapea a B01_TIPO_COMUNICACION
    private Integer tipoTramaBn;          // Mapea a B01_TIPO_TRAMA_BN
    private Integer tipoTramaEx;          // Mapea a B01_TIPO_TRAMA_EX
    private Integer indMapeo;             // Mapea a B01_IND_MAPEO
    private Integer estado;               // Mapea a B01_ESTADO (antes 'activo')
    private String usuarioCreacion;       // Mapea a B01_USU_CRE
    private Date fechaCreacion;           // Mapea a B01_FEC_CRE
    private String usuarioModificacion;   // Mapea a B01_USU_MOD
    private Date fechaModificacion;       // Mapea a B01_FEC_MOD

    // Lombok @Data, @AllArgsConstructor y @NoArgsConstructor se encargan de generar
    // los getters, setters y constructores automáticamente.
    // No es necesario definir constructores manuales a menos que se requiera una lógica específica.
}
