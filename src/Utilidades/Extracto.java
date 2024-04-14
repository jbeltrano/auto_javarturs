package Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Extracto {
    
    public static final int ESTUDIANTIL = 1;
    public static final int EMPRESARIAL = 3;
    public static final int PARTICULAR = 2;
    public static final int PERSONALIZADO = 4;
    public static final String COMPLEMENTO_NOMRAL = "OCASIONAL NORMAL";
    public static final String COMPLEMENTO_EXTEMPORANEO = "OCASIONAL EXTEMPORANEO";
    public static final String TIPO_CONTRATO_PARTICULAR = "SERVICIO DE TRANSPORTE DE PERSONAL PARTICULAR";
    public static final String TIPO_CONTRATO_EMPRESARIAL = "SERVICIO DE TRANSPORTE DE PERSONAL EMPRESARIAL";
    public static final String TIPO_CONTRATO_ESTUDIANTIL = "SERVICIO DE TRANSPORTE DE PERSONAL ESTUDIANTIL";
    public static final String TIPO_CONTRATO_PERSONALIZADO = "SERVICIO DE TRANSPORTE DE PERSONAL PRESENTADO POR ";
    public static final String [] MES = {"","ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO","AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
    private static final int ROW_NUMERO_PRINCIPAL = 8;
    private static final int COLUMN_NUMERO_PRINCIPAL = 0;
    private static final int ROW_CONTRATO = 11;
    private static final int COLUMN_CONTRARO = 2;
    private static final int ROW_CONTRATANTE = 12;
    private static final int COLUMN_NOMBRE_CONTRATANTE = 2;
    private static final int COLUMN_NUM_ID_CONTRATANTE = 4;
    private static final int ROW_OBJETO_CONTRATO = 13;
    private static final int COLUMN_BOJETO_CONTRATO = 2;
    private static final int ROW_ORIGEN_DESTINO = 14;
    private static final int COLUMN_ORIGEN_DESTINO = 2;
    private static final int ROW_CONVENIO = 15;
    private static final int COLUMN_CONVENIO_NOMBRE = 0;
    private static final int COLUMN_CONVENIO_DOCUMENTO = 4;
    private static final int ROW_FECHA_INICIAL = 18;
    private static final int ROW_FECHA_FINAL = 20;
    private static final int COLUMN_DIA = 3;
    private static final int COLUMN_MES = 4;
    private static final int COLUMN_AÑO = 5;
    private static final int ROW_DATOS_VEHICULO1 = 23;
    private static final int ROW_DATOS_VEHICULO2 = 25;
    private static final int COLUMN_PLACA = 0;
    private static final int COLUMN_MODELO = 2;
    private static final int COLUMN_MARCA = 4;
    private static final int COLUMN_CLASE = 5;
    private static final int COLUMN_NUMERO_INTERNO = 0;
    private static final int COLUMN_TOP = 3;
    private static final int ROW_CONDUCTOR1 = 27;
    private static final int ROW_CONDUCTOR2 = 29;
    private static final int ROW_CONDUCTOR3 = 31;
    private static final int COLUMN_NOMBRE = 2;
    private static final int COLUMN_CEDULA = 3;
    private static final int COLUMN_LICENCIA = 4;
    private static final int COLUMN_VIGENCIA = 5;
    private static final int ROW_RESPONSABLE_CONTRATO = 33;
    private static final int COLUMN_CELULAR = 4;
    private static final int COLUMN_DIRECCION = 5;

    private Workbook libro;
    private Sheet hoja;
    private FileInputStream plantilla;

    public Extracto(String dir_formato) throws IOException{

        
        plantilla = new FileInputStream(new File(dir_formato));
        libro = new XSSFWorkbook(plantilla);
        plantilla.close();
        hoja = libro.getSheetAt(0);

    }


    // Metodos especificos
    public void set_numero_principal(String año, String contrato, String consecutivo){

        String numero = "No 550018702";
        numero += año + agregar_ceros(contrato) + agregar_ceros(consecutivo);
        set_cell(ROW_NUMERO_PRINCIPAL, COLUMN_NUMERO_PRINCIPAL, numero);
    
    }

    public void set_contrato(String valor){
        String contrato = "C-" + agregar_ceros(valor);
        set_cell(ROW_CONTRATO, COLUMN_CONTRARO, contrato);
    }

    /**
     * Este metodo se encarga de modificar los valores
     * de las celdas donde esta ubicado el contratante
     * adicionalmente dependiendo del tipo de documento
     * hace un set en en tipo de contrato, si es nit
     * es empresarial y si no es particular
     * @param nombre
     * @param tipo
     * @param documento
     */
    public void set_contratante(String nombre, String tipo, String documento){
        String documento_completo;
        if(tipo.equals("NIT")){
            documento_completo = tipo + ": " + convertir_formato(documento) + "-" + Digito.get_digito_nit(documento);
            set_tipo_contrato(TIPO_CONTRATO_EMPRESARIAL);
        }else{
            documento_completo = tipo + ": " + convertir_formato(documento);
            set_tipo_contrato(TIPO_CONTRATO_PARTICULAR);
        }
        
        set_cell(ROW_CONTRATANTE, COLUMN_NOMBRE_CONTRATANTE, nombre);
        set_cell(ROW_CONTRATANTE, COLUMN_NUM_ID_CONTRATANTE, documento_completo);

    }

    public void set_tipo_contrato(String valor){
        
        set_cell(ROW_OBJETO_CONTRATO, COLUMN_BOJETO_CONTRATO, valor);
    }

    public void set_tipo_contrato(int valor){

        if(valor == ESTUDIANTIL){
            set_cell(ROW_OBJETO_CONTRATO, COLUMN_BOJETO_CONTRATO, TIPO_CONTRATO_ESTUDIANTIL);
        }else if(valor == EMPRESARIAL){
            set_cell(ROW_OBJETO_CONTRATO, COLUMN_BOJETO_CONTRATO, TIPO_CONTRATO_EMPRESARIAL);
        }else if(valor == PERSONALIZADO){
            
            set_cell(ROW_OBJETO_CONTRATO, COLUMN_BOJETO_CONTRATO, TIPO_CONTRATO_PERSONALIZADO + get_cell(ROW_CONTRATANTE, COLUMN_NOMBRE_CONTRATANTE));
        }else{
            set_cell(ROW_OBJETO_CONTRATO, COLUMN_BOJETO_CONTRATO, TIPO_CONTRATO_PARTICULAR);
        }
    }

    public void set_origen_destino(String municipio_origen, String departamento_origen, String municipio_destino, String departamento_destino){
        municipio_origen = municipio_origen.toUpperCase();
        municipio_destino = municipio_destino.toUpperCase();
        departamento_origen = departamento_origen.toUpperCase();
        departamento_destino = departamento_destino.toUpperCase();

        String ruta = municipio_origen + " (" + departamento_origen + ") - " + municipio_destino + " (" + departamento_destino + ") IDA Y REGRESO";

        set_cell(ROW_ORIGEN_DESTINO, COLUMN_ORIGEN_DESTINO, ruta);
    }

    public void set_origen_destino(String ruta){

        set_cell(ROW_ORIGEN_DESTINO, COLUMN_ORIGEN_DESTINO, ruta);

    }

    public void set_convenio(String id, String nombre){
        String documento = "NIT: " + convertir_formato(id) + "-" + Digito.get_digito_nit(id);
        String nombre_empresa = "CONVENIO CONSORCIO UNION TEMPORAL CON: \n"+nombre;

        set_cell(ROW_CONVENIO, COLUMN_CONVENIO_NOMBRE,nombre_empresa);
        set_cell(ROW_CONVENIO, COLUMN_CONVENIO_DOCUMENTO,documento);
    }
    public void set_fecha_incial(int dia, String mes, int año){
        mes = mes.toUpperCase();

        set_cell(ROW_FECHA_INICIAL, COLUMN_DIA, dia);
        set_cell(ROW_FECHA_INICIAL, COLUMN_MES, mes);
        set_cell(ROW_FECHA_INICIAL, COLUMN_AÑO, año);

    }

    public void set_fecha_inicial(String fecha){
        LocalDate date;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-M-d");
        date = LocalDate.parse(fecha, formato);

        set_fecha_incial(date.getDayOfMonth(), MES[date.getMonthValue()], date.getYear());

    }

    public void set_fecha_final(String fecha){
        LocalDate date;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-M-d");
        date = LocalDate.parse(fecha, formato);

        set_fecha_final(date.getDayOfMonth(), MES[date.getMonthValue()], date.getYear());
        
    }

    public void set_fecha_final(int dia, String mes, int año){
        mes = mes.toUpperCase();

        set_cell(ROW_FECHA_FINAL, COLUMN_DIA, dia);
        set_cell(ROW_FECHA_FINAL, COLUMN_MES, mes);
        set_cell(ROW_FECHA_FINAL, COLUMN_AÑO, año);
    }

    public void set_datos_vehiculo(String placa, int modelo, String marca, String clase, int numero_interno, int top){
        
        set_cell(ROW_DATOS_VEHICULO1, COLUMN_PLACA, placa);
        set_cell(ROW_DATOS_VEHICULO1, COLUMN_MODELO, modelo);
        set_cell(ROW_DATOS_VEHICULO1, COLUMN_MARCA, marca);
        set_cell(ROW_DATOS_VEHICULO1, COLUMN_CLASE, clase);
        set_cell(ROW_DATOS_VEHICULO2, COLUMN_NUMERO_INTERNO, numero_interno);
        set_cell(ROW_DATOS_VEHICULO2, COLUMN_TOP, top);

    }

    public void set_conductor1(String nombre, String documento, String vigencia){

        nombre = nombre.toUpperCase();

        set_cell(ROW_CONDUCTOR1, COLUMN_NOMBRE, nombre);
        set_cell(ROW_CONDUCTOR1, COLUMN_CEDULA, convertir_formato(documento));
        set_cell(ROW_CONDUCTOR1, COLUMN_LICENCIA, convertir_formato(documento));
        set_cell(ROW_CONDUCTOR1, COLUMN_VIGENCIA, vigencia);

    }

    public void set_conductor2(String nombre, String documento, String vigencia){
        
        nombre = nombre.toUpperCase();

        set_cell(ROW_CONDUCTOR2, COLUMN_NOMBRE, nombre);
        set_cell(ROW_CONDUCTOR2, COLUMN_CEDULA, convertir_formato(documento));
        set_cell(ROW_CONDUCTOR2, COLUMN_LICENCIA, convertir_formato(documento));
        set_cell(ROW_CONDUCTOR2, COLUMN_VIGENCIA, vigencia);
        

    }

    public void set_conductor3(String nombre, String documento, String vigencia){
        
        nombre = nombre.toUpperCase();

        set_cell(ROW_CONDUCTOR3, COLUMN_NOMBRE, nombre);
        set_cell(ROW_CONDUCTOR3, COLUMN_CEDULA, convertir_formato(documento));
        set_cell(ROW_CONDUCTOR3, COLUMN_LICENCIA, convertir_formato(documento));
        set_cell(ROW_CONDUCTOR3, COLUMN_VIGENCIA, vigencia);
    
    }

    public void set_responsable(String nombre, String documento, String celular, String direccion){

        nombre = nombre.toUpperCase();
        direccion = direccion.toUpperCase();

        set_cell(ROW_RESPONSABLE_CONTRATO,COLUMN_NOMBRE,nombre);
        set_cell(ROW_RESPONSABLE_CONTRATO,COLUMN_CEDULA,convertir_formato(documento));
        set_cell(ROW_RESPONSABLE_CONTRATO,COLUMN_CELULAR,celular);
        set_cell(ROW_RESPONSABLE_CONTRATO,COLUMN_DIRECCION,direccion);
    }

    // Metodos generales
    /**
     * Este metodo se utiliza para realizar un set a cualquier
     * celda dentro de la hoja de caculo
     * @param row
     * @param column
     * @param valor
     */
    private void set_cell(int row, int column, String valor){
        valor = valor.toUpperCase();
        Row fila = hoja.getRow(row);
        Cell celda = fila.getCell(column);
        celda.setCellValue(valor);

    }

    private void set_cell(int row, int column, int valor){

        Row fila = hoja.getRow(row);
        Cell celda = fila.getCell(column);
        celda.setCellValue(valor);

    }

    private String get_cell(int row, int column){
        Row fila = hoja.getRow(row);
        Cell celda = fila.getCell(column);
        
        return celda.toString();
    }

    private String agregar_ceros(String dato){
        String cadena = "";
        for(int i = 0;i < 4 - dato.length(); i++){
            cadena += "0";
        }

        cadena += dato;
        return cadena;
    }

    /**
     * Este metodo guarda las modificaciones que se hicieron
     * en el archivo con el numero de placa y en la direccion indicada
     * 
     * @param dir_salida
     * @param tipo
     * @throws IOException
     */
    public void guardar(String dir_salida) throws IOException{

        guardar(dir_salida, "");
        
    }

    public void guardar(String dir_salida, String complemento) throws IOException{
        String nombre_archivo = get_cell(ROW_DATOS_VEHICULO1, COLUMN_PLACA) + " " + complemento + ".xlsx";
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
            libro.close();
            plantilla.close();
            
        }catch(IOException ex){
            throw ex;
        }
        
    }


    // Metodos estaticos
    public static String convertir_formato(String numero){
        numero = String.format("%,d", Long.parseLong(numero));
        return numero;
    }

    
}
