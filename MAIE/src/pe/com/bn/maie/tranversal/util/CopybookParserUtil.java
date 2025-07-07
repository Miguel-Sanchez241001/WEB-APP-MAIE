package pe.com.bn.maie.tranversal.util; // O un paquete más adecuado para utilidades

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.com.bn.maie.persistencia.dto.CamposTrama; // Importa tu DTO

public class CopybookParserUtil {

    /**
     * Parsea un archivo Copybook (.cpy) y extrae los campos de trama.
     * Ignora líneas de comentario, campos binarios (COMP, COMP-3) y se enfoca en PIC X y PIC 9.
     *
     * @param rutaArchivo La ruta completa del archivo Copybook.
     * @return Una lista de objetos CamposTrama con la información extraída.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<CamposTrama> parsearCopybook(String rutaArchivo) throws IOException {
        List<CamposTrama> campos = new ArrayList<>();
        BufferedReader lector = null;
        try {
            lector = new BufferedReader(new FileReader(rutaArchivo));
            String linea;

            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();

                // 1. Ignorar comentarios (líneas que empiezan con '*') o líneas sin "PIC"
                if (linea.startsWith("*") || !linea.toUpperCase().contains("PIC")) {
                    continue;
                }

                // 2. Separar en 2 partes: antes y después de PIC
                int posPic = linea.toUpperCase().indexOf("PIC");
                if (posPic == -1) continue; // No debería pasar si ya se verificó antes, pero por seguridad

                String parteIzquierda = linea.substring(0, posPic).trim();
                String parteDerecha = linea.substring(posPic + 3).trim(); // quita "PIC"

                // 3. Si contiene COMP o COMP-3, ignorar (campos binarios)
                if (parteDerecha.toUpperCase().contains("COMP")) {
                    continue;
                }

                // 4. Obtener nombre del campo
                String nombreCampo = null;
                // Buscar el segundo token numérico o alfabético después del nivel (ej. "05 CAMPO-NOMBRE")
                Pattern namePattern = Pattern.compile("^\\d+\\s+([A-Z0-9-]+)");
                Matcher nameMatcher = namePattern.matcher(parteIzquierda);
                if (nameMatcher.find()) {
                    nombreCampo = nameMatcher.group(1);
                } else {
                    // Si no se encuentra el patrón típico, intentar una extracción más simple
                    String[] tokensIzquierda = parteIzquierda.split("\\s+");
                    if (tokensIzquierda.length >= 2) {
                        nombreCampo = tokensIzquierda[1];
                    }
                }

                if (nombreCampo == null || nombreCampo.isEmpty()) {
                    continue; // No se pudo extraer el nombre del campo, saltar línea
                }

                // 5. Determinar tipo de dato (B08_TIPO_DATO) y tipo de campo (B08_TIPO_CAMPO)
                String tipoDato; // N, A, AN, D (Numérico, Alfabético, Alfanumérico, Fecha/Decimal)
                Integer tipoCampo; // 1: Cadena, 2: Entero, 3: Decimal

                int longitud = 0;
                Matcher par = Pattern.compile("\\((\\d+)\\)").matcher(parteDerecha);
                if (par.find()) {
                    longitud = Integer.parseInt(par.group(1));
                } else {
                    // Si no hay paréntesis, buscar longitud directa (ej. X(10) vs XXXXX)
                    Matcher directoX = Pattern.compile("X+").matcher(parteDerecha);
                    if (directoX.find()) {
                        longitud = directoX.group(0).length();
                    }
                    Matcher directo9 = Pattern.compile("9+").matcher(parteDerecha);
                    if (directo9.find()) {
                        longitud = directo9.group(0).length();
                    }
                }


                if (parteDerecha.toUpperCase().startsWith("X")) {
                    tipoDato = "AN"; // Alfanumérico
                    tipoCampo = 1; // Cadena
                } else if (parteDerecha.toUpperCase().startsWith("9")) {
                    if (parteDerecha.toUpperCase().contains("V")) {
                        tipoDato = "N"; // Numérico con decimales (ej. 9V99)
                        tipoCampo = 3; // Decimal
                        // Para la longitud, si es 9(5)V9(2), la longitud total es 7.
                        // La lógica actual de `longitud` ya suma los dígitos.
                        Matcher numDecimal = Pattern.compile("9+\\((\\d+)\\)V9+\\((\\d+)\\)").matcher(parteDerecha);
                        if (numDecimal.find()) {
                            longitud = Integer.parseInt(numDecimal.group(1)) + Integer.parseInt(numDecimal.group(2));
                        } else {
                            // Caso 99V99
                            Matcher numDecimalSimple = Pattern.compile("9+V9+").matcher(parteDerecha);
                            if(numDecimalSimple.find()) {
                                String[] parts = numDecimalSimple.group(0).split("V");
                                longitud = parts[0].length() + parts[1].length();
                            }
                        }
                    } else {
                        tipoDato = "N"; // Numérico entero
                        tipoCampo = 2; // Entero
                    }
                } else {
                    tipoDato = "A"; // Por defecto, Alfabético si no es X ni 9
                    tipoCampo = 1; // Cadena
                }

                // Crear objeto CamposTrama
                CamposTrama campoTrama = new CamposTrama();
                campoTrama.setTagName(nombreCampo);
                campoTrama.setTipoDato(tipoDato);
                campoTrama.setTipoCampo(tipoCampo);
                campoTrama.setLongitud(longitud);
                // Valores por defecto para otros campos que no se extraen del copybook
                campoTrama.setOrden(0); // Se asignará un orden real después
                campoTrama.setIndRelleno(0);
                campoTrama.setValorRelleno(null);
                campoTrama.setValorDefecto(null);
                campoTrama.setAlineacion(1); // 1: Derecha por defecto para numéricos, 2: Izquierda para alfanuméricos
                if (tipoDato.equals("AN") || tipoDato.equals("A")) {
                    campoTrama.setAlineacion(2); // Izquierda para texto
                }

                campos.add(campoTrama);
            }
        } finally {
            if (lector != null) {
                lector.close();
            }
        }
        return campos;
    }
}
