package pe.com.bn.maie.tranversal.util;

 
import java.util.Random;

 


public class StringsUtils {
	public static String obtenerCadenaDespuesDePunto(String cadenaConPunto) {
		return cadenaConPunto.substring(cadenaConPunto.lastIndexOf('.') + 1);
	}

	public static String concatenarCadena(Object... objetos) {
		StringBuffer sb = new StringBuffer();
		for (Object objeto : objetos) {
			sb.append(objeto.toString());
		}
		return sb.toString();
	}

	public static String[] obtenerSubCadenas(String cadena, String separador) {
		return cadena.split(separador, 2);
	}

	public static String obtenerCadenaDespuesDe(String cadena, String separador) {
		if (cadena != null && cadena.length() > 0) {
			return cadena.substring(cadena.lastIndexOf(separador) + 1);
		}
		return cadena;
	}

	public static String obtenerCadenaAntesDe(String cadena, String separador) {
		if (cadena != null && cadena.length() > 0) {
			return cadena.substring(0, cadena.lastIndexOf(separador));
		}
		return cadena;
	}

	public static String removerUltimoCaracter(String cadena) {
		if (cadena != null && cadena.length() > 0) {
			cadena = cadena.substring(0, cadena.length() - 1);
		}
		return cadena;
	}

	public static String random() {
		Random a = new Random();
		a.setSeed(System.currentTimeMillis());
		int ia = a.nextInt(900000) + 100000;
		return Integer.toString(ia);
	}
	
	public static String quitarCeroIzquierdaString(String valor){
		String rpta="";
	
		long numero=Long.parseLong(valor);
		rpta = String.valueOf(numero);
		return rpta;
	}
	
	public static String llenarCerosAlaIzquierdaV2(String text, int longitud){
		
		 String formatted = String.format("%0" + longitud + "d", Long.valueOf(text));
		
		return formatted;
	}
	
	public static String formateo_DNI(String valor){
		String n = "";
		
		try{
			n = valor.substring(4,12);
			
		}catch(Exception e){}
			
		return n;
	}

	public static String llenarEspaciosAlaDerecha(String text, int longitud) {
		
		String formatted = ""; 
		
		int cantEspacios = longitud - text.length();
		
		if (cantEspacios > 0) {
			
			formatted = String.format("%-" + cantEspacios + "s", text);
			
		} else {
			
			formatted = text;
		}
		
		return formatted;
	}
	
	public static String limitarCadenaLongitudMaxima(String texto, int longMax) {
		
		String result = ""; 
		
		if ( texto == null ) {
			return result;
		}
		
		if ( texto.length() > longMax ) {
			result = texto.substring(0, longMax);
		} else {
			result = texto;
		}
		
		return result;
	}
	
	public static String soloTextoAlfaNumerico(String texto) {

		String result = texto.replaceAll("[^a-zA-Z0-9 ]", " "); 
		
		return result;
	}
	public static String ofuscarNumeroTarjeta(String numeroTarjeta) {
		if (numeroTarjeta == null || numeroTarjeta.length() < 8) {
			return numeroTarjeta; 
		}
		if (numeroTarjeta.length() == 19) {
			numeroTarjeta = numeroTarjeta.substring(3);
		}
		int longitud = numeroTarjeta.length();
		if (longitud < 8) {
			return numeroTarjeta;
		}
		String primeros4 = numeroTarjeta.substring(0, 4);
		String ultimos4 = numeroTarjeta.substring(longitud - 4);
		StringBuilder ofuscado = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			ofuscado.append("*");
		}
		return primeros4 + ofuscado.toString() + ultimos4;
	}
	

	public static String Codigo_error_atributos(String cod_error) {
		    switch (cod_error) {
		        case "000":
		            return "Sin error";
		        case "006":
		            return "Valor no debe ser cero";
		        case "007":
		            return "Valor no debe ser espacios";
		        case "008":
		            return "Valor debe ser numérico";
		        case "010":
		            return "Valores válidos: S= Sí, N= No";
		        case "011":
		            return "Campo tiene caracteres inválidos";
		        case "012":
		            return "Fecha debe tener formato ddmmyyyy";
		        case "013":
		            return "Fecha inicial es mayor a fecha final de viaje";
		        case "401":
		            return "No existe tarjeta en registro de Consulta";
		        case "990":
		            return "Data Inválida";
		        case "991":
		            return "TimeOut";
		        case "992":
		            return "Error en desencriptación";
		        case "993":
		            return "Falla en la Conexión";
		        case "999":
		            return "Error desconocido";
		        default:
		            return "Código de error no reconocido: " + cod_error;
		    }	
	}
	
	public static String CelrellenarConCeros(String numero, int longitud) {
        if (numero == null) numero = "";
        return String.format("%" + longitud + "s", numero).replace(' ', '0');
    }

    
    public static String CorreorellenarConEspacios(String texto, int longitud) {
        if (texto == null) texto = "";
        return String.format("%-" + longitud + "s", texto);
    }
    
    
    public static String obtenerEstado(String valor) {
        if ("S".equalsIgnoreCase(valor)) {
            return "Activado";
        } else if ("N".equalsIgnoreCase(valor)) {
            return "Desactivado";
        } else {
            return "Valor desconocido";
        }
    }
    
    public static String ofuscarTarjeta4(String numeroTarjeta) {
    	
        if (numeroTarjeta == null || numeroTarjeta.length() < 4) {
            return "00000000000";
        }

        int longitud = numeroTarjeta.length();
        String ultimos4 = numeroTarjeta.substring(longitud - 4);
        StringBuilder ofuscado = new StringBuilder();

        for (int i = 0; i < longitud - 4; i++) {
            ofuscado.append("*");
        }

        return ofuscado + ultimos4;
    }
    

        public static String completarConCerosLong(long numero) {
            return String.format("%08d", numero);
        }

    
    

    

}
