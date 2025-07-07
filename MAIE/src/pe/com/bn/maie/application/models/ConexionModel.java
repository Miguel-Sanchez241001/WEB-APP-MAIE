package pe.com.bn.maie.application.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pe.com.bn.maie.persistencia.dto.Conexion; // Importa tu DTO de Conexión

@Data
public class ConexionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Conexion> listaConexiones;
    private List<Conexion> conexionesSeleccionadas;
    private Conexion conexionEdicion;
    private boolean modificacionConexion;

    // Para la funcionalidad de búsqueda por ID
    private Long filtroIdConexion;
    private boolean buscarPorId;

    public ConexionModel() {
        this.conexionEdicion = new Conexion();
        this.modificacionConexion = false;
        this.buscarPorId = false;
    }
}
