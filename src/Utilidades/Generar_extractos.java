package Utilidades;

import java.io.IOException;
import java.sql.SQLException;
import Base.Base;

public class Generar_extractos {

    public static String generar_extracto_mensual_excel(String placa, int consecutivo, String url)throws SQLException, IOException, NullPointerException{
        Extracto extracto;
        Base base = new Base(url);
        boolean parque_automotor = true;
        String[] datos_extracto;
        String[] datos_vehiculo;
        String[] datos_contratante;
        String[] datos_origen;
        String[] datos_destino;
        String[][] datos_conductores;
        String[] vehiculo_empresa_externa;
        String año;
        int datos_tipo_contrato;
        String localizacion_fichero = System.getProperty("user.home") + "\\Desktop\\Extractos_mensuales";

        try{
        // inicializacion del objeto para modificar la plantilla de extractos
        extracto = new Extracto("src\\Formatos\\Extracto.xlsx");
        datos_extracto = base.consultar_uno_extracto_mensual(placa, consecutivo);
        datos_contratante = base.consultar_uno_contrato_mensual(Integer.parseInt(datos_extracto[2]));
        datos_conductores = base.consultar_conductor_has_vehiculo(placa);
        datos_origen = base.consultar_uno_ciudades(datos_extracto[5]);
        datos_destino = base.consultar_uno_ciudades(datos_extracto[6]);
        datos_vehiculo = base.consultar_uno_vw_vehiculo_extracto(placa);
        datos_tipo_contrato = base.consultar_tipo_contrato_mensual(Integer.parseInt(datos_contratante[0]));
        parque_automotor = Boolean.parseBoolean(base.consultar_uno_vehiculo(placa)[16]);
        vehiculo_empresa_externa = base.consultar_uno_vehiculo_externo(placa);

        if(datos_vehiculo[0] == null){
            NullPointerException ex = new NullPointerException("El vehiculo " + placa + ".\nNo posee documentos");
            throw ex;
        }
        año = datos_extracto[4].split("-")[0];

        // Modificando como tal el documento
        extracto.set_numero_principal(año, datos_extracto[2], datos_extracto[1]);
        extracto.set_contrato(datos_extracto[2]);
        extracto.set_contratante(datos_contratante[3], datos_contratante[1], datos_contratante[2]);
        extracto.set_tipo_contrato(Extracto.TIPO_CONTRATO_EMPRESARIAL);

        if(Integer.parseInt(datos_origen[0]) != 0 && Integer.parseInt(datos_destino[0]) != 0){
            extracto.set_origen_destino(datos_origen[1], datos_origen[2], datos_destino[1], datos_destino[2]);
        }else{
            if(Integer.parseInt(datos_origen[0]) != 0){
                extracto.set_ruta(datos_origen[1], datos_origen[2]);
            }else if(Integer.parseInt(datos_destino[0]) != 0){
                extracto.set_ruta(datos_destino[1], datos_destino[2]);
            }
        }
        extracto.set_fecha_inicial(datos_extracto[3]);
        extracto.set_fecha_final(datos_extracto[4]);
        extracto.set_datos_vehiculo(placa, Integer.parseInt(datos_vehiculo[1]), datos_vehiculo[2], datos_vehiculo[3], Integer.parseInt(datos_vehiculo[4]), Integer.parseInt(datos_vehiculo[5]));

        if(datos_tipo_contrato == Extracto.ESTUDIANTIL){

            extracto.set_tipo_contrato(Extracto.ESTUDIANTIL);

        }else if(datos_tipo_contrato == Extracto.EMPRESARIAL){

            extracto.set_tipo_contrato(Extracto.EMPRESARIAL);

        }else if(datos_tipo_contrato == Extracto.PERSONALIZADO){

            extracto.set_tipo_contrato(Extracto.PERSONALIZADO);

        }else{

            extracto.set_tipo_contrato(Extracto.PARTICULAR);

        }

        if(!parque_automotor){
            extracto.set_convenio(vehiculo_empresa_externa[1], vehiculo_empresa_externa[2]);
        }

        if(datos_conductores.length > 1){
            extracto.set_conductor1(datos_conductores[1][3], datos_conductores[1][2], datos_conductores[1][5]);
        } 
        if(datos_conductores.length > 2){
            extracto.set_conductor2(datos_conductores[2][3], datos_conductores[2][2], datos_conductores[2][5]);
        } 
        if(datos_conductores.length > 3){
            extracto.set_conductor3(datos_conductores[3][3], datos_conductores[3][2], datos_conductores[3][5]);
        }
        if(datos_conductores.length < 2){
            
            NullPointerException ex = new NullPointerException("El vehiculo " + placa + ".\nNo tiene conductores registrados");
            throw ex;
            
        }

        //extracto.set_responsable(nombre, document0, celular, direccion);
        extracto.set_responsable(datos_contratante[5], datos_contratante[4], datos_contratante[6], datos_contratante[7]);
        
        extracto.guardar(localizacion_fichero, "MENSUAL (" + consecutivo + ")");

        return localizacion_fichero + placa +" MENSUAL (" + consecutivo + ")";
        }finally{
            base.close();
        }
        
    }
}
