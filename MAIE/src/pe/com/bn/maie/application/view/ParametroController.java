package pe.com.bn.maie.application.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Data;
import pe.com.bn.maie.application.models.ParametroModel;
import pe.com.bn.maie.infraestructura.services.internal.CrudParametro;
import pe.com.bn.maie.persistencia.dto.Parametro;
import pe.com.bn.maie.tranversal.util.JSFUtils;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@ManagedBean
@ViewScoped
@Data
public class ParametroController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ParametroController.class);
    
    private ParametroModel parametroModel;
    
    @ManagedProperty("#{crudParametro}")
    private CrudParametro crudParametro;
    
    @PostConstruct
    public void init() {
        parametroModel = new ParametroModel();
        parametroModel.setParametroEdicion(new Parametro());
        parametroModel.setModificacionParametro(false);
        parametroModel.setParametroConOperacion(false);
        cargarParametros();
        cargarGrupos();
    }
    
    public void cargarParametros() {
        try {
            parametroModel.setListaParametros(crudParametro.obtenerParametros());
        } catch (PersistenceException e) {
            logger.error("Error al cargar parámetros", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar los parámetros");
        }
    }
    
    public void cargarGrupos() {
        try {
            parametroModel.setListaGrupos(crudParametro.obtenerGrupos());
        } catch (PersistenceException e) {
            logger.error("Error al cargar grupos", e);
            JSFUtils.mensajeError("Error", "No se pudieron cargar los grupos de parámetros");
        }
    }
    
    public void registrarParametro() {
        Parametro parametro = parametroModel.getParametroEdicion();
        
        // Obtener usuario actual (ejemplo)
        String usuarioActual = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        if (usuarioActual == null) {
            usuarioActual = "SYSTEM"; // Usuario por defecto
        }
        
        if (parametroModel.isModificacionParametro()) {
            try {
                parametro.setFechaModificacion(new Date());
                parametro.setUsuarioModificacion(usuarioActual);
                
                if (parametroModel.isParametroConOperacion()) {
                    crudParametro.actualizarParametroConOperacion(parametro);
                } else {
                    crudParametro.actualizarParametroGeneral(parametro);
                }
                JSFUtils.mensajeInformativo("Modificación exitosa", "Parámetro actualizado correctamente");
                cargarParametros();
            } catch (PersistenceException e) {
                JSFUtils.mensajeError("Error al modificar", e.getMessage());
            }
        } else {
            try {
                parametro.setFechaCreacion(new Date());
                parametro.setUsuarioCreacion(usuarioActual);
                parametro.setIndicadorActivo(1); // Activo por defecto
                
                crudParametro.insertarParametro(parametro);
                JSFUtils.mensajeInformativo("Registro exitoso", "Parámetro registrado correctamente");
                cargarParametros();
            } catch (PersistenceException e) {
                JSFUtils.mensajeError("Error al registrar", e.getMessage());
            }
        }
        
        limpiarFormulario();
    }
    
    public void limpiarFormulario() {
        parametroModel.setParametroEdicion(new Parametro());
        parametroModel.setModificacionParametro(false);
        parametroModel.setParametroConOperacion(false);
    }
    
    public void cargarParametroSeleccionado() {
        List<Parametro> seleccionados = parametroModel.getParametrosSeleccionados();
        if (seleccionados != null && seleccionados.size() == 1) {
            Parametro seleccionado = seleccionados.get(0);
            
            // Clonar el objeto para evitar modificaciones directas
            Parametro clonado = new Parametro();
            clonado.setIdParametro(seleccionado.getIdParametro());
            clonado.setIndicadorActivo(seleccionado.getIndicadorActivo());
            clonado.setCodigoGrupo(seleccionado.getCodigoGrupo());
            clonado.setCodigoParametro(seleccionado.getCodigoParametro());
            clonado.setDescripcion(seleccionado.getDescripcion());
            clonado.setFechaCreacion(seleccionado.getFechaCreacion());
            clonado.setUsuarioCreacion(seleccionado.getUsuarioCreacion());
            clonado.setValor1(seleccionado.getValor1());
            clonado.setValor2(seleccionado.getValor2());
            clonado.setCodigoOperacion(seleccionado.getCodigoOperacion());
            
            parametroModel.setParametroEdicion(clonado);
            parametroModel.setModificacionParametro(true);
            parametroModel.setParametroConOperacion(seleccionado.getCodigoOperacion() != null);
            
            JSFUtils.ejecutarJs("PF('dlgParametro').show();");
        } else {
            JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar un parámetro para modificar");
        }
    }
    
    public void cambiarEstadoParametros(boolean activar) {
        try {
            List<Parametro> seleccionados = parametroModel.getParametrosSeleccionados();
            if (seleccionados != null && !seleccionados.isEmpty()) {
                String usuarioActual = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
                if (usuarioActual == null) {
                    usuarioActual = "SYSTEM";
                }
                
                for (Parametro param : seleccionados) {
                    param.setIndicadorActivo(activar ? 1 : 0);
                    param.setFechaModificacion(new Date());
                    param.setUsuarioModificacion(usuarioActual);
                    
                    if (param.getCodigoOperacion() != null) {
                        crudParametro.actualizarParametroConOperacion(param);
                    } else {
                        crudParametro.actualizarParametroGeneral(param);
                    }
                }
                
                JSFUtils.mensajeInformativo("Estado actualizado", 
                                         "Los parámetros han sido " + (activar ? "activados" : "desactivados") + " correctamente");
                cargarParametros();
            } else {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe seleccionar al menos un parámetro");
            }
        } catch (PersistenceException e) {
            JSFUtils.mensajeError("Error al actualizar estado", e.getMessage());
        }
    }
    
    public void activarParametros() {
        cambiarEstadoParametros(true);
    }
    
    public void desactivarParametros() {
        cambiarEstadoParametros(false);
    }
    
    public void buscarParametro() {
        try {
            if (parametroModel.getFiltroCodigoGrupo() == null || parametroModel.getFiltroCodigoGrupo().isEmpty() ||
                parametroModel.getFiltroCodigoParametro() == null || parametroModel.getFiltroCodigoParametro().isEmpty()) {
                JSFUtils.mensajeAdvertencia("Advertencia", "Debe ingresar tanto el código de grupo como el código de parámetro");
                return;
            }
            
            Parametro parametro = crudParametro.buscarParametro(
                parametroModel.getFiltroCodigoGrupo(),
                parametroModel.getFiltroCodigoParametro()
            );
            
            if (parametro != null) {
                List<Parametro> resultado = java.util.Collections.singletonList(parametro);
                parametroModel.setListaParametros(resultado);
                JSFUtils.mensajeInformativo("Búsqueda realizada", "Parámetro encontrado");
            } else {
                JSFUtils.mensajeInformativo("Búsqueda realizada", "No se encontraron parámetros con los criterios especificados");
                cargarParametros();
            }
        } catch (PersistenceException e) {
            logger.error("Error al buscar parámetro", e);
            JSFUtils.mensajeError("Error", "No se pudo completar la búsqueda: " + e.getMessage());
        }
    }
    
    public void limpiarFiltros() {
        parametroModel.setFiltroCodigoGrupo("");
        parametroModel.setFiltroCodigoParametro("");
        cargarParametros();
    }
    
    public void prepararNuevoGrupo() {
        parametroModel.setNuevoCodigoGrupo("");
        parametroModel.setNuevaDescripcionGrupo("");
        JSFUtils.ejecutarJs("PF('dlgNuevoGrupo').show();");
    }
    
    public void crearNuevoGrupo() {
        try {
            String codigoGrupo = parametroModel.getNuevoCodigoGrupo();
            String descripcion = parametroModel.getNuevaDescripcionGrupo();
            
            if (codigoGrupo == null || codigoGrupo.isEmpty()) {
                JSFUtils.mensajeAdvertencia("Advertencia", "El código de grupo es obligatorio");
                return;
            }
            
            if (descripcion == null || descripcion.isEmpty()) {
                JSFUtils.mensajeAdvertencia("Advertencia", "La descripción del grupo es obligatoria");
                return;
            }
            
            // Obtener usuario actual
            String usuarioActual = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            if (usuarioActual == null) {
                usuarioActual = "SYSTEM"; // Usuario por defecto
            }
            
            crudParametro.crearGrupo(codigoGrupo, descripcion, usuarioActual);
            
            JSFUtils.mensajeInformativo("Grupo creado", "El grupo de parámetros se ha creado correctamente");
            cargarGrupos();
            JSFUtils.ejecutarJs("PF('dlgNuevoGrupo').hide();");
        } catch (PersistenceException e) {
            logger.error("Error al crear grupo", e);
            JSFUtils.mensajeError("Error", "No se pudo crear el grupo: " + e.getMessage());
        }
    }
}