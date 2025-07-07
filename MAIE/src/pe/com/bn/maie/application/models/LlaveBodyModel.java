package pe.com.bn.maie.application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import pe.com.bn.maie.persistencia.dto.LlaveBody;
import pe.com.bn.maie.persistencia.dto.Operacion;
import pe.com.bn.maie.persistencia.dto.Entidad;
import org.primefaces.model.UploadedFile; // For file uploads

@Data
public class LlaveBodyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // Main lists for the table display
    private List<LlaveBody> listaLlavesBody;
    private List<LlaveBody> llavesBodySeleccionadas;
    private LlaveBody llaveBodyEdicion; // For manual creation/editing modal

    // UI state to control panel/modal visibility
    private boolean showRegisterLlaveBodyUI; // UI for registering LlaveBody (manual/JSON)
    private boolean showViewLlaveBodyUI;     // UI for viewing LlaveBody
    private boolean showModifyLlaveBodyUI;   // UI for modifying LlaveBody

    // Data for Operation and Entity selection
    private List<Operacion> listaOperaciones;
    private List<Entidad> listaEntidades;
    private Long selectedIdOperacion;
    private String selectedIdEntidad;
    private Integer selectedTipoOrigen; // 1: Request, 2: Response

    // Details of the selected operation (to display in the header)
    private Operacion operacionSeleccionadaDetalle;

    // Options for field registration
    private String registroMode; // "MANUAL" or "JSON"
    private UploadedFile uploadedJsonFile; // Uploaded file for JSON

    // Temporary list for fields to be registered (manual or from JSON) before final confirmation
    private List<LlaveBody> llavesPendientesDeRegistro;

    // Flag to indicate if an existing LlaveBody is being modified
    private boolean modificacionLlaveBody;

    public LlaveBodyModel() {
        this.listaLlavesBody = new ArrayList<>();
        this.llavesBodySeleccionadas = new ArrayList<>();
        this.llaveBodyEdicion = new LlaveBody();
        this.llavesPendientesDeRegistro = new ArrayList<>();

        // Initialize UI states
        this.showRegisterLlaveBodyUI = false;
        this.showViewLlaveBodyUI = false;
        this.showModifyLlaveBodyUI = false;
        this.registroMode = "MANUAL"; // Default value
        this.modificacionLlaveBody = false;
    }
}
