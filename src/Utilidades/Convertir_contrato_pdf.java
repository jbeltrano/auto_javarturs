package Utilidades;

import java.io.IOException;

public class Convertir_contrato_pdf {
    
    /**
     * Este metodo se encarga de convertir un contrato ocasional
     * a formato PDF utilizando el programa LibreOffice en modo headless.
     * @param origen Es la ruta del archivo de origen que se va a convertir.
     * @param destino Es la ruta donde se guardará el archivo convertido a PDF.
     * @throws IOException
     */
    public static void convertir_contrato_pdf(String origen, String destino) throws IOException {
        // Comando para ejecutar el script de PowerShell que convierte el contrato a PDF
        ProcessBuilder processBuilder = new ProcessBuilder(
            "LibreOffice\\program\\soffice.exe", "--headless", "--convert-to", "pdf", "--outdir", destino, origen
        );
        // Ejecuta el comando
        processBuilder.start();
    }
}
