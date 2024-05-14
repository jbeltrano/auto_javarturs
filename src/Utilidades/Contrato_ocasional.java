package Utilidades;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Contrato_ocasional{
    
    // Atributos necesarios para dar formatos a numeros
    public static final String[] NUMERO_TEXTO_60 = {"Cero", "Un", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciséis", "Diecisiete", "Dieciocho", "Diecinueve", "Veinte", "Veintiuno", "Veintidós", "Veintitrés", "Veinticuatro", "Veinticinco", "Veintiséis", "Veintisiete", "Veintiocho", "Veintinueve", "Treinta", "Treinta y uno", "Treinta y dos", "Treinta y tres", "Treinta y cuatro", "Treinta y cinco", "Treinta y seis", "Treinta y siete", "Treinta y ocho", "Treinta y nueve", "Cuarenta", "Cuarenta y uno", "Cuarenta y dos", "Cuarenta y tres", "Cuarenta y cuatro", "Cuarenta y cinco", "Cuarenta y seis", "Cuarenta y siete", "Cuarenta y ocho", "Cuarenta y nueve", "Cincuenta", "Cincuenta y uno", "Cincuenta y dos", "Cincuenta y tres", "Cincuenta y cuatro", "Cincuenta y cinco", "Cincuenta y seis", "Cincuenta y siete", "Cincuenta y ocho", "Cincuenta y nueve", "Sesenta"};
    public static final String[] NUMERO_TEXTO_31 = {"cero","primero","segundo","tercero","cuarto","quinto","sexto","séptimo","octavo","noveno","décimo","undécimo","duodécimo","decimotercero","decimocuarto","decimoquinto","decimosexto","decimoséptimo","decimoctavo","decimonoveno","vigésimo","vigésimo primero","vigésimo segundo","vigésimo tercero","vigésimo cuarto","vigésimo quinto","vigésimo sexto","vigésimo séptimo","vigésimo octavo","vigésimo noveno","trigésimo","trigésimo primero"};
    public static final String [] MES = {"","ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO","AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
    private FileInputStream fis;
    private XWPFDocument documento;
    private String numero_contrato;

    /**
     * Constructor por defecto que abre
     * el archivo que se pasa por el parametro url
     * y para posterirormente aplicar los demas metodos
     * @param url
     * @throws IOException
     */
    public Contrato_ocasional(String url) throws IOException{

        fis = new FileInputStream(url);
        documento = new XWPFDocument(fis);
        
        
        fis.close();  
    }

    /**
     * Este metodo se encarga de modificar el archivo .docx
     * y el nombre del archivo con el numero del contrato
     * @param numero
     */
    public void set_numero_contrato(String numero){

        numero_contrato = numero;
        XWPFParagraph parrafo = documento.getParagraphs().get(5);
        XWPFRun run = parrafo.getRuns().get(0);
        
        while(numero.length() < 4){
            numero = "0"+ numero;
        }
        
        numero = "C-" + numero;
        
        run.setText(numero,0);

    }

    public void set_numero_contrato(int numero){

        set_numero_contrato(""+ numero);

    }

    /**
     * Este metodo se encargar de modificar el parrafo
     * del archivo donde se consentra la infromacion del
     * contratante junto a la tabla de firma de los mismos 
     * @param nombre
     * @param tipo_documento
     * @param numero_documento
     * @param telefono
     * @param direccion
     * @param ciudad
     */
    public void set_contratante(String nombre, String tipo_documento, String numero_documento, String telefono, String direccion, String ciudad){

        nombre = nombre.toUpperCase();
        numero_documento = Digito.convertir_formato(numero_documento);
        String texto;
        XWPFParagraph parrafo;
        XWPFRun run;
        XWPFTable tabla;
        XWPFTableRow row; 
        XWPFTableCell cell;

        
        // Modificacion del primer parrafo que tiene que ver con el contratante
        parrafo = documento.getParagraphs().get(6);

        // Modificacion del nombre del contratante
        run = parrafo.getRuns().get(15);
        run.setText(nombre,0);

        // Modificacion de los demas datos del contratante
        texto = " "+tipo_documento.toUpperCase() + ": " + numero_documento + ", teléfono: " + telefono + ", dirección: " + direccion + " " + ciudad;
        run = parrafo.getRuns().get(19);
        run.setText(texto,0);


        // Modificacion del parrafo o tabla donde esta otra parte de al inforamcion del contratante
        tabla = documento.getTables().get(0);

        // Modificando el nombre del contratante
        row = tabla.getRow(0);
        cell = row.getCell(1);
        cell.getParagraphs().get(0).getRuns().get(0).setText(nombre, 0);

        // Modificando el documento del contratante
        row = tabla.getRow(1);
        cell = row.getCell(1);
        cell.getParagraphs().get(0).getRuns().get(0).setText(tipo_documento.toUpperCase() + ": " + numero_documento,0);
    }



    public void set_cantidad_vehiculos(int cantidad){
        
        if(cantidad > 1){
            XWPFParagraph parrafo;
            XWPFRun run; 
            String text;
    
            // Dando formato al texto
            text = NUMERO_TEXTO_60[cantidad];
            text = text.toLowerCase();
    
            // Remplazando el texto en el documento
            parrafo = documento.getParagraphs().get(7);
            run = parrafo.getRuns().get(2);
            run.setText(text, 0);

            parrafo = documento.getParagraphs().get(7);
            run = parrafo.getRuns().get(3);
            run.setText(" vehículos", 0);
        }
        
    }

    /**
     * Se encarga de cargar en el archivo
     * la cantidad de pasajeros que hay en
     * la plantilla de word
     * @param cantidad
     */
    public void set_cantidad_pasajeros(int cantidad){
        
        XWPFParagraph parrafo;
        XWPFRun run; 
        String text;

        // Dando formato al texto
        text = " " +NUMERO_TEXTO_60[cantidad] + " (" + cantidad + ") ";

        // Remplazando el texto en el documento 4
        parrafo = documento.getParagraphs().get(7);
        run = parrafo.getRuns().get(8);
        run.setText(text, 0);
        
    }

    /**
     * Se encarga de cargar en el archivo
     * la cantidad de pasajros que hay, en
     * la plantilla
     * @param cantidad
     */
    public void set_cantidad_pasajeros(String cantidad){

        int cant = Integer.parseInt(cantidad); 
        set_cantidad_pasajeros(cant);

    }

    /**
     * Se encarga de establecer el origen y destino
     * en el documento de word
     * 
     * Tienen que tener el formato Municiopio o ruta (Departamento)
     * @param origen
     * @param destino
     */
    public void set_origen_destino(String origen, String destino){

        XWPFParagraph parrafo;
        XWPFRun run; 
        

        // Definiendo el parrafo a modificar
        parrafo = documento.getParagraphs().get(7);

        // localizando y remplazando el origen
        run = parrafo.getRuns().get(13);
        run.setText( " "+origen, 0);

        // localizando y remplazando el destino
        run = parrafo.getRuns().get(16);
        run.setText( destino , 0);

    }

    /**
     * Carga en el archivo el valor tanto en numero,
     * como en letras del valor del contrato
     * @param numero
     */
    public void set_valor_contrato(int numero){
        
        String texto;
        texto = Convertir_numero_texto.convertir_numero_texto(numero) + " pesos m/cte ($" + Digito.convertir_formato("" + numero) + ")";

        XWPFParagraph parrafo;
        XWPFRun run; 
        

        // Definiendo el parrafo a modificar
        parrafo = documento.getParagraphs().get(7);


        run = parrafo.getRuns().get(25);
        run.setText(texto,0);

    }

    /**
     * Cambia la duracion del contrato, como la
     * duracion en dias, y el dia mes y año para
     * el inicio y destino
     * 
     * Los dias y años junto con la duracion
     * van en datos de tipo entero, pero el mes
     * van en la descripcion de palabras
     * @param duracion
     * @param dia_inicial
     * @param mes_inicial
     * @param año_inicial
     * @param dia_final
     * @param mes_final
     * @param año_final
     */
    public void set_duracion_contrato(int duracion, int dia_inicial, String mes_inicial, int año_inicial, int dia_final, String mes_final, int año_final){

        String texto_incial;
        String texto_final;
        String texto_firma;
        XWPFParagraph parrafo;
        XWPFRun run; 
        
        mes_inicial = mes_inicial.toLowerCase();
        mes_final = mes_final.toLowerCase();
        texto_incial = dia_inicial + " de " + mes_inicial + " del " + año_inicial + " ";
        texto_final = dia_final + " de " + mes_final + " del " + año_final;
        texto_firma = " " + NUMERO_TEXTO_31[dia_inicial] + " (" + dia_inicial + ") día del mes de "+ mes_inicial + " de " + año_inicial;

        // Definiendo el parrafo a modificar
        parrafo = documento.getParagraphs().get(7);

        // modificacion de duracion
        run = parrafo.getRuns().get(37);
        if(duracion == 1){
            run.setText(NUMERO_TEXTO_60[1] + " día (1) contado",0);
        }else{
            run.setText(Convertir_numero_texto.convertir_numero_texto(duracion) + " días (" + duracion + ") contados", 0);
        }

        // modifciacion de fecha inical
        run = parrafo.getRuns().get(40);
        run.setText(texto_incial,0);

        // modificacion de fecha final
        run = parrafo.getRuns().get(43);
        run.setText(texto_final,0);
        

        // Definiendo el parrafo a modificar la ultima fecha
        parrafo = documento.getParagraphs().get(9);

        // modificacion de ultima fecha
        run = parrafo.getRuns().get(2);
        run.setText(texto_firma,0);
    }

    /**
     * Cambia la duracion del contrato
     * tanto en la duracion de dias,
     * como en la parte de las fechas
     * 
     * sin embargo todos los datos deben ser
     * enteros
     * 
     * @param duracion
     * @param dia_inicial
     * @param n_mes_inicial
     * @param año_inicial
     * @param dia_final
     * @param n_mes_final
     * @param año_final
     */
    public void set_duracion_contrato(int duracion, int dia_inicial, int n_mes_inicial, int año_inicial, int dia_final, int n_mes_final, int año_final){

        String texto_incial;
        String texto_final;
        String texto_firma;
        String mes_inicial;
        String mes_final;
        XWPFParagraph parrafo;
        XWPFRun run; 
        
        mes_inicial = MES[n_mes_inicial].toLowerCase();
        mes_final = MES[n_mes_final].toLowerCase();
        texto_incial = dia_inicial + " de " + mes_inicial + " del " + año_inicial + " ";
        texto_final = dia_final + " de " + mes_final + " del " + año_final;
        texto_firma = " " + NUMERO_TEXTO_31[dia_inicial] + " (" + dia_inicial + ") día del mes de "+ mes_inicial + " de " + año_inicial;

        // Definiendo el parrafo a modificar
        parrafo = documento.getParagraphs().get(7);

        // modificacion de duracion
        run = parrafo.getRuns().get(33);
        if(duracion == 1){
            run.setText(NUMERO_TEXTO_60[1] + " día (1) contado",0);
        }else{
            run.setText(Convertir_numero_texto.convertir_numero_texto(duracion) + " días (" + duracion + ") contados", 0);
        }

        // modifciacion de fecha inical
        run = parrafo.getRuns().get(36);
        run.setText(texto_incial,0);

        // modificacion de fecha final
        run = parrafo.getRuns().get(39);
        run.setText(texto_final,0);
        

        // Definiendo el parrafo a modificar la ultima fecha
        parrafo = documento.getParagraphs().get(9);

        // modificacion de ultima fecha
        run = parrafo.getRuns().get(2);
        run.setText(texto_firma,0);
    }    

    /**
     * Se encarga de guardar el arhivo en
     * el formato docx relacionando
     * el numeor de placa del vehiculo
     * @param url
     * @param placas
     * @throws IOException
     */
    public void guardar(String url, String placas) throws IOException{

        url = url + numero_contrato + "(" + placas + ").docx";
        FileOutputStream fos = new FileOutputStream(url);
        documento.write(fos);

        fos.close();

    }

    /**
     * Guarda el archivo en formato docx
     * relacionando solo el numero de contrato
     * @param url
     * @throws IOException
     */
    public void guardar(String url) throws IOException{

        url = url + numero_contrato + ".docx";
        FileOutputStream fos = new FileOutputStream(url);
        documento.write(fos);

        fos.close();

    }

    /**
     * Guarda el documento en formato docx
     * con varias placas
     * @param url
     * @param placas
     * @throws IOException
     */
    public void guardar(String url, String[] placas) throws IOException{

        url = url + numero_contrato + "(" + placas + ").docx";
        FileOutputStream fos = new FileOutputStream(url);
        documento.write(fos);

        fos.close();

    }
}

