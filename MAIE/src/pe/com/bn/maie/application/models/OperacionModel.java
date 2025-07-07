package pe.com.bn.maie.application.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pe.com.bn.maie.persistencia.dto.Operacion; // Importa tu DTO de Operacion
import pe.com.bn.maie.persistencia.dto.Entidad;   // Importa tu DTO de Entidad
import pe.com.bn.maie.persistencia.dto.Conexion;  // Importa tu DTO de Conexion

@Data
public class OperacionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Operacion> listaOperaciones;
    private List<Operacion> operacionesSeleccionadas;
    private Operacion operacionEdicion;
    private boolean modificacionOperacion;

    // Para la funcionalidad de búsqueda
    private Long filtroIdOperacion;
    private String filtroIdEntidad;
    private String filtroCodigoOperacion; // NUEVO: para buscar por código de operación
    private boolean buscarPorIdOperacion;
    private boolean buscarPorIdEntidad;
    private boolean buscarPorCodigoOperacion; // NUEVO: checkbox para buscar por código de operación

    // Listas para los selectOneMenu en el diálogo de edición
    private List<Entidad> listaEntidades;
    private List<Conexion> listaConexiones;

    public OperacionModel() {
        this.operacionEdicion = new Operacion();
        this.modificacionOperacion = false;
        this.buscarPorIdOperacion = false;
        this.buscarPorIdEntidad = false;
        this.buscarPorCodigoOperacion = false; // Inicializar
    }
}
