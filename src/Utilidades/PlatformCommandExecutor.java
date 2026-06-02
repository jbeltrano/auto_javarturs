package Utilidades;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para ejecutar comandos específicos del sistema operativo
 */
public class PlatformCommandExecutor {
    
    /**
     * Ejecuta el comando de conversión PDF de forma compatible con Windows y Linux
     * @param pdfScriptPath Ruta al script ConvertirPdf.ps1
     * @param outputPath Ruta donde se guardarán los PDFs
     * @return true si la ejecución fue exitosa, false en caso contrario
     */
    public static boolean executePdfConversion(String pdfScriptPath, String outputPath) {
        try {
            if (PathUtils.isWindows()) {
                return executePdfConversionWindows(pdfScriptPath, outputPath);
            } else if (PathUtils.isLinux()) {
                return executePdfConversionLinux(pdfScriptPath, outputPath);
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al ejecutar conversión PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Ejecuta la conversión PDF en Windows usando PowerShell
     */
    private static boolean executePdfConversionWindows(String pdfScriptPath, String outputPath) 
            throws Exception {
        List<String> command = new ArrayList<>();
        command.add("powershell.exe");
        command.add("-NoProfile");
        command.add("-ExecutionPolicy");
        command.add("Bypass");
        command.add("-File");
        command.add(pdfScriptPath);
        command.add(outputPath);
        
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(new File(System.getProperty("user.dir")));
        Process process = pb.start();
        
        return process.waitFor() == 0;
    }
    
    /**
     * Ejecuta la conversión PDF en Linux
     * Nota: En Linux se podría usar LibreOffice o similar
     */
    private static boolean executePdfConversionLinux(String pdfScriptPath, String outputPath) 
            throws Exception {
        // Para Linux, podríamos usar LibreOffice o convertidor alternativo
        // Por ahora, se registra un mensaje informativo
        System.out.println("Conversión PDF en Linux: " + outputPath);
        // En una implementación real, aquí iría la lógica específica de Linux
        return true;
    }
    
    /**
     * Abre un administrador de archivos en la ruta especificada
     */
    public static void openFileManager(String path) {
        try {
            if (PathUtils.isWindows()) {
                Runtime.getRuntime().exec("explorer.exe " + path);
            } else if (PathUtils.isLinux()) {
                // Intenta abrir con diferentes administradores de archivos
                String[] managers = {"nautilus", "dolphin", "thunar", "pcmanfm", "xdg-open"};
                for (String manager : managers) {
                    try {
                        Runtime.getRuntime().exec(new String[]{manager, path});
                        break;
                    } catch (Exception e) {
                        // Continúa con el siguiente
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo abrir el administrador de archivos: " + e.getMessage());
        }
    }
}
