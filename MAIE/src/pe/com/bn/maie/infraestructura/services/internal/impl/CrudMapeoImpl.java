package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.maie.infraestructura.services.internal.CrudMapeo;
import pe.com.bn.maie.persistencia.dto.Mapeo;
import pe.com.bn.maie.persistencia.mapper.internal.MapeoMapper; // Asumiendo que el mapper está en esta ruta
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@Service("crudMapeo")
public class CrudMapeoImpl implements CrudMapeo {

    private static final Logger logger = LogManager.getLogger(CrudMapeoImpl.class);

    @Autowired
    private MapeoMapper mapeoMapper;

    @Override
    public List<Mapeo> obtenerMapeos() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            mapeoMapper.seleccionarMapeos(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<Mapeo> listaMapeos = (List<Mapeo>) params.get("o_cursor");
            return listaMapeos;
        } catch (Exception e) {
            logger.error("Error persistence en obtenerMapeos: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public Mapeo buscarMapeoPorId(Long idMapeo) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b10_id_mapeo", idMapeo);
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            mapeoMapper.buscarMapeo(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<Mapeo> listaMapeos = (List<Mapeo>) params.get("o_cursor");

            if (listaMapeos != null && !listaMapeos.isEmpty()) {
                return listaMapeos.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error persistence en buscarMapeoPorId: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void actualizarMapeo(Mapeo mapeo) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b10_id_mapeo", mapeo.getIdMapeo());
            params.put("p_b02_id_operacion", mapeo.getIdOperacion());
            params.put("p_b01_id_entidad", mapeo.getIdEntidad());
            params.put("p_b08_id_campo_trama", mapeo.getIdCampoTrama());
            params.put("p_b09_id_llave_body", mapeo.getIdLlaveBody());
            params.put("p_b10_ind_transformacion", mapeo.getIndTransformacion());
            params.put("p_b10_expresion_transform", mapeo.getExpresionTransform());
            params.put("p_b10_ind_trim", mapeo.getIndTrim());
            params.put("p_b10_ind_autocompletar", mapeo.getIndAutocompletar());
            params.put("p_b10_valor_autocompletar", mapeo.getValorAutocompletar());
            params.put("p_b10_descripcion", mapeo.getDescripcion());
            params.put("p_b10_usu_mod", mapeo.getUsuarioModificacion() != null ? mapeo.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            mapeoMapper.actualizarMapeo(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en actualizarMapeo: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void insertarMapeo(Mapeo mapeo) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", mapeo.getIdOperacion());
            params.put("p_b01_id_entidad", mapeo.getIdEntidad());
            params.put("p_b08_id_campo_trama", mapeo.getIdCampoTrama());
            params.put("p_b09_id_llave_body", mapeo.getIdLlaveBody());
            params.put("p_b10_ind_transformacion", mapeo.getIndTransformacion());
            params.put("p_b10_expresion_transform", mapeo.getExpresionTransform());
            params.put("p_b10_ind_trim", mapeo.getIndTrim());
            params.put("p_b10_ind_autocompletar", mapeo.getIndAutocompletar());
            params.put("p_b10_valor_autocompletar", mapeo.getValorAutocompletar());
            params.put("p_b10_descripcion", mapeo.getDescripcion());
            params.put("p_b10_usu_cre", mapeo.getUsuarioCreacion() != null ? mapeo.getUsuarioCreacion() : "SYSTEM");
            params.put("o_b10_id_mapeo", null); // Para que el SP devuelva el ID generado
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            mapeoMapper.insertarMapeo(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            // Asignar el ID generado de vuelta al objeto Mapeo
            mapeo.setIdMapeo((Long) params.get("o_b10_id_mapeo"));

        } catch (Exception e) {
            logger.error("Error persistence en insertarMapeo: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Mapeo> buscarMapeosPorOperacion(Long idOperacion, String idEntidad) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", idOperacion);
            params.put("p_b01_id_entidad", idEntidad);
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            mapeoMapper.buscarMapeosPorOperacion(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<Mapeo> listaMapeos = (List<Mapeo>) params.get("o_cursor");
            return listaMapeos;
        } catch (Exception e) {
            logger.error("Error persistence en buscarMapeosPorOperacion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
}
