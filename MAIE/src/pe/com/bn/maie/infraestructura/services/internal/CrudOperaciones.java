package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.Operacion;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudOperaciones {

    /**
     * Obtiene todas las operaciones.
     *
     * @return Lista de objetos Operacion.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<Operacion> obtenerOperaciones() throws PersistenceException;

    /**
     * Busca una operación por su ID de Operación, ID de Entidad y Código de Operación.
     *
     * @param idOperacion ID de la operación a buscar.
     * @param idEntidad ID de la entidad a buscar.
     * @param codigoOperacion Código de la operación a buscar.
     * @return Objeto Operacion si se encuentra, o null si no.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    Operacion buscarOperacion(Long idOperacion, String idEntidad, String codigoOperacion) throws PersistenceException;

    /**
     * Actualiza una operación existente.
     *
     * @param operacion Objeto Operacion con los datos a actualizar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void actualizarOperacion(Operacion operacion) throws PersistenceException;

    /**
     * Inserta una nueva operación.
     *
     * @param operacion Objeto Operacion con los datos a insertar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void insertarOperacion(Operacion operacion) throws PersistenceException;

    /**
     * Inserta una operación y, si requiere body de envío, un parámetro asociado.
     *
     * @param operacionEdicion Objeto Operacion con los datos a insertar.
     * @param selectedPathVariable Valor para el parámetro de path variable.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void insertOperacionTrans(Operacion operacionEdicion, String selectedPathVariable) throws PersistenceException;
}
