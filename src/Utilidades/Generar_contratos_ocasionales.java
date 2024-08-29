package Utilidades;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import Base.Base;
import Base.Ciudad;
import Base.Contratante;
import Base.Vehiculo;
import Base.BContrato_ocasional;

public class Generar_contratos_ocasionales {

    public static String generar_contrato_ocasional(String[] placas,String contrato, String url)throws IOException, SQLException{

        Contrato_ocasional doc_contrato = null;
        //String url_destino = System.getProperty("user.home") + "\\Desktop\\Extractos\\Contratos Ocasionales\\";
        String url_destino = "H:\\.shortcut-targets-by-id\\1t_bzTNBvxadgo0YhZcGtt_W_kRVu_3Hh\\0. EXTRACTOS Y CONTRATOS\\CONTRATOS DE SERVICIOS OCASIONALES\\";
        String url_destino2 = System.getProperty("user.home") + "\\Desktop\\Extractos\\Contratos Ocasionales\\";
        Base base;
        String contratante[];
        String contrato_ocasional[];
        String origen[];
        String destino[];
        LocalDate fecha_inicial;
        LocalDate fecha_final;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        int cantidad_pasajeros = 0;

        base = new Base(url);
        Ciudad base_ciudad = new Ciudad(url);
        Vehiculo base_Vehiculo = new Vehiculo(url);
        BContrato_ocasional base_contrato_ocasional = new BContrato_ocasional(url);
        Contratante base_contratante = new Contratante(url);
        try{
            // Carga la infromarcion necesaria
            contrato_ocasional = base_contrato_ocasional.consultar_uno_contrato_ocasional(Integer.parseInt(contrato));
            contratante = base_contratante.consultar_uno_contratante(contrato_ocasional[1]);
            origen = base_ciudad.consultar_uno_ciudades(contrato_ocasional[4]);
            destino = base_ciudad.consultar_uno_ciudades(contrato_ocasional[5]);
            fecha_inicial = LocalDate.parse(contrato_ocasional[2], formatter);
            fecha_final = LocalDate.parse(contrato_ocasional[3], formatter);
            doc_contrato = new Contrato_ocasional("src\\Formatos\\Documento.docx"); // Inicializa el documento

            // Consulta la capacidad maxima entre los dos set_cantidad_vehiculos
            for(int i = 0; i < placas.length; i++){
                int aux = base_Vehiculo.consultar_capacidad_vehiculo(placas[i]);
                cantidad_pasajeros = (aux >= cantidad_pasajeros)?aux:cantidad_pasajeros;
            }
            // Hace un set al numero de contrato
            doc_contrato.set_numero_contrato(contrato);

            
            // hace un set al contratante
            if(contratante[1].equals("NIT") && !(contratante[0].equals(contratante[3]))){      // En este caso si es un nit hace un set especial
                doc_contrato.set_contratante(contratante[2],   // Nombre de la empresa
                                            contratante[1],    // tipo de documento de la empresa
                                            contratante[0],    // Nit de la empresa
                                            contratante[4],    // Nompre representante
                                            "C.C.",    // Tipo documento representante
                                            contratante[3],          // Documento representante
                                            contratante[5],       // Telefono representante
                                            contratante[6],            // Direccion representante
                                            contratante[7] + " ("+contratante[8] + ")");   // Ciudad Representante

            }else{
                doc_contrato.set_contratante(contratante[2],           // Nombre del contratante
                                            contratante[1],            // Tipo de documento
                                            contratante[0],            // Numero de documento
                                            contratante[5],            // Numero de telefono
                                            contratante[6],            // Direccion
                                            contratante[7] + " (" + contratante[8] + ")");  // Ciudad
            }
            
            // Hace un set a la cantidad de vehiculos
            doc_contrato.set_cantidad_vehiculos(placas.length);

            // Hace un set a la cantidad de pasajeros
            doc_contrato.set_cantidad_pasajeros(cantidad_pasajeros);

            // Hace un set al origen y destino
            doc_contrato.set_origen_destino(origen[1] + " (" + origen[2] + ")",      // Origen en formato municipio (departamento) 
                                            destino[1] + " (" + destino[2] + ")");   // Destino en formato municipio (departamento)
            // Hace un set al precio
            doc_contrato.set_valor_contrato(Integer.parseInt(contrato_ocasional[6]));
            
            // Hace un set a la druacion del contrato
            doc_contrato.set_duracion_contrato((int) ChronoUnit.DAYS.between(fecha_inicial, fecha_final), 
                                                fecha_inicial.getDayOfMonth(), 
                                                fecha_inicial.getMonthValue(), 
                                                fecha_final.getYear(), 
                                                fecha_final.getDayOfMonth(), 
                                                fecha_final.getMonthValue(), 
                                                fecha_final.getYear());
                
            // Finalmente Guarda en la ruta de destino con su respectivo nombre
            doc_contrato.guardar(url_destino, placas);
            doc_contrato.guardar(url_destino2,placas);
        }finally{
            base.close();
            base_Vehiculo.close();
            doc_contrato.close();
            base_contrato_ocasional.close();
            base_contratante.close();
        }
        return url_destino;

    }
}
