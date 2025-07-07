package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.Parametro;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudParametro {
    
    /**
     * Obtiene todos los parámetros
     * 
     * @return Lista de parámetros
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public List<Parametro> obtenerParametros() throws PersistenceException;
    
    /**
     * Busca un parámetro por su código de grupo y código de parámetro
     * 
     * @param codigoGrupo Código del grupo
     * @param codigoParametro Código del parámetro
     * @return Parámetro encontrado
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public Parametro buscarParametro(String codigoGrupo, String codigoParametro) throws PersistenceException;
    
    /**
     * Inserta un nuevo parámetro
     * 
     * @param parametro Parámetro a insertar
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public void insertarParametro(Parametro parametro) throws PersistenceException;
    
    /**
     * Actualiza un parámetro relacionado a una operación
     * 
     * @param parametro Parámetro con los datos actualizados
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public void actualizarParametroConOperacion(Parametro parametro) throws PersistenceException;
    
    /**
     * Actualiza un parámetro general (sin relación a operación)
     * 
     * @param parametro Parámetro con los datos actualizados
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public void actualizarParametroGeneral(Parametro parametro) throws PersistenceException;
    
    /**
     * Crea un nuevo grupo de parámetros
     * 
     * @param codigoGrupo Código del nuevo grupo
     * @param descripcion Descripción del grupo
     * @param usuarioCreacion Usuario que crea el grupo
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public void crearGrupo(String codigoGrupo, String descripcion, String usuarioCreacion) throws PersistenceException;
    
    /**
     * Obtiene todos los grupos de parámetros
     * 
     * @return Lista de parámetros que representan grupos (código de parámetro = "0")
     * @throws PersistenceException si ocurre un error en la capa de persistencia
     */
    public List<Parametro> obtenerGrupos() throws PersistenceException;
    
    
    public List<Parametro> obtenerParametrosGrupos(String codGrupo) throws PersistenceException;

	public Integer obtenerMaximoParamDeGrupo(Integer integer, Long long1, String codGrup) throws PersistenceException;

	String obtenerMaximoParamDeGrupo(String codGrup) throws PersistenceException;

    
}