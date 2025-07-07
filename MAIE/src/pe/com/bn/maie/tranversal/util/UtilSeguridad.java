package pe.com.bn.maie.tranversal.util;

import java.util.List;

import pe.com.bn.maie.persistencia.dto.Permiso;
import pe.com.bn.maie.tranversal.util.constantes.ConstantesSeguridad;

public class UtilSeguridad {
public static long getRolByPermisos(List<Permiso> permisosUsuario) {
		
		/**
		02S		Bloqueo MA
		03S		Bloqueo RA
		04S		Bloqueo PF
		05S		Bloqueo SO
		**/

		long rol = -1;
		
		for (int i = 0; i < permisosUsuario.size(); i++) {
			
			Permiso permiso = permisosUsuario.get(i);
			
			if (permiso.getCodigo().equalsIgnoreCase("02S")) {
				
				rol = ConstantesSeguridad.BLOQUEO_MA;
				break;
			}

			if (permiso.getCodigo().equalsIgnoreCase("03S")) {
				
				rol = ConstantesSeguridad.BLOQUEO_RA;
				break;
			}

			if (permiso.getCodigo().equalsIgnoreCase("04S")) {
				
				rol = ConstantesSeguridad.BLOQUEO_PF;
				break;
			}

			if (permiso.getCodigo().equalsIgnoreCase("05S")) {
				
				rol = ConstantesSeguridad.BLOQUEO_SO;
				break;
			}
			if (permiso.getCodigo().equalsIgnoreCase("07S")) {
				
				rol = ConstantesSeguridad.AUDITORIA;
				break;
			}
		}
		
		return rol;
	}
}
