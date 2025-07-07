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
import pe.com.bn.maie.application.models.ConexionModel;
import pe.com.bn.maie.infraestructura.services.internal.CrudConexion;
import pe.com.bn.maie.persistencia.dto.Conexion; // Asegúrate de que esta es la importación correcta para tu DTO
import pe.com.bn.maie.tranversal.util.JSFUtils;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@ManagedBean
@ViewScoped
@Data
public class ConexionController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ConexionController.class);

    @ManagedProperty("#{crudConexion}")
    private CrudConexion crudConexion;

    private ConexionModel conexionModel;

    @PostConstruct
    public void init() {
        conexionModel = new ConexionModel();
        cargarConexiones();
    }

    public void cargarConexiones() {
        try {
            conexionModel.setListaConexiones(crudConexion.obtenerConexiones());
        } catch (PersistenceException e) {
            logger.error("Error al cargar conexiones", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las conexiones: " + e.getMessage());
        }
    }

    public void registrarConexion() {
        Conexion conexion = conexionModel.getConexionEdicion();
        if (conexion != null && conexion.getIdConexion() != null && conexionModel.isModificacionConexion()) {
            modificarConexion();
        } else {
            try {
                // Asegurarse de que los campos numéricos tengan un valor por defecto si son nulos
                if (conexion.getPuerto() == null) conexion.setPuerto(0);
                if (conexion.getUsaTls() == null) conexion.setUsaTls(0);
                if (conexion.getIndCredenciales() == null) conexion.setIndCredenciales(0);
                if (conexion.getTimeoutConexionMs() == null) conexion.setTimeoutConexionMs(0);
                if (conexion.getTimeoutLecturaMs() == null) conexion.setTimeoutLecturaMs(0);
                if (conexion.getMaxReintentos() == null) conexion.setMaxReintentos(0);
                if (conexion.getReintentoEsperaMs() == null) conexion.setReintentoEsperaMs(0);

                crudConexion.insertarConexion(conexion);
                JSFUtils.mensajeInformativo("Registro exitoso", "Conexión registrada correctamente");
                cargarConexiones(); // Recargar la lista para mostrar la nueva conexión
            } catch (PersistenceException e) {
                JSFUtils.mensajeError("Error al registrar", e.getMessage());
            }
        }
        conexionModel.setModificacionConexion(false);
        conexionModel.setConexionEdicion(new Conexion()); // Limpiar el objeto de edición
    }

    public void cargarConexionSeleccionada() {
        List<Conexion> seleccionadas = conexionModel.getConexionesSeleccionadas();
        if (seleccionadas != null && seleccionadas.size() == 1) {
            conexionModel.setConexionEdicion(seleccionadas.get(0));
            conexionModel.setModificacionConexion(true);
            JSFUtils.ejecutarJs("PF('dlgConexion').show();");
        } else {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una conexión para modificar");
        }
    }

    public void modificarConexion() {
        try {
            Conexion seleccionada = conexionModel.getConexionEdicion();
            if (seleccionada != null) {
                // Asegurarse de que los campos numéricos tengan un valor por defecto si son nulos
                if (seleccionada.getPuerto() == null) seleccionada.setPuerto(0);
                if (seleccionada.getUsaTls() == null) seleccionada.setUsaTls(0);
                if (seleccionada.getIndCredenciales() == null) seleccionada.setIndCredenciales(0);
                if (seleccionada.getTimeoutConexionMs() == null) seleccionada.setTimeoutConexionMs(0);
                if (seleccionada.getTimeoutLecturaMs() == null) seleccionada.setTimeoutLecturaMs(0);
                if (seleccionada.getMaxReintentos() == null) seleccionada.setMaxReintentos(0);
                if (seleccionada.getReintentoEsperaMs() == null) seleccionada.setReintentoEsperaMs(0);

                crudConexion.actualizarConexion(seleccionada);
                JSFUtils.mensajeInformativo("Modificación exitosa", "Conexión actualizada correctamente");
                cargarConexiones();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una conexión para modificar");
            }
        } catch (PersistenceException e) {
            logger.error("Error al modificar conexión", e);
            JSFUtils.mensajeError("Error al modificar", e.getMessage());
        }
    }

    // Ejemplo de cómo manejar un cambio de estado si 'usaTls' se usa como un indicador de estado
    public void cambiarEstadoUsaTls(Integer nuevoEstado) {
        try {
            List<Conexion> seleccionadas = conexionModel.getConexionesSeleccionadas();
            if (seleccionadas != null && !seleccionadas.isEmpty()) {
                for (Conexion con : seleccionadas) {
                    con.setUsaTls(nuevoEstado); // 1 para habilitar TLS, 0 para deshabilitar
                    crudConexion.actualizarConexion(con);
                }
                JSFUtils.mensajeInformativo("Estado TLS actualizado", "Conexiones actualizadas correctamente");
                cargarConexiones();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar al menos una conexión");
            }
        } catch (PersistenceException e) {
            logger.error("Error al actualizar estado TLS", e);
            JSFUtils.mensajeError("Error al actualizar estado TLS", e.getMessage());
        }
    }

    public void buscarConexionPorId() {
        try {
            if (conexionModel.getFiltroIdConexion() == null) {
                cargarConexiones();
            } else {
                Conexion encontrada = crudConexion.buscarConexionPorId(conexionModel.getFiltroIdConexion());
                if (encontrada != null) {
                    conexionModel.setListaConexiones(Collections.singletonList(encontrada));
                    JSFUtils.mensajeInformativo("Búsqueda realizada", "Filtro por ID aplicado");
                } else {
                    conexionModel.setListaConexiones(Collections.emptyList());
                    JSFUtils.mensajeInformativo("Búsqueda realizada", "No se encontraron registros para el ID: " + conexionModel.getFiltroIdConexion());
                }
                conexionModel.setFiltroIdConexion(null); // Limpiar el filtro
            }
        } catch (PersistenceException e) {
            logger.error("Error en búsqueda por ID", e);
            JSFUtils.mensajeError("Error en búsqueda por ID", e.getMessage());
        }
    }

    public void seleccionarBusquedaPorId() {
        // Lógica para alternar la búsqueda si hubiera múltiples opciones
        // Por ahora, solo se activa la búsqueda por ID.
        if (conexionModel.isBuscarPorId()) {
            // Puedes resetear otros filtros si existieran
        }
    }
}
