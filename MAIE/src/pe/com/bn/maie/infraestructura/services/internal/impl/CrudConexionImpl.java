package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.maie.infraestructura.services.internal.CrudConexion;
import pe.com.bn.maie.persistencia.dto.Conexion;
import pe.com.bn.maie.persistencia.mapper.internal.ConexionMapper; // Asumiendo que el mapper está en esta ruta
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;

@Service("crudConexion")
public class CrudConexionImpl implements CrudConexion {

    private static final Logger logger = LogManager.getLogger(CrudConexionImpl.class);

    @Autowired
    private ConexionMapper conexionMapper;

    @Override
    public List<Conexion> obtenerConexiones() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            conexionMapper.seleccionarConexiones(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<Conexion> listaConexiones = (List<Conexion>) params.get("o_cursor");
            return listaConexiones;
        } catch (Exception e) {
            logger.error("Error persistence en obtenerConexiones: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public Conexion buscarConexionPorId(Long idConexion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b07_id_con", idConexion);
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            conexionMapper.buscarConexion(params); // O conexionMapper.buscarConexionId(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }

            @SuppressWarnings("unchecked")
            List<Conexion> listaConexiones = (List<Conexion>) params.get("o_cursor");

            if (listaConexiones != null && !listaConexiones.isEmpty()) {
                return listaConexiones.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error persistence en buscarConexionPorId: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void actualizarConexion(Conexion conexion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b07_id_con", conexion.getIdConexion());
            params.put("p_b01_id_entidad", conexion.getIdEntidad());
            params.put("p_b07_protocolo", conexion.getProtocolo());
            params.put("p_b07_host", conexion.getHost());
            params.put("p_b07_puerto", conexion.getPuerto());
            params.put("p_b07_usa_tls", conexion.getUsaTls());
            params.put("p_b07_ind_credenciales", conexion.getIndCredenciales());
            params.put("p_b07_timeout_conexion_ms", conexion.getTimeoutConexionMs());
            params.put("p_b07_timeout_lectura_ms", conexion.getTimeoutLecturaMs());
            params.put("p_b07_max_reintentos", conexion.getMaxReintentos());
            params.put("p_b07_reintento_espera_ms", conexion.getReintentoEsperaMs());
            params.put("p_b07_usu_mod", conexion.getUsuarioModificacion() != null ? conexion.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            conexionMapper.actualizarConexion(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en actualizarConexion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void insertarConexion(Conexion conexion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b01_id_entidad", conexion.getIdEntidad());
            params.put("p_b07_protocolo", conexion.getProtocolo());
            params.put("p_b07_host", conexion.getHost());
            params.put("p_b07_puerto", conexion.getPuerto());
            params.put("p_b07_usa_tls", conexion.getUsaTls());
            params.put("p_b07_ind_credenciales", conexion.getIndCredenciales());
            params.put("p_b07_timeout_conexion_ms", conexion.getTimeoutConexionMs());
            params.put("p_b07_timeout_lectura_ms", conexion.getTimeoutLecturaMs());
            params.put("p_b07_max_reintentos", conexion.getMaxReintentos());
            params.put("p_b07_reintento_espera_ms", conexion.getReintentoEsperaMs());
            params.put("p_b07_usu_cre", conexion.getUsuarioCreacion() != null ? conexion.getUsuarioCreacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");

            conexionMapper.insertarConexion(params);

            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");

            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en insertarConexion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
}
