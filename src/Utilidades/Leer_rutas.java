package Utilidades;

import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

/**
 * Esta clase se utiliza para leer el archivo
 * de texto rutas.txt, de esta forma se puden
 * obtener las rutas y no tener que escribirlas
 * cada que se vayan a utilizar, y que de esta forma
 * tambien se puedan cambiar de una manera mas facil
 */
public class Leer_rutas {
    
    public static String EXTRACTOS_MENSUALES = "guardar_extractos_mensuales";
    public static String EXTRACTOS_OCASIONALES = "guardar_extractos_ocasionales";
    public static String CONTRATOS_OCASIONALES = "guardar_contratos_ocasionales";
    public static String PLANTILLA_EXTRACTOS = "plantilla_extractos";
    public static String PLANTILLA_CONTRATOS = "pantilla_contratos_ocasionales";
    public static String GUARDAR_PDF_EXE = "guardar_pdf_exe";
    public static String GUARDAR_PDF_POWERSHELL = "guardar_pdf_ps1";
    public static String DB = "base_datos";

    private HashMap<String,String> map;

    /**
     * El contrsuctor se encarga de cargar los archivos en un hasmap
     * para que sea mas facil la busqueda de los elementos o rutas
     * @throws IOException
     */
    public Leer_rutas()throws IOException{
        // Inciando los componentes para leer el archivo
        Scanner archivo = new Scanner(new File("src\\config\\rutas.txt"));
        map = new HashMap<>();
        
        while (archivo.hasNextLine()) {
            String aux[] = archivo.nextLine().split(" = ");
            map.put(aux[0], aux[1]);
        }

        archivo.close();
    }

    /**
     * Se encarga de obtener las rutas que existen en
     * el hasmap, siempre y cuando estos existan,
     * simplemente es pasar como parametro la llave
     * y retorna la rua
     * @param nombre_id
     * @return
     */
    public String get_ruta(String nombre_id){
        return map.get(nombre_id);
    }
}
