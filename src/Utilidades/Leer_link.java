package Utilidades;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import Estructuras_datos.Queue;
import java.util.HashSet;

public class Leer_link {

    private static HashSet<String> set;

    /**
     * Este metodo se encarga de retornar
     * los identificadores del archivo links
     * para que sea mas facil leerlos despues
     * @return
     */
    public static HashSet<String> get_set(){
        return set;
    }

    /**
     * Este metodo devuelve un HashMap, con los diferentes links
     * y nombres, para que para el programa, sea mas facil agregar
     * items en un meno dado, de tal manera que se utilizan enteros,
     * para posteriormente iterar sobre estoy y hacerlo de una forma
     * mas dinamica.
     * 
     * Es recomendable, solo utilizar este metodo una vez
     * @return
     * @throws IOException
     */
    public static HashMap<String, Queue<String[]>> get_links() throws IOException{

        HashMap<String,Queue<String[]>> map = new HashMap<>();
        Scanner leer = null;
        Queue<String[]> cola = new Queue<>();
        set = new HashSet<>();

        
        try{
            leer = new Scanner(new File("src\\config\\links.csv"));

            String identificador;
            String complemento[] = null;
            String aux;
            String div[];

            while(leer.hasNextLine()){
                
                // Crea un nuevo objeto par almacenar la informacion
                complemento = new String[2];

                // Lee la siguiente linea del archivo
                aux = leer.nextLine();
                
                // Divide las instrucciones por punto y coma
                div = aux.split(";");

                // Asigna los valores dependiendo de cada variable
                identificador = div[0];
                complemento[0] = div[1];
                complemento[1] = div[2];
                
                

                if(map.containsKey(identificador)){
                    cola.enqueue(complemento);
                    map.replace(identificador, cola);
                }else{
                    cola = new Queue<>();
                    cola.enqueue(complemento);
                    map.put(identificador, cola);
                }
                
                set.add(identificador); // Guarda el identificador

            }
            
        }catch(IOException ex){
            throw ex;
        }finally{
            leer.close();
        }

        return map;
    }
    public static String get_portafoleo() throws IOException{

        String link = "";
        String aux;
        Scanner leer;

        leer = new Scanner(new File("src\\config\\links.csv"));

        while(leer.hasNextLine()){
            
            aux = leer.nextLine();

            if(aux.contains("portafoleo")){
                link = aux.split(";")[2];
                break;
            }
        }
        
        leer.close();
        return link;
    }

    public static LinkedList<String> get_runt()throws IOException{

        LinkedList<String> cola = new LinkedList<>();
        String aux;
        Scanner leer;

        leer = new Scanner(new File("src\\config\\links.csv"));

        while(leer.hasNextLine()){
            
            aux = leer.nextLine();

            if(aux.contains("runt")){

                cola.offer(aux.split(";")[2]);
                
            }
        }
        
        leer.close();
        return cola;
    }

    public static LinkedList<String> get_seguridad_social()throws IOException{
        LinkedList<String> cola = new LinkedList<>();
        String aux;
        Scanner leer;

        leer = new Scanner(new File("src\\config\\links.csv"));

        while(leer.hasNextLine()){
            
            aux = leer.nextLine();

            if(aux.contains("ssocial")){

                cola.offer(aux.split(";")[2]);
                
            }
        }
        
        leer.close();
        return cola;
    }

}
