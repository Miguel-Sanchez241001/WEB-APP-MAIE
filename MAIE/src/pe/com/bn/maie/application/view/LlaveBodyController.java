package pe.com.bn.maie.application.view;

import java.io.IOException;
import java.io.Serializable;
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
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

import lombok.Data;
import pe.com.bn.maie.application.models.LlaveBodyModel;
import pe.com.bn.maie.infraestructura.services.internal.CrudEntidades;
import pe.com.bn.maie.infraestructura.services.internal.CrudLlaveBody;
import pe.com.bn.maie.infraestructura.services.internal.CrudOperaciones;
import pe.com.bn.maie.persistencia.dto.Entidad;
import pe.com.bn.maie.persistencia.dto.LlaveBody;
import pe.com.bn.maie.persistencia.dto.Operacion;
import pe.com.bn.maie.tranversal.util.JSFUtils;
import pe.com.bn.maie.tranversal.util.JsonToLlaveBodyConverter; // New import for JSON utility
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@ManagedBean
@ViewScoped
@Data
public class LlaveBodyController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(LlaveBodyController.class);

    @ManagedProperty("#{crudLlaveBody}")
    private CrudLlaveBody crudLlaveBody;

    @ManagedProperty("#{crudOperaciones}")
    private CrudOperaciones crudOperaciones;

    @ManagedProperty("#{crudEntidades}")
    private CrudEntidades crudEntidades;

    private LlaveBodyModel llaveBodyModel;
    
    private int activeRegisterStep;

    @PostConstruct
    public void init() {
        llaveBodyModel = new LlaveBodyModel();
        cargarListasMaestras();
        cargarTodasLasLlavesBody();
        activeRegisterStep = 0; 
    }

    private void cargarListasMaestras() {
        try {
            llaveBodyModel.setListaOperaciones(crudOperaciones.obtenerOperaciones());
            llaveBodyModel.setListaEntidades(crudEntidades.obtenerEntidades());
        } catch (PersistenceException e) {
            logger.error("Error al cargar listas maestras (operaciones/entidades)", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las listas de operaciones o entidades.");
        }
    }

    private void cargarTodasLasLlavesBody() {
        try {
            llaveBodyModel.setListaLlavesBody(crudLlaveBody.obtenerLlavesBody());
        } catch (PersistenceException e) {
            logger.error("Error al cargar todas las llaves body", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar todas las llaves body.");
        }
    }

    /**
     * Prepares the UI for LlaveBody registration after selecting operation, entity, and type.
     */
    public void seleccionarOperacionYTipoOrigenParaRegistrar() {
        if (llaveBodyModel.getSelectedIdOperacion() == null ||
            llaveBodyModel.getSelectedIdEntidad() == null ||
            llaveBodyModel.getSelectedTipoOrigen() == null) {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una Operación, Entidad y Tipo de Origen.");
            return;
        }

        try {
            Operacion op = crudOperaciones.buscarOperacion(
                llaveBodyModel.getSelectedIdOperacion(),
                llaveBodyModel.getSelectedIdEntidad(),
                "0001" // Assuming a default code, adjust if needed
            );
            if (op == null) {
                JSFUtils.mensajeError("Error", "No se encontró la operación seleccionada.");
                return;
            }
            llaveBodyModel.setOperacionSeleccionadaDetalle(op);

            llaveBodyModel.setShowRegisterLlaveBodyUI(true);
            llaveBodyModel.setLlavesPendientesDeRegistro(new ArrayList<>());
            activeRegisterStep = 1; // Move to the second step (Register fields)
            
        } catch (PersistenceException e) {
            logger.error("Error al seleccionar operación para registrar LlaveBody", e);
            JSFUtils.mensajeError("Error", "No se pudo procesar la selección: " + e.getMessage());
        }
    }

    /**
     * Resets the UI and returns to the main LlaveBody screen.
     */
    public void volverAPrincipal() {
        llaveBodyModel.setShowRegisterLlaveBodyUI(false);
        llaveBodyModel.setShowViewLlaveBodyUI(false);
        llaveBodyModel.setShowModifyLlaveBodyUI(false);
        llaveBodyModel.setLlavesPendientesDeRegistro(new ArrayList<>());
        llaveBodyModel.setLlaveBodyEdicion(new LlaveBody());
        llaveBodyModel.setModificacionLlaveBody(false);
        llaveBodyModel.setUploadedJsonFile(null);
        llaveBodyModel.setOperacionSeleccionadaDetalle(null);
        activeRegisterStep = 0;
        cargarTodasLasLlavesBody();
    }

    // --- Logic for "Register LlaveBody" ---

    public void prepararNuevaLlaveBodyManual() {
        llaveBodyModel.setLlaveBodyEdicion(new LlaveBody());
        llaveBodyModel.setModificacionLlaveBody(false);
    }

    public void agregarLlaveBodyManual() {
        LlaveBody nuevaLlave = llaveBodyModel.getLlaveBodyEdicion();
        if (nuevaLlave == null || nuevaLlave.getTagName() == null || nuevaLlave.getTagName().isEmpty()) {
            JSFUtils.mensajeAdvertencia("Advertencia", "El Tag Name es obligatorio.");
            return;
        }
        if (nuevaLlave.getTipoDatoEsperado() == null || nuevaLlave.getEsPadre() == null || nuevaLlave.getOrden() == null) {
             JSFUtils.mensajeAdvertencia("Advertencia", "Tipo de Dato Esperado, Es Padre y Orden son obligatorios.");
             return;
        }

        nuevaLlave.setIdOperacion(llaveBodyModel.getSelectedIdOperacion());
        nuevaLlave.setIdEntidad(llaveBodyModel.getSelectedIdEntidad());
        nuevaLlave.setTipoOrigen(llaveBodyModel.getSelectedTipoOrigen());
        nuevaLlave.setUsuarioCreacion("SYSTEM");

        llaveBodyModel.getLlavesPendientesDeRegistro().add(nuevaLlave);
        int order = 1;
        for (LlaveBody lb : llaveBodyModel.getLlavesPendientesDeRegistro()) {
            lb.setOrden(order++);
        }

        JSFUtils.mensajeInformativo("Llave Body Agregada", "Llave '" + nuevaLlave.getTagName() + "' añadida a la lista pendiente.");
        JSFUtils.ejecutarJs("PF('dlgManualEntryLlaveBody').hide();");
        llaveBodyModel.setLlaveBodyEdicion(new LlaveBody());
    }

    public void handleJsonUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null) {
            llaveBodyModel.setUploadedJsonFile(file);
            try {
                // Use the new utility to convert JSON to LlaveBody list
                List<LlaveBody> parsedLlaves = JsonToLlaveBodyConverter.convertJsonToLlaveBodyList(file.getInputstream());
                
                // Assign operation, entity, type, and creation user to each parsed LlaveBody
                int order = 1;
                for (LlaveBody lb : parsedLlaves) {
                    lb.setIdOperacion(llaveBodyModel.getSelectedIdOperacion());
                    lb.setIdEntidad(llaveBodyModel.getSelectedIdEntidad());
                    lb.setTipoOrigen(llaveBodyModel.getSelectedTipoOrigen());
                    lb.setOrden(order++); // Re-assign sequential order based on parsed order
                    lb.setUsuarioCreacion("SYSTEM");
                    
                    // Ensure non-nullable fields have default values if not set by converter
                    if (lb.getEsPadre() == null) lb.setEsPadre(0);
                    if (lb.getIndObligatorio() == null) lb.setIndObligatorio(0);
                    if (lb.getIndMapeable() == null) lb.setIndMapeable(0);
                    if (lb.getTipoDatoEsperado() == null) lb.setTipoDatoEsperado(1); // Default to String
                    // idLlavePadre will remain null unless explicitly handled by a more complex JSON structure mapping
                }
                llaveBodyModel.setLlavesPendientesDeRegistro(parsedLlaves);
                JSFUtils.mensajeInformativo("Archivo Cargado", "JSON '" + file.getFileName() + "' procesado. " + parsedLlaves.size() + " llaves encontradas.");
            } catch (IOException e) {
                logger.error("Error al procesar el archivo JSON", e);
                JSFUtils.mensajeError("Error de Carga", "No se pudo procesar el archivo JSON: " + e.getMessage());
            }
        }
    }

    public void showConfirmSaveDialog() {
        if (llaveBodyModel.getLlavesPendientesDeRegistro().isEmpty()) {
            JSFUtils.mensajeAdvertencia("Advertencia", "No hay llaves body para registrar.");
            return;
        }
        JSFUtils.ejecutarJs("PF('dlgConfirmSaveLlaveBody').show();");
    }

    public void confirmarRegistroLlavesBody() {
        try {
            for (LlaveBody llave : llaveBodyModel.getLlavesPendientesDeRegistro()) {
                crudLlaveBody.insertarLlaveBody(llave);
            }
            JSFUtils.mensajeInformativo("Registro Exitoso", "Todas las llaves body han sido registradas correctamente.");
            volverAPrincipal();
            JSFUtils.ejecutarJs("PF('dlgConfirmSaveLlaveBody').hide();");
        } catch (PersistenceException e) {
            logger.error("Error al registrar llaves body", e);
            JSFUtils.mensajeError("Error de Registro", "No se pudieron registrar las llaves body: " + e.getMessage());
        }
    }

    // --- Logic for "View LlaveBody" ---

    public void cargarLlavesBodyParaVer() {
        llaveBodyModel.setLlaveBodyEdicion(new LlaveBody());
        llaveBodyModel.setModificacionLlaveBody(false);

        if (llaveBodyModel.getSelectedIdOperacion() == null ||
            llaveBodyModel.getSelectedIdEntidad() == null ||
            llaveBodyModel.getSelectedTipoOrigen() == null) {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar una Operación, Entidad y Tipo de Origen para buscar.");
            llaveBodyModel.setListaLlavesBody(new ArrayList<>());
            return;
        }

        try {
            List<LlaveBody> llaves = crudLlaveBody.buscarLlavesPorOperacion(
                llaveBodyModel.getSelectedIdOperacion(),
                llaveBodyModel.getSelectedIdEntidad(),
                llaveBodyModel.getSelectedTipoOrigen(),
                null // Assuming we want top-level keys for now, adjust if nested search is needed
            );
            llaveBodyModel.setListaLlavesBody(llaves);
            if (llaves.isEmpty()) {
                JSFUtils.mensajeInformativo("Información", "No se encontraron llaves body para los criterios seleccionados.");
            }
        } catch (PersistenceException e) {
            logger.error("Error al cargar llaves body para ver", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar las llaves body para visualización: " + e.getMessage());
        }
    }

    // --- Logic for "Modify LlaveBody" ---

    public void cargarLlavesBodyParaModificar() {
        // Similar to cargarLlavesBodyParaVer, but might have different UI implications
        // For now, it will use the same search logic
        cargarLlavesBodyParaVer(); // Reuse logic
    }

    public void editarLlaveBody(LlaveBody llave) {
        llaveBodyModel.setLlaveBodyEdicion(llave);
        llaveBodyModel.setModificacionLlaveBody(true);
        JSFUtils.ejecutarJs("PF('dlgManualEntryLlaveBody').show();");
    }

    public void guardarLlaveBodyModificado() {
        try {
            LlaveBody llave = llaveBodyModel.getLlaveBodyEdicion();
            if (llave == null || llave.getIdLlaveBody() == null) {
                JSFUtils.mensajeAdvertencia("Advertencia", "No hay llave body seleccionada para modificar.");
                return;
            }
            if (llave.getTagName() == null || llave.getTagName().isEmpty()) {
                JSFUtils.mensajeAdvertencia("Advertencia", "El Tag Name es obligatorio.");
                return;
            }
            if (llave.getTipoDatoEsperado() == null || llave.getEsPadre() == null || llave.getOrden() == null) {
                 JSFUtils.mensajeAdvertencia("Advertencia", "Tipo de Dato Esperado, Es Padre y Orden son obligatorios.");
                 return;
            }

            llave.setUsuarioModificacion("SYSTEM");
            llave.setFechaModificacion(new Date());

            crudLlaveBody.actualizarLlaveBody(llave);
            JSFUtils.mensajeInformativo("Modificación Exitosa", "Llave Body actualizada correctamente.");
            JSFUtils.ejecutarJs("PF('dlgManualEntryLlaveBody').hide();");
            
            // Reload the table based on which tab is active
            if (llaveBodyModel.isShowViewLlaveBodyUI()) {
                cargarLlavesBodyParaVer();
            } else if (llaveBodyModel.isShowModifyLlaveBodyUI()) {
                cargarLlavesBodyParaModificar();
            } else {
                cargarTodasLasLlavesBody();
            }
            
        } catch (PersistenceException e) {
            logger.error("Error al modificar llave body", e);
            JSFUtils.mensajeError("Error de Modificación", "No se pudo actualizar la llave body: " + e.getMessage());
        }
    }

    /**
     * Handles the action for adding a new LlaveBody or saving modifications to an existing LlaveBody.
     * This method is called from the dialog's command button.
     */
    public void handleSaveOrAddLlaveBody() {
        if (llaveBodyModel.isModificacionLlaveBody()) {
            guardarLlaveBodyModificado();
        } else {
            agregarLlaveBodyManual();
        }
    }

    // --- Methods for SelectItems ---
    public List<javax.faces.model.SelectItem> getOperacionesAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione una Operación", "", true));
        if (llaveBodyModel.getListaOperaciones() != null) {
            for (Operacion op : llaveBodyModel.getListaOperaciones()) {
                items.add(new javax.faces.model.SelectItem(op.getIdOperacion(), op.getDescripcion() + " (Cod: " + op.getCodigoOperacion() + ")"));
            }
        }
        return items;
    }

    public List<javax.faces.model.SelectItem> getEntidadesAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione una Entidad", "", true));
        if (llaveBodyModel.getListaEntidades() != null) {
            for (Entidad ent : llaveBodyModel.getListaEntidades()) {
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

    public List<javax.faces.model.SelectItem> getTiposDatoEsperadoAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione Tipo de Dato", "", true));
        items.add(new javax.faces.model.SelectItem(1, "String"));
        items.add(new javax.faces.model.SelectItem(2, "Integer"));
        items.add(new javax.faces.model.SelectItem(3, "Decimal"));
        items.add(new javax.faces.model.SelectItem(4, "Boolean"));
        items.add(new javax.faces.model.SelectItem(5, "Date")); // Note: Date is typically parsed from String
        items.add(new javax.faces.model.SelectItem(6, "Object/Array")); // For complex types
        return items;
    }

    public List<javax.faces.model.SelectItem> getEsPadreAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione", "", true));
        items.add(new javax.faces.model.SelectItem(0, "No (Hijo)"));
        items.add(new javax.faces.model.SelectItem(1, "Sí (Padre)"));
        return items;
    }

    public List<javax.faces.model.SelectItem> getIndObligatorioAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione", "", true));
        items.add(new javax.faces.model.SelectItem(0, "No"));
        items.add(new javax.faces.model.SelectItem(1, "Sí"));
        return items;
    }

    public List<javax.faces.model.SelectItem> getIndMapeableAsSelectItems() {
        List<javax.faces.model.SelectItem> items = new ArrayList<>();
        items.add(new javax.faces.model.SelectItem(null, "Seleccione", "", true));
        items.add(new javax.faces.model.SelectItem(0, "No"));
        items.add(new javax.faces.model.SelectItem(1, "Sí"));
        return items;
    }

    /**
     * Listener for tab changes in the p:tabView.
     * Used to reset UI state or load data specific to the activated tab.
     * @param event The TabChangeEvent.
     */
    public void onTabChange(TabChangeEvent event) {
        String tabId = event.getTab().getId();
        
        // Reset all UI flags
        llaveBodyModel.setShowRegisterLlaveBodyUI(false);
        llaveBodyModel.setShowViewLlaveBodyUI(false);
        llaveBodyModel.setShowModifyLlaveBodyUI(false);
        llaveBodyModel.setLlavesPendientesDeRegistro(new ArrayList<>());
        llaveBodyModel.setLlaveBodyEdicion(new LlaveBody());
        llaveBodyModel.setModificacionLlaveBody(false);
        llaveBodyModel.setUploadedJsonFile(null);
        llaveBodyModel.setOperacionSeleccionadaDetalle(null);
        llaveBodyModel.setListaLlavesBody(new ArrayList<>()); // Clear displayed table

        // Set flags based on the activated tab
        if ("registerLlaveBodyTab".equals(tabId)) {
            activeRegisterStep = 0; // Reset steps for registration
        } else if ("viewLlaveBodyTab".equals(tabId)) {
            llaveBodyModel.setShowViewLlaveBodyUI(true);
        } else if ("editLlaveBodyTab".equals(tabId)) {
            llaveBodyModel.setShowModifyLlaveBodyUI(true);
        }
        // Reset selected filters for all tabs for a clean start
        llaveBodyModel.setSelectedIdOperacion(null);
        llaveBodyModel.setSelectedIdEntidad(null);
        llaveBodyModel.setSelectedTipoOrigen(null);
    }
}
