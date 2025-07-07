package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.Conexion;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudConexion {

    /**
     * Obtiene todas las conexiones.
     *
     * @return Lista de objetos Conexion.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<Conexion> obtenerConexiones() throws PersistenceException;

    /**
     * Busca una conexión por su ID.
     *
     * @param idConexion ID de la conexión a buscar.
     * @return Objeto Conexion si se encuentra, o null si no.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    Conexion buscarConexionPorId(Long idConexion) throws PersistenceException;

    /**
     * Actualiza una conexión existente.
     *
     * @param conexion Objeto Conexion con los datos a actualizar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void actualizarConexion(Conexion conexion) throws PersistenceException;

    /**
     * Inserta una nueva conexión.
     *
     * @param conexion Objeto Conexion con los datos a insertar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void insertarConexion(Conexion conexion) throws PersistenceException;
}
