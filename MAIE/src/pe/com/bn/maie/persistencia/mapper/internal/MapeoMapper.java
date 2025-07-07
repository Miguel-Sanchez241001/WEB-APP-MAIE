package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapeoMapper {

    /**
     * Invoca al procedimiento SP_01_BUSCAR_MAPEO
     * Busca un mapeo por su ID.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b10_id_mapeo: ID del mapeo a buscar (IN)
     * - o_cursor: cursor con el resultado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarMapeo(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_02_SELECCIONAR_MAPEOS
     * Selecciona todos los mapeos.
     *
     * @param paramMap Mapa que debe contener:
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void seleccionarMapeos(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_03_ACTUALIZAR_MAPEO
     * Actualiza un mapeo existente.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b10_id_mapeo: ID del mapeo a actualizar (IN)
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b08_id_campo_trama: ID del campo de trama (IN)
     * - p_b09_id_llave_body: ID de la llave del cuerpo (IN)
     * - p_b10_ind_transformacion: Indicador de transformación (IN)
     * - p_b10_expresion_transform: Expresión de transformación (IN)
     * - p_b10_ind_trim: Indicador de TRIM (IN)
     * - p_b10_ind_autocompletar: Indicador de autocompletar (IN)
     * - p_b10_valor_autocompletar: Valor de autocompletar (IN)
     * - p_b10_descripcion: Descripción (IN)
     * - p_b10_usu_mod: Usuario de modificación (IN)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void actualizarMapeo(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_04_INSERTAR_MAPEO
     * Inserta un nuevo mapeo.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b08_id_campo_trama: ID del campo de trama (IN)
     * - p_b09_id_llave_body: ID de la llave del cuerpo (IN)
     * - p_b10_ind_transformacion: Indicador de transformación (IN)
     * - p_b10_expresion_transform: Expresión de transformación (IN)
     * - p_b10_ind_trim: Indicador de TRIM (IN)
     * - p_b10_ind_autocompletar: Indicador de autocompletar (IN)
     * - p_b10_valor_autocompletar: Valor de autocompletar (IN)
     * - p_b10_descripcion: Descripción (IN)
     * - p_b10_usu_cre: Usuario de creación (IN)
     * - o_b10_id_mapeo: ID del mapeo generado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void insertarMapeo(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_05_BUSCAR_MAPEOS_POR_OPERACION
     * Busca mapeos asociados a una operación y entidad específicas.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarMapeosPorOperacion(Map<String, Object> paramMap);
}
