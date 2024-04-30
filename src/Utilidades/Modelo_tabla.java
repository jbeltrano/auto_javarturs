package Utilidades;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Modelo_tabla {
    
    public static DefaultTableModel set_modelo_tablas(String [][] datos){
        DefaultTableModel modelo;

        
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas no sean editables
                return false;
            }
        };

        for(int i = 0; i < datos[0].length; i++){
            modelo.addColumn(datos[0][i]);
        }
    
        for(int i = 1; i < datos.length; i++){
                
            modelo.addRow(datos[i]);
    
        }
        return modelo;
    }
    
    
    public static void add_mouse_listener(JTable tabla){

        tabla.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Obtener la fila seleccionada
                    int filaSeleccionada = tabla.rowAtPoint(e.getPoint());
                    int columna = tabla.columnAtPoint(e.getPoint());

                    // Seleccionar la fila
                    tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                    tabla.setColumnSelectionInterval(columna,columna);

                    // Mostrar el menú contextual
                    
                }
                
            }
        });
    }

    public static JTable set_tabla_contratante(String[][] datos){
        
        JTable tab;
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(100);
        clum_model.getColumn(1).setPreferredWidth(40);
        clum_model.getColumn(2).setPreferredWidth(200);
        clum_model.getColumn(3).setPreferredWidth(100);
        clum_model.getColumn(4).setPreferredWidth(200);
        clum_model.getColumn(5).setPreferredWidth(100);
        clum_model.getColumn(6).setPreferredWidth(180);
        

        return tab;

    }

    public static JTable set_tabla_contratos_mensuales(String[][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(60);
        clum_model.getColumn(1).setPreferredWidth(60);
        clum_model.getColumn(2).setPreferredWidth(100);
        clum_model.getColumn(3).setPreferredWidth(200);
        clum_model.getColumn(4).setPreferredWidth(100);
        clum_model.getColumn(5).setPreferredWidth(200);
        clum_model.getColumn(6).setPreferredWidth(100);
        clum_model.getColumn(7).setPreferredWidth(200);
        

        return tab;

    }

    public static JTable set_tabla_extractos_ocasionales(String[][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Virifica las columnas donde se encuentran las fechas                
                if (column == 7) {
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 0;
                    String valor = table.getValueAt(row, column).toString();
                    LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                    LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                    cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

                    // Esto es para que los ToolTip funcionen correctamente
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ToolTipManager.sharedInstance().setDismissDelay(60000);

                    // Verifica cuntos dias quedan para dar un color a las celdas
                    if(cantidad_dias < 0){

                        component.setBackground(Color.red);
                        component.setForeground(Color.white);
                        setToolTipText("Extracto Vencido");

                    }else{

                        component.setBackground(Color.white);
                        component.setForeground(Color.black);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(Color.white);
                    component.setForeground(Color.black);
                    setToolTipText(null);

                }
                
                if (isSelected) {
                    component.setBackground(table.getSelectionBackground());
                    component.setForeground(table.getSelectionForeground());
                }
                return component;
            }
        };

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setDefaultRenderer(Object.class, renderer);     //Agrega el renderer personalizado realizado anteriormente
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(70);
        clum_model.getColumn(1).setPreferredWidth(40);
        clum_model.getColumn(2).setPreferredWidth(40);
        clum_model.getColumn(3).setPreferredWidth(40);
        clum_model.getColumn(4).setPreferredWidth(100);
        clum_model.getColumn(5).setPreferredWidth(180);
        

        return tab;

    }

    public static JTable set_tabla_extractos_mensuales(String[][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Virifica las columnas donde se encuentran las fechas                
                if (column == 7) {
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 0;
                    String valor = table.getValueAt(row, column).toString();
                    LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                    LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                    cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

                    // Esto es para que los ToolTip funcionen correctamente
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ToolTipManager.sharedInstance().setDismissDelay(60000);

                    // Verifica cuntos dias quedan para dar un color a las celdas
                    if(cantidad_dias < 0){

                        component.setBackground(Color.red);
                        component.setForeground(Color.white);
                        setToolTipText("Extracto Vencido");

                    }else{

                        component.setBackground(Color.white);
                        component.setForeground(Color.black);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(Color.white);
                    component.setForeground(Color.black);
                    setToolTipText(null);

                }
                
                return component;
            }
        };

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setDefaultRenderer(Object.class, renderer);     //Agrega el renderer personalizado realizado anteriormente
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(70);
        clum_model.getColumn(1).setPreferredWidth(40);
        clum_model.getColumn(2).setPreferredWidth(40);
        clum_model.getColumn(3).setPreferredWidth(40);
        clum_model.getColumn(4).setPreferredWidth(100);
        clum_model.getColumn(5).setPreferredWidth(180);
        

        return tab;

    }

    public static JTable set_tabla_conductores(String[][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Virifica las columnas donde se encuentran las fechas                
                if (column == 3) {
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 0;
                    String valor = table.getValueAt(row, column).toString();
                    LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                    LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                    cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

                    // Esto es para que los ToolTip funcionen correctamente
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ToolTipManager.sharedInstance().setDismissDelay(60000);

                    // Verifica cuntos dias quedan para dar un color a las celdas
                    if(cantidad_dias < 0){

                        component.setBackground(Color.red);
                        component.setForeground(Color.white);
                        setToolTipText("Documento Vencido");

                    }else if(cantidad_dias <= 25){

                        component.setBackground(Color.yellow);
                        component.setForeground(Color.black);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }else{      // Deja las demas celdas por defecto

                        component.setBackground(Color.white);
                        component.setForeground(Color.black);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(Color.white);
                    component.setForeground(Color.black);
                    setToolTipText(null);

                }
                
                return component;
            }
        };

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setDefaultRenderer(Object.class, renderer);     //Agrega el renderer personalizado realizado anteriormente
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(100);
        clum_model.getColumn(1).setPreferredWidth(200);
        clum_model.getColumn(2).setPreferredWidth(80);
        clum_model.getColumn(3).setPreferredWidth(120);
        

        return tab;

    }

    public static JTable set_tabla_personas(String[][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(80);
        clum_model.getColumn(1).setPreferredWidth(50);
        clum_model.getColumn(2).setPreferredWidth(200);
        clum_model.getColumn(3).setPreferredWidth(90);
        clum_model.getColumn(4).setPreferredWidth(70);
        clum_model.getColumn(5).setPreferredWidth(60);
        clum_model.getColumn(6).setPreferredWidth(150);
        clum_model.getColumn(7).setPreferredWidth(220);


        return tab;

    }

    public static JTable set_tabla_departamento(String [][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(50);
        clum_model.getColumn(1).setPreferredWidth(160);
        

        return tab;
    }

    public static JTable set_tabla_ciudad(String [][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(30);
        clum_model.getColumn(1).setPreferredWidth(140);
        clum_model.getColumn(2).setPreferredWidth(160);
        
        
        return tab;

    }

    public static JTable set_tabla_vehiculo(String [][] datos){
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(50);
        clum_model.getColumn(1).setPreferredWidth(70);
        clum_model.getColumn(2).setPreferredWidth(70);
        clum_model.getColumn(3).setPreferredWidth(110);
        clum_model.getColumn(4).setPreferredWidth(70);
        clum_model.getColumn(5).setPreferredWidth(60);
        clum_model.getColumn(6).setPreferredWidth(100);
        clum_model.getColumn(7).setPreferredWidth(80);
        clum_model.getColumn(8).setPreferredWidth(60);
        clum_model.getColumn(9).setPreferredWidth(70);
        clum_model.getColumn(10).setPreferredWidth(110);
        clum_model.getColumn(11).setPreferredWidth(140);
        clum_model.getColumn(12).setPreferredWidth(35);
        clum_model.getColumn(13).setPreferredWidth(40);
        clum_model.getColumn(14).setPreferredWidth(80);
        clum_model.getColumn(15).setPreferredWidth(200);

        return tab;
    }

    public static JTable set_tabla_contratos_ocasionales(String[][] datos){
        JTable tab;
        DefaultTableModel modelo;
        TableColumnModel cl_model;
        modelo = set_modelo_tablas(datos);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(column == 10){
                    setValue(Digito.convertir_formato((String) value));

                }
                if ( column == 5) { // Virifica las columnas donde se encuentran las fechas
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 0;
                    String valor = table.getValueAt(row, column).toString();
                    LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                    LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                    cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

                    // Esto es para que los ToolTip funcionen correctamente
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ToolTipManager.sharedInstance().setDismissDelay(60000);

                    // Verifica cuntos dias quedan para dar un color a las celdas
                    if(cantidad_dias < 0){

                        component.setBackground(Color.red);
                        component.setForeground(Color.white);
                        setToolTipText("contrato vencido");

                    }else if(cantidad_dias <= 3){

                        component.setBackground(Color.yellow);
                        component.setForeground(Color.black);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el contrato se venza");

                    }else{      // Deja las demas celdas por defecto

                        component.setBackground(Color.white);
                        component.setForeground(Color.black);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(Color.white);
                    component.setForeground(Color.black);
                    setToolTipText(null);

                }
                
                if (isSelected) {
                    component.setBackground(table.getSelectionBackground());
                    component.setForeground(table.getSelectionForeground());
                }
                
                return component;
            }
        };


        tab = new JTable(modelo);
        tab.setDefaultRenderer(Object.class, renderer);     //Agrega el renderer personalizado realizado anteriormente
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setCellSelectionEnabled(true);
        add_mouse_listener(tab);
        
        
        cl_model = tab.getColumnModel();
        cl_model.getColumn(0).setPreferredWidth(50);
        cl_model.getColumn(1).setPreferredWidth(100);
        cl_model.getColumn(2).setPreferredWidth(45);
        cl_model.getColumn(3).setPreferredWidth(200);
        cl_model.getColumn(4).setPreferredWidth(85);
        cl_model.getColumn(5).setPreferredWidth(85);
        cl_model.getColumn(6).setPreferredWidth(100);
        cl_model.getColumn(7).setPreferredWidth(100);
        cl_model.getColumn(8).setPreferredWidth(100);
        cl_model.getColumn(9).setPreferredWidth(100);

        return tab;
    }

    public static JTable set_tabla_vehiculo_has_conductor(String[][] datos){

        JTable tab;
        DefaultTableModel modelo;
        TableColumnModel cl_model;

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Virifica las columnas donde se encuentran las fechas                
                if (column == 5) {
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 0;
                    String valor = table.getValueAt(row, column).toString();
                    LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                    LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                    cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

                    // Esto es para que los ToolTip funcionen correctamente
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ToolTipManager.sharedInstance().setDismissDelay(60000);

                    // Verifica cuntos dias quedan para dar un color a las celdas
                    if(cantidad_dias < 0){

                        component.setBackground(Color.red);
                        component.setForeground(Color.white);
                        setToolTipText("Documento Vencido");

                    }else if(cantidad_dias <= 25){

                        component.setBackground(Color.yellow);
                        component.setForeground(Color.black);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }else{      // Deja las demas celdas por defecto

                        component.setBackground(Color.white);
                        component.setForeground(Color.black);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(Color.white);
                    component.setForeground(Color.black);
                    setToolTipText(null);

                }
                
                return component;
            }
        };

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setDefaultRenderer(Object.class, renderer);     //Agrega el renderer personalizado realizado anteriormente
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setCellSelectionEnabled(true);
        add_mouse_listener(tab);
        
        
        cl_model = tab.getColumnModel();
        cl_model.getColumn(0).setPreferredWidth(50);
        cl_model.getColumn(1).setPreferredWidth(35);
        cl_model.getColumn(2).setPreferredWidth(100);
        cl_model.getColumn(3).setPreferredWidth(180);
        cl_model.getColumn(5).setPreferredWidth(100);

        return tab;
    }

    public static JTable set_tabla_documentos_vehiculos(String [][] datos){

        JTable tab;
        DefaultTableModel modelo;
        TableColumnModel cl_model;

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Virifica las columnas donde se encuentran las fechas                
                if (column == 2 || column == 3 || column == 4 || column == 5 || column == 7) {
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 0;
                    String valor = table.getValueAt(row, column).toString();
                    LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                    LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                    cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos

                    // Esto es para que los ToolTip funcionen correctamente
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ToolTipManager.sharedInstance().setDismissDelay(60000);

                    // Verifica cuntos dias quedan para dar un color a las celdas
                    if(cantidad_dias < 0){

                        component.setBackground(Color.red);
                        component.setForeground(Color.white);
                        setToolTipText("Documento Vencido");

                    }else if(cantidad_dias <= 60 && cantidad_dias >= 25 && column == 7){   

                        component.setBackground(Color.MAGENTA);
                        component.setForeground(Color.white);
                        setToolTipText("Iniciar Tramite Renovacion Tarjeta de Operación");

                    }else if(cantidad_dias <= 25){

                        component.setBackground(Color.yellow);
                        component.setForeground(Color.black);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }else{      // Deja las demas celdas por defecto

                        component.setBackground(Color.white);
                        component.setForeground(Color.black);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(Color.white);
                    component.setForeground(Color.black);
                    setToolTipText(null);

                }
                
                return component;
            }
        };


        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setDefaultRenderer(Object.class, renderer);     //Agrega el renderer personalizado realizado anteriormente
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setCellSelectionEnabled(true);
        add_mouse_listener(tab);
        
        
        cl_model = tab.getColumnModel();
        cl_model.getColumn(0).setPreferredWidth(50);
        cl_model.getColumn(1).setPreferredWidth(35);
        cl_model.getColumn(2).setPreferredWidth(100);
        cl_model.getColumn(3).setPreferredWidth(180);
        cl_model.getColumn(5).setPreferredWidth(100);

        return tab;
    }

    

}
