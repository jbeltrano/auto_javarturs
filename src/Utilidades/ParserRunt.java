package Utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser que extrae datos de un vehiculo desde el texto
 * copiado de la pagina web del RUNT (Colombia).
 * 
 * Uso:
 *   HashMap<String, String> datos = ParserRunt.parsear(textoPegado);
 *   String placa = datos.get("placa");
 * 
 *   HashMap<String, String> docs = ParserRunt.parsearDocumentos(textoPegado);
 *   String soat = docs.get("soat_fecha_fin");
 */
public class ParserRunt {

    private static final Pattern PATRON_FECHA = Pattern.compile("(\\d{2}/\\d{2}/\\d{4})");

    public static HashMap<String, String> parsear(String texto) {
        HashMap<String, String> datos = new HashMap<>();

        if (texto == null || texto.trim().isEmpty()) {
            return datos;
        }

        texto = texto.replaceAll("(?m):\\s*\\n\\s*", ": ");

        datos.put("placa",           obtenerCampo(texto, "PLACA DEL VEH[IÍ]CULO\\s*:\\s*(.+)"));
        datos.put("clase_vehiculo",  obtenerCampo(texto, "Clase de vehículo\\s*:\\s*(.+)"));
        datos.put("marca",           obtenerCampo(texto, "Marca\\s*:\\s*(.+)"));
        datos.put("linea",           obtenerCampo(texto, "L[ií]nea\\s*:\\s*(.+)"));
        datos.put("modelo",          obtenerCampo(texto, "Modelo\\s*:\\s*(.+)"));
        datos.put("color",           obtenerCampo(texto, "Color\\s*:\\s*(.+)"));
        datos.put("numero_motor",    obtenerCampo(texto, "N[uú]mero de motor\\s*:\\s*(.+)"));
        datos.put("numero_chasis",   obtenerCampo(texto, "N[uú]mero de chasis\\s*:\\s*(.+)"));
        datos.put("cilindrada",      obtenerCampo(texto, "Cilindraje\\s*:\\s*(.+)"));
        datos.put("carroceria",      obtenerCampo(texto, "Tipo de carrocer[ií]a\\s*:\\s*(.+)"));
        datos.put("combustible",     obtenerCampo(texto, "Tipo Combustible\\s*:\\s*(.+)"));
        datos.put("pasajeros",       obtenerCampo(texto, "Capacidad Pasajeros Sentados\\s*:\\s*(.+)"));
        datos.put("servicio",        obtenerCampo(texto, "Tipo de servicio\\s*:\\s*(.+)"));

        return datos;
    }

    /**
     * Extrae datos de documentos (SOAT, polizas, RTM, TOP)
     * desde el texto copiado del RUNT. Las fechas se retornan
     * en formato dd/MM/yyyy.
     *
     * Claves retornadas: placa, soat_fecha_fin, polizas_fecha_fin,
     * rtm_fecha_fin, top_numero, top_fecha_fin
     */
    public static HashMap<String, String> parsearDocumentos(String texto) {
        HashMap<String, String> datos = new HashMap<>();

        if (texto == null || texto.trim().isEmpty()) {
            return datos;
        }

        texto = texto.replaceAll("(?m):\\s*\\n\\s*", ": ");

        datos.put("placa",             obtenerCampo(texto, "PLACA DEL VEH[IÍ]CULO\\s*:\\s*(.+)"));
        datos.put("soat_fecha_fin",    obtenerFechaSeccionVigente(texto, "Póliza SOAT", "Registros por página", "VIGENTE"));
        datos.put("polizas_fecha_fin", obtenerFechaSeccionVigente(texto, "Pólizas de Responsabilidad Civil", "Certificado de revisión", "VIGENTE"));
        datos.put("rtm_fecha_fin",     obtenerFechaRtmVigente(texto));
        datos.put("top_numero",        obtenerCampo(texto, "Nro\\.\\s*Tarjeta de Operaci[oó]n\\s*:\\s*(.+)"));
        datos.put("top_fecha_fin",     obtenerCampo(texto, "Fecha Fin de Vigencia\\s*:\\s*(\\d{2}/\\d{2}/\\d{4})"));

        return datos;
    }

    private static String obtenerCampo(String texto, String etiquetaRegex) {
        Pattern p = Pattern.compile(etiquetaRegex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(texto);
        if (m.find()) {
            String valor = m.group(1).trim();
            return valor.isEmpty() ? null : valor;
        }
        return null;
    }

    /**
     * Busca en una sección tabular (SOAT, Polizas) la línea con
     * el estado indicado (VIGENTE) y retorna la última fecha
     * dd/MM/yyyy que aparece antes de esa línea.
     */
    private static String obtenerFechaSeccionVigente(String texto, String marcadorInicio, String marcadorFin, String valorEstado) {
        int inicio = texto.indexOf(marcadorInicio);
        if (inicio < 0) return null;

        int fin = texto.length();
        int idxFin = texto.indexOf(marcadorFin, inicio + marcadorInicio.length());
        if (idxFin > 0) fin = idxFin;

        String seccion = texto.substring(inicio, fin);
        String[] lineas = seccion.split("\\n");

        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i].trim().equals(valorEstado)) {
                for (int j = i - 1; j >= 0; j--) {
                    Matcher m = PATRON_FECHA.matcher(lineas[j]);
                    if (m.find()) {
                        return m.group(1);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Extrae la fecha de vigencia de la RTM vigente.
     * Las filas RTM se agrupan por "REVISION TECNICO-MECANICO"
     * y la columna Vigente (SI/NO) está en la posición 4 de cada fila.
     */
    private static String obtenerFechaRtmVigente(String texto) {
        int inicio = texto.indexOf("Certificado de revisión técnico mecánica");
        if (inicio < 0) return null;

        int fin = texto.indexOf("Registros por página", inicio);
        if (fin < 0) fin = texto.length();

        String seccion = texto.substring(inicio, fin);
        String[] lineas = seccion.split("\\n");

        List<List<String>> filas = new ArrayList<>();
        List<String> filaActual = null;

        for (String linea : lineas) {
            String trimmed = linea.trim();
            if (trimmed.toUpperCase().startsWith("REVISION TECNICO")) {
                if (filaActual != null) filas.add(filaActual);
                filaActual = new ArrayList<>();
            }
            if (filaActual != null) {
                filaActual.add(trimmed);
            }
        }
        if (filaActual != null) filas.add(filaActual);

        for (List<String> fila : filas) {
            if (fila.size() > 4 && fila.get(4).equals("SI")) {
                Matcher m = PATRON_FECHA.matcher(fila.get(2));
                if (m.find()) return m.group(1);
            }
        }
        return null;
    }
}
