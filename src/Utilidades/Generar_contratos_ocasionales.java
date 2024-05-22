package Utilidades;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import Base.Base;
import Utilidades.Contrato_ocasional;

public class Generar_contratos_ocasionales {
    
    public static String generar_contrato_ocasional(String placa, String contrato, String url)throws SQLException, IOException, NullPointerException{
        
        Contrato_ocasional doc_contrato = null;
        String url_destino = System.getProperty("user.home") + "\\Desktop\\Extractos\\Contratos Ocasionales\\";
        Base base;
        String contratante[];
        String contrato_ocasional[];
        String origen[];
        String destino[];
        LocalDate fecha_inicial;
        LocalDate fecha_final;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

        base = new Base(url);
        try{
            // Carga la infromarcion necesaria
            contrato_ocasional = base.consultar_uno_contrato_ocasional(Integer.parseInt(contrato));
            contratante = base.consultar_uno_contratante(contrato_ocasional[1]);
            origen = base.consultar_uno_ciudades(contrato_ocasional[4]);
            destino = base.consultar_uno_ciudades(contrato_ocasional[5]);
            fecha_inicial = LocalDate.parse(contrato_ocasional[2], formatter);
            fecha_final = LocalDate.parse(contrato_ocasional[3], formatter);

            doc_contrato = new Contrato_ocasional("src\\Formatos\\Documento.docx"); // Inicializa el documento

            // Hace un set al numero de contrato
            doc_contrato.set_numero_contrato(contrato);

            // hace un set al contratante
            // if(contratante[1].equals("NIT")){      // En este caso si es un nit hace un set especial
            //     doc_contrato.set_contratante(url_destino,   // Nombre de la empresa
            //                                 url_destino,    // tipo de documento de la empresa
            //                                 url_destino,    // Nit de la empresa
            //                                 url_destino,    // Nompre representante
            //                                 url_destino,    // Tipo documento representante
            //                                 placa,          // Documento representante
            //                                 contrato,       // Telefono representante
            //                                 url,            // Direccion representante
            //                                 url_destino);   // Ciudad Representante

            // }else{
                doc_contrato.set_contratante(contratante[2],           // Nombre del contratante
                                        contratante[1],            // Tipo de documento
                                        contratante[0],            // Numero de documento
                                        contratante[5],            // Numero de telefono
                                        contratante[6],            // Direccion
                                        "Acacias (Meta)");  // Ciudad
            //}
            
            // Hace un set a la cantidad de pasajeros
            doc_contrato.set_cantidad_pasajeros(45);

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
            doc_contrato.guardar(url_destino, placa); 
        }finally{
            base.close();
            doc_contrato.close();
            
        }
        return "";
    }

    public static String generar_contrato_ocasional(String contrato, String url){
        return "";
    }
}
