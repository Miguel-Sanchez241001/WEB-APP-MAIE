package pe.com.bn.maie.application.models;

import java.util.List;
import lombok.Data;
import pe.com.bn.maie.persistencia.dto.Parametro;

@Data
public class ParametroModel {
    
    private List<Parametro> listaParametros;
    private List<Parametro> listaGrupos;
    private Parametro parametroSeleccionado;
    private Parametro parametroEdicion;
    private List<Parametro> parametrosSeleccionados;
    private boolean modificacionParametro;
    
    // Filtros de búsqueda
    private String filtroCodigoGrupo;
    private String filtroCodigoParametro;
    
    // Para la creación de grupos
    private String nuevoCodigoGrupo;
    private String nuevaDescripcionGrupo;
    
    // Indica si se está editando un parámetro general o relacionado a operación
    private boolean parametroConOperacion;
}