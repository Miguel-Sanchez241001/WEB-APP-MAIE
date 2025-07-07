package pe.com.bn.maie.tranversal.util.constantes;

import java.util.HashMap;
import java.util.Map;

public class ConstantesSeguridad {
	
	public static final String ACCESO_OPERACIONES_BLOQUEO_TARJETA = "ACCESO_OPERACIONES_BLOQUEO_TARJETA";
	public static final String ACCESO_OPERACIONES_BLOQUEO_MA = "ACCESO_OPERACIONES_BLOQUEO_MA";
	public static final String ACCESO_OPERACIONES_BLOQUEO_RA = "ACCESO_OPERACIONES_BLOQUEO_RA";
	public static final String ACCESO_OPERACIONES_BLOQUEO_PF = "ACCESO_OPERACIONES_BLOQUEO_PF";
	public static final String ACCESO_OPERACIONES_BLOQUEO_SO = "ACCESO_OPERACIONES_BLOQUEO_SO";
	public static final String ACCESO_CONSULTAS_CONSULTA = "ACCESO_CONSULTAS_CONSULTA";
	public static final String ACCESO_AUDITORIA_CONSULTA = "ACCESO_AUDITORIA_CONSULTA";
	public static final String ACCESO_ATRIBUTOS = "ACCESO_ATRIBUTOS";

	public static final int BLOQUEO_MA = 5;
	public static final int BLOQUEO_RA = 6;
	public static final int BLOQUEO_PF = 7;
	public static final int BLOQUEO_SO = 8;
	public static final int AUDITORIA = 9;
	
	/**
	Operaciones	01
	Bloqueo MA	02
	Bloqueo RA	03
	Bloqueo PF	04
	Bloqueo SO	05
	Consultas	06
	Auditoria	07
	Atributos	08

	**/
	public final static Map<String, String> OPCION_ACC = new HashMap<String, String>() {
		
		{
			put("01S", ACCESO_OPERACIONES_BLOQUEO_TARJETA);
			put("02S", ACCESO_OPERACIONES_BLOQUEO_MA);
			put("03S", ACCESO_OPERACIONES_BLOQUEO_RA);
			put("04S", ACCESO_OPERACIONES_BLOQUEO_PF);
			put("05S", ACCESO_OPERACIONES_BLOQUEO_SO);
			put("06S", ACCESO_CONSULTAS_CONSULTA);
			put("07S", ACCESO_AUDITORIA_CONSULTA);
			put("08S", ACCESO_ATRIBUTOS);
 	 
		}
		
	};

}