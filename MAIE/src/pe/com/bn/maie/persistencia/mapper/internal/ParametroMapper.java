package pe.com.bn.maie.persistencia.mapper.internal;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParametroMapper {
    
    public void buscarParametro(Map<String, Object> paramMap);
    
    public void listarParametros(Map<String, Object> paramMap);
    
    public void insertarParametro(Map<String, Object> paramMap);
    
    public void actualizarParametroConOperacion(Map<String, Object> paramMap);
    
    public void actualizarParametroGeneral(Map<String, Object> paramMap);
    
    public void crearGrupo(Map<String, Object> paramMap);
    
    public void listarGrupos(Map<String, Object> paramMap);
    
    public void listarParametrosGrupos(Map<String, Object> paramMap);

	public void obtenerMaximoParamDeGrupo(Map<String, Object> params);
}