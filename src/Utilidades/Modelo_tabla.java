package Utilidades;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.table.TableColumn;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Modelo_tabla {
    
    public static float DEFAULT_ZOOM_FACTOR = 1.1f;
    private static final float MIN_ZOOM = 0.5f;
    private static final float MAX_ZOOM = 2.0f;
    private static final String ZOOM_KEY = "table.zoom.factor";

    public static void setTableZoom(JTable table, float zoomFactor) {
        // Obtener el zoom actual de la tabla específica
        float currentZoom = table.getClientProperty(ZOOM_KEY) != null ? 
            (float) table.getClientProperty(ZOOM_KEY) : 1.0f;
            
        // Si el factor es 1.0, significa que queremos mantener el zoom actual
        float newZoom = (zoomFactor == 1.0f) ? currentZoom : currentZoom * zoomFactor;
        
        // Verificar límites de zoom
        if (newZoom < MIN_ZOOM || newZoom > MAX_ZOOM) {
            return;
        }
        
        // Guardar el nuevo zoom en la tabla específica
        table.putClientProperty(ZOOM_KEY, newZoom);
        
        // Obtener la fuente actual
        Font currentFont = table.getFont();
        // Calcular el nuevo tamaño de fuente base
        int baseSize = 12; // Tamaño base de la fuente
        int newSize = Math.round(baseSize * currentZoom);
        // Crear nueva fuente con el tamaño ajustado
        Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), newSize);
        
        // Aplicar la nueva fuente a la tabla y al encabezado
        table.setFont(newFont);
        table.getTableHeader().setFont(newFont);
        
        // Ajustar la altura de las filas
        int baseRowHeight = 16; // Altura base de la fila
        int rowHeight = Math.round(baseRowHeight * currentZoom);
        table.setRowHeight(rowHeight);
        
        // Ajustar el ancho de las columnas proporcionalmente usando los anchos base
        TableColumnModel columnModel = table.getColumnModel();
        int[] baseWidths = (int[]) table.getClientProperty(COLUMN_BASE_WIDTHS);
        if (baseWidths != null) {
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);
                // Aplicamos el zoom al ancho base original
                column.setPreferredWidth(Math.round(baseWidths[i] * newZoom));
            }
        }
    }
    
    private static final String COLUMN_BASE_WIDTHS = "table.column.base.widths";

    public static void setupZoomListener(JTable table) {
        // Guardar los anchos base de las columnas
        TableColumnModel columnModel = table.getColumnModel();
        int[] baseWidths = new int[columnModel.getColumnCount()];
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            baseWidths[i] = columnModel.getColumn(i).getPreferredWidth();
        }
        table.putClientProperty(COLUMN_BASE_WIDTHS, baseWidths);

        // Listener para el mouse wheel
        table.addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                // Zoom con Ctrl + rueda
                float zoomFactor = e.getWheelRotation() < 0 ? 1.1f : 0.9f;
                setTableZoom(table, zoomFactor);
                e.consume(); // Evita que el evento se propague
            } else {
                // Scroll normal cuando no se presiona Ctrl
                int unitsToScroll = e.getUnitsToScroll();
                Rectangle visibleRect = table.getVisibleRect();
                visibleRect.y += unitsToScroll * table.getRowHeight();
                table.scrollRectToVisible(visibleRect);
            }
        });
        
        // Listener para las teclas Ctrl + y Ctrl -
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) {
                        setTableZoom(table, 1.1f);
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                        setTableZoom(table, 0.9f);
                        e.consume();
                    }
                }
            }
        });
    }
    
    private static void preserveTableProperties(JTable source, JTable target) {
        // Preservar el zoom
        Float zoom = (Float) source.getClientProperty(ZOOM_KEY);
        if (zoom != null) {
            target.putClientProperty(ZOOM_KEY, zoom);
            setTableZoom(target, 1.0f);
        }
        
        // Preservar anchos de columna si tienen el mismo número de columnas
        if (source.getColumnCount() == target.getColumnCount()) {
            TableColumnModel sourceColumns = source.getColumnModel();
            TableColumnModel targetColumns = target.getColumnModel();
            for (int i = 0; i < sourceColumns.getColumnCount(); i++) {
                targetColumns.getColumn(i).setPreferredWidth(
                    sourceColumns.getColumn(i).getPreferredWidth());
            }
        }
    }

    public static void updateTableModel(JTable table, String[][] datos) {
        // Crear una tabla temporal con los nuevos datos
        JTable tempTable = new JTable(set_modelo_tablas(datos));
        
        // Preservar propiedades antes de actualizar el modelo
        preserveTableProperties(table, tempTable);
        
        // Actualizar el modelo y columnas
        table.setModel(tempTable.getModel());
        table.setColumnModel(tempTable.getColumnModel());
    }

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
        JTable tab = new JTable();
        TableColumnModel clum_model;
        
        // Configuración inicial de la tabla
        tab = new JTable(set_modelo_tablas(datos));
        tab.setShowGrid(true);
        tab.setGridColor(Color.GRAY);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuración del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(100);
        clum_model.getColumn(1).setPreferredWidth(40);
        clum_model.getColumn(2).setPreferredWidth(200);
        clum_model.getColumn(3).setPreferredWidth(100);
        clum_model.getColumn(4).setPreferredWidth(200);
        clum_model.getColumn(5).setPreferredWidth(100);
        clum_model.getColumn(6).setPreferredWidth(180);
        
        // Guardar los anchos base de las columnas para el zoom
        int[] baseWidths = new int[clum_model.getColumnCount()];
        for (int i = 0; i < clum_model.getColumnCount(); i++) {
            baseWidths[i] = clum_model.getColumn(i).getPreferredWidth();
        }
        tab.putClientProperty(COLUMN_BASE_WIDTHS, baseWidths);
        
        setupZoomListener(tab);
        return tab;

    }

    public static JTable set_tabla_contratos_mensuales(String[][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        

        
        setupZoomListener(tab);
        return tab;

    }

    /**
     * Este metodo retorna un JTable, con los datos cargados
     * relacionados a la tabla que se va a mostar
     * para los vehiculos externos o con conveio
     * @param datos
     * @return JTable
     */
    public static JTable set_tabla_vh_convenio(String[][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;

        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        

        
        setupZoomListener(tab);
        return tab;
    }

    public static JTable set_tabla_extractos_ocasionales(String[][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        Color back_color = tab.getBackground();
        Color fore_color = tab.getForeground();

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

                        component.setBackground(back_color);
                        component.setForeground(fore_color);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(back_color);
                    component.setForeground(fore_color);
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
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        

        
        setupZoomListener(tab);
        return tab;

    }

    public static JTable set_tabla_extractos_mensuales(String[][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        Color back_color = tab.getBackground();
        Color fore_color = tab.getForeground();

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

                        component.setBackground(back_color);
                        component.setForeground(fore_color);
                        setToolTipText("Quedan " + cantidad_dias + " días para que el documento se venza");

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(back_color);
                    component.setForeground(fore_color);
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
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        

        
        setupZoomListener(tab);
        return tab;

    }

    public static JTable set_tabla_conductores(String[][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        Color back_color = tab.getBackground();
        Color fore_color = tab.getForeground();
        
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

                        component.setBackground(back_color);
                        component.setForeground(fore_color);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(back_color);
                    component.setForeground(fore_color);
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
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        

        
        setupZoomListener(tab);
        return tab;

    }

    public static JTable set_tabla_personas(String[][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);    // Cambia el color de las lineas de las tablas
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        

        // tab.setFillsViewportHeight(true);
        // tab.setGridColor(Color.BLACK); // Cambiar el color de las líneas de la cuadrícula
        // tab.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Cambiar la fuente de la tabla
        // tab.setBackground(Color.WHITE); // Cambiar el color de fondo de la tabla
        // tab.setForeground(Color.BLACK); // Cambiar el color del texto de la tabla
        // tab.setSelectionBackground(new Color(51, 153, 255)); // Cambiar el color de fondo de la selección
        // tab.setSelectionForeground(Color.WHITE); // Cambiar el color del texto de la selección
        // tab.getTableHeader().setBackground(new Color(240, 240, 240)); // Cambiar el color de fondo del encabezado
        // tab.getTableHeader().setForeground(Color.BLACK);

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
        


        
        setupZoomListener(tab);  // Agregamos el control de zoom
        setupZoomListener(tab);
        return tab;

    }

    public static JTable set_tabla_departamento(String [][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(50);
        clum_model.getColumn(1).setPreferredWidth(160);
        

        
        setupZoomListener(tab);
        return tab;
    }

    public static JTable set_tabla_ruta(String [][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(50);
        clum_model.getColumn(1).setPreferredWidth(160);
        clum_model.getColumn(2).setPreferredWidth(50);
        clum_model.getColumn(3).setPreferredWidth(160);
        clum_model.getColumn(4).setPreferredWidth(60);
        

        
        setupZoomListener(tab);
        return tab;
    }

    public static JTable set_tabla_ciudad(String [][] datos){
        
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        
        
        
        setupZoomListener(tab);
        return tab;

    }

    public static JTable set_tabla_vehiculo(String [][] datos){
        JTable tab = new JTable();
        DefaultTableModel modelo; 
        TableColumnModel clum_model;
        
        modelo = set_modelo_tablas(datos);
        tab = new JTable(modelo);
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add_mouse_listener(tab);
        tab.setCellSelectionEnabled(true);
        
        // Configuarcion del tamaño de las columnas
        clum_model = tab.getColumnModel();
        clum_model.getColumn(0).setPreferredWidth(70);
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

        
        setupZoomListener(tab);
        return tab;
    }

    public static JTable set_tabla_contratos_ocasionales(String[][] datos){
        JTable tab = new JTable();
        DefaultTableModel modelo;
        TableColumnModel cl_model;
        modelo = set_modelo_tablas(datos);
        Color back_color = tab.getBackground();
        Color fore_color = tab.getForeground();
        
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

                        component.setBackground(back_color);
                        component.setForeground(fore_color);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(back_color);
                    component.setForeground(fore_color);
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
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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
        cl_model.getColumn(10).setPreferredWidth(100);
        cl_model.getColumn(11).setPreferredWidth(100);

        
        setupZoomListener(tab);
        return tab;
    }

    public static JTable set_tabla_vehiculo_has_conductor(String[][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo;
        TableColumnModel cl_model;
        Color back_color = tab.getBackground();
        Color fore_color = tab.getForeground();

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

                        component.setBackground(back_color);
                        component.setForeground(fore_color);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(back_color);
                    component.setForeground(fore_color);
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
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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

        
        setupZoomListener(tab);
        return tab;
    }

    public static JTable set_tabla_documentos_vehiculos(String [][] datos){

        JTable tab = new JTable();
        DefaultTableModel modelo;
        TableColumnModel cl_model;
        Color back_color = tab.getBackground();
        Color fore_color = tab.getForeground();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Virifica las columnas donde se encuentran las fechas                
                if (column == 2 || column == 3 || column == 4 || column == 5 || column == 7) {
                    //Incializa las variables necesarias para el calculo
                    long cantidad_dias = 100;
                    String valor;
                    if(table.getValueAt(row, column).toString() != "NULL"){
                        valor = table.getValueAt(row, column).toString();
                        LocalDate fecha_sistema = LocalDate.now();      // Obtiene la fecha actual del sistema para hacer la comparacion
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
                        LocalDate fecha_tabla = LocalDate.parse(valor,formatter);       // Aplica el formato que se declaro anteriormente
                        cantidad_dias = ChronoUnit.DAYS.between(fecha_sistema, fecha_tabla);        // Compara las dos fechas para obtener la cantidad de dias entre estas dos
                    }
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

                        component.setBackground(back_color);
                        component.setForeground(fore_color);
                        setToolTipText(null);

                    }

                }else{      // Deja las demas Filas y/o celdas por defecto

                    component.setBackground(back_color);
                    component.setForeground(fore_color);
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
        tab.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
        tab.setGridColor(Color.GRAY);
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

        
        setupZoomListener(tab);
        return tab;
    }

    

}
