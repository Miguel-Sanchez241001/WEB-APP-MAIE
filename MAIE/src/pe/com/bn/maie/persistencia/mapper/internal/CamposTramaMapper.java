package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CamposTramaMapper {

    /**
     * Invoca al procedimiento SP_01_BUSCAR_CAMPO_TRAMA
     * Busca un campo de trama por su ID.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b08_id_campo_trama: ID del campo de trama a buscar (IN)
     * - o_cursor: cursor con el resultado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarCampoTrama(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_02_SELECCIONAR_CAMPOS_TRAMA
     * Selecciona todos los campos de trama.
     *
     * @param paramMap Mapa que debe contener:
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void seleccionarCamposTrama(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_03_ACTUALIZAR_CAMPO_TRAMA
     * Actualiza un campo de trama existente.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b08_id_campo_trama: ID del campo de trama a actualizar (IN)
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b08_tag_name: Nombre del tag (IN)
     * - p_b08_orden: Orden del campo (IN)
     * - p_b08_tipo_origen: Tipo de origen (1=Request, 2=Response) (IN)
     * - p_b08_tipo_campo: Tipo de campo (1=Cadena, 2=Entero, 3=Decimal) (IN)
     * - p_b08_tipo_dato: Tipo de dato (N, A, AN, D) (IN)
     * - p_b08_longitud: Longitud del campo (IN)
     * - p_b08_ind_relleno: Indicador de relleno (IN)
     * - p_b08_valor_relleno: Valor de relleno (IN)
     * - p_b08_valor_defecto: Valor por defecto (IN)
     * - p_b08_alineacion: Alineación (1=Derecha, 2=Izquierda) (IN)
     * - p_b08_usu_mod: Usuario de modificación (IN)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void actualizarCampoTrama(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_04_INSERTAR_CAMPO_TRAMA
     * Inserta un nuevo campo de trama.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b08_tag_name: Nombre del tag (IN)
     * - p_b08_orden: Orden del campo (IN)
     * - p_b08_tipo_origen: Tipo de origen (1=Request, 2=Response) (IN)
     * - p_b08_tipo_campo: Tipo de campo (1=Cadena, 2=Entero, 3=Decimal) (IN)
     * - p_b08_tipo_dato: Tipo de dato (N, A, AN, D) (IN)
     * - p_b08_longitud: Longitud del campo (IN)
     * - p_b08_ind_relleno: Indicador de relleno (IN)
     * - p_b08_valor_relleno: Valor de relleno (IN)
     * - p_b08_valor_defecto: Valor por defecto (IN)
     * - p_b08_alineacion: Alineación (1=Derecha, 2=Izquierda) (IN)
     * - p_b08_usu_cre: Usuario de creación (IN)
     * - o_b08_id_campo_trama: ID del campo de trama generado (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void insertarCampoTrama(Map<String, Object> paramMap);

    /**
     * Invoca al procedimiento SP_05_BUSCAR_CAMPOS_POR_OPERACION
     * Busca campos de trama asociados a una operación y entidad específicas, opcionalmente filtrando por tipo de origen.
     *
     * @param paramMap Mapa que debe contener:
     * - p_b02_id_operacion: ID de la operación (IN)
     * - p_b01_id_entidad: ID de la entidad (IN)
     * - p_b08_tipo_origen: Tipo de origen (1=Request, 2=Response) (IN, opcional)
     * - o_cursor: cursor con la lista de resultados (OUT)
     * - code_rpta: código de respuesta (OUT)
     * - desc_rpta: descripción de la respuesta (OUT)
     */
    void buscarCamposPorOperacion(Map<String, Object> paramMap);
}
