package Ejecucion;

import java.io.IOException;
import javax.swing.SwingUtilities;
import Front.Principal;
import Utilidades.Leer_rutas;

public class Main {
    
    public static void main(String[] args) throws IOException{

        // Esto se encargaa de obtener la base de datos
        Leer_rutas ruta = new Leer_rutas();
        String base_datos = ruta.get_ruta(Leer_rutas.DB);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal(base_datos);
            }
        });
    }
}