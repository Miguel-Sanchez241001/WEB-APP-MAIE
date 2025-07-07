package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.LlaveBody;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudLlaveBody {

    /**
     * Obtiene todas las llaves de cuerpo.
     *
     * @return Lista de objetos LlaveBody.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<LlaveBody> obtenerLlavesBody() throws PersistenceException;

    /**
     * Busca una llave de cuerpo por su ID.
     *
     * @param idLlaveBody ID de la llave de cuerpo a buscar.
     * @return Objeto LlaveBody si se encuentra, o null si no.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    LlaveBody buscarLlaveBodyPorId(Long idLlaveBody) throws PersistenceException;

    /**
     * Actualiza una llave de cuerpo existente.
     *
     * @param llaveBody Objeto LlaveBody con los datos a actualizar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void actualizarLlaveBody(LlaveBody llaveBody) throws PersistenceException;

    /**
     * Inserta una nueva llave de cuerpo.
     *
     * @param llaveBody Objeto LlaveBody con los datos a insertar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void insertarLlaveBody(LlaveBody llaveBody) throws PersistenceException;

    /**
     * Busca llaves de cuerpo por ID de operación, ID de entidad,
     * opcionalmente por tipo de origen y llave padre.
     *
     * @param idOperacion ID de la operación.
     * @param idEntidad ID de la entidad.
     * @param tipoOrigen Tipo de origen (1=Request, 2=Response). Puede ser null para no filtrar.
     * @param idLlavePadre ID de la llave padre. Puede ser null para llaves raíz.
     * @return Lista de objetos LlaveBody.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<LlaveBody> buscarLlavesPorOperacion(Long idOperacion, String idEntidad, Integer tipoOrigen, Long idLlavePadre) throws PersistenceException;
}
