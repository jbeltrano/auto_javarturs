package Front.Extractos;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Base.Base;
import Utilidades.Generar_extractos;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;
import Utilidades.Windows_bar;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JDialog;
import com.toedter.calendar.JDateChooser;

public class Insertar_extracto_mensual extends Modal_documento {

    private static final int POSICION_X = 10;
    private static final int LONGITUD_Y = 20;
    private JButton boton_exportar;
    private JButton boton_guardar;
    private JLabel label_vehiculo;
    private JLabel label_contratante;
    private JLabel label_fecha_inicial;
    private JLabel label_fecha_final;
    private JLabel label_origen;
    private JLabel label_destino;
    private JLabel label_consecutivo;
    private JPanel jPanel1;
    private JScrollPane scroll_vehiculo;
    private JScrollPane scroll_contratante;
    private JScrollPane scroll_origen;
    private JScrollPane scroll_destino;
    protected JTable tabla_vehiculo;
    protected JTable tabla_contratante;
    protected JTable tabla_origen;
    protected JTable tabla_destino;
    protected JTextField text_contratante;
    protected JTextField text_origen;
    protected JTextField text_destino;
    protected JTextField text_placa;
    protected JTextField text_consecutivo;
    private JDialog ventana;
    protected JDateChooser fecha_incial;
    protected JDateChooser fecha_final;
    private Date fecha_sistema;
    

    public Insertar_extracto_mensual(JFrame padre, String url){
        super(padre, url);
        ventana = this;
    }

