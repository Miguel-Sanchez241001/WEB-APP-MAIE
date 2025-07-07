package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.maie.infraestructura.services.internal.CrudParametro;
import pe.com.bn.maie.persistencia.dto.Parametro;
import pe.com.bn.maie.persistencia.mapper.internal.ParametroMapper;
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@Service("crudParametro")
public class CrudParametroImpl implements CrudParametro {
    
    private static final Logger logger = LogManager.getLogger(CrudParametroImpl.class);
    
    @Autowired
    private ParametroMapper parametroMapper;
    
    @Override
    public List<Parametro> obtenerParametros() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.listarParametros(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            @SuppressWarnings("unchecked")
            List<Parametro> listaParametros = (List<Parametro>) params.get("o_cursor");
            
            return listaParametros;
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public Parametro buscarParametro(String codigoGrupo, String codigoParametro) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_cod_grup", codigoGrupo);
            params.put("p_cod_para", codigoParametro);
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.buscarParametro(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            @SuppressWarnings("unchecked")
            List<Parametro> listaParametros = (List<Parametro>) params.get("o_cursor");
            
            if (listaParametros != null && !listaParametros.isEmpty()) {
                return listaParametros.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void insertarParametro(Parametro parametro) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_cod_grup", parametro.getCodigoGrupo());
            params.put("p_cod_para", parametro.getCodigoParametro());
            params.put("p_descrip", parametro.getDescripcion());
            params.put("p_valor1", parametro.getValor1());
            params.put("p_valor2", parametro.getValor2());
            params.put("p_ind_act", parametro.getIndicadorActivo());
            params.put("p_cod_oper", parametro.getCodigoOperacion());
            params.put("p_usu_cre", parametro.getUsuarioCreacion() != null ? parametro.getUsuarioCreacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.insertarParametro(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void actualizarParametroConOperacion(Parametro parametro) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_id_para", parametro.getIdParametro());
            params.put("p_valor1", parametro.getValor1());
            params.put("p_valor2", parametro.getValor2());
            params.put("p_ind_act", parametro.getIndicadorActivo());
            params.put("p_cod_oper", parametro.getCodigoOperacion());
            params.put("p_usu_mod", parametro.getUsuarioModificacion() != null ? parametro.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.actualizarParametroConOperacion(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void actualizarParametroGeneral(Parametro parametro) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_id_para", parametro.getIdParametro());
            params.put("p_desc", parametro.getDescripcion());
            params.put("p_valor1", parametro.getValor1());
            params.put("p_valor2", parametro.getValor2());
            params.put("p_ind_act", parametro.getIndicadorActivo());
            params.put("p_usu_mod", parametro.getUsuarioModificacion() != null ? parametro.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.actualizarParametroGeneral(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void crearGrupo(String codigoGrupo, String descripcion, String usuarioCreacion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_cod_grup", codigoGrupo);
            params.put("p_descrip", descripcion);
            params.put("p_usu_cre", usuarioCreacion != null ? usuarioCreacion : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.crearGrupo(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public List<Parametro> obtenerGrupos() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            parametroMapper.listarGrupos(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            @SuppressWarnings("unchecked")
            List<Parametro> listaGrupos = (List<Parametro>) params.get("o_cursor");
            
            return listaGrupos;
        } catch (Exception e) {
            logger.error("Error persistence : " + e.getMessage());
            throw new PersistenceException(e.getMessage());
        }
    }

	@Override
	public List<Parametro> obtenerParametrosGrupos(String codGrupo) throws PersistenceException {
		  try {
	            Map<String, Object> params = new HashMap<>();
	            params.put("p_cod_grup", codGrupo);
 	            params.put("code_rpta", "");
	            params.put("desc_rpta", "");
	            
	            parametroMapper.listarParametrosGrupos(params);
	            
	            String codeRpta = (String) params.get("code_rpta");
	            String descRpta = (String) params.get("desc_rpta");
	            
	            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
	                throw new PersistenceException(descRpta);
	            }
	            
	            @SuppressWarnings("unchecked")
	            List<Parametro> listaParametros = (List<Parametro>) params.get("o_cursor");
	            
	         
	            return listaParametros;
	            
	        } catch (Exception e) {
	            logger.error("Error persistence : " + e.getMessage());
	            throw new PersistenceException(e.getMessage());
	        }
	}

	 @Override
	    public Integer obtenerMaximoParamDeGrupo(Integer codGrup, Long codigoOperacion, String idEntidadFk) throws PersistenceException {
	        try {
	            Map<String, Object> params = new HashMap<>();
	            params.put("p_cod_grup", codGrup);
	            params.put("p_cod_oper", codigoOperacion);
	            params.put("p_id_entidad_fk", idEntidadFk);
	            params.put("code_rpta", "");
	            params.put("desc_rpta", "");
	            
	            parametroMapper.obtenerMaximoParamDeGrupo(params);
	            
	            String codeRpta = (String) params.get("code_rpta");
	            String descRpta = (String) params.get("desc_rpta");
	            
	            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
	                throw new PersistenceException(descRpta);
	            }
	            
	            String maximoStr = (String) params.get("p_cod_nuevo");
	            
	            // Convertir el String a Integer
	            return Integer.valueOf(maximoStr);
	            
	        } catch (Exception e) {
	            logger.error("Error persistence en obtenerMaximoParamDeGrupo: " + e.getMessage(), e);
	            throw new PersistenceException(e.getMessage());
	        }
	    }

	@Override
    public String obtenerMaximoParamDeGrupo(String codGrup) throws PersistenceException {
        try {
            // Se asume que codigoOperacion y idEntidadFk pueden ser null para esta sobrecarga.
            // Si el SP subyacente (SP_09_OBTENER_MAXIMO_PARAMETRO) requiere valores no nulos
            // para estos campos, esta llamada podría resultar en NO_DATA_FOUND o errores.
            Integer maximoInteger = obtenerMaximoParamDeGrupo(Integer.valueOf(codGrup), null, null);
            return String.valueOf(maximoInteger);
        } catch (NumberFormatException e) {
            logger.error("Error de formato al convertir codGrup a Integer: " + e.getMessage(), e);
            throw new PersistenceException("Formato de código de grupo inválido: " + codGrup);
        } catch (Exception e) {
            logger.error("Error persistence en obtenerMaximoParamDeGrupo (String): " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

	 
}