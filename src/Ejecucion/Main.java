package Ejecucion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Base.Documentos;
import Base.Licencia;

import javax.swing.JOptionPane;
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

                SwingUtilities.invokeLater(() -> {
                
                Dialogo_documentos dialog = new Dialogo_documentos(null);

                ArrayList<String[]> mensajes = verificar_documentos();

                for(String[] mensaje : mensajes){
                    dialog.addVehicle(mensaje);
                }

                dialog.setVisible(true);

                });
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

    public static ArrayList<String[]> verificar_documentos(){

        ArrayList<String[]> mensajes = new ArrayList<>();
        String[] mensaje_auxiliar;

        Documentos base_doc_vehiculos = null;
        Licencia base_licencias = null;
        try{

            base_doc_vehiculos = new Documentos();
            base_licencias = new Licencia();

            String[][] datos_vehiculos = base_doc_vehiculos.consultar_documentos("");
            String[][] datos_licencias = base_licencias.consultar_licencia();
            

            for(int i = 1; i < datos_vehiculos.length; i++){

                if(datos_vehiculos[i][7] == "NULL"){

                    mensaje_auxiliar = Utilidades.Verificacion_documentos.verificar_documentos_vehiculo(
                        datos_vehiculos[i][0],
                        datos_vehiculos[i][2],
                        datos_vehiculos[i][3]
                    );

                }else {

                    mensaje_auxiliar = Utilidades.Verificacion_documentos.verificar_documentos_vehiculo(
                        datos_vehiculos[i][0],
                        datos_vehiculos[i][2],
                        datos_vehiculos[i][3],
                        datos_vehiculos[i][4],
                        datos_vehiculos[i][7]
                    );
                }
                
                if(mensaje_auxiliar != null){
                    mensajes.add(mensaje_auxiliar);
                }
                
            }

            for(int j = 1; j < datos_licencias.length; j++){

                String[] mensaje_licencia = Utilidades.Verificacion_documentos.verificar_documentos_conductor(
                    datos_licencias[j][1],
                    datos_licencias[j][3]
                );

                if(mensaje_licencia != null){
                    mensajes.add(mensaje_licencia);
                }
            }
        }catch(IOException | SQLException ex){

            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }finally{
            if(base_doc_vehiculos != null) base_doc_vehiculos.close();
            if(base_licencias != null) base_licencias.close();
        }
    
        return mensajes;
    }
}

            
