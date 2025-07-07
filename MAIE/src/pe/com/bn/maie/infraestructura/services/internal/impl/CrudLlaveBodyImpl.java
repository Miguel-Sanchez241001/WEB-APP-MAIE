package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.maie.infraestructura.services.internal.CrudLlaveBody;
import pe.com.bn.maie.persistencia.dto.LlaveBody;
import pe.com.bn.maie.persistencia.mapper.internal.LlaveBodyMapper; // Asumiendo que el mapper está en esta ruta
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@Service("crudLlaveBody")
public class CrudLlaveBodyImpl implements CrudLlaveBody {

    private static final Logger logger = LogManager.getLogger(CrudLlaveBodyImpl.class);

    @Autowired
    private LlaveBodyMapper llaveBodyMapper;

    @Override
    public List<LlaveBody> obtenerLlavesBody() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            llaveBodyMapper.seleccionarLlavesBody(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<LlaveBody> listaLlavesBody = (List<LlaveBody>) params.get("o_cursor");
            return listaLlavesBody;
        } catch (Exception e) {
            logger.error("Error persistence en obtenerLlavesBody: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public LlaveBody buscarLlaveBodyPorId(Long idLlaveBody) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b09_id_llave_body", idLlaveBody);
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            llaveBodyMapper.buscarLlaveBody(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<LlaveBody> listaLlavesBody = (List<LlaveBody>) params.get("o_cursor");

            if (listaLlavesBody != null && !listaLlavesBody.isEmpty()) {
                return listaLlavesBody.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error persistence en buscarLlaveBodyPorId: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void actualizarLlaveBody(LlaveBody llaveBody) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b09_id_llave_body", llaveBody.getIdLlaveBody());
            params.put("p_b02_id_operacion", llaveBody.getIdOperacion());
            params.put("p_b01_id_entidad", llaveBody.getIdEntidad());
            params.put("p_b09_tag_name", llaveBody.getTagName());
            params.put("p_b09_tipo_origen", llaveBody.getTipoOrigen());
            params.put("p_b09_id_llave_padre", llaveBody.getIdLlavePadre());
            params.put("p_b09_tipo_dato_esperado", llaveBody.getTipoDatoEsperado());
            params.put("p_b09_es_padre", llaveBody.getEsPadre());
            params.put("p_b09_valor_defecto", llaveBody.getValorDefecto());
            params.put("p_b09_orden", llaveBody.getOrden());
            params.put("p_b09_ind_obligatorio", llaveBody.getIndObligatorio());
            params.put("p_b09_descripcion", llaveBody.getDescripcion());
            params.put("p_b09_ind_mapeable", llaveBody.getIndMapeable());
            params.put("p_b09_usu_mod", llaveBody.getUsuarioModificacion() != null ? llaveBody.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            llaveBodyMapper.actualizarLlaveBody(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en actualizarLlaveBody: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void insertarLlaveBody(LlaveBody llaveBody) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", llaveBody.getIdOperacion());
            params.put("p_b01_id_entidad", llaveBody.getIdEntidad());
            params.put("p_b09_tag_name", llaveBody.getTagName());
            params.put("p_b09_tipo_origen", llaveBody.getTipoOrigen());
            params.put("p_b09_id_llave_padre", llaveBody.getIdLlavePadre());
            params.put("p_b09_tipo_dato_esperado", llaveBody.getTipoDatoEsperado());
            params.put("p_b09_es_padre", llaveBody.getEsPadre());
            params.put("p_b09_valor_defecto", llaveBody.getValorDefecto());
            params.put("p_b09_orden", llaveBody.getOrden());
            params.put("p_b09_ind_obligatorio", llaveBody.getIndObligatorio());
            params.put("p_b09_descripcion", llaveBody.getDescripcion());
            params.put("p_b09_ind_mapeable", llaveBody.getIndMapeable());
            params.put("p_b09_usu_cre", llaveBody.getUsuarioCreacion() != null ? llaveBody.getUsuarioCreacion() : "SYSTEM");
            params.put("o_b09_id_llave_body", null); // Para que el SP devuelva el ID generado
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            llaveBodyMapper.insertarLlaveBody(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            // Asignar el ID generado de vuelta al objeto LlaveBody
            llaveBody.setIdLlaveBody((Long) params.get("o_b09_id_llave_body"));

        } catch (Exception e) {
            logger.error("Error persistence en insertarLlaveBody: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<LlaveBody> buscarLlavesPorOperacion(Long idOperacion, String idEntidad, Integer tipoOrigen, Long idLlavePadre) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", idOperacion);
            params.put("p_b01_id_entidad", idEntidad);
            params.put("p_b09_tipo_origen", tipoOrigen); // Puede ser null
            params.put("p_b09_id_llave_padre", idLlavePadre); // Puede ser null
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            llaveBodyMapper.buscarLlavesPorOperacion(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<LlaveBody> listaLlavesBody = (List<LlaveBody>) params.get("o_cursor");
            return listaLlavesBody;
        } catch (Exception e) {
            logger.error("Error persistence en buscarLlavesPorOperacion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
}

