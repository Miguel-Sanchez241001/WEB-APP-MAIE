package pe.com.bn.maie.application.view;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;
import pe.com.bn.maie.application.models.OperacionModel;
import pe.com.bn.maie.infraestructura.services.internal.CrudOperaciones;
import pe.com.bn.maie.infraestructura.services.internal.CrudEntidades;
import pe.com.bn.maie.infraestructura.services.internal.CrudConexion;
import pe.com.bn.maie.persistencia.dto.Operacion; // Asegúrate de que esta es la importación correcta para tu DTO
import pe.com.bn.maie.persistencia.dto.Entidad;
import pe.com.bn.maie.persistencia.dto.Conexion;
import pe.com.bn.maie.tranversal.util.JSFUtils;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@ManagedBean
@ViewScoped
@Data
public class OperacionController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(OperacionController.class);

    @ManagedProperty("#{crudOperaciones}")
    private CrudOperaciones crudOperaciones;

    @ManagedProperty("#{crudEntidades}")
    private CrudEntidades crudEntidades;

    @ManagedProperty("#{crudConexion}")
    private CrudConexion crudConexion;

    private OperacionModel operacionModel;

    @PostConstruct
    public void init() {
        operacionModel = new OperacionModel();
        operacionModel.setOperacionEdicion(new Operacion());
        operacionModel.setModificacionOperacion(false);
        cargarOperaciones();
        cargarListasParaDialogo();
    }

    public void cargarOperaciones() {
        try {
            operacionModel.setListaOperaciones(crudOperaciones.obtenerOperaciones());
        } catch (PersistenceException e) {
            logger.error("Error al cargar operaciones", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las operaciones: " + e.getMessage());
        }
    }

    public void cargarListasParaDialogo() {
        try {
            operacionModel.setListaEntidades(crudEntidades.obtenerEntidades());
            operacionModel.setListaConexiones(crudConexion.obtenerConexiones());
        } catch (PersistenceException e) {
            logger.error("Error al cargar listas para diálogo de operaciones", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las listas de entidades/conexiones: " + e.getMessage());
        }
    }

    public void registrarOperacion() {
        Operacion operacion = operacionModel.getOperacionEdicion();
        if (operacion != null && operacion.getIdOperacion() != null && operacionModel.isModificacionOperacion()) {
            modificarOperacion();
        } else {
            try {
                // Asegurarse de que los campos numéricos tengan un valor por defecto si son nulos
                if (operacion.getRequiereBodyEnvio() == null) operacion.setRequiereBodyEnvio(0); // Valor por defecto
                if (operacion.getCodigoOperacion() == null || operacion.getCodigoOperacion().trim().isEmpty()) {
                    JSFUtils.mensajeAdvertencia("Advertencia", "El Código de Operación es obligatorio.");
                    return;
                }

                crudOperaciones.insertarOperacion(operacion);
                JSFUtils.mensajeInformativo("Registro exitoso", "Operación registrada correctamente");
                cargarOperaciones(); // Recargar la lista para mostrar la nueva operación
            } catch (PersistenceException e) {
                logger.error("Error al registrar operación", e);
                JSFUtils.mensajeError("Error al registrar", e.getMessage());
            }
        }
        operacionModel.setModificacionOperacion(false);
        operacionModel.setOperacionEdicion(new Operacion()); // Limpiar el objeto de edición
    }

    public void cargarOperacionSeleccionada() {
        List<Operacion> seleccionadas = operacionModel.getOperacionesSeleccionadas();
        if (seleccionadas != null && seleccionadas.size() == 1) {
            operacionModel.setOperacionEdicion(seleccionadas.get(0));
            operacionModel.setModificacionOperacion(true);
            JSFUtils.ejecutarJs("PF('dlgOperacion').show();");
        } else {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una operación para modificar");
        }
    }

    public void modificarOperacion() {
        try {
            Operacion seleccionada = operacionModel.getOperacionEdicion();
            if (seleccionada != null) {
                // Asegurarse de que los campos numéricos tengan un valor por defecto si son nulos
                if (seleccionada.getRequiereBodyEnvio() == null) seleccionada.setRequiereBodyEnvio(0);
                if (seleccionada.getCodigoOperacion() == null || seleccionada.getCodigoOperacion().trim().isEmpty()) {
                    JSFUtils.mensajeAdvertencia("Advertencia", "El Código de Operación es obligatorio.");
                    return;
                }

                crudOperaciones.actualizarOperacion(seleccionada);
                JSFUtils.mensajeInformativo("Modificación exitosa", "Operación actualizada correctamente");
                cargarOperaciones();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una operación para modificar");
            }
        } catch (PersistenceException e) {
            logger.error("Error al modificar operación", e);
            JSFUtils.mensajeError("Error al modificar", e.getMessage());
        }
    }

    public void buscarOperacionPorId() {
        try {
            // Si todos los filtros están vacíos, cargar todas las operaciones
            if (operacionModel.getFiltroIdOperacion() == null &&
                (operacionModel.getFiltroIdEntidad() == null || operacionModel.getFiltroIdEntidad().trim().isEmpty()) &&
                (operacionModel.getFiltroCodigoOperacion() == null || operacionModel.getFiltroCodigoOperacion().trim().isEmpty())) {
                cargarOperaciones();
            } else {
                Operacion encontrada = crudOperaciones.buscarOperacion(
                    operacionModel.getFiltroIdOperacion(),
                    operacionModel.getFiltroIdEntidad(),
                    operacionModel.getFiltroCodigoOperacion()
                );
                if (encontrada != null) {
                    operacionModel.setListaOperaciones(Collections.singletonList(encontrada));
                    JSFUtils.mensajeInformativo("Búsqueda realizada", "Filtro aplicado.");
                } else {
                    operacionModel.setListaOperaciones(Collections.emptyList()); // Limpiar la tabla si no se encuentra
                    JSFUtils.mensajeInformativo("Búsqueda realizada", "No se encontraron registros para los filtros proporcionados.");
                }
                // Limpiar los filtros después de la búsqueda
                operacionModel.setFiltroIdOperacion(null);
                operacionModel.setFiltroIdEntidad(null);
                operacionModel.setFiltroCodigoOperacion(null);
            }
        } catch (PersistenceException e) {
            logger.error("Error en búsqueda de operación", e);
            JSFUtils.mensajeError("Error en búsqueda de operación", e.getMessage());
        }
    }

    public void seleccionarBusquedaPorIdOperacion() {
        if (operacionModel.isBuscarPorIdOperacion()) {
            operacionModel.setBuscarPorIdEntidad(false);
            operacionModel.setBuscarPorCodigoOperacion(false); // Desactivar otros filtros
            operacionModel.setFiltroIdEntidad(null);
            operacionModel.setFiltroCodigoOperacion(null);
            cargarOperaciones(); // Recargar todas las operaciones
        }
    }

    public void seleccionarBusquedaPorIdEntidad() {
        if (operacionModel.isBuscarPorIdEntidad()) {
            operacionModel.setBuscarPorIdOperacion(false);
            operacionModel.setBuscarPorCodigoOperacion(false); // Desactivar otros filtros
            operacionModel.setFiltroIdOperacion(null);
            operacionModel.setFiltroCodigoOperacion(null);
            cargarOperaciones(); // Recargar todas las operaciones
        }
    }

    public void seleccionarBusquedaPorCodigoOperacion() { // NUEVO método
        if (operacionModel.isBuscarPorCodigoOperacion()) {
            operacionModel.setBuscarPorIdOperacion(false);
            operacionModel.setBuscarPorIdEntidad(false); // Desactivar otros filtros
            operacionModel.setFiltroIdOperacion(null);
            operacionModel.setFiltroIdEntidad(null);
            cargarOperaciones(); // Recargar todas las operaciones
        }
    }
}
