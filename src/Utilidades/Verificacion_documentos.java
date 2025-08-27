package Utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Verificacion_documentos {
    
    /**
     * Este metodo se encarga de verificar los documentos de un vehiculo
     * @param placa
     * @param fecha_rtm
     * @param fecha_soat
     * @param fecha_seguros
     * @param fecha_top
     * @return
     */
    public static String[] verificar_documentos_vehiculo(
                                            String placa,
                                            String fecha_rtm,
                                            String fecha_soat,
                                            String fecha_seguros,
                                            String fecha_top
                                        ){

        String[] resultado = new String[2];
        resultado[0] = "";
        resultado[1] = "";

        // Mensaje de resultado, que se retornara al final del metodo
        resultado[0] = "El vehicuo de placas " + placa + " ,tiene las siguientes novedades:";
        

        // Guardan la cantidad de dias faltantes respecto a cada documento
        long rtm =  comparar_fecha(fecha_rtm);
        long soat = comparar_fecha(fecha_soat);
        long seguros = comparar_fecha(fecha_seguros);
        long top = comparar_fecha(fecha_top);

        // Si todos los documentos estan vigentes, retorna una cadena vacia
        if(rtm > 30 && soat > 30 && seguros > 30 && top > 30){
            return null;
        }

        // Si alguno de los documentos esta por vencer, se agrega al mensaje de resultado
        resultado[1] += (rtm >= 0 && rtm <= 30)?"Tecnomecanica, vence en " + rtm + " dias\n":"";
        resultado[1] += (soat >= 0 && soat <= 30)?"SOAT, vence en " + soat + " dias\n":"";
        resultado[1] += (seguros >= 0 && seguros <= 30)?"Seguros rcc y rce, vencen en " + seguros + " dias\n" :"";
        resultado[1] += (top >= 0 && top <= 30)?"Tarjeta de Operacion, vence en " + top + " dias\n" :"";

        // Dado cada resultado, lo adiciona en el meensaje de resultado
        resultado[1] += (rtm < 0)?"Tecnomecanica, vencio el " + fecha_rtm + "\n":"";
        resultado[1] += (soat < 0)?"SOAT, vencio el " + fecha_soat + "\n":"";
        resultado[1] += (seguros < 0)?"Seguros rcc y rce, vencieron el " + fecha_seguros + "\n" :"";
        resultado[1] += (top < 0)?"Tarjeta de Operacion, vencio el " + fecha_top + "\n" :"";
        
        return resultado;
    }

    /**
     * Este metodo se encarga de verificar los documentos de un vehiculo
     * @param placa
     * @param fecha_rtm
     * @param fecha_soat
     * @return
     */
    public static String[] verificar_documentos_vehiculo(
                                            String placa,
                                            String fecha_rtm,
                                            String fecha_soat
                                        ){

        String[] resultado = new String[2];
        resultado[0] = "";
        resultado[1] = "";
        // Mensaje de resultado, que se retornara al final del metodo
        resultado[0] = "El vehicuo de placas " + placa + " ,tiene las siguientes novedades:";

        // Guardan la cantidad de dias faltantes respecto a cada documento
        long rtm =  comparar_fecha(fecha_rtm);
        long soat = comparar_fecha(fecha_soat);

        // Si todos los documentos estan vigentes, retorna una cadena vacia
        if(rtm > 30 && soat > 30){
            return null;
        }

        // Si alguno de los documentos esta por vencer, se agrega al mensaje de resultado
        resultado[1] += (rtm >= 0 && rtm <= 30)?"Tecnomecanica, vence en " + rtm + " dias\n":"";
        resultado[1] += (soat >= 0 && soat <= 30)?"SOAT, vence en " + soat + " dias\n":"";

        // Dado cada resultado, lo adiciona en el meensaje de resultado
        resultado[1] += (rtm < 0)?"Tecnomecanica, vencio el " + fecha_rtm + "\n":"";
        resultado[1] += (soat < 0)?"SOAT, vencio el " + fecha_soat + "\n":"";
        
        return resultado;
    }


    /**
     * Este metodo se encarga de verificar los documentos de un conductor
     * @param nombre
     * @param fecha_pase
     * @return
     */
    public static String[] verificar_documentos_conductor(
                                            String nombre,
                                            String fecha_pase
                                        ){

        String[] resultado = new String[2];
        resultado[0] = "";
        resultado[1] = "";
        resultado[0] = "El conductor " + nombre + " tiene las siguientes novedades:";

        long cantidad_dias = comparar_fecha(fecha_pase);

        if(cantidad_dias > 30){
            return null;
        }

        resultado[1] += (cantidad_dias >= 0 && cantidad_dias <= 30)?
            "El pase de " + nombre + " vence en " + cantidad_dias + " dias.\n":"";
        
        resultado[1] += (cantidad_dias < 0)?
            "Pase, vencio el " + fecha_pase + "\n":"";


        return resultado;
    }


    /**
     * Este metodo se encarga de comprar la fecha actual
     * con una fecha dada y retorna la diferencia en dias
     * @param fecha Es la fecha a comparar con la fecha actual o del sistema
     * @return retorna un long con la cantidad de dias
     */
    public static long comparar_fecha(String fecha){

        long cantidad_dias;

        LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
        LocalDate fecha_tabla = LocalDate.parse(fecha,formatter);       // Aplica el formato que se declaro anteriormente
        cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

        return cantidad_dias;

    }
}
