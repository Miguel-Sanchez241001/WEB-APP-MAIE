package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntidadMapper {
    
    /**
     * Invoca al procedimiento SP_01_BUSCAR_ENTIDAD
     * Busca una entidad por su ID y retorna su descripci�n
     * 
     * @param paramMap Mapa que debe contener:
     *                - idEntidad: ID de la entidad a buscar (IN)
     *                - descripcionEntidad: descripci�n de la entidad (OUT)
     *                - codeRpta: c�digo de respuesta (OUT)
     *                - descRpta: descripci�n de la respuesta (OUT)
     */
    void buscarEntidad(Map<String, Object> paramMap);
    
    /**
     * Invoca al procedimiento SP_02_SELECCIONAR_ENTIDADES
     * Selecciona todas las entidades activas
     * 
     * @param paramMap Mapa que debe contener:
     *                - cursor: lista de resultados (OUT)
     *                - codeRpta: c�digo de respuesta (OUT)
     *                - descRpta: descripci�n de la respuesta (OUT)
     */
    void seleccionarEntidades(Map<String, Object> paramMap);
    
    /**
     * Invoca al procedimiento SP_03_ACTUALIZAR_ENTIDAD
     * Actualiza la descripci�n de una entidad
     * 
     * @param paramMap Mapa que debe contener:
     *                - idEntidad: ID de la entidad a actualizar (IN)
     *                - descripcionEntidad: nueva descripci�n (IN)
     *                - usuarioMod: usuario que realiza la modificaci�n (IN)
     *                - codeRpta: c�digo de respuesta (OUT)
     *                - descRpta: descripci�n de la respuesta (OUT)
     */
    void actualizarEntidad(Map<String, Object> paramMap);
    
 
    
    /**
     * Invoca al procedimiento para insertar una entidad
     * 
     * @param paramMap Mapa que debe contener:
     *                - entidad: objeto Entidad con los datos a insertar (IN)
     *                - codeRpta: c�digo de respuesta (OUT)
     *                - descRpta: descripci�n de la respuesta (OUT)
     */
    void insertarEntidad(Map<String, Object> paramMap);

	void buscarEntidadId(Map<String, Object> params);
    
 
}
