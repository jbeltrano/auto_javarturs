# Guía de Compatibilidad Multiplataforma

## Resumen

Este documento explica la arquitectura y los cambios realizados para hacer que el proyecto sea compatible con Windows y Linux.

## Arquitectura de Compatibilidad

### 1. Clase PathUtils

**Ubicación**: `src/Utilidades/PathUtils.java`

Esta clase centraliza toda la lógica de construcción de rutas multiplataforma. Proporciona métodos estáticos para:

- `getFileSeparator()`: Obtiene el separador del sistema (`\` en Windows, `/` en Linux)
- `buildPath(String... parts)`: Construye rutas usando el separador del sistema
- `buildHomeBasedPath(String... parts)`: Construye rutas relativas al directorio HOME del usuario
- `buildProjectBasedPath(String... parts)`: Construye rutas relativas al directorio del proyecto
- `isWindows()`: Detecta si el SO es Windows
- `isLinux()`: Detecta si el SO es Linux

#### Métodos de Conveniencia

- `getExtractosMensualesPath()`: Ruta a carpeta de extractos mensuales
- `getExtractosOcasionalesPath()`: Ruta a carpeta de extractos ocasionales
- `getContratosOcasionalesPath()`: Ruta a carpeta de contratos ocasionales
- `getConvertPdfScript()`: Ruta al script ConvertirPdf.ps1
- `getPdfConverterExe()`: Ruta al ejecutable PDF

#### Ejemplo de Uso

```java
// Antes (solo Windows):
String ruta = System.getProperty("user.home") + "\\" + "Extractos" + "\\";

// Después (multiplataforma):
String ruta = PathUtils.getExtractosPath() + File.separator;
```

### 2. Clase PlatformCommandExecutor

**Ubicación**: `src/Utilidades/PlatformCommandExecutor.java`

Esta clase ejecuta comandos específicos del sistema operativo de forma compatible.

#### Métodos Principales

- `executePdfConversion(String pdfScriptPath, String outputPath)`: Ejecuta conversión PDF de forma compatible con el SO
- `openFileManager(String path)`: Abre el administrador de archivos en la ruta especificada

#### Implementaciones Específicas

**Windows**: Usa PowerShell para ejecutar scripts
```java
command.add("powershell.exe");
command.add("-NoProfile");
command.add("-ExecutionPolicy");
command.add("Bypass");
```

**Linux**: Puede usar LibreOffice o herramientas similares

#### Ejemplo de Uso

```java
// Ejecutar conversión PDF de forma compatible
boolean success = PlatformCommandExecutor.executePdfConversion(
    PathUtils.getConvertPdfScript(),
    PathUtils.getExtractosMensualesPath()
);

if (success) {
    System.out.println("Conversión completada");
}
```

## Patrones de Migración

### Patrón 1: Rutas Hardcodeadas

**Antes**:
```java
String path = System.getProperty("user.home") + "\\" + "Desktop" + "\\" + "Extractos\\Mensuales";
```

**Después**:
```java
String path = PathUtils.buildPath(
    System.getProperty("user.home"), 
    "Desktop", 
    "Extractos", 
    "Mensuales"
);
```

O usar métodos de conveniencia si existe:
```java
String path = PathUtils.getExtractosMensualesPath();
```

### Patrón 2: Comandos Específicos de SO

**Antes**:
```java
String comando[] = {
    System.getProperty("user.dir") + "\\src\\Utilidades\\PDF\\a.exe",
    System.getProperty("user.dir") + "\\src\\Utilidades\\PDF\\ConvertirPdf.ps1",
    System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Mensuales"
};
runtime.exec(comando);
```

**Después**:
```java
PlatformCommandExecutor.executePdfConversion(
    PathUtils.getConvertPdfScript(),
    PathUtils.getExtractosMensualesPath()
);
```

### Patrón 3: Separador de Archivos

**Antes**:
```java
String ruta = path + "\\archivo.pdf";
```

**Después**:
```java
String ruta = path + File.separator + "archivo.pdf";
// O mejor aún:
Path fullPath = Paths.get(path, "archivo.pdf");
```

## Consideraciones Importantes

### GUI en Linux

En Linux con entorno gráfico, el script `run.sh` configura automáticamente las variables `DISPLAY` y `XAUTHORITY` necesarias para que Swing funcione correctamente:

```bash
export DISPLAY="${DISPLAY:-:0}"
if [[ -f "/run/user/$(id -u)/gdm/Xauthority" ]]; then
    export XAUTHORITY="/run/user/$(id -u)/gdm/Xauthority"
fi
```

### Diferencias en Rutas

- **Windows**: `C:\Users\Usuario\Desktop\Extractos`
- **Linux**: `/home/usuario/Extractos` (no tiene carpeta Desktop en los sistemas estándar)

La clase `PathUtils.getExtractosPath()` maneja estas diferencias automáticamente.

### Conversión PDF Multiplataforma

**Problema**: El proyecto usa PowerShell en Windows para convertir Excel a PDF, lo cual no es disponible en Linux.

**Solución**: La clase `PlatformCommandExecutor` detecta el SO y ejecuta la alternativa apropiada:
- **Windows**: PowerShell con el script ConvertirPdf.ps1
- **Linux**: LibreOffice o herramientas alternativas (a implementar según necesidades)

## Mantenimiento y Extensión

### Agregar Nueva Funcionalidad Multiplataforma

1. Crear un método en `PathUtils` si es sobre rutas
2. Crear un método en `PlatformCommandExecutor` si es sobre comandos del SO
3. Documentar las diferencias de comportamiento entre plataformas

### Testing

Para verificar compatibilidad:

1. **Windows**: Ejecutar `run.bat` y `run.ps1`
2. **Linux**: Ejecutar `./run.sh`
3. Verificar que las rutas generadas sean correctas para el SO
4. Probar funciones específicas en cada plataforma

## Ejemplo Completo: Exportar Extracto

```java
// En Insertar_extracto_mensual.java
boton_exportar.addActionListener(_ ->{
    boolean band = guardar_extracto_mensual();

    if(band){
        try{
            String ruta;
            
            // Generar extracto en la ubicación correcta según el SO
            ruta = Generar_extractos.generar_extracto_mensual_excel(
                text_placa.getText(), 
                Integer.parseInt(text_consecutivo.getText())
            );
            
            // Ejecutar conversión PDF compatible
            PlatformCommandExecutor.executePdfConversion(
                PathUtils.getConvertPdfScript(), 
                PathUtils.getExtractosMensualesPath()
            );
            
            JOptionPane.showMessageDialog(
                this, 
                "Extracto guardado con exito.\nUbicacion: " + ruta, 
                "Guardado Exitoso", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }catch(Exception e){
            JOptionPane.showMessageDialog(
                this, 
                e.getMessage(),
                "Error",  
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
});
```

## Recursos Adicionales

- `PathUtils.java`: Documentación de métodos para rutas
- `PlatformCommandExecutor.java`: Documentación para ejecución de comandos
- `run.bat`: Script de ejecución para Windows
- `run.sh`: Script de ejecución para Linux/Unix
