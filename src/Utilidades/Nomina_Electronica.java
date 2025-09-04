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
import org.apache.poi.ss.usermodel.CellStyle;


public class Nomina_Electronica {
    
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


    private Workbook libro;
    private Sheet hoja_principal;
    private Sheet hoja_nomina;
    private Sheet hoja_novedades;
    private FileInputStream plantilla;

    private int prin_fila_actual = ROW_INICIO_TRABAJADORES;
    private int codigo_inicial = 1;

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


    public void add_trabajador(String[] datos){
        
        set_cell(hoja_nomina, prin_fila_actual, COL_CODIGO_TRABAJADOR, codigo_inicial);
        set_cell(hoja_nomina, prin_fila_actual, COL_TIPO_DOCUMENTO, datos[0]);
        set_cell(hoja_nomina, prin_fila_actual, COL_NUMERO_DOCUMENTO, Long.parseLong(datos[1]));
        set_cell(hoja_nomina, prin_fila_actual, COL_PRIMER_APELLIDO, datos[2]);
        set_cell(hoja_nomina, prin_fila_actual, COL_SEGUNDO_APELLIDO, datos[3]);
        set_cell(hoja_nomina, prin_fila_actual, COL_PRIMER_NOMBRE, datos[4]);
        set_cell(hoja_nomina, prin_fila_actual, COL_SEGUNDO_NOMBRE, datos[5]);
        set_cell(hoja_nomina, prin_fila_actual, COL_CORREO, datos[6]);
        set_cell(hoja_nomina, prin_fila_actual, COL_PAIS, datos[7]);
        set_cell(hoja_nomina, prin_fila_actual, COL_DEPARTAMENTO, datos[8]);
        set_cell(hoja_nomina, prin_fila_actual, COL_CIUDAD, datos[9]);
        set_cell(hoja_nomina, prin_fila_actual, COL_DIRECCION, datos[10]);
        set_cell(hoja_nomina, prin_fila_actual, COL_TIPO_CONTRATO, datos[11]);
        prin_fila_actual++;
        codigo_inicial++;

    }

    
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


