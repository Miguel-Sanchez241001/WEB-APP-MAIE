package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.maie.infraestructura.services.internal.CrudEntidades;
import pe.com.bn.maie.persistencia.dto.Entidad;
import pe.com.bn.maie.persistencia.mapper.internal.EntidadMapper; // Asumiendo que el mapper está en esta ruta
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@Service("crudEntidades")
public class CrudEntidadesImpl implements CrudEntidades {
    
    private static final Logger logger = LogManager.getLogger(CrudEntidadesImpl.class);
    
    @Autowired
    private EntidadMapper entidadMapper;
    
    @Override
    public List<Entidad> obtenerEntidades() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            entidadMapper.seleccionarEntidades(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            @SuppressWarnings("unchecked")
            List<Entidad> listaEntidades = (List<Entidad>) params.get("o_cursor");
            return listaEntidades;
        } catch (Exception e) {
            logger.error("Error persistence en obtenerEntidades: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public Entidad buscarEntidadPorId(String idEntidad) throws PersistenceException { // Cambiado a String idEntidad
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b01_id_entidad", idEntidad); // Cambiado a p_b01_id_entidad
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            entidadMapper.buscarEntidad(params); // O entidadMapper.buscarEntidadId(params); ambos mapean al mismo SP
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            @SuppressWarnings("unchecked")
            List<Entidad> listaEntidades = (List<Entidad>) params.get("o_cursor");

            if (listaEntidades != null && !listaEntidades.isEmpty()) {
                return listaEntidades.get(0);
            } else {
                return null; // O lanzar una excepción específica si no se encuentra
            }
        } catch (Exception e) {
            logger.error("Error persistence en buscarEntidadPorId: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void actualizarEntidad(Entidad entidad) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b01_id_entidad", entidad.getIdEntidad()); // Nuevo nombre de propiedad
            params.put("p_b01_descripcion", entidad.getDescripcion()); // Nuevo nombre de propiedad
            params.put("p_b01_tipo_comunicacion", entidad.getTipoComunicacion()); // Nueva propiedad
            params.put("p_b01_tipo_trama_bn", entidad.getTipoTramaBn()); // Nueva propiedad
            params.put("p_b01_tipo_trama_ex", entidad.getTipoTramaEx()); // Nueva propiedad
            params.put("p_b01_ind_mapeo", entidad.getIndMapeo()); // Nueva propiedad
            params.put("p_b01_estado", entidad.getEstado()); // Nuevo nombre de propiedad
            params.put("p_b01_usu_mod", entidad.getUsuarioModificacion() != null ? entidad.getUsuarioModificacion() : "SYSTEM"); // Usar el valor de la DTO o un default
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            entidadMapper.actualizarEntidad(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en actualizarEntidad: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void insertarEntidad(Entidad entidad) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b01_id_entidad", entidad.getIdEntidad()); // Nuevo nombre de propiedad
            params.put("p_b01_descripcion", entidad.getDescripcion()); // Nuevo nombre de propiedad
            params.put("p_b01_tipo_comunicacion", entidad.getTipoComunicacion()); // Nueva propiedad
            params.put("p_b01_tipo_trama_bn", entidad.getTipoTramaBn()); // Nueva propiedad
            params.put("p_b01_tipo_trama_ex", entidad.getTipoTramaEx()); // Nueva propiedad
            params.put("p_b01_ind_mapeo", entidad.getIndMapeo()); // Nueva propiedad
            params.put("p_b01_estado", entidad.getEstado()); // Nuevo nombre de propiedad
            params.put("p_b01_usu_cre", entidad.getUsuarioCreacion() != null ? entidad.getUsuarioCreacion() : "SYSTEM"); // Usar el valor de la DTO o un default
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            entidadMapper.insertarEntidad(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en insertarEntidad: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

	@Override
	public Entidad buscarEntidadPorId(Long id) throws PersistenceException {
		// TODO Apéndice de método generado automáticamente
		return null;
	}
}
