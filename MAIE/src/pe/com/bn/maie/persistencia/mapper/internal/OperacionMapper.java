package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperacionMapper {

    /**
     * Invoca al procedimiento SP_01_BUSCAR_OPERACION
     * Busca una operación por su ID de Operación, ID de Entidad y Código de Operación.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación a buscar (IN)
     * - p_b01_id_entidad: ID de la entidad a buscar (IN)
     * - p_b02_cod_operacion: Código de la operación a buscar (IN) - NUEVO
     * - o_cursor: cursor con el resultado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarOperacion(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_02_SELECCIONAR_OPERACIONES
     * Selecciona todas las operaciones con información de la entidad.
     *
     * @param paramMap Mapa que debe contener:
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void traerTodasLasOperaciones(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_03_ACTUALIZAR_OPERACION
     * Actualiza una operación existente.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación a actualizar (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b02_cod_operacion: Código de la operación (IN) - NUEVO (para WHERE)
     * - p_b07_id_conexion: ID de la conexión (IN)
     * - p_b02_descripcion: Descripción de la operación (IN)
     * - p_b02_requiere_body_envio: Indicador si requiere body de envío (IN)
     * - p_b02_usu_mod: Usuario de modificación (IN)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void actualizarOperacion(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_04_INSERTAR_OPERACION
     * Inserta una nueva operación.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b07_id_conexion: ID de la conexión (IN)
     * - p_b02_descripcion: Descripción de la operación (IN)
     * - p_b02_requiere_body_envio: Indicador si requiere body de envío (IN)
     * - p_b02_cod_operacion: Código de la operación (IN) - NUEVO
     * - p_b02_usu_cre: Usuario de creación (IN)
     * - o_b02_id_operacion: ID de la operación generado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void insertarOperacion(Map<String, Object> paramMap);
}
