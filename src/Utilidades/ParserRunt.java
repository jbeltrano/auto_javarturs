package Utilidades;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser que extrae datos de un vehiculo desde el texto
 * copiado de la pagina web del RUNT (Colombia).
 * 
 * Uso:
 *   HashMap<String, String> datos = ParserRunt.parsear(textoPegado);
 *   String placa = datos.get("placa");
 */
public class ParserRunt {

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

    private static String obtenerCampo(String texto, String etiquetaRegex) {
        Pattern p = Pattern.compile(etiquetaRegex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(texto);
        if (m.find()) {
            String valor = m.group(1).trim();
            return valor.isEmpty() ? null : valor;
        }
        return null;
    }
}
