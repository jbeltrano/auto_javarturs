package Utilidades;

import java.nio.file.Paths;
import java.io.File;

/**
 * Clase utility para manejo de rutas multiplataforma (Windows y Linux)
 * Proporciona métodos para construir rutas de forma compatible
 */
public class PathUtils {
    
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_WINDOWS = OS_NAME.contains("win");
    private static final boolean IS_LINUX = OS_NAME.contains("linux") || OS_NAME.contains("unix");
    
    /**
     * Obtiene el separador de archivos del sistema (\ para Windows, / para Linux)
     */
    public static String getFileSeparator() {
        return File.separator;
    }
    
    /**
     * Obtiene el separador de rutas del sistema (; para Windows, : para Linux)
     */
    public static String getPathSeparator() {
        return File.pathSeparator;
    }
    
    /**
     * Verifica si el sistema operativo es Windows
     */
    public static boolean isWindows() {
        return IS_WINDOWS;
    }
    
    /**
     * Verifica si el sistema operativo es Linux
     */
    public static boolean isLinux() {
        return IS_LINUX;
    }
    
    /**
     * Construye una ruta absoluta de forma compatible
     */
    public static String buildPath(String... parts) {
        return String.join(File.separator, parts);
    }
    
    /**
     * Construye una ruta desde user.home de forma compatible
     */
    public static String buildHomeBasedPath(String... parts) {
        String[] allParts = new String[parts.length + 1];
        allParts[0] = System.getProperty("user.home");
        System.arraycopy(parts, 0, allParts, 1, parts.length);
        return buildPath(allParts);
    }
    
    /**
     * Construye una ruta desde user.dir (directorio del proyecto) de forma compatible
     */
    public static String buildProjectBasedPath(String... parts) {
        String[] allParts = new String[parts.length + 1];
        allParts[0] = System.getProperty("user.dir");
        System.arraycopy(parts, 0, allParts, 1, parts.length);
        return buildPath(allParts);
    }
    
    /**
     * Obtiene la carpeta extractos en Desktop de forma compatible
     */
    public static String getExtractosPath() {
        if (isWindows()) {
            return buildHomeBasedPath("Desktop", "Extractos");
        } else {
            return buildHomeBasedPath("Extractos");
        }
    }
    
    /**
     * Obtiene la carpeta extractos mensuales
     */
    public static String getExtractosMensualesPath() {
        return buildPath(getExtractosPath(), "Extractos Mensuales");
    }
    
    /**
     * Obtiene la carpeta extractos ocasionales
     */
    public static String getExtractosOcasionalesPath() {
        return buildPath(getExtractosPath(), "Extractos Ocasionales");
    }
    
    /**
     * Obtiene la carpeta contratos ocasionales
     */
    public static String getContratosOcasionalesPath() {
        return buildPath(getExtractosPath(), "Contratos Ocasionales");
    }
    
    /**
     * Obtiene la ubicación del script ConvertirPdf.ps1
     */
    public static String getConvertPdfScript() {
        return buildProjectBasedPath("src", "Utilidades", "PDF", "ConvertirPdf.ps1");
    }
    
    /**
     * Obtiene la ubicación del ejecutable de conversión PDF
     */
    public static String getPdfConverterExe() {
        return buildProjectBasedPath("src", "Utilidades", "PDF", "a.exe");
    }
}
