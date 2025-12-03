package Ejecucion;

import java.io.IOException;
import java.util.ArrayList;
import Front.Dialogo_documentos;
import Front.Principal;
import Utilidades.Nomina_Electronica;

public class Main {
    public static void main(String[] args) throws IOException{

        new Runnable() {

            public void run() {
                new Principal();
            }
            
        }.run();

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

        new Runnable() {
            public void run() {
                try {
                    // Aquí puedes ejecutar el comando de la consola
                    
                    Nomina_Electronica nomina = new Nomina_Electronica("C:\\Users\\juanp\\Desktop\\auto_javarturs\\src\\Formatos\\Formato_Nomina_E.xlsx");
                    nomina.add_trabajador(new String[]{"13-CC","123456789","Perez","Gomez","Juan","Carlos","juancarlos@gmail.com","CO-Colombia","_50_Meta","Medellin","Calle 123 #45-67","2-Término Indefinido"});
                    nomina.add_trabajador(new String[]{"13-CC","987654321","Lopez","Martinez","Ana","Maria","anamartinez@gmail.com","CO-Colombia","_50_Meta","Bogota","Carrera 89 #12-34","2-Término Indefinido"});
                    nomina.guardar("C:\\Users\\juanp\\Desktop");
                    nomina.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }

    
}

            
// Necesitarás una biblioteca como Apache Batik para cargar SVG
// Instala la biblioteca y asegúrate de que esté en tu classpath.

// import org.apache.batik.swing.JSVGImage;
// import javax.swing.*;
// import java.net.URL;
// import java.awt.*;

// public class Main extends JFrame {
//     public BotonSvg() {
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(300, 200);

//         JButton boton = new JButton();

//         // Carga la imagen SVG
//         try {
//             URL imageUrl = getClass().getResource("/ruta/a/tu/icono.svg");
//             if (imageUrl != null) {
//                 // Usa Batik para cargar y renderizar el SVG como un Image
//                 JSVGImage svgImage = new JSVGImage(imageUrl.toString());
//                 // Puedes ajustar el tamaño si es necesario
//                 Image scaledImage = svgImage.createImage(new Dimension(20, 20));
//                 boton.setIcon(new ImageIcon(scaledImage));
//             } else {
//                 System.err.println("No se encontró la imagen SVG en la ruta especificada.");
//                 boton.setText("Icono no encontrado");
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             boton.setText("Error al cargar");
//         }

//         // Para ajustar el tamaño del botón y que el icono se vea mejor
//         boton.setHorizontalAlignment(SwingConstants.CENTER);
//         boton.setVerticalAlignment(SwingConstants.CENTER);
//         boton.setPreferredSize(new Dimension(50, 50)); // Ajusta según necesites

//         add(boton);
//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(BotonSvg::new);
//     }
// }
