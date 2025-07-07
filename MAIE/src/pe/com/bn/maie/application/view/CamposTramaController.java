package pe.com.bn.maie.application.view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent; // Import for TabChangeEvent
import org.primefaces.model.UploadedFile;

import lombok.Data;
import pe.com.bn.maie.application.models.CamposTramaModel;
import pe.com.bn.maie.infraestructura.services.internal.CrudCamposTrama;
import pe.com.bn.maie.infraestructura.services.internal.CrudEntidades;
import pe.com.bn.maie.infraestructura.services.internal.CrudOperaciones;
import pe.com.bn.maie.persistencia.dto.CamposTrama;
import pe.com.bn.maie.persistencia.dto.Entidad;
import pe.com.bn.maie.persistencia.dto.Operacion;
import pe.com.bn.maie.tranversal.util.CopybookParserUtil;
import pe.com.bn.maie.tranversal.util.JSFUtils;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;


@ManagedBean
@ViewScoped
@Data
public class CamposTramaController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(CamposTramaController.class);

    @ManagedProperty("#{crudCamposTrama}")
    private CrudCamposTrama crudCamposTrama;

    @ManagedProperty("#{crudOperaciones}")
    private CrudOperaciones crudOperaciones;

    @ManagedProperty("#{crudEntidades}")
    private CrudEntidades crudEntidades;

    private CamposTramaModel camposTramaModel;
    
    // New property to control the active step in the p:steps component
    private int activeRegisterStep;

    @PostConstruct
    public void init() {
        camposTramaModel = new CamposTramaModel();
        cargarListasMaestras();
        cargarTodosLosCamposTrama();
        // Initialize the first step as active
        activeRegisterStep = 0; 
    }

    private void cargarListasMaestras() {
        try {
            camposTramaModel.setListaOperaciones(crudOperaciones.obtenerOperaciones());
            camposTramaModel.setListaEntidades(crudEntidades.obtenerEntidades());
        } catch (PersistenceException e) {
            logger.error("Error al cargar listas maestras (operaciones/entidades)", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las listas de operaciones o entidades.");
        }
    }

    private void cargarTodosLosCamposTrama() {
        try {
            camposTramaModel.setListaCamposTrama(crudCamposTrama.obtenerCamposTrama());
        } catch (PersistenceException e) {
            logger.error("Error al cargar todos los campos de trama", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar todos los campos de trama.");
        }
    }

    /**
     * Prepares the UI for field registration after selecting operation, entity, and type.
     * This is the new method called from the "Register" tab.
     */
    public void seleccionarOperacionYTipoOrigenParaRegistrar() {
        if (camposTramaModel.getSelectedIdOperacion() == null ||
            camposTramaModel.getSelectedIdEntidad() == null ||
            camposTramaModel.getSelectedTipoOrigen() == null) {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una Operación, Entidad y Tipo de Origen.");
            return;
        }

        try {
            // Search for the complete operation to display its details
            Operacion op = crudOperaciones.buscarOperacion(
                camposTramaModel.getSelectedIdOperacion(),
                camposTramaModel.getSelectedIdEntidad(),
                "0001" // No filtering by codigoOperacion here
            );
            if (op == null) {
                JSFUtils.mensajeError("Error", "No se encontró la operación seleccionada.");
                return;
            }
            camposTramaModel.setOperacionSeleccionadaDetalle(op);

            // Show the registration panel and move to the next step
            camposTramaModel.setShowRegisterFieldsUI(true);
            camposTramaModel.setCamposPendientesDeRegistro(new ArrayList<>());
            activeRegisterStep = 1; // Move to the second step (Register fields)
            
        } catch (PersistenceException e) {
            logger.error("Error al seleccionar operación para registrar", e);
            JSFUtils.mensajeError("Error", "No se pudo procesar la selección: " + e.getMessage());
        }
    }


    /**
     * Resets the UI and returns to the main Campos Trama screen.
     */
    public void volverAPrincipal() {
        camposTramaModel.setShowRegisterFieldsUI(false);
        camposTramaModel.setShowViewFieldsUI(false);
        camposTramaModel.setShowModifyFieldsUI(false);
        camposTramaModel.setCamposPendientesDeRegistro(new ArrayList<>());
        camposTramaModel.setCampoTramaEdicion(new CamposTrama());
        camposTramaModel.setModificacionCampoTrama(false);
        camposTramaModel.setUploadedCopybookFile(null);
        camposTramaModel.setOperacionSeleccionadaDetalle(null);
        // Reset the steps to the first one
        activeRegisterStep = 0;
        // Reload the main table if necessary
        cargarTodosLosCamposTrama();
    }

    // --- Logic for "Register Fields" ---

    public void prepararNuevoCampoManual() {
        camposTramaModel.setCampoTramaEdicion(new CamposTrama());
        camposTramaModel.setModificacionCampoTrama(false);
    }

    public void agregarCampoManual() {
        CamposTrama nuevoCampo = camposTramaModel.getCampoTramaEdicion();
        if (nuevoCampo == null || nuevoCampo.getTagName() == null || nuevoCampo.getTagName().isEmpty()) {
            JSFUtils.mensajeAdvertencia("Advertencia", "El Tag Name es obligatorio.");
            return;
        }
        if (nuevoCampo.getTipoCampo() == null || nuevoCampo.getTipoDato() == null || nuevoCampo.getLongitud() == null) {
             JSFUtils.mensajeAdvertencia("Advertencia", "Tipo de Campo, Tipo de Dato y Longitud son obligatorios.");
             return;
        }

        nuevoCampo.setIdOperacion(camposTramaModel.getSelectedIdOperacion());
        nuevoCampo.setIdEntidad(camposTramaModel.getSelectedIdEntidad());
        nuevoCampo.setTipoOrigen(camposTramaModel.getSelectedTipoOrigen());
        nuevoCampo.setUsuarioCreacion("SYSTEM");

        camposTramaModel.getCamposPendientesDeRegistro().add(nuevoCampo);
        int order = 1;
        for (CamposTrama ct : camposTramaModel.getCamposPendientesDeRegistro()) {
            ct.setOrden(order++);
        }

        JSFUtils.mensajeInformativo("Campo Agregado", "Campo '" + nuevoCampo.getTagName() + "' añadido a la lista pendiente.");
        JSFUtils.ejecutarJs("PF('dlgManualEntry').hide();");
        camposTramaModel.setCampoTramaEdicion(new CamposTrama());
    }

    public void handleCopybookUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null) {
            camposTramaModel.setUploadedCopybookFile(file);
            try {
                File tempFile = File.createTempFile("copybook_", ".cpy");
                Files.copy(file.getInputstream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                List<CamposTrama> parsedCampos = CopybookParserUtil.parsearCopybook(tempFile.getAbsolutePath());
                
                int order = 1;
                for (CamposTrama ct : parsedCampos) {
                    ct.setIdOperacion(camposTramaModel.getSelectedIdOperacion());
                    ct.setIdEntidad(camposTramaModel.getSelectedIdEntidad());
                    ct.setTipoOrigen(camposTramaModel.getSelectedTipoOrigen());
                    ct.setOrden(order++);
                    ct.setUsuarioCreacion("SYSTEM");
                }
                camposTramaModel.setCamposPendientesDeRegistro(parsedCampos);
                JSFUtils.mensajeInformativo("Archivo Cargado", "Copybook '" + file.getFileName() + "' procesado. " + parsedCampos.size() + " campos encontrados.");
            } catch (IOException e) {
                logger.error("Error al procesar el copybook", e);
                JSFUtils.mensajeError("Error de Carga", "No se pudo procesar el archivo Copybook: " + e.getMessage());
            }
        }
    }

    public void showConfirmSaveDialog() {
        if (camposTramaModel.getCamposPendientesDeRegistro().isEmpty()) {
            JSFUtils.mensajeAdvertencia("Advertencia", "No hay campos para registrar.");
            return;
        }
        JSFUtils.ejecutarJs("PF('dlgConfirmSave').show();");
    }

    public void confirmarRegistroCampos() {
        try {
            for (CamposTrama campo : camposTramaModel.getCamposPendientesDeRegistro()) {
                crudCamposTrama.insertarCampoTrama(campo);
            }
            JSFUtils.mensajeInformativo("Registro Exitoso", "Todos los campos han sido registrados correctamente.");
            volverAPrincipal();
            JSFUtils.ejecutarJs("PF('dlgConfirmSave').hide();");
        } catch (PersistenceException e) {
            logger.error("Error al registrar campos de trama", e);
            JSFUtils.mensajeError("Error de Registro", "No se pudieron registrar los campos: " + e.getMessage());
        }
    }

    // --- Logic for "View Fields" ---

    public void cargarCamposParaVer() {
        // Clear previous selection/edit state
        camposTramaModel.setCampoTramaEdicion(new CamposTrama());
        camposTramaModel.setModificacionCampoTrama(false);

        if (camposTramaModel.getSelectedIdOperacion() == null ||
            camposTramaModel.getSelectedIdEntidad() == null ||
            camposTramaModel.getSelectedTipoOrigen() == null) {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una Operación, Entidad y Tipo de Origen para buscar.");
            camposTramaModel.setListaCamposTrama(new ArrayList<>()); // Clear table if criteria not met
            return;
        }

        try {
            List<CamposTrama> campos = crudCamposTrama.buscarCamposPorOperacion(
                camposTramaModel.getSelectedIdOperacion(),
                camposTramaModel.getSelectedIdEntidad(),
                camposTramaModel.getSelectedTipoOrigen()
            );
            camposTramaModel.setListaCamposTrama(campos);
            if (campos.isEmpty()) {
                JSFUtils.mensajeInformativo("Información", "No se encontraron campos para la operación y tipo de origen seleccionados.");
            }
        } catch (PersistenceException e) {
            logger.error("Error al cargar campos para ver", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar los campos para visualización: " + e.getMessage());
        }
    }

    // --- Logic for "Modify Fields" ---

    public void cargarCamposParaModificar() {
        try {
            List<CamposTrama> campos = crudCamposTrama.buscarCamposPorOperacion(
                camposTramaModel.getSelectedIdOperacion(),
                camposTramaModel.getSelectedIdEntidad(),
                camposTramaModel.getSelectedTipoOrigen()
            );
            camposTramaModel.setListaCamposTrama(campos);
            if (campos.isEmpty()) {
                JSFUtils.mensajeInformativo("Información", "No se encontraron campos para la operación y tipo de origen seleccionados.");
            }
        } catch (PersistenceException e) {
            logger.error("Error al cargar campos para modificar", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar los campos para modificación: " + e.getMessage());
        }
    }

    public void editarCampoTrama(CamposTrama campo) {
        camposTramaModel.setCampoTramaEdicion(campo);
        camposTramaModel.setModificacionCampoTrama(true);
        JSFUtils.ejecutarJs("PF('dlgManualEntry').show();");
    }

    public void guardarCampoTramaModificado() {
        try {
            CamposTrama campo = camposTramaModel.getCampoTramaEdicion();
            if (campo == null || campo.getIdCampoTrama() == null) {
                JSFUtils.mensajeAdvertencia("Advertencia", "No hay campo seleccionado para modificar.");
                return;
            }
            if (campo.getTagName() == null || campo.getTagName().isEmpty()) {
                JSFUtils.mensajeAdvertencia("Advertencia", "El Tag Name es obligatorio.");
                return;
            }
            if (campo.getTipoCampo() == null || campo.getTipoDato() == null || campo.getLongitud() == null) {
                 JSFUtils.mensajeAdvertencia("Advertencia", "Tipo de Campo, Tipo de Dato y Longitud son obligatorios.");
                 return;
            }

            campo.setUsuarioModificacion("SYSTEM");
            campo.setFechaModificacion(new Date());

            crudCamposTrama.actualizarCampoTrama(campo);
            JSFUtils.mensajeInformativo("Modificación Exitosa", "Campo de trama actualizado correctamente.");
            JSFUtils.ejecutarJs("PF('dlgManualEntry').hide();");
            
            // Reload the table based on which tab is active
            if (camposTramaModel.isShowViewFieldsUI()) { // If currently in "View Fields" tab
                cargarCamposParaVer();
            } else if (camposTramaModel.isShowModifyFieldsUI()) { // If currently in "Modify Fields" tab
                cargarCamposParaModificar();
            } else { // Default or if coming from Register tab after saving
                cargarTodosLosCamposTrama();
            }
            
        } catch (PersistenceException e) {
            logger.error("Error al modificar campo de trama", e);
            JSFUtils.mensajeError("Error de Modificación", "No se pudo actualizar el campo: " + e.getMessage());
        }
    }

    /**
     * Handles the action for adding a new field or saving modifications to an existing field.
     * This method is called from the dialog's command button.
     */
    public void handleSaveOrAddCampoTrama() {
        if (camposTramaModel.isModificacionCampoTrama()) {
            guardarCampoTramaModificado();
        } else {
            agregarCampoManual();
        }
    }

    // --- Methods for SelectItems ---
    public List<javax.faces.model.SelectItem> getOperacionesAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione una Operación", "", true));
        if (camposTramaModel.getListaOperaciones() != null) {
            for (Operacion op : camposTramaModel.getListaOperaciones()) {
                items.add(new javax.faces.model.SelectItem(op.getIdOperacion(), op.getDescripcion() + " (Cod: " + op.getCodigoOperacion() + ")"));
            }
        }
        return items;
    }

    public List<javax.faces.model.SelectItem> getEntidadesAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione una Entidad", "", true));
        if (camposTramaModel.getListaEntidades() != null) {
            for (Entidad ent : camposTramaModel.getListaEntidades()) {
                items.add(new javax.faces.model.SelectItem(ent.getIdEntidad(), ent.getDescripcion() + " (ID: " + ent.getIdEntidad() + ")"));
            }
        }
        return items;
    }

    public List<javax.faces.model.SelectItem> getTiposOrigenAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione Tipo de Origen", "", true));
        items.add(new javax.faces.model.SelectItem(1, "Request"));
        items.add(new javax.faces.model.SelectItem(2, "Response"));
        return items;
    }

    public List<javax.faces.model.SelectItem> getTiposCampoAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione Tipo de Campo", "", true));
        items.add(new javax.faces.model.SelectItem(1, "Cadena"));
        items.add(new javax.faces.model.SelectItem(2, "Entero"));
        items.add(new javax.faces.model.SelectItem(3, "Decimal"));
        return items;
    }

    public List<javax.faces.model.SelectItem> getTiposDatoAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione Tipo de Dato", "", true));
        items.add(new javax.faces.model.SelectItem("N", "Numérico"));
        items.add(new javax.faces.model.SelectItem("A", "Alfabético"));
        items.add(new javax.faces.model.SelectItem("AN", "Alfanumérico"));
        items.add(new javax.faces.model.SelectItem("D", "Fecha"));
        return items;
    }

    public List<javax.faces.model.SelectItem> getIndicadorRellenoAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione", "", true));
        items.add(new javax.faces.model.SelectItem(1, "Sí"));
        items.add(new javax.faces.model.SelectItem(0, "No"));
        return items;
    }

    public List<javax.faces.model.SelectItem> getAlineacionAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione", "", true));
        items.add(new javax.faces.model.SelectItem(1, "Derecha"));
        items.add(new javax.faces.model.SelectItem(2, "Izquierda"));
        return items;
    }

    // Getter and Setter for activeRegisterStep (Lombok @Data will handle this)
    public int getActiveRegisterStep() {
        return activeRegisterStep;
    }
    public void setActiveRegisterStep(int activeRegisterStep) {
        this.activeRegisterStep = activeRegisterStep;
    }

    /**
     * Listener for tab changes in the p:tabView.
     * Used to reset UI state or load data specific to the activated tab.
     * @param event The TabChangeEvent.
     */
    public void onTabChange(TabChangeEvent event) {
        String tabId = event.getTab().getId();
        
        // Reset all UI flags
        camposTramaModel.setShowRegisterFieldsUI(false);
        camposTramaModel.setShowViewFieldsUI(false);
        camposTramaModel.setShowModifyFieldsUI(false);
        camposTramaModel.setCamposPendientesDeRegistro(new ArrayList<>());
        camposTramaModel.setCampoTramaEdicion(new CamposTrama());
        camposTramaModel.setModificacionCampoTrama(false);
        camposTramaModel.setUploadedCopybookFile(null);
        camposTramaModel.setOperacionSeleccionadaDetalle(null);
        camposTramaModel.setListaCamposTrama(new ArrayList<>()); // Clear displayed table

        // Set flags based on the activated tab
        if ("registerTab".equals(tabId)) {
            activeRegisterStep = 0; // Reset steps for registration
            // camposTramaModel.setShowRegisterFieldsUI(true); // This is handled by the steps panel rendering
        } else if ("viewTab".equals(tabId)) {
            camposTramaModel.setShowViewFieldsUI(true);
            // No need to call cargarCamposParaVer here, it will be called by the "Buscar Campos" button
        } else if ("editTab".equals(tabId)) {
            camposTramaModel.setShowModifyFieldsUI(true);
            // No need to call cargarCamposParaModificar here, it will be called by the "Buscar Campos" button
        }
        // Reset selected filters for all tabs for a clean start
        camposTramaModel.setSelectedIdOperacion(null);
        camposTramaModel.setSelectedIdEntidad(null);
        camposTramaModel.setSelectedTipoOrigen(null);
    }
}
