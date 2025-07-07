package pe.com.bn.maie.tranversal.util;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

public final class JSFUtils {

    private JSFUtils() {
    }

    // =============================
    //   Mensajes JSF
    // =============================

    public static void mensajeInformativo(String resumen) {
        mensajeInformativo(resumen, null);
    }

    public static void mensajeInformativo(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, detalle));
    }

    public static void mensajeError(String resumen) {
        mensajeError(resumen, null);
    }

    public static void mensajeError(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, detalle));
    }

    public static void mensajeAdvertencia(String resumen) {
        mensajeAdvertencia(resumen, null);
    }

    public static void mensajeAdvertencia(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, detalle));
    }

    public static void mensajeFatal(String resumen) {
        mensajeFatal(resumen, null);
    }

    public static void mensajeFatal(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, resumen, detalle));
    }

    // =============================
    // 🔁 Redirecciones
    // =============================

    public static String redireccionar(String pagina) {
        return pagina + "?faces-redirect=true";
    }

    public static void redirigir(String pagina) {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/" + pagina + "?faces-redirect=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =============================
    // 🚀 PrimeFaces JS Execution
    // =============================

    /**
     * Ejecuta código JavaScript desde el backend.
     * Ejemplo: JSFUtils.ejecutarJs("PF('dlgEntidad').show();");
     */
    public static void ejecutarJs(String script) {
        PrimeFaces.current().executeScript(script);
    }

    /**
     * Actualiza componentes desde el backend.
     * Ejemplo: JSFUtils.actualizar("form:tabla");
     */
    public static void actualizar(String idComponente) {
        PrimeFaces.current().ajax().update(idComponente);
    }

    // =============================
    // ⚙️ Utilitarios de contexto
    // =============================

    public static ExternalContext externalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static FacesContext facesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Map<String, String> getParametrosRequest() {
        return externalContext().getRequestParameterMap();
    }

    public static String getParametro(String nombre) {
        return externalContext().getRequestParameterMap().get(nombre);
    }

    public static String getUsuarioSesion() {
        // Aquí debes obtener el usuario real desde el contexto de seguridad
        return "TEST"; // Puedes adaptar esto si tienes login implementado
    }

    public static Object getAtributoSesion(String nombre) {
        return externalContext().getSessionMap().get(nombre);
    }

    public static void setAtributoSesion(String nombre, Object valor) {
        externalContext().getSessionMap().put(nombre, valor);
    }

    public static void eliminarAtributoSesion(String nombre) {
        externalContext().getSessionMap().remove(nombre);
    }
}
