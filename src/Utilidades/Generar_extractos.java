package Utilidades;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import Base.BContrato_ocasional;
import Base.Base;
import Base.Ciudad;
import Base.Contratante;
import Base.Contrato_mensual;
import Base.Vehiculo;
import Base.Vehiculo_has_conductor;
import Base.Extractos;

public class Generar_extractos {

    private static Leer_rutas rutas;
    
    static {

        try {
            rutas = new Leer_rutas();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Este metodo se encarga de exportar los extractos
     * en formato pdf carecterizando el archivo como extracto
     * mensual con placa y id del vehiculo
     * @param placa
     * @param consecutivo
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws NullPointerException
     */
    public static String generar_extracto_mensual_excel(String placa, int consecutivo)throws SQLException, IOException, NullPointerException{
        Leer_rutas rutas = new Leer_rutas();
        Extracto extracto = null;
        Base base = new Base();
        Vehiculo base_vehiculo = new Vehiculo();
        Ciudad base_ciudad = new Ciudad();
        Vehiculo_has_conductor base_vhc = new Vehiculo_has_conductor();
        Contrato_mensual base_cm = new Contrato_mensual();
        Extractos base_extractos = new Extractos();
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
        String localizacion_fichero = System.getProperty("user.home") + "\\" + rutas.get_ruta(Leer_rutas.EXTRACTOS_MENSUALES) + "\\";
        String localizacion2_fichero = rutas.get_ruta(Leer_rutas.EXTRACTOS_MENSUALES_DRIVE) + "\\";

        

        try{
            // inicializacion del objeto para modificar la plantilla de extractos
            extracto = new Extracto(rutas.get_ruta(Leer_rutas.PLANTILLA_EXTRACTOS));
            datos_extracto = base_extractos.consultar_uno_extracto_mensual(placa, consecutivo);
            datos_contratante = base_cm.consultar_uno_contrato_mensual(Integer.parseInt(datos_extracto[2]));
            datos_conductores = base_vhc.consultar_conductor_has_vehiculo(placa);
            datos_origen = base_ciudad.consultar_uno_ciudades(datos_extracto[5]);
            datos_destino = base_ciudad.consultar_uno_ciudades(datos_extracto[6]);
            datos_vehiculo = base_extractos.consultar_uno_vw_vehiculo_extracto(placa);
            datos_tipo_contrato = base_cm.consultar_tipo_contrato_mensual(Integer.parseInt(datos_contratante[0]));
            parque_automotor = Boolean.parseBoolean(base_vehiculo.consultar_uno_vehiculo(placa)[16]);
            vehiculo_empresa_externa = base_extractos.consultar_uno_vehiculo_externo(placa);

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

            
            extracto.set_tipo_contrato(datos_tipo_contrato);

            if(!parque_automotor){
                extracto.set_convenio(vehiculo_empresa_externa[2], vehiculo_empresa_externa[3]);
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
            extracto.guardar(localizacion2_fichero);
            
            
            return localizacion_fichero + placa +" MENSUAL (" + consecutivo + ")";
            
        }finally{
            base.close();
            base_vehiculo.close();
            base_ciudad.close();
            base_vhc.close();
            base_extractos.close();
            base_cm.close();
            extracto.close();
            
        }
        
    }

    /**
     * Este metodo se encarga de generar un extracto ocasional
     * para exportarlo en pdf con su respectivo contrato que
     * tambien sera exportado en formato pdf
     * @param placa
     * @param consecutivo
     * @param contrato
     * @param band Define si se muestra la ruta entre origen y destino
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws NullPointerException
     */
    public static String generar_extracto_ocasional(String placa, int consecutivo,int contrato, boolean band)throws SQLException, IOException, NullPointerException{
        
        Extracto extracto = null;
        Base base = new Base();
        Vehiculo base_vehiculo = new Vehiculo();
        Ciudad base_ciudad = new Ciudad();
        Vehiculo_has_conductor base_vhc = new Vehiculo_has_conductor();
        BContrato_ocasional base_co = new BContrato_ocasional();
        Contratante base_contratante = new Contratante();
        Extractos base_extracto = new Extractos();
        boolean parque_automotor = true;
        String sub_ocasional = "OCASIONAL ";
        String[] datos_per_contratante;
        String[] datos_vehiculo;
        String[] datos_contratante;
        String[] datos_origen;
        String[] datos_destino;
        String[][] datos_conductores;
        String[] vehiculo_empresa_externa;
        String[] placas_contrato;
        String año;
        String localizacion_contrato;
        int datos_tipo_contrato;
        String localizacion_fichero = System.getProperty("user.home") + "\\" + rutas.get_ruta(Leer_rutas.EXTRACTOS_OCASIONALES) + "\\";
        String localizacion2_fichero = rutas.get_ruta(Leer_rutas.EXTRACTOS_OCASIONALES_DIRVE) +"\\";
        
        

        try{
            // inicializacion del objeto para modificar la plantilla de extractos
            extracto = new Extracto(rutas.get_ruta(Leer_rutas.PLANTILLA_EXTRACTOS));
            datos_contratante = base_co.consultar_uno_contrato_ocasional(contrato);
            datos_per_contratante = base_contratante.consultar_uno_contratante(datos_contratante[1]);
            datos_conductores = base_vhc.consultar_conductor_has_vehiculo(placa);
            datos_origen = base_ciudad.consultar_uno_ciudades(datos_contratante[4]);
            datos_destino = base_ciudad.consultar_uno_ciudades(datos_contratante[5]);
            datos_vehiculo = base_extracto.consultar_uno_vw_vehiculo_extracto(placa);
            datos_tipo_contrato = base_co.consultar_tipo_contrato_ocasional(Integer.parseInt(datos_contratante[0]));
            parque_automotor = Boolean.parseBoolean(base_vehiculo.consultar_uno_vehiculo(placa)[16]);
            vehiculo_empresa_externa = base_extracto.consultar_uno_vehiculo_externo(placa);
            placas_contrato = base_co.consultar_placas_contrato_ocasional(contrato);
            
            // Verifica si el vehiculo a insertar contiene documentos para seguir con el proceso
            if(datos_vehiculo[0] == null){
                NullPointerException ex = new NullPointerException("El vehiculo " + placa + ".\nNo posee documentos");
                throw ex;
            }
            año = datos_contratante[3].split("-")[0];

            // Modificando como tal el documento
            extracto.set_numero_principal(año, ""+contrato, ""+consecutivo);    // hace el set al numero principal
            extracto.set_contrato(""+contrato);     // realiza el set al contrato
            extracto.set_contratante(datos_per_contratante[2],  // Nombre del contratante
                                    datos_per_contratante[1],  // tipo de documento
                                    datos_per_contratante[0]); // Numero de documento
            extracto.set_tipo_contrato(Extracto.TIPO_CONTRATO_EMPRESARIAL);     // hace el set al tipo de contrato por defecto

            // Se encarga de hacer el set al origen y destino del extracto ocasional
            // Verificando si es origen destino o ruta como tal, si tiene solo un origen o solo un destino
            if(Integer.parseInt(datos_origen[0]) != 0 && Integer.parseInt(datos_destino[0]) != 0){
                extracto.set_origen_destino(datos_origen[1], datos_origen[2], datos_destino[1], datos_destino[2]);
            }else{
                if(Integer.parseInt(datos_origen[0]) != 0){
                    extracto.set_ruta(datos_origen[1], datos_origen[2]);
                }else if(Integer.parseInt(datos_destino[0]) != 0){
                    extracto.set_ruta(datos_destino[1], datos_destino[2]);
                }
            }
            
            // Establece las fechas iniciales y finales del extracto
            extracto.set_fecha_inicial(datos_contratante[2]);
            extracto.set_fecha_final(datos_contratante[3]);

            // hace un set a los datos del vehiculo
            extracto.set_datos_vehiculo(placa, 
                                        Integer.parseInt(datos_vehiculo[1]), 
                                        datos_vehiculo[2], 
                                        datos_vehiculo[3], 
                                        Integer.parseInt(datos_vehiculo[4]), 
                                        Integer.parseInt(datos_vehiculo[5]));

            // Verifica el tipo de contrato y si es necesario hace un set
            extracto.set_tipo_contrato(datos_tipo_contrato);


            // Verifica si el vehiculo es parte del parque automotor de la empresa
            // caso contrario simplemente continua con el proceso
            if(!parque_automotor){
                extracto.set_convenio(vehiculo_empresa_externa[1], vehiculo_empresa_externa[2]);
            }

            // Verifica los conductores que tiene el vehiculo para continuar con el proceso
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

            // hace el set del responsable del contrato
            extracto.set_responsable(datos_per_contratante[4], 
                                    datos_per_contratante[3], 
                                    datos_per_contratante[5], 
                                    datos_per_contratante[6]);
            
            // verifica si el extracto sale como extemporaneo o normal
            if(is_extemporaneo()){
                sub_ocasional = sub_ocasional + "EXTEMPORANEO";
            }else{
                sub_ocasional = sub_ocasional + "NORMAL";
            }

            // Guarda el extracto en la direccion indicada por el path, o el archivo de configuracion
            extracto.guardar(localizacion_fichero, sub_ocasional+" (" + consecutivo + ")");
            extracto.guardar(localizacion2_fichero);
            
            localizacion_contrato = 
                Generar_contratos_ocasionales.generar_contrato_ocasional(
                    placas_contrato,    // Estas son las placas que van a ir en el contrato
                    ""+contrato,        // Este es el numero del contrato
                    band                // Define si es una ruta o un origen y un destino
                    );

        // Retorna la direccion de guardado
        }finally{
            if(base != null) base.close();
            if(base_vehiculo != null) base_vehiculo.close();
            if(base_ciudad != null) base_ciudad.close();
            if(base_vhc != null) base_vhc.close();
            if(base_co != null) base_co.close();
            if(base_extracto != null) base_extracto.close();
            if(base_contratante != null) base_contratante.close();
            if(base_extracto != null) extracto.close();
        }
        
        return localizacion_fichero + placa + sub_ocasional +" (" + consecutivo + ")\n El contrato se guardo en: " + localizacion_contrato;

    }

    // La idea es que este se encarga de exportar los extractos por el contrato
    // de tal forma que exporta los extractos a los vehiculos que tienen este contrato
    // Y posteriormente genera el extracto mensual
    public static String generar_extracto_ocasional(int contrato, boolean band)throws IOException, SQLException, NullPointerException{
        
        Extracto extracto = null;
        Base base = new Base();
        Vehiculo base_vehiculo = new Vehiculo();
        Ciudad base_ciudad = new Ciudad();
        Vehiculo_has_conductor base_vhc = new Vehiculo_has_conductor();
        Extractos base_extractos = new Extractos();
        BContrato_ocasional base_co = new BContrato_ocasional();
        Contratante base_contratante = new Contratante();
        boolean parque_automotor = true;
        String sub_ocasional = "OCASIONAL ";
        String[] datos_per_contratante;
        String[] datos_vehiculo;
        String[] datos_contratante;
        String[] datos_origen;
        String[] datos_destino;
        String[][] datos_conductores;
        String[] vehiculo_empresa_externa;
        String año;
        String localizacion_contrato;
        int datos_tipo_contrato;
        int consecutivo;
        String placas_contrato[];    // Se utiliza para obtener las placas que hacen relacion al contrato
        String localizacion_fichero = System.getProperty("user.home") + "\\" + rutas.get_ruta(Leer_rutas.EXTRACTOS_OCASIONALES) + "\\";
        String localizacion2_fichero = rutas.get_ruta(Leer_rutas.EXTRACTOS_OCASIONALES_DIRVE) +"\\";

        

        try{
            // Consultando la cantidad de extractos a realizar con el msimo contrato
            placas_contrato = base_co.consultar_placas_contrato_ocasional(contrato);

            // inicializacion del objeto para modificar la plantilla de extractos
            extracto = new Extracto(rutas.get_ruta(Leer_rutas.PLANTILLA_EXTRACTOS));
            datos_contratante = base_co.consultar_uno_contrato_ocasional(contrato);
            datos_origen = base_ciudad.consultar_uno_ciudades(datos_contratante[4]);
            datos_destino = base_ciudad.consultar_uno_ciudades(datos_contratante[5]);
            datos_per_contratante = base_contratante.consultar_uno_contratante(datos_contratante[1]);
            datos_tipo_contrato = base_co.consultar_tipo_contrato_ocasional(Integer.parseInt(datos_contratante[0]));
            
            año = datos_contratante[3].split("-")[0];

            extracto.set_contrato(""+contrato);     // realiza el set al contrato
            extracto.set_contratante(datos_per_contratante[2],  // Nombre del contratante
                                    datos_per_contratante[1],  // tipo de documento
                                    datos_per_contratante[0]); // Numero de documento
            extracto.set_tipo_contrato(Extracto.TIPO_CONTRATO_EMPRESARIAL);     // hace el set al tipo de contrato por defecto

            
            if(Integer.parseInt(datos_origen[0]) != 0 && Integer.parseInt(datos_destino[0]) != 0){
                extracto.set_origen_destino(datos_origen[1],    // Establece el municipio de origen
                                            datos_origen[2],    // Establece el departamento de origen
                                            datos_destino[1],   // Establece el municipio de destino
                                            datos_destino[2]);  // Establece el departamento de destino
            }else{  // En caso que alguno de los dos sea indefinido, pasa hacer el set correspondiente

                if(Integer.parseInt(datos_origen[0]) != 0){     

                    extracto.set_ruta(datos_origen[1], datos_origen[2]);

                }else if(Integer.parseInt(datos_destino[0]) != 0){

                    extracto.set_ruta(datos_destino[1], datos_destino[2]);

                }
            }
            
            // Hace el set Pertinente de el tipo de contrato
            extracto.set_tipo_contrato(datos_tipo_contrato);

            
            
            // Establece las fechas iniciales y finales del extracto
            extracto.set_fecha_inicial(datos_contratante[2]);
            extracto.set_fecha_final(datos_contratante[3]);
            
            // hace el set del responsable del contrato
            extracto.set_responsable(datos_per_contratante[4], 
            datos_per_contratante[3], 
            datos_per_contratante[5], 
            datos_per_contratante[6]);
            
            // verifica si el extracto sale como extemporaneo o normal
            if(is_extemporaneo()){
                sub_ocasional = sub_ocasional + "EXTEMPORANEO";
            }else{
                sub_ocasional = sub_ocasional + "NORMAL";
            }
            
            // Hace un extracto por cada contrato
            for(int i = 0; i < placas_contrato.length; i++){
                // Se encarga de blanquear los extractos
                init_extracto(extracto);

                // Obitiene la nueva informacion, para los extractos
                datos_conductores = base_vhc.consultar_conductor_has_vehiculo(placas_contrato[i]);
                datos_vehiculo = base_extractos.consultar_uno_vw_vehiculo_extracto(placas_contrato[i]);
                parque_automotor = Boolean.parseBoolean(base_vehiculo.consultar_uno_vehiculo(placas_contrato[i])[16]);
                vehiculo_empresa_externa = base_extractos.consultar_uno_vehiculo_externo(placas_contrato[i]);
                consecutivo = base_extractos.consultar_uno_consecutivo_extracto_ocasional(placas_contrato[i], contrato);
                
                // Verifica si el vehiculo a insertar contiene documentos para seguir con el proceso
                if(datos_vehiculo[0] == null){
                    NullPointerException ex = new NullPointerException("El vehiculo " + placas_contrato[i] + ".\nNo posee documentos");
                    throw ex;
                }
                

                // Modificando como tal el documento
                extracto.set_numero_principal(año, ""+contrato, ""+consecutivo);    // hace el set al numero principal
                // Se encarga de hacer el set al origen y destino del extracto ocasional
                // Verificando si es origen destino o ruta como tal, si tiene solo un origen o solo un destino
                

                // hace un set a los datos del vehiculo
                extracto.set_datos_vehiculo(placas_contrato[i], 
                                            Integer.parseInt(datos_vehiculo[1]), 
                                            datos_vehiculo[2], 
                                            datos_vehiculo[3], 
                                            Integer.parseInt(datos_vehiculo[4]), 
                                            Integer.parseInt(datos_vehiculo[5]));

                

                // Verifica si el vehiculo es parte del parque automotor de la empresa
                // caso contrario simplemente continua con el proceso
                if(!parque_automotor){
                    extracto.set_convenio(vehiculo_empresa_externa[2], vehiculo_empresa_externa[3]);
                }
                

                // Verifica los conductores que tiene el vehiculo para continuar con el proceso
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
                    
                    NullPointerException ex = new NullPointerException("El vehiculo " + placas_contrato[i] + ".\nNo tiene conductores registrados");
                    throw ex;
                    
                }
                
                
                // Guarda el extracto en la direccion indicada por el path, o el archivo de configuracion
                extracto.guardar(localizacion_fichero, sub_ocasional+" (" + consecutivo + ")");
                extracto.guardar(localizacion2_fichero);
                

            }
            
            localizacion_contrato = Generar_contratos_ocasionales.generar_contrato_ocasional(placas_contrato,""+contrato, band);

        }finally{
            if(base != null) base.close();
            if(base_vehiculo != null) base_vehiculo.close();
            if(base_ciudad != null) base_ciudad.close();
            if(base_vhc != null) base_vhc.close();
            if(base_extractos != null) base_extractos.close();
            if(base_co != null) base_co.close();
            if(base_contratante != null) base_contratante.close();
            if(extracto != null) extracto.close();
        }
        // Retorna la localizacion del los extractos
        return localizacion_fichero + "\n Contrato guardado en: " + localizacion_contrato;
        
    }

    /**
     * Este metodo se utiliza para inicializar un extracto
     * para evitar cruces de informacion con otros extractos
     * especificamente, cuando se hacen varios en un ciclo for
     * @param extracto
     */
    private static void init_extracto(Extracto extracto){
        
        // Blanquea la parte del convenio del vehiculo
        extracto.set_convenio();
        
        // Blanquea la lista de conducotres
        extracto.set_conductores();
    }

    private static boolean is_extemporaneo() {
        LocalDateTime ahora = LocalDateTime.now();
        DayOfWeek diaDeLaSemana = ahora.getDayOfWeek();
        int hora = ahora.getHour();

        if(hora >= 17){                                                 // Si es mas de las 5 pm es extemporaneo
            return true;
        } else if(diaDeLaSemana == DayOfWeek.SATURDAY && hora >= 12){   // si es sabado mas de las 12 pm es extemporaneo
            return true;
        } else if (diaDeLaSemana == DayOfWeek.SUNDAY) {                 // si es domingo es extemporaneo
            return true;
        } else {                                                        // caso contrario no es extemporaneo
            return false;
        }
    }
}
