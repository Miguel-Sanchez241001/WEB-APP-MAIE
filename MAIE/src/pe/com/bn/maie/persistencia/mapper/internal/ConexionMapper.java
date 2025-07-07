package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConexionMapper {

    /**
     * Invoca al procedimiento SP_01_BUSCAR_CONEXION
     * Busca una conexión por su ID.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b07_id_con: ID de la conexión a buscar (IN)
     * - o_cursor: cursor con el resultado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarConexion(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_02_SELECCIONAR_CONEXIONES
     * Selecciona todas las conexiones.
     *
     * @param paramMap Mapa que debe contener:
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void seleccionarConexiones(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_03_ACTUALIZAR_CONEXION
     * Actualiza una conexión existente.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b07_id_con: ID de la conexión a actualizar (IN)
     * - p_b01_id_entidad: ID de la entidad asociada (IN)
     * - p_b07_protocolo: Protocolo de conexión (IN)
     * - p_b07_host: Host de la conexión (IN)
     * - p_b07_puerto: Puerto de la conexión (IN)
     * - p_b07_usa_tls: Indicador de uso de TLS (IN)
     * - p_b07_ind_credenciales: Indicador de credenciales (IN)
     * - p_b07_timeout_conexion_ms: Timeout de conexión en ms (IN)
     * - p_b07_timeout_lectura_ms: Timeout de lectura en ms (IN)
     * - p_b07_max_reintentos: Máximo de reintentos (IN)
     * - p_b07_reintento_espera_ms: Tiempo de espera entre reintentos en ms (IN)
     * - p_b07_usu_mod: Usuario de modificación (IN)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void actualizarConexion(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_04_INSERTAR_CONEXION
     * Inserta una nueva conexión.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b01_id_entidad: ID de la entidad asociada (IN)
     * - p_b07_protocolo: Protocolo de conexión (IN)
     * - p_b07_host: Host de la conexión (IN)
     * - p_b07_puerto: Puerto de la conexión (IN)
     * - p_b07_usa_tls: Indicador de uso de TLS (IN)
     * - p_b07_ind_credenciales: Indicador de credenciales (IN)
     * - p_b07_timeout_conexion_ms: Timeout de conexión en ms (IN)
     * - p_b07_timeout_lectura_ms: Timeout de lectura en ms (IN)
     * - p_b07_max_reintentos: Máximo de reintentos (IN)
     * - p_b07_reintento_espera_ms: Tiempo de espera entre reintentos en ms (IN)
     * - p_b07_usu_cre: Usuario de creación (IN)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void insertarConexion(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_05_BUSCAR_CONEXION_ID
     * Busca una conexión por su ID (similar a SP_01).
     *
     * @param paramMap Mapa que debe contener:
     * - p_b07_id_con: ID de la conexión a buscar (IN)
     * - o_cursor: cursor con el resultado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarConexionId(Map<String, Object> paramMap);
}
