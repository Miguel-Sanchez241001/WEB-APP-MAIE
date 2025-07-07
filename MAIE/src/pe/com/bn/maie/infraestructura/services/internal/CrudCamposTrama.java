package pe.com.bn.maie.infraestructura.services.internal;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.CamposTrama;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

public interface CrudCamposTrama {

    /**
     * Obtiene todos los campos de trama.
     *
     * @return Lista de objetos CamposTrama.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<CamposTrama> obtenerCamposTrama() throws PersistenceException;

    /**
     * Busca un campo de trama por su ID.
     *
     * @param idCampoTrama ID del campo de trama a buscar.
     * @return Objeto CamposTrama si se encuentra, o null si no.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    CamposTrama buscarCampoTramaPorId(Long idCampoTrama) throws PersistenceException;

    /**
     * Actualiza un campo de trama existente.
     *
     * @param camposTrama Objeto CamposTrama con los datos a actualizar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void actualizarCampoTrama(CamposTrama camposTrama) throws PersistenceException;

    /**
     * Inserta un nuevo campo de trama.
     *
     * @param camposTrama Objeto CamposTrama con los datos a insertar.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    void insertarCampoTrama(CamposTrama camposTrama) throws PersistenceException;

    /**
     * Busca campos de trama por ID de operación, ID de entidad y opcionalmente por tipo de origen.
     *
     * @param idOperacion ID de la operación.
     * @param idEntidad ID de la entidad.
     * @param tipoOrigen Tipo de origen (1=Request, 2=Response). Puede ser null para no filtrar.
     * @return Lista de objetos CamposTrama.
     * @throws PersistenceException si ocurre un error en la capa de persistencia.
     */
    List<CamposTrama> buscarCamposPorOperacion(Long idOperacion, String idEntidad, Integer tipoOrigen) throws PersistenceException;
}
