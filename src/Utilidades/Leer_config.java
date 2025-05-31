package Utilidades;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;
public class Leer_config {
    
    public static String TEMA = "tema";
    public static String COLOR_PRINCIPAL = "color_principal";
    public static String COLOR_SECUNDARIO = "color_secundario";

    private int tema;
    private int color_principal;
    private int color_secundario;
    private int color_principal_oscuro;
    private int color_secundario_oscuro;

    public Leer_config(){
        Scanner archivo = null;
        String aux[];
        try{
            
            archivo = new Scanner(new File("src\\config\\config.csv"));

            aux = archivo.nextLine().split(";");
            tema = Integer.parseInt(aux[1]);

            aux = archivo.nextLine().split(";");
            color_principal = Integer.parseInt(aux[1],16);

            aux = archivo.nextLine().split(";");
            color_secundario = Integer.parseInt(aux[1],16);

            aux = archivo.nextLine().split(";");
            color_principal_oscuro = Integer.parseInt(aux[1],16);

            aux = archivo.nextLine().split(";");
            color_secundario_oscuro = Integer.parseInt(aux[1],16);

        }catch(IOException ex){
            /*
             * En caso de no encontrar el archivo, se estableceran
             * los colores por defecto y el tema por defecto
             */
            defect_config();
        }finally{
            archivo.close();
        }
        
        
    }

    public int get_tema(){
        return tema;
    }
    public int get_color_principal(){
        return color_principal;
    }
    public int get_color_secundario(){
        return color_secundario;
    }
    public int get_color_principal_oscuro(){
        return color_principal_oscuro;
    }
    public int get_color_secundario_oscuro(){
        return color_secundario_oscuro;
    }
    
    private void defect_config(){
        tema = 0;
        color_principal = 0x1C6006;
        color_secundario = 0x348719;
        color_principal_oscuro = 0x1C6006;
        color_secundario_oscuro = 0x348719;
    }
}
