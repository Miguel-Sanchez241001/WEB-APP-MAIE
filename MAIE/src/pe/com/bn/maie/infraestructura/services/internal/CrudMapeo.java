package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.Mapeo;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudMapeo {

    /**
     * Obtiene todos los mapeos.
     *
     * @return Lista de objetos Mapeo.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<Mapeo> obtenerMapeos() throws PersistenceException;

    /**
     * Busca un mapeo por su ID.
     *
     * @param idMapeo ID del mapeo a buscar.
     * @return Objeto Mapeo si se encuentra, o null si no.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    Mapeo buscarMapeoPorId(Long idMapeo) throws PersistenceException;

    /**
     * Actualiza un mapeo existente.
     *
     * @param mapeo Objeto Mapeo con los datos a actualizar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void actualizarMapeo(Mapeo mapeo) throws PersistenceException;

    /**
     * Inserta un nuevo mapeo.
     *
     * @param mapeo Objeto Mapeo con los datos a insertar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void insertarMapeo(Mapeo mapeo) throws PersistenceException;

    /**
     * Busca mapeos asociados a una operación y entidad específicas.
     *
     * @param idOperacion ID de la operación.
     * @param idEntidad ID de la entidad.
     * @return Lista de objetos Mapeo.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<Mapeo> buscarMapeosPorOperacion(Long idOperacion, String idEntidad) throws PersistenceException;
}
