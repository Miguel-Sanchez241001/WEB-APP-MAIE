package pe.com.bn.maie.infraestructura.services.internal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.bn.maie.infraestructura.services.internal.CrudEntidades;
import pe.com.bn.maie.infraestructura.services.internal.CrudOperaciones;
import pe.com.bn.maie.infraestructura.services.internal.CrudParametro;
import pe.com.bn.maie.persistencia.dto.Operacion; // Importación actualizada
import pe.com.bn.maie.persistencia.dto.Parametro; // Asegúrate de que esta es la ruta correcta de tu DTO Parametro
import pe.com.bn.maie.persistencia.mapper.internal.OperacionMapper; // Asumiendo que el mapper está en esta ruta
import pe.com.bn.maie.tranversal.util.constantes.ConstantesGenerales;
import pe.com.bn.maie.tranversal.util.excepciones.PersistenceException;
 
@Service("crudOperaciones")
public class CrudOperacionesImpl implements CrudOperaciones {
    
    private static final Logger logger = LogManager.getLogger(CrudOperacionesImpl.class);
    
    @Autowired
    private OperacionMapper operacionMapper;
    
    @Autowired
    private CrudEntidades crudEntidades; // Se mantiene por si se usa en otros métodos no mostrados
    
    @Autowired
    private CrudParametro crudParametro; // Se mantiene por si se usa en otros métodos no mostrados
    
    @Override
    public List<Operacion> obtenerOperaciones() throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            operacionMapper.traerTodasLasOperaciones(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            @SuppressWarnings("unchecked")
            List<Operacion> listaOperaciones = (List<Operacion>) params.get("o_cursor");
            
            return listaOperaciones;
        } catch (Exception e) {
            logger.error("Error persistence en obtenerOperaciones: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public Operacion buscarOperacion(Long idOperacion, String idEntidad, String codigoOperacion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", idOperacion);
            params.put("p_b01_id_entidad", idEntidad);
            params.put("p_b02_cod_operacion", codigoOperacion); // Nuevo parámetro
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            operacionMapper.buscarOperacion(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            @SuppressWarnings("unchecked")
            List<Operacion> listaOperaciones = (List<Operacion>) params.get("o_cursor");
            
            if (listaOperaciones != null && !listaOperaciones.isEmpty()) {
                Operacion opeTem = listaOperaciones.get(0);
                return opeTem;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error persistence en buscarOperacion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void actualizarOperacion(Operacion operacion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b02_id_operacion", operacion.getIdOperacion());
            params.put("p_b01_id_entidad", operacion.getIdEntidad());
            params.put("p_b02_cod_operacion", operacion.getCodigoOperacion()); // Nuevo parámetro
            params.put("p_b07_id_conexion", operacion.getIdConexion());
            params.put("p_b02_descripcion", operacion.getDescripcion());
            params.put("p_b02_requiere_body_envio", operacion.getRequiereBodyEnvio());
            params.put("p_b02_usu_mod", operacion.getUsuarioModificacion() != null ? operacion.getUsuarioModificacion() : "SYSTEM");
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            operacionMapper.actualizarOperacion(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
        } catch (Exception e) {
            logger.error("Error persistence en actualizarOperacion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public void insertarOperacion(Operacion operacion) throws PersistenceException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_b01_id_entidad", operacion.getIdEntidad());
            params.put("p_b07_id_conexion", operacion.getIdConexion());
            params.put("p_b02_descripcion", operacion.getDescripcion());
            params.put("p_b02_requiere_body_envio", operacion.getRequiereBodyEnvio());
            params.put("p_b02_cod_operacion", operacion.getCodigoOperacion()); // Nuevo parámetro
            params.put("p_b02_usu_cre", operacion.getUsuarioCreacion() != null ? operacion.getUsuarioCreacion() : "SYSTEM");
            params.put("o_b02_id_operacion", null); // Para que el SP devuelva el ID generado
            params.put("code_rpta", "");
            params.put("desc_rpta", "");
            
            operacionMapper.insertarOperacion(params);
            
            String codeRpta = (String) params.get("code_rpta");
            String descRpta = (String) params.get("desc_rpta");
            
            if (codeRpta.equals(ConstantesGenerales.COD_BD_ERROR)) {
                throw new PersistenceException(descRpta);
            }
            
            // Asignar el ID generado de vuelta al objeto Operacion
            operacion.setIdOperacion((Long) params.get("o_b02_id_operacion"));

        } catch (Exception e) {
            logger.error("Error persistence en insertarOperacion: " + e.getMessage(), e);
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void insertOperacionTrans(Operacion operacionEdicion, String selectedPathVariable) throws PersistenceException {
        insertarOperacion(operacionEdicion); // Este método ahora asigna el ID generado a operacionEdicion

        // Asumiendo que `requiereBodyEnvio` (antes `indPathVariable`) es el indicador para path variables
        if (operacionEdicion.getRequiereBodyEnvio() == 1) { // Usando el nuevo nombre de propiedad
            
            // Buscar la operación recién insertada por su PK compuesta
            // operacionEdicion ya debería tener el idOperacion e idEntidad después de insertarOperacion
            Operacion operacionTem = buscarOperacion(operacionEdicion.getIdOperacion(), operacionEdicion.getIdEntidad(), operacionEdicion.getCodigoOperacion()); // Actualizado con codigoOperacion
            
            if (operacionTem != null) {
                Parametro parametro = new Parametro();

                parametro.setCodigoGrupo(Integer.valueOf("0009")); // Convertir a Integer
                // Asumiendo que obtenerMaximoParamDeGrupo ahora toma los IDs de operación y entidad
                parametro.setCodigoParametro(crudParametro.obtenerMaximoParamDeGrupo(
                    parametro.getCodigoGrupo(),
                    operacionTem.getIdOperacion(),
                    operacionTem.getIdEntidad()
                ));
                parametro.setDescripcion("PATH VARIABLE " + operacionEdicion.getDescripcion()); // Usando el nuevo nombre de propiedad
                parametro.setValor1(selectedPathVariable);
                parametro.setValor2(null);
                parametro.setValor3(null); // Asegurarse de que valor3 se inicializa
                parametro.setIndicadorActivo(1);
                parametro.setCodigoOperacion(operacionTem.getIdOperacion());
                parametro.setIdEntidadFk(operacionTem.getIdEntidad()); // Asignar el ID de entidad FK

                crudParametro.insertarParametro(parametro);
            } else {
                logger.error("Error: No se pudo recuperar la operación recién insertada para configurar parámetros.");
                throw new PersistenceException("No se pudo recuperar la operación recién insertada.");
            }
        }
    }
}
