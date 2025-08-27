package Ejecucion;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import Front.Dialogo_documentos;
import Front.Principal;

public class Main {
    
    public static void main(String[] args) throws IOException{
        
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new Principal();
            }
            
        });

        new Runnable(){
            public void run() {
                
                Dialogo_documentos dialog = new Dialogo_documentos(null);
                
                ArrayList<String[]> mensajes = Utilidades.Verificacion_documentos.verificar_documentos();

                for(String[] mensaje : mensajes){
                    dialog.addVehicle(mensaje);
                }

                dialog.setVisible(true);
            }
        }.run();

        // new Runnable(){
        //     public void run() {
        //         try {
        //             // Aquí puedes ejecutar el comando de la consola
        //             String[] argumentos = {"Mensajes.exe", "+573112188934", "Hola, este es un mensaje de prueba desde Java!"};
        //             ProcessBuilder processBuilder = new ProcessBuilder(argumentos);
        //             // Ejecuta el comando
        //             processBuilder.start();
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }.run();

    
        // String[] argumentos = {"Mensajes.exe", "+573214848600", "Hola, este es un mensaje de prueba desde Java!"};
        // ProcessBuilder processBuilder = new ProcessBuilder(argumentos);
        // // Ejecuta el comando
        // processBuilder.start();

    }

    
}

            
