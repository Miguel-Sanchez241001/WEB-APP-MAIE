package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.Entidad;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudEntidades {
    
    /**
     * Obtiene todas las entidades activas
     * 
     * @return Lista de entidades
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public List<Entidad> obtenerEntidades() throws PersistenceException;
    
    /**
     * Busca una entidad por su ID
     * 
     * @param id ID de la entidad
     * @return Descripci�n de la entidad
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public Entidad buscarEntidadPorId(String id) throws PersistenceException;
    
    /**
     * Busca una entidad por su ID
     * 
     * @param id ID de la entidad
     * @return Descripci�n de la entidad
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public Entidad buscarEntidadPorId(Long id) throws PersistenceException;
    
    
    
    /**
     * Actualiza una entidad
     * 
     * @param id ID de la entidad
     * @param descripcion Nueva descripci�n
     * @param usuarioMod Usuario que realiza la modificaci�n
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public void actualizarEntidad(Entidad entidad) throws PersistenceException;
    
    
    /**
     * Inserta una nueva entidad
     * 
     * @param entidad Entidad a insertar
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public void insertarEntidad(Entidad entidad) throws PersistenceException;
    
   
}
