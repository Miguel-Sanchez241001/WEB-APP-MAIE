package pe.com.bn.maie.application.view;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;
import pe.com.bn.maie.application.models.EntidadModel;
import pe.com.bn.maie.infraestructura.services.internal.CrudEntidades;
import pe.com.bn.maie.persistencia.dto.Entidad; // Asegúrate de que esta es la importación correcta para tu DTO
import pe.com.bn.maie.tranversal.util.JSFUtils;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@ManagedBean
@ViewScoped
@Data
public class EntidadController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(EntidadController.class);

    @ManagedProperty("#{crudEntidades}")
    private CrudEntidades crudEntidades;

    private EntidadModel entidadModel;

    @PostConstruct
    public void init() {
        entidadModel = new EntidadModel();
        entidadModel.setEntidadEdicion(new Entidad());
        entidadModel.setModificacionEntidad(false);
        cargarEntidades();
    }

    public void cargarEntidades() {
        try {
            entidadModel.setListaEntidades(crudEntidades.obtenerEntidades());
        } catch (PersistenceException e) {
            logger.error("Error al cargar entidades", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las entidades: " + e.getMessage());
        }
    }

    public void registrarEntidad() {
        Entidad entidad = entidadModel.getEntidadEdicion();
        if (entidad != null && entidad.getIdEntidad() != null && !entidad.getIdEntidad().isEmpty() && entidadModel.isModificacionEntidad()) {
            modificarEntidad();
        } else {
            try {
                // Asegurarse de que los campos numéricos tengan un valor por defecto si son nulos
                if (entidad.getTipoComunicacion() == null) entidad.setTipoComunicacion(1); // Valor por defecto
                if (entidad.getTipoTramaBn() == null) entidad.setTipoTramaBn(1); // Valor por defecto
                if (entidad.getTipoTramaEx() == null) entidad.setTipoTramaEx(1); // Valor por defecto
                if (entidad.getIndMapeo() == null) entidad.setIndMapeo(0); // Valor por defecto
                if (entidad.getEstado() == null) entidad.setEstado(1); // Valor por defecto

                crudEntidades.insertarEntidad(entidadModel.getEntidadEdicion());
                JSFUtils.mensajeInformativo("Registro exitoso", "Entidad registrada correctamente");
                cargarEntidades();
            } catch (PersistenceException e) {
                JSFUtils.mensajeError("Error al registrar", e.getMessage());
            }
        }
        entidadModel.setModificacionEntidad(false);
        entidadModel.setEntidadEdicion(new Entidad()); // Limpiar el objeto de edición
    }

    public void cargarEntidadSeleccionada() {
        List<Entidad> seleccionadas = entidadModel.getEntidadesSeleccionadas();
        if (seleccionadas != null && seleccionadas.size() == 1) {
            entidadModel.setEntidadEdicion(seleccionadas.get(0));
            entidadModel.setModificacionEntidad(true);
            JSFUtils.ejecutarJs("PF('dlgEntidad').show();");
        } else {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una entidad para modificar");
        }
    }

    public void modificarEntidad() {
        try {
            Entidad seleccionada = entidadModel.getEntidadEdicion();
            if (seleccionada != null) {
                // Asegurarse de que los campos numéricos tengan un valor por defecto si son nulos
                if (seleccionada.getTipoComunicacion() == null) seleccionada.setTipoComunicacion(1);
                if (seleccionada.getTipoTramaBn() == null) seleccionada.setTipoTramaBn(1);
                if (seleccionada.getTipoTramaEx() == null) seleccionada.setTipoTramaEx(1);
                if (seleccionada.getIndMapeo() == null) seleccionada.setIndMapeo(0);
                if (seleccionada.getEstado() == null) seleccionada.setEstado(1);

                crudEntidades.actualizarEntidad(seleccionada);
                JSFUtils.mensajeInformativo("Modificación exitosa", "Entidad actualizada correctamente");
                cargarEntidades();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una entidad para modificar");
            }
        } catch (PersistenceException e) {
            logger.error("Error al modificar entidad", e);
            JSFUtils.mensajeError("Error al modificar", e.getMessage());
        }
    }

    public void cambiarEstadoEntidadActivar() {
        try {
            List<Entidad> seleccionadas = entidadModel.getEntidadesSeleccionadas();
            if (seleccionadas != null && !seleccionadas.isEmpty()) {
                for (Entidad ent : seleccionadas) {
                    ent.setEstado(1); // Activar
                    crudEntidades.actualizarEntidad(ent);
                }
                JSFUtils.mensajeInformativo("Estado actualizado", "Entidades activadas correctamente");
                cargarEntidades();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar al menos una entidad para activar");
            }
        } catch (PersistenceException e) {
            logger.error("Error al actualizar estado (activar)", e);
            JSFUtils.mensajeError("Error al actualizar estado", e.getMessage());
        }
    }

    public void cambiarEstadoEntidaDesactivar() {
        try {
            List<Entidad> seleccionadas = entidadModel.getEntidadesSeleccionadas();
            if (seleccionadas != null && !seleccionadas.isEmpty()) {
                for (Entidad ent : seleccionadas) {
                    ent.setEstado(0); // Desactivar
                    crudEntidades.actualizarEntidad(ent);
                }
                JSFUtils.mensajeInformativo("Estado actualizado", "Entidades desactivadas correctamente");
                cargarEntidades();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar al menos una entidad para desactivar");
            }
        } catch (PersistenceException e) {
            logger.error("Error al actualizar estado (desactivar)", e);
            JSFUtils.mensajeError("Error al actualizar estado", e.getMessage());
        }
    }

    public void buscarEntidadPorCodigo() {
        try {
            if (entidadModel.getFiltroCodigo() == null || entidadModel.getFiltroCodigo().trim().isEmpty()) {
                cargarEntidades(); // Si el filtro está vacío, cargar todas las entidades
            } else {
                Entidad encontrada = crudEntidades.buscarEntidadPorId(entidadModel.getFiltroCodigo());
                if (encontrada != null) {
                    entidadModel.setListaEntidades(Collections.singletonList(encontrada));
                    JSFUtils.mensajeInformativo("Búsqueda realizada", "Filtro por código aplicado");
                } else {
                    entidadModel.setListaEntidades(Collections.emptyList()); // Limpiar la tabla si no se encuentra
                    JSFUtils.mensajeInformativo("Búsqueda realizada", "No se encontraron registros para el código: " + entidadModel.getFiltroCodigo());
                }
                entidadModel.setFiltroCodigo(""); // Limpiar el filtro después de la búsqueda
            }
        } catch (PersistenceException e) {
            logger.error("Error en búsqueda por código", e);
            JSFUtils.mensajeError("Error en búsqueda por código", e.getMessage());
        }
    }

    public void seleccionarBusquedaPorCodigo() {
        if (entidadModel.isBuscarPorCodigo()) {
            entidadModel.setBuscarPorDescripcion(false);
            entidadModel.setFiltroCodigo(null); // Limpiar filtro al cambiar
            entidadModel.setListaEntidadesBusqueda(null); // Limpiar lista de búsqueda por descripción
            cargarEntidades(); // Recargar todas las entidades
        }
    }

    public void seleccionarBusquedaPorDescripcion() {
        if (entidadModel.isBuscarPorDescripcion()) {
            try {
                entidadModel.setListaEntidadesBusqueda(crudEntidades.obtenerEntidades());
            } catch (PersistenceException e) {
                logger.error("Error al cargar entidades para búsqueda por descripción", e);
                JSFUtils.mensajeError("Error", "No se pudieron cargar las entidades para la búsqueda por descripción");
            }
            entidadModel.setBuscarPorCodigo(false);
            entidadModel.setFiltroCodigo(null); // Limpiar filtro al cambiar
            cargarEntidades(); // Recargar todas las entidades
        }
    }

    // Este método ya no es necesario en la interfaz CrudEntidades ni aquí,
    // ya que el ID de Entidad ahora es String.
    /*
    @Override
    public Entidad buscarEntidadPorId(Long id) throws PersistenceException {
        // Implementación no necesaria si el ID es String
        return null;
    }
    */
}
