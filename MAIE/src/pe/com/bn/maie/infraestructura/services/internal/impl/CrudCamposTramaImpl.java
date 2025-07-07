package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.maie.infraestructura.services.internal.CrudCamposTrama;
import pe.com.bn.maie.persistencia.dto.CamposTrama;
import pe.com.bn.maie.persistencia.mapper.internal.CamposTramaMapper; // Asumiendo que el mapper está en esta ruta
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@Service("crudCamposTrama")
public class CrudCamposTramaImpl implements CrudCamposTrama {

    private static final Logger logger = LogManager.getLogger(CrudCamposTramaImpl.class);

    @Autowired
    private CamposTramaMapper camposTramaMapper;

    @Override
    public List<CamposTrama> obtenerCamposTrama() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            camposTramaMapper.seleccionarCamposTrama(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<CamposTrama> listaCamposTrama = (List<CamposTrama>) params.get("o_cursor");
            return listaCamposTrama;
        } catch (Exception e) {
            logger.error("Error persistence en obtenerCamposTrama: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public CamposTrama buscarCampoTramaPorId(Long idCampoTrama) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b08_id_campo_trama", idCampoTrama);
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            camposTramaMapper.buscarCampoTrama(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<CamposTrama> listaCamposTrama = (List<CamposTrama>) params.get("o_cursor");

            if (listaCamposTrama != null && !listaCamposTrama.isEmpty()) {
                return listaCamposTrama.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error persistence en buscarCampoTramaPorId: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void actualizarCampoTrama(CamposTrama camposTrama) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b08_id_campo_trama", camposTrama.getIdCampoTrama());
            params.put("p_b02_id_operacion", camposTrama.getIdOperacion());
            params.put("p_b01_id_entidad", camposTrama.getIdEntidad());
            params.put("p_b08_tag_name", camposTrama.getTagName());
            params.put("p_b08_orden", camposTrama.getOrden());
            params.put("p_b08_tipo_origen", camposTrama.getTipoOrigen());
            params.put("p_b08_tipo_campo", camposTrama.getTipoCampo());
            params.put("p_b08_tipo_dato", camposTrama.getTipoDato());
            params.put("p_b08_longitud", camposTrama.getLongitud());
            params.put("p_b08_ind_relleno", camposTrama.getIndRelleno());
            params.put("p_b08_valor_relleno", camposTrama.getValorRelleno());
            params.put("p_b08_valor_defecto", camposTrama.getValorDefecto());
            params.put("p_b08_alineacion", camposTrama.getAlineacion());
            params.put("p_b08_usu_mod", camposTrama.getUsuarioModificacion() != null ? camposTrama.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            camposTramaMapper.actualizarCampoTrama(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en actualizarCampoTrama: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void insertarCampoTrama(CamposTrama camposTrama) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", camposTrama.getIdOperacion());
            params.put("p_b01_id_entidad", camposTrama.getIdEntidad());
            params.put("p_b08_tag_name", camposTrama.getTagName());
            params.put("p_b08_orden", camposTrama.getOrden());
            params.put("p_b08_tipo_origen", camposTrama.getTipoOrigen());
            params.put("p_b08_tipo_campo", camposTrama.getTipoCampo());
            params.put("p_b08_tipo_dato", camposTrama.getTipoDato());
            params.put("p_b08_longitud", camposTrama.getLongitud());
            params.put("p_b08_ind_relleno", camposTrama.getIndRelleno());
            params.put("p_b08_valor_relleno", camposTrama.getValorRelleno());
            params.put("p_b08_valor_defecto", camposTrama.getValorDefecto());
            params.put("p_b08_alineacion", camposTrama.getAlineacion());
            params.put("p_b08_usu_cre", camposTrama.getUsuarioCreacion() != null ? camposTrama.getUsuarioCreacion() : "SYSTEM");
            params.put("o_b08_id_campo_trama", null); // Para que el SP devuelva el ID generado
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            camposTramaMapper.insertarCampoTrama(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            // Asignar el ID generado de vuelta al objeto CamposTrama
            camposTrama.setIdCampoTrama((Long) params.get("o_b08_id_campo_trama"));

        } catch (Exception e) {
            logger.error("Error persistence en insertarCampoTrama: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<CamposTrama> buscarCamposPorOperacion(Long idOperacion, String idEntidad, Integer tipoOrigen) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", idOperacion);
            params.put("p_b01_id_entidad", idEntidad);
            params.put("p_b08_tipo_origen", tipoOrigen); // Puede ser null
            params.put("code_rpta", "");
            params.put("code_rpta", ""); // Duplicado, corregido en el Mapper XML
            params.put("desc_rpta", "");

            camposTramaMapper.buscarCamposPorOperacion(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<CamposTrama> listaCamposTrama = (List<CamposTrama>) params.get("o_cursor");
            return listaCamposTrama;
        } catch (Exception e) {
            logger.error("Error persistence en buscarCamposPorOperacion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
}
