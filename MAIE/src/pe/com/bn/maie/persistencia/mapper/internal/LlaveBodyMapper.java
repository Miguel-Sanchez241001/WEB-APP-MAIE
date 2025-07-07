package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LlaveBodyMapper {

    /**
     * Invoca al procedimiento SP_01_BUSCAR_LLAVE_BODY
     * Busca una llave de cuerpo por su ID.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b09_id_llave_body: ID de la llave de cuerpo a buscar (IN)
     * - o_cursor: cursor con el resultado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarLlaveBody(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_02_SELECCIONAR_LLAVES_BODY
     * Selecciona todas las llaves de cuerpo.
     *
     * @param paramMap Mapa que debe contener:
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void seleccionarLlavesBody(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_03_ACTUALIZAR_LLAVE_BODY
     * Actualiza una llave de cuerpo existente.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b09_id_llave_body: ID de la llave de cuerpo a actualizar (IN)
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b09_tag_name: Nombre del tag (IN)
     * - p_b09_tipo_origen: Tipo de origen (1=Request, 2=Response) (IN)
     * - p_b09_id_llave_padre: ID de la llave padre (IN, puede ser NULL)
     * - p_b09_tipo_dato_esperado: Tipo de dato esperado (IN)
     * - p_b09_es_padre: Indicador si es padre (IN)
     * - p_b09_valor_defecto: Valor por defecto (IN)
     * - p_b09_orden: Orden (IN)
     * - p_b09_ind_obligatorio: Indicador de obligatoriedad (IN)
     * - p_b09_descripcion: Descripción (IN)
     * - p_b09_ind_mapeable: Indicador de mapeable (IN)
     * - p_b09_usu_mod: Usuario de modificación (IN)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void actualizarLlaveBody(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_04_INSERTAR_LLAVE_BODY
     * Inserta una nueva llave de cuerpo.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b09_tag_name: Nombre del tag (IN)
     * - p_b09_tipo_origen: Tipo de origen (1=Request, 2=Response) (IN)
     * - p_b09_id_llave_padre: ID de la llave padre (IN, puede ser NULL)
     * - p_b09_tipo_dato_esperado: Tipo de dato esperado (IN)
     * - p_b09_es_padre: Indicador si es padre (IN)
     * - p_b09_valor_defecto: Valor por defecto (IN)
     * - p_b09_orden: Orden (IN)
     * - p_b09_ind_obligatorio: Indicador de obligatoriedad (IN)
     * - p_b09_descripcion: Descripción (IN)
     * - p_b09_ind_mapeable: Indicador de mapeable (IN)
     * - p_b09_usu_cre: Usuario de creación (IN)
     * - o_b09_id_llave_body: ID de la llave de cuerpo generado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void insertarLlaveBody(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_05_BUSCAR_LLAVES_POR_OPERACION
     * Busca llaves de cuerpo asociadas a una operación y entidad específicas,
     * opcionalmente filtrando por llave padre y tipo de origen.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b09_tipo_origen: Tipo de origen (1=Request, 2=Response) (IN, opcional)
     * - p_b09_id_llave_padre: ID de la llave padre (IN, opcional, NULL para llaves raíz)
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarLlavesPorOperacion(Map<String, Object> paramMap);
}
