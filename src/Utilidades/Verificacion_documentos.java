package Utilidades;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Base.Documentos;
import Base.Licencia;

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
                                            String fecha_soat,
                                            String fecha_rtm,
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
        if(rtm > 3 && soat > 8 && seguros > 30 && top > 30){
            return null;
        }

        // Si alguno de los documentos esta por vencer, se agrega al mensaje de resultado
        resultado[1] += (rtm >= 0 && rtm <= 3)?"Tecnomecanica, vence en " + rtm + " dias\n":"";
        resultado[1] += (soat >= 0 && soat <= 8)?"SOAT, vence en " + soat + " dias\n":"";
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
                                            String fecha_soat,
                                            String fecha_rtm
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
        if(rtm > 3 && soat > 8){
            return null;
        }

        // Si alguno de los documentos esta por vencer, se agrega al mensaje de resultado
        resultado[1] += (rtm >= 0 && rtm <= 3)?"Tecnomecanica, vence en " + rtm + " dias\n":"";
        resultado[1] += (soat >= 0 && soat <= 8)?"SOAT, vence en " + soat + " dias\n":"";

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

        if(cantidad_dias > 4){
            return null;
        }

        resultado[1] += (cantidad_dias >= 0 && cantidad_dias <= 4)?
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

    public static ArrayList<String[]> verificar_documentos(){

        ArrayList<String[]> mensajes = new ArrayList<>();
        String[] mensaje_auxiliar;

        Documentos base_doc_vehiculos = null;
        Licencia base_licencias = null;
        try{

            base_doc_vehiculos = new Documentos();
            base_licencias = new Licencia();

            String[][] datos_vehiculos = base_doc_vehiculos.consultar_documentos("");
            String[][] datos_licencias = base_licencias.consultar_licencia();
            

            for(int i = 1; i < datos_vehiculos.length; i++){

                if(datos_vehiculos[i][7] == "NULL"){

                    mensaje_auxiliar = Utilidades.Verificacion_documentos.verificar_documentos_vehiculo(
                        datos_vehiculos[i][0],
                        datos_vehiculos[i][2],
                        datos_vehiculos[i][3]
                    );

                }else {

                    mensaje_auxiliar = Utilidades.Verificacion_documentos.verificar_documentos_vehiculo(
                        datos_vehiculos[i][0],
                        datos_vehiculos[i][2],
                        datos_vehiculos[i][3],
                        datos_vehiculos[i][4],
                        datos_vehiculos[i][7]
                    );
                }
                
                if(mensaje_auxiliar != null){
                    mensajes.add(mensaje_auxiliar);
                }
                
            }

            for(int j = 1; j < datos_licencias.length; j++){

                String[] mensaje_licencia = Utilidades.Verificacion_documentos.verificar_documentos_conductor(
                    datos_licencias[j][1],
                    datos_licencias[j][3]
                );

                if(mensaje_licencia != null){
                    mensajes.add(mensaje_licencia);
                }
            }
        }catch(IOException | SQLException ex){

            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }finally{
            if(base_doc_vehiculos != null) base_doc_vehiculos.close();
            if(base_licencias != null) base_licencias.close();
        }
    
        return mensajes;
    }
    
}