    @Override
    protected void iniciar_componentes(){

        fecha_sistema = Calendar.getInstance().getTime();
        jPanel1 = new JPanel();
        text_placa = new JTextField();
        label_vehiculo = new JLabel();
        scroll_vehiculo = new JScrollPane();
        scroll_origen = new JScrollPane();
        scroll_destino = new JScrollPane();
        label_contratante = new JLabel();
        label_consecutivo = new JLabel();
        text_contratante = new JTextField();
        text_origen = new JTextField();
        text_destino = new JTextField();
        text_consecutivo = new JTextField();
        scroll_contratante = new JScrollPane();
        label_fecha_inicial = new JLabel();
        label_fecha_final = new JLabel();
        label_origen = new JLabel();
        label_destino = new JLabel();
        boton_exportar = new JButton();
        boton_guardar = new JButton();
        tabla_vehiculo = new JTable();
        tabla_origen = new JTable();
        tabla_destino = new JTable();
        fecha_incial = new JDateChooser();
        fecha_final = new JDateChooser();
        
        
        // Configuracion de barras de scroll

        scroll_contratante.getVerticalScrollBar().setUI(new Windows_bar());
        scroll_contratante.getHorizontalScrollBar().setUI(new Windows_bar());

        scroll_destino.getVerticalScrollBar().setUI(new Windows_bar());
        scroll_destino.getHorizontalScrollBar().setUI(new Windows_bar());

        scroll_origen.getVerticalScrollBar().setUI(new Windows_bar());
        scroll_origen.getHorizontalScrollBar().setUI(new Windows_bar());

        scroll_vehiculo.getVerticalScrollBar().setUI(new Windows_bar());
        scroll_vehiculo.getHorizontalScrollBar().setUI(new Windows_bar());


        // incializando las tablas
        base = new Base(url);
        try{

            tabla_vehiculo = Modelo_tabla.set_tabla_vehiculo(base.consultar_vehiculo(true));
            tabla_contratante = Modelo_tabla.set_tabla_contratos_mensuales(base.consultar_contratos_mensuales(""));
            tabla_origen = Modelo_tabla.set_tabla_ciudad(base.consultar_ciudades(""));
            tabla_destino = Modelo_tabla.set_tabla_ciudad(base.consultar_ciudades(""));
            

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base.close();
        }
        
        label_vehiculo.setText("Vehiculo");
        jPanel1.add(label_vehiculo);
        label_vehiculo.setBounds(POSICION_X, POSICION_X, 64, LONGITUD_Y);

        jPanel1.setLayout(null);
        jPanel1.add(text_placa);
        text_placa.setBounds(POSICION_X, label_vehiculo.getY()+ label_vehiculo.getHeight() + 10, 130, LONGITUD_Y);
        text_placa.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion(){
                String datos[][] = null;

                base = new Base(url);
                try{
                    
                    datos = base.consultar_vehiculo(text_placa.getText());
                    JTable tabla_auxiliar = Modelo_tabla.set_tabla_vehiculo(datos);
                    tabla_vehiculo.setModel(tabla_auxiliar.getModel());
                    tabla_vehiculo.setColumnModel(tabla_auxiliar.getColumnModel());
                    scroll_vehiculo.setViewportView(tabla_vehiculo);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();

                tabla_vehiculo.changeSelection(0, 0, false, false);
                
            }

            @Override
            public void accion2(){

                accion_tabla_vehiculo();

            }
            

        });
        
        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                
                accion_tabla_vehiculo();
                
            }

        });

        scroll_vehiculo.setViewportView(tabla_vehiculo);

        jPanel1.add(scroll_vehiculo);
        scroll_vehiculo.setBounds(10, 70, 210, 100);

        label_contratante.setText("Contratante");
        jPanel1.add(label_contratante);
        label_contratante.setBounds(260, POSICION_X, 90, LONGITUD_Y);
        jPanel1.add(text_contratante);
        text_contratante.setBounds(260, label_contratante.getY() + label_contratante.getHeight() +10 , 150, LONGITUD_Y);
        text_contratante.addKeyListener(new Key_adapter() {
            

            @Override
            public void accion(){
                String [][] datos = null;
                base = new Base(url);
                try{
                    datos = base.consultar_contratos_mensuales(text_contratante.getText());
                    JTable tabla_auxiliar = Modelo_tabla.set_tabla_contratos_mensuales(datos);
                    tabla_contratante.setModel(tabla_auxiliar.getModel());
                    tabla_contratante.setColumnModel(tabla_auxiliar.getColumnModel());
                    scroll_contratante.setViewportView(tabla_contratante);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
                tabla_contratante.changeSelection(0, 0, false, false);
            }

            @Override
            public void accion2(){
                accion_tabla_contratante();
            }
        });

        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                accion_tabla_contratante();
            }
        });

        scroll_contratante.setViewportView(tabla_contratante);

        jPanel1.add(scroll_contratante);
        scroll_contratante.setBounds(260, 70, 350, 100);

        label_consecutivo.setText("Consecutivo");
        label_consecutivo.setBounds(text_contratante.getX() + text_contratante.getWidth() + 30, POSICION_X, 90, LONGITUD_Y);
        jPanel1.add(label_consecutivo);

        text_consecutivo.setEnabled(false);
        text_consecutivo.setText("");
        text_consecutivo.setBounds(label_consecutivo.getX(), label_consecutivo.getY() + label_consecutivo.getHeight() + 10, 100, LONGITUD_Y);
        jPanel1.add(text_consecutivo);

        label_fecha_inicial.setText("Fecha inicial");
        jPanel1.add(label_fecha_inicial);
        label_fecha_inicial.setBounds(150, 200, 80, 16);

        fecha_incial.setDate(fecha_sistema);
        fecha_incial.setBounds(150, 230, 130, LONGITUD_Y);
        jPanel1.add(fecha_incial);

        fecha_final.setDate(fecha_sistema);
        label_fecha_final.setText("Fecha final");
        jPanel1.add(label_fecha_final);
        label_fecha_final.setBounds(320, 200, 90, 16);

        fecha_final.setBounds(320, 230, 130, LONGITUD_Y);
        
        jPanel1.add(fecha_final);

        tabla_destino.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                accion_tabla_destino();
            }
        });    

        label_origen.setText("Origen");
        label_origen.setBounds(POSICION_X, fecha_incial.getY() + LONGITUD_Y + 20, 130, LONGITUD_Y);
        jPanel1.add(label_origen);

        text_origen.setBounds(POSICION_X, label_origen.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_origen.addKeyListener(new Key_adapter() {
            
            
            @Override
            public void accion() {
                String datos[][] = null;

                base = new Base(url);
                try{
                    datos = base.consultar_ciudades(text_origen.getText());
                    tabla_origen = Modelo_tabla.set_tabla_ciudad(datos);
                    tabla_origen.addMouseListener(new MouseAdapter() {
            
                        public void mousePressed(MouseEvent evt){
                            int valor_auxilia = tabla_origen.getSelectedRow();
                            text_origen.setText("" + tabla_origen.getValueAt(valor_auxilia, 0));
                        }
                    });
                    scroll_origen.setViewportView(tabla_origen);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
                tabla_origen.changeSelection(0, 0, false, false);
                
            }
            @Override
            public void accion2() {
                accion_tabla_origen();
            }
        });

        jPanel1.add(text_origen);

        scroll_origen.setViewportView(tabla_origen);
        scroll_origen.setBounds(POSICION_X, text_origen.getY() + LONGITUD_Y + 10, 300, 100);
        jPanel1.add(scroll_origen);

        tabla_origen.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                accion_tabla_origen();
            }
        });

        label_destino.setText("Destino");
        label_destino.setBounds(label_contratante.getX()+60, label_origen.getY(), 130, LONGITUD_Y);
        jPanel1.add(label_destino);

        text_destino.setBounds(label_destino.getX(), label_destino.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_destino.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion() {
                
                String datos[][] = null;
                
                base = new Base(url);
                try{
                    datos = base.consultar_ciudades(text_destino.getText());
                    JTable tabla_aux = Modelo_tabla.set_tabla_ciudad(datos);
                    tabla_destino.setModel(tabla_aux.getModel());
                    tabla_destino.setColumnModel(tabla_aux.getColumnModel());
                    
                    scroll_destino.setViewportView(tabla_destino);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
                tabla_destino.changeSelection(0, 0, false, false);

            }
            @Override
            public void accion2() {
                
                accion_tabla_destino();
                
            }
        });
        jPanel1.add(text_destino);

        
        scroll_destino.setViewportView(tabla_destino);
        scroll_destino.setBounds(text_destino.getX(), text_destino.getY() + LONGITUD_Y + 10, 300, 100);
        jPanel1.add(scroll_destino);

        boton_exportar.setText("Guardar y Exportar");
        jPanel1.add(boton_exportar);
        boton_exportar.setBounds(150, 450, 140, 23);
        boton_exportar.addActionListener(accion ->{
            boolean band = guardar_extracto_mensual();
            Runtime runtime = Runtime.getRuntime();
            String comando[] = new String[3];
            comando[0] = System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\a.exe";
            comando[1] = System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\ConvertirPdf.ps1";
            comando[2] = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Mensuales";

            if(band){
                try{
                    String ruta;
                    ruta = Generar_extractos.generar_extracto_mensual_excel(text_placa.getText(), Integer.parseInt(text_consecutivo.getText()), url);
                    runtime.exec(comando);
                    JOptionPane.showMessageDialog(this, "Extracto guardado con exito.\nUbicacion: " + ruta, "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, e.getMessage(),"Error",  JOptionPane.ERROR_MESSAGE);
                }
                
                setVisible(false);
            }
            
            
        });

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(20, 450, 100, 23);
        boton_guardar.addActionListener(accion ->{
            boolean band = guardar_extracto_mensual();
            if(band) 
                setVisible(false);
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    
    @Override
    protected Dimension set_dimension(){
        
        return new Dimension(650,550);
    }

    protected boolean guardar_extracto_mensual(){
        
        int consecutivo = 0;
        String vehiculo;
        int contratante;
        String ffecha_inicial;
        String ffecha_final;
        int origen;
        int destino;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");

        base = new Base(url);
        try{

            vehiculo = (text_placa.getText().compareTo("") == 0)?null: text_placa.getText();
            contratante = (text_contratante.getText().compareTo("") == 0)?0: Integer.parseInt(text_contratante.getText());
            ffecha_inicial = formato.format(fecha_incial.getDate());
            ffecha_final = formato.format(fecha_final.getDate());
            origen = (text_origen.getText().compareTo("") == 0)?0: Integer.parseInt(text_origen.getText());
            destino = (text_destino.getText().compareTo("") == -1)?0: Integer.parseInt(text_destino.getText());
            consecutivo = (text_consecutivo.getText().compareTo("") == 0?0:Integer.parseInt(text_consecutivo.getText()));
            
            if(vehiculo != null && contratante != 0 && origen != 0 && destino != -1 && consecutivo != 0){

                base.insertar_extracto_mensual(vehiculo, contratante, ffecha_inicial, ffecha_final, origen, destino, consecutivo);
                JOptionPane.showMessageDialog(this, "Extracto mensual guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
                return true;

            }else{
                JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos para continuar\nRecuerde que si es el primer extracto del vehiculo\nDebe establecer el consecutivo", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }finally{
            base.close();
        }
        
    }

    private void accion_tabla_vehiculo(){

        int valor_auxilia = tabla_vehiculo.getSelectedRow();
        int consecutivo = 0;
        text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));
        base = new Base(url);
        try{

            consecutivo = base.consultar_consecutivo_mensual(text_placa.getText());
            if(consecutivo == 0){
                text_consecutivo.setEnabled(true);
                text_consecutivo.setText("");
            }else{
                text_consecutivo.setEnabled(false);
                text_consecutivo.setText("" + (consecutivo + 1));
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(ventana, ex.getMessage());
        }finally{
            base.close();
        }
    }

    private void accion_tabla_contratante(){
        int valor_auxilia = tabla_contratante.getSelectedRow();
        text_contratante.setText("" + tabla_contratante.getValueAt(valor_auxilia, 0));
    }

    private void accion_tabla_origen(){
        int valor_auxilia = tabla_origen.getSelectedRow();
        text_origen.setText("" + tabla_origen.getValueAt(valor_auxilia, 0));
    }
    
    private void accion_tabla_destino(){
        int valor_auxilia = tabla_destino.getSelectedRow();
        text_destino.setText("" + tabla_destino.getValueAt(valor_auxilia, 0));
    }
}
