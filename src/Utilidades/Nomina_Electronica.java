package Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;


public class Nomina_Electronica {
    
    private static final String TIPO_DOCUMENTO = "13-CC";
    private static final String TIPO_CONTRATO = "2-Término Indefinido";
    private static final String PAIS = "CO-Colombia";
    private static final String DEPARTAMENTO = "_50_Meta";

    private static final int COL_CODIGO_TRABAJADOR = 1;
    private static final int COL_TIPO_DOCUMENTO = 2;
    private static final int COL_NUMERO_DOCUMENTO = 3;
    private static final int COL_PRIMER_APELLIDO = 4;
    private static final int COL_SEGUNDO_APELLIDO = 5;
    private static final int COL_PRIMER_NOMBRE = 6;
    private static final int COL_SEGUNDO_NOMBRE = 7;
    private static final int COL_CORREO = 8;
    private static final int COL_PAIS = 9;
    private static final int COL_DEPARTAMENTO = 10;
    private static final int COL_CIUDAD = 11;
    private static final int COL_DIRECCION = 12;
    private static final int COL_TIPO_CONTRATO = 13;

    private static final int ROW_INICIO_TRABAJADORES = 8;
    private static final int ROW_INICIO_NOMINA = 9;


    private Workbook libro;
    private Sheet hoja_principal;
    private Sheet hoja_nomina;
    private Sheet hoja_novedades;
    private FileInputStream plantilla;

    private int prin_fila_actual = ROW_INICIO_TRABAJADORES;
    private int codigo_inicial = 1;

    private int nom_fila_actual = ROW_INICIO_NOMINA;

    /**
     * Constructor de la clase Nomina_Electronica
     * @param ruta
     * @throws IOException
     */
    public Nomina_Electronica(String ruta) throws IOException{
        
        plantilla = new FileInputStream(ruta);
        libro = new XSSFWorkbook(plantilla);
        hoja_principal = libro.getSheetAt(1);
        hoja_nomina = libro.getSheetAt(2);
        hoja_novedades = libro.getSheetAt(3);

    }

    /**
     * Establece el valor de una celda en una hoja específica
     * @param hoja Hoja en la cual se esta trabajando
     * @param row  Fila
     * @param column Columna
     * @param valor Valor a establecer
     */
    private void set_cell(Sheet hoja, int row, int column, String valor){
        Row fila = hoja.getRow(row);
        Cell celda = fila.getCell(column);
        
        celda.setCellValue(valor);
    }

    private void set_cell(Sheet hoja, int row, int column, long valor){
        Row fila = hoja.getRow(row);
        Cell celda = fila.getCell(column);
        
        celda.setCellValue(valor);
    }

    /**
     * Cierra el libro y la plantilla
     * @throws IOException
     */
    public void close() throws IOException{
        libro.close();
        plantilla.close();
    }


    /**
     * Agrega un trabajador a la nómina electrónica,
     * especificamente en la primera hoja del libro,
     * puesto que aqui es donde se encuentran los
     * primeros datos del trabajador.
     * @param datos Arreglo de String con los datos del trabajador
     * Los datos deben estar en el siguiente orden:
     * {Codigo del trabajador, Tipo de documento, Número de documento,
     * Primer apellido, Segundo apellido, Primer nombre, Segundo nombre,
     * Correo electrónico, País, Departamento, Ciudad, Dirección, Tipo de contrato}
     */
    public void add_trabajador(String[] datos){
        
        set_cell(hoja_nomina, prin_fila_actual, COL_CODIGO_TRABAJADOR, codigo_inicial);             // Código del trabajador
        set_cell(hoja_nomina, prin_fila_actual, COL_TIPO_DOCUMENTO, datos[0]);                      // Tipo de documento
        set_cell(hoja_nomina, prin_fila_actual, COL_NUMERO_DOCUMENTO, Long.parseLong(datos[1]));    // Número de documento
        set_cell(hoja_nomina, prin_fila_actual, COL_PRIMER_APELLIDO, datos[2]);                     // Primer apellido
        set_cell(hoja_nomina, prin_fila_actual, COL_SEGUNDO_APELLIDO, datos[3]);                    // Segundo apellido
        set_cell(hoja_nomina, prin_fila_actual, COL_PRIMER_NOMBRE, datos[4]);                       // Primer nombre
        set_cell(hoja_nomina, prin_fila_actual, COL_SEGUNDO_NOMBRE, datos[5]);                      // Segundo nombre
        set_cell(hoja_nomina, prin_fila_actual, COL_CORREO, datos[6]);                              // Correo electrónico
        set_cell(hoja_nomina, prin_fila_actual, COL_PAIS, datos[7]);                                // País
        set_cell(hoja_nomina, prin_fila_actual, COL_DEPARTAMENTO, datos[8]);                        // Departamento
        set_cell(hoja_nomina, prin_fila_actual, COL_CIUDAD, datos[9]);                              // Ciudad
        set_cell(hoja_nomina, prin_fila_actual, COL_DIRECCION, datos[10]);                          // Dirección
        set_cell(hoja_nomina, prin_fila_actual, COL_TIPO_CONTRATO, datos[11]);                      // Tipo de contrato

        prin_fila_actual++;
        codigo_inicial++;

    }

    public void add_trabajador_nomina(String[] datos_empleado, String[] datos_empresa, String[] datos_generales, String[] datos_devengos, String[] datos_deduciones, String[] total_nomina){

        
    }

    /**
     * Guarda el libro en la ruta especificada
     * @param dir_salida
     * @throws IOException
     */
    public void guardar(String dir_salida) throws IOException{

        String nombre_archivo = "Nomina_Electronica.xlsx";
        try{
            File file = new File(dir_salida,nombre_archivo);
            OutputStream out;

            if (!file.getParentFile().exists()) {
                // Si no existe, intenta crearla
                file.getParentFile().mkdirs();
            }

            out = new FileOutputStream(dir_salida +"\\"+ nombre_archivo);
            libro.write(out);
            out.close();
            
            
        }catch(IOException ex){
            throw ex;
        }
        
    }

}


