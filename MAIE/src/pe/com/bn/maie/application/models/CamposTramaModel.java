package pe.com.bn.maie.application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import pe.com.bn.maie.persistencia.dto.CamposTrama;
import pe.com.bn.maie.persistencia.dto.Operacion;
import pe.com.bn.maie.persistencia.dto.Entidad;
import org.primefaces.model.UploadedFile; // Para la carga de archivos

@Data
public class CamposTramaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // Listas principales para la tabla
    private List<CamposTrama> listaCamposTrama;
    private List<CamposTrama> camposTramaSeleccionados;
    private CamposTrama campoTramaEdicion; // Para el modal de edición/creación manual

    // Estado de la UI para controlar la visibilidad de los paneles/modales
    private boolean showSelectOperacionModal; // Modal inicial para seleccionar operación
    private boolean showRegisterFieldsUI;     // UI para registrar campos (manual/copybook)
    private boolean showViewFieldsUI;         // UI para ver campos
    private boolean showModifyFieldsUI;       // UI para modificar campos
    private boolean showManualEntryModal;     // Modal para entrada manual de un solo campo
    private boolean modificacionCampoTrama;   // Indica si se está modificando un campo existente

    // Datos para la selección de Operación y Entidad
    private List<Operacion> listaOperaciones;
    private List<Entidad> listaEntidades; // Para el dropdown de entidades si es necesario
    private Long selectedIdOperacion;
    private String selectedIdEntidad;
    private Integer selectedTipoOrigen; // 1: Request, 2: Response

    // Detalles de la operación seleccionada (para mostrar en la cabecera)
    private Operacion operacionSeleccionadaDetalle;

    // Opciones para el registro de campos
    private String registroMode; // "MANUAL" o "COPYBOOK"
    private UploadedFile uploadedCopybookFile; // Archivo subido para copybook

    // Lista temporal para campos a registrar (manual o desde copybook) antes de la confirmación final
    private List<CamposTrama> camposPendientesDeRegistro;

    public CamposTramaModel() {
        this.listaCamposTrama = new ArrayList<>();
        this.camposTramaSeleccionados = new ArrayList<>();
        this.campoTramaEdicion = new CamposTrama();
        this.camposPendientesDeRegistro = new ArrayList<>();

        // Inicializar estados de UI
        this.showSelectOperacionModal = false;
        this.showRegisterFieldsUI = false;
        this.showViewFieldsUI = false;
        this.showModifyFieldsUI = false;
        this.showManualEntryModal = false;
        this.modificacionCampoTrama = false;
        this.registroMode = "MANUAL"; // Valor por defecto
    }
}
