package Utilidades;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import Base.Ruta;
import Base.Ciudad;
import Base.Contratante;
import Base.Vehiculo;
import Base.BContrato_ocasional;
import Estructuras_datos.Graph;
import Estructuras_datos.HashTable;
import Estructuras_datos.Queue;
import Estructuras_datos.Stack;

public class Generar_contratos_ocasionales {

    /**
     * Este es un metodo para generar un contrato ocasional
     * en formato .docx o word, donde se edita una plantilla
     * y se modifican los datos relacionados
     * @param placas Arreglo tipo String con todas las placas
     * que van a estar relacionadas al contrato
     * @param contrato Este es el consecutivo del contrato ocacional
     * @param band {false} si se va insertar origen destino {true}
     * si se va insertar una ruta
     * @return Retorna la direccion donde se va a almacenar el documento
     * @throws IOException
     * @throws SQLException
     */
    public static String generar_contrato_ocasional(String[] placas,String contrato, boolean band)throws IOException, SQLException{

        Contrato_ocasional doc_contrato = null;
        Leer_rutas rutas = new Leer_rutas();
        
    
        // _destino = "H:\\.shortcut-targets-by-id\\1t_bzTNBvxadgo0YhZcGtt_W_kRVu_3Hh\\0. EXTRACTOS Y CONTRATOS\\CONTRATOS DE SERVICIOS OCASIONALES\\";
        String url_destino = rutas.get_ruta(Leer_rutas.CONTRATOS_OCASIONALES_DRIVE) + "\\";
        //_destino2 = System.getProperty("user.home") + "\\Desktop\\Extractos\\Contratos Ocasionales\\";
        String url_destino2 = System.getProperty("user.home") +"\\"+ rutas.get_ruta(Leer_rutas.CONTRATOS_OCASIONALES) + "\\";
        String contratante[];
        String contrato_ocasional[];
        String origen[];
        String destino[];
        LocalDate fecha_inicial;
        LocalDate fecha_final;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        int cantidad_pasajeros = 0;
        HashTable<Integer, String> tabla_hash = new HashTable<>();
        Graph grafo = new Graph();
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new Queue<>();
        

        Ruta base_ruta = new Ruta();
        Ciudad base_ciudad = new Ciudad();
        Vehiculo base_Vehiculo = new Vehiculo();
        BContrato_ocasional base_contrato_ocasional = new BContrato_ocasional();
        Contratante base_contratante = new Contratante();
        try{
            // Carga la infromarcion necesaria
            contrato_ocasional = base_contrato_ocasional.consultar_uno_contrato_ocasional(Integer.parseInt(contrato));
            contratante = base_contratante.consultar_uno_contratante(contrato_ocasional[1]);
            origen = base_ciudad.consultar_uno_ciudades(contrato_ocasional[4]);
            destino = base_ciudad.consultar_uno_ciudades(contrato_ocasional[5]);
            fecha_inicial = LocalDate.parse(contrato_ocasional[2], formatter);
            fecha_final = LocalDate.parse(contrato_ocasional[3], formatter);
            doc_contrato = new Contrato_ocasional(); // Inicializa el documento

            // Ahora para cargar el grafo y la tabla has se hace de la siguiente manera
            base_ruta.cargarRutasGrafo(grafo);
            stack = grafo.dijkstra(Integer.parseInt(origen[0]), Integer.parseInt(destino[0])); 

            while(!stack.isEmpty()){
                queue.enqueue(stack.peek());
                String datos[] = base_ciudad.consultar_uno_ciudades("" + stack.peek());
                int id = stack.pop();
                String formato = datos[1] + " (" + datos[2] + ")";
                tabla_hash.put(id, formato);
            }


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
                                            contratante[3],       // Documento representante
                                            contratante[5],       // Telefono representante
                                            contratante[6],       // Direccion representante
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
            if(!band || queue.size() <= 2){
                doc_contrato.set_origen_destino(origen[1] + " (" + origen[2] + ")",      // Origen en formato municipio (departamento) 
                                                destino[1] + " (" + destino[2] + ")");   // Destino en formato municipio (departamento)
            }else{
                doc_contrato.set_origen_destino(queue, tabla_hash);   // Destino en formato municipio (departamento)
            }
            
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
            String direccion_origen = doc_contrato.guardar(url_destino2,placas);
            
            Convertir_contrato_pdf.convertir_contrato_pdf(direccion_origen, url_destino2);
            
        }finally{
            if(base_Vehiculo != null) base_Vehiculo.close();
            if(doc_contrato != null) doc_contrato.close();
            if(base_contrato_ocasional != null) base_contrato_ocasional.close();
            if(base_contratante != null) base_contratante.close();
            if(base_ruta != null) base_ruta.close();
        }
        return url_destino;

    }
}
