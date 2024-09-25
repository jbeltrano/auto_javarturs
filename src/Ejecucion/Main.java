package Ejecucion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import Base.Base;
import Base.Documentos;
import Front.Principal;

public class Main {
    
    public static void main(String[] args) {
        
        Base base = null;
        try{
            base = new Documentos("src\\DB\\base.db");

            ((Documentos)base).insertar_documento2("SXT705","documento.pdf",leerArchivo("C:\\Users\\Juan Beltran\\Desktop\\Parcial practico.pdf"));
            
            escribirArchivo("C:\\Users\\Juan Beltran\\Desktop\\documento.pdf", ((Documentos)base).consultar_documento2("SXT705"));
            
        }catch(IOException | SQLException e){
            System.out.println(e);
        }finally{
            base.close();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal("src\\DB\\base.db");
            }
        });
        

        
    }

    private static byte[] leerArchivo(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    private static void escribirArchivo(String filePath, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        }
    }
}

// import java.sql.SQLException;
// import Base.Ruta;
// import Estructuras_datos.Graph;
// import Estructuras_datos.Stack;

// public class Main {
//     public static void main(String[] args) {
//         // Crear el objeto Grafo y cargar las rutas
//         Graph grafo = new Graph();
//         Ruta base_ruta = new Ruta("src\\DB\\base.db");
//         try{
//             base_ruta.cargarRutasGrafo(grafo);
//         }catch(SQLException ex){
//             System.out.println(ex);
//         }finally{
//             base_ruta.close();
//         }

//         // Definir el nodo de origen y destino
//         int origen = 21;  // Cambia según el id de tu municipio de origen
//         int destino = 35; // Cambia según el id de tu municipio de destino

//         // Obtener el camino más corto
//         Stack<Integer> camino = grafo.dijkstra(origen, destino);

//         if (camino.isEmpty()) {
//             System.out.println("No se encontró un camino entre los municipios.");
//         } else {
//             System.out.print("El camino más corto es por: ");
//             for (int i = 0; !camino.isEmpty(); i++) {
//                 if (i != 0) System.out.print(", ");
//                 System.out.print(camino.pop());  // Aquí podrías consultar el nombre de la ciudad
//             }
//             System.out.println();
//         }
//     }
// }
