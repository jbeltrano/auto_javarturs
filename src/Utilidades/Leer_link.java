package Utilidades;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

public class Leer_link {
    
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
