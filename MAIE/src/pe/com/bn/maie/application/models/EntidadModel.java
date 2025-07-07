package pe.com.bn.maie.application.models;

 
import java.util.List;

import lombok.Data;
import pe.com.bn.maie.persistencia.dto.Entidad;
 
 
@Data
public class EntidadModel {
	
	private boolean buscarPorCodigo;
	private boolean buscarPorDescripcion;
    private List<Entidad> listaEntidades;
    private List<Entidad> listaEntidadesBusqueda;
    private Entidad entidadSeleccionada;   // Para habilitar/modificar/deshabilitar
    private Entidad entidadEdicion;        // Para el formulario (registro/modificaci�n)
    private String filtroCodigo;
    private String codigoBusqueda;
    private String descripcionBusqueda;
    private List<Entidad> entidadesSeleccionadas;
    private boolean modificacionEntidad;

}

