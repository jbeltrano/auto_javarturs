package Ejecucion;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import Front.Dialogo_documentos;
import Front.Principal;
import Utilidades.Nomina_Electronica;

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

        new Runnable() {
            public void run() {
                try {
                    // Aquí puedes ejecutar el comando de la consola
                    
                    Nomina_Electronica nomina = new Nomina_Electronica("C:\\Users\\juan\\Desktop\\auto_javarturs\\src\\Formatos\\Formato_Nomina_E.xlsx");
                    nomina.add_trabajador(new String[]{"13-CC","123456789","Perez","Gomez","Juan","Carlos","juancarlos@gmail.com","CO-Colombia","_50_Meta","Medellin","Calle 123 #45-67","2-Término Indefinido"});
                    nomina.add_trabajador(new String[]{"13-CC","987654321","Lopez","Martinez","Ana","Maria","anamartinez@gmail.com","CO-Colombia","_50_Meta","Bogota","Carrera 89 #12-34","2-Término Indefinido"});
                    nomina.guardar("C:\\Users\\juan\\Desktop");
                    nomina.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }

    
}

            
