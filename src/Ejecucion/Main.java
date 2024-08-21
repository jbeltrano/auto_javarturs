package Ejecucion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import Base.Base;
import Front.Principal;

public class Main {
    
    public static void main(String[] args) {
        Base base = null;
        try{
            base = new Base("src\\DB\\base.db");        

            base.insertar_documento2("SXT705","documento.pdf",leerArchivo("C:\\Users\\Juan Beltran\\Desktop\\Parcial practico.pdf"));
            
            escribirArchivo("C:\\Users\\Juan Beltran\\Desktop\\documento.pdf", base.consultar_documento2("SXT705"));
            
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
