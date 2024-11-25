package Front.Extractos;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Base.Base;
import Base.Ciudad;
import Base.Contrato_mensual;
import Base.Extractos;
import Base.Vehiculo;
import Front.Ciudades_departamentos.Insertar_ciudad;
import Utilidades.Generar_extractos;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;
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
        jPanel1 = new JPanel(null);
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


        
        Contrato_mensual base_cm = new Contrato_mensual(url);
        Ciudad base_ciudad = new Ciudad(url);
        Vehiculo base_vehiculo = new Vehiculo(url);
        try{

            tabla_vehiculo = Modelo_tabla.set_tabla_vehiculo(base_vehiculo.consultar_vehiculo(true));
            tabla_contratante = Modelo_tabla.set_tabla_contratos_mensuales(base_cm.consultar_contratos_mensuales(""));
            tabla_origen = Modelo_tabla.set_tabla_ciudad(base_ciudad.consultar_ciudades(""));
            tabla_destino = Modelo_tabla.set_tabla_ciudad(base_ciudad.consultar_ciudades(""));
            

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base_vehiculo.close();
            base_ciudad.close();
            base_cm.close();
        }
        
        // Modificacion label vehiculo.
        label_vehiculo.setText("Vehiculo");
        label_vehiculo.setBounds(POSICION_X, POSICION_X, 64, LONGITUD_Y);

        // Modificacion labet text placa.
        text_placa.setBounds(POSICION_X, label_vehiculo.getY()+ label_vehiculo.getHeight() + 10, 130, LONGITUD_Y);
        text_placa.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion(){
                String datos[][] = null;

                base = new Base(url);
                Vehiculo base_vehiculo = new Vehiculo(url);
                try{
                    
                    // Dependiendo de lo que vaya ingresando el usuario, se modifica la tabla
                    datos = base_vehiculo.consultar_vehiculo(text_placa.getText());
                    JTable tabla_auxiliar = Modelo_tabla.set_tabla_vehiculo(datos);
                    tabla_vehiculo.setModel(tabla_auxiliar.getModel());
                    tabla_vehiculo.setColumnModel(tabla_auxiliar.getColumnModel());
                    scroll_vehiculo.setViewportView(tabla_vehiculo);

                    // Va seleccionando el primer argumento que aparesca en la tabla si existe
                    tabla_vehiculo.changeSelection(0, 0, false, false);

                    // Repinta el panel
                    jPanel1.revalidate();
                    jPanel1.repaint();
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    
                }finally{
                    base.close();
                    base_vehiculo.close();
                }
                
            }

            @Override
            public void accion2(){

                accion_tabla_vehiculo();

            }
            

        });
        
        // Modifica la tabla como tal y lo agrega al scroll respectivo
        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                
                accion_tabla_vehiculo();
                
            }

        });

        scroll_vehiculo.setViewportView(tabla_vehiculo);        
        scroll_vehiculo.setBounds(10, 70, 210, 100);

        // Modifica el label contratante
        label_contratante.setText("Contratante");
        label_contratante.setBounds(260, POSICION_X, 90, LONGITUD_Y);

        // Modifica el text contratante.
        text_contratante.setBounds(260, label_contratante.getY() + label_contratante.getHeight() +10 , 150, LONGITUD_Y);
        text_contratante.addKeyListener(new Key_adapter() {

            @Override
            public void accion(){
                
                set_tabla_contratante();
                tabla_contratante.changeSelection(0, 0, false, false);
                jPanel1.revalidate();
                jPanel1.repaint();
            }

            @Override
            public void accion2(){
                accion_tabla_contratante();
            }
        });

        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                if(SwingUtilities.isLeftMouseButton(evt)){
                    accion_tabla_contratante();
                }else{
                    new Insertar_contratante(ventana, url).setVisible(true);
                    set_tabla_contratante();
                }
                
            }
        });

        scroll_contratante.setViewportView(tabla_contratante);

        scroll_contratante.setBounds(260, 70, 350, 100);

        label_consecutivo.setText("Consecutivo");
        label_consecutivo.setBounds(text_contratante.getX() + text_contratante.getWidth() + 30, POSICION_X, 90, LONGITUD_Y);

        text_consecutivo.setEnabled(false);
        text_consecutivo.setText("");
        text_consecutivo.setBounds(label_consecutivo.getX(), label_consecutivo.getY() + label_consecutivo.getHeight() + 10, 100, LONGITUD_Y);

        label_fecha_inicial.setText("Fecha inicial");
        label_fecha_inicial.setBounds(150, 200, 80, 16);

        fecha_incial.setDate(fecha_sistema);
        fecha_incial.setBounds(150, 230, 130, LONGITUD_Y);

        fecha_final.setDate(fecha_sistema);
        label_fecha_final.setText("Fecha final");
        label_fecha_final.setBounds(320, 200, 90, 16);

        fecha_final.setBounds(320, 230, 130, LONGITUD_Y);
        

        tabla_destino.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                if(SwingUtilities.isLeftMouseButton(evt)){
                    accion_tabla_destino();
                }else{
                    new Insertar_ciudad(ventana, url).setVisible(true);
                    set_tabla_destino();
                }
            }
        });    

        label_origen.setText("Origen");
        label_origen.setBounds(POSICION_X, fecha_incial.getY() + LONGITUD_Y + 20, 130, LONGITUD_Y);

        text_origen.setBounds(POSICION_X, label_origen.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_origen.addKeyListener(new Key_adapter() {
            
            
            @Override
            public void accion() {
                set_tabla_origen();
                tabla_origen.changeSelection(0, 0, false, false);
                jPanel1.revalidate();
                jPanel1.repaint();
                
            }
            @Override
            public void accion2() {
                accion_tabla_origen();
            }
        });


        scroll_origen.setViewportView(tabla_origen);
        scroll_origen.setBounds(POSICION_X, text_origen.getY() + LONGITUD_Y + 10, 300, 100);

        tabla_origen.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                if(SwingUtilities.isLeftMouseButton(evt)){
                    accion_tabla_origen();
                }else{
                    new Insertar_ciudad(ventana, url).setVisible(true);
                    set_tabla_origen();
                }
            }
        });

        label_destino.setText("Destino");
        label_destino.setBounds(label_contratante.getX()+60, label_origen.getY(), 130, LONGITUD_Y);

        text_destino.setBounds(label_destino.getX(), label_destino.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_destino.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion() {
                
                set_tabla_destino();
                tabla_destino.changeSelection(0, 0, false, false);
                jPanel1.revalidate();
                jPanel1.repaint();

            }
            @Override
            public void accion2() {
                
                accion_tabla_destino();
                
            }
        });


        
        scroll_destino.setViewportView(tabla_destino);
        scroll_destino.setBounds(text_destino.getX(), text_destino.getY() + LONGITUD_Y + 10, 300, 100);

        boton_exportar.setText("Guardar y Exportar");
        boton_exportar.setBounds(150, 450, 140, 23);
        boton_exportar.addActionListener(_ ->{
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
        boton_guardar.setBounds(20, 450, 100, 23);
        boton_guardar.addActionListener(_ ->{
            boolean band = guardar_extracto_mensual();
            if(band) 
                setVisible(false);
        });
        
        
        jPanel1.add(boton_guardar);
        jPanel1.add(boton_exportar);
        jPanel1.add(scroll_destino);
        jPanel1.add(text_destino);
        jPanel1.add(label_destino);
        jPanel1.add(scroll_origen);
        jPanel1.add(text_origen);
        jPanel1.add(label_origen);
        jPanel1.add(fecha_final);
        jPanel1.add(label_fecha_final);
        jPanel1.add(fecha_incial);
        jPanel1.add(label_fecha_inicial);
        jPanel1.add(text_consecutivo);
        jPanel1.add(label_consecutivo);
        jPanel1.add(scroll_contratante);
        jPanel1.add(text_contratante);
        jPanel1.add(label_contratante);
        jPanel1.add(scroll_vehiculo);
        jPanel1.add(text_placa);
        jPanel1.add(label_vehiculo);

        add(jPanel1);
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

        base = new Extractos(url);
        try{

            vehiculo = (text_placa.getText().compareTo("") == 0)?null: text_placa.getText();
            contratante = (text_contratante.getText().compareTo("") == 0)?0: Integer.parseInt(text_contratante.getText());
            ffecha_inicial = formato.format(fecha_incial.getDate());
            ffecha_final = formato.format(fecha_final.getDate());
            origen = (text_origen.getText().compareTo("") == 0)?0: Integer.parseInt(text_origen.getText());
            destino = (text_destino.getText().compareTo("") == -1)?0: Integer.parseInt(text_destino.getText());
            consecutivo = (text_consecutivo.getText().compareTo("") == 0?0:Integer.parseInt(text_consecutivo.getText()));
            
            if(vehiculo != null && contratante != 0 && origen != 0 && destino != -1 && consecutivo != 0){

                ((Extractos)base).insertar_extracto_mensual(vehiculo, contratante, ffecha_inicial, ffecha_final, origen, destino, consecutivo);
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
        base = new Extractos(url);
        try{

            consecutivo = ((Extractos)base).consultar_consecutivo_mensual(text_placa.getText());
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
            jPanel1.revalidate();
            jPanel1.repaint();
        }
    }

    private void accion_tabla_contratante(){
        int valor_auxilia = tabla_contratante.getSelectedRow();
        text_contratante.setText("" + tabla_contratante.getValueAt(valor_auxilia, 0));
        
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void accion_tabla_origen(){
        int valor_auxilia = tabla_origen.getSelectedRow();
        text_origen.setText("" + tabla_origen.getValueAt(valor_auxilia, 0));
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    private void accion_tabla_destino(){
        int valor_auxilia = tabla_destino.getSelectedRow();
        text_destino.setText("" + tabla_destino.getValueAt(valor_auxilia, 0));
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void set_tabla_contratante(){
        String [][] datos = null;
        base = new Contrato_mensual(url);
        try{
            datos = ((Contrato_mensual)base).consultar_contratos_mensuales(text_contratante.getText());
            JTable tabla_auxiliar = Modelo_tabla.set_tabla_contratos_mensuales(datos);
            tabla_contratante.setModel(tabla_auxiliar.getModel());
            tabla_contratante.setColumnModel(tabla_auxiliar.getColumnModel());
            
            jPanel1.revalidate();
            jPanel1.repaint();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ventana.setVisible(false);
        }finally{
            base.close();
        }
    }

    private void set_tabla_origen(){
        String datos[][] = null;

        base = new Ciudad(url);
        try{
            datos = ((Ciudad)base).consultar_ciudades(text_origen.getText());
            JTable auxiliar = Modelo_tabla.set_tabla_ciudad(datos);
            tabla_origen.setModel(auxiliar.getModel());
            tabla_origen.setColumnModel(auxiliar.getColumnModel());
            
            jPanel1.revalidate();
            jPanel1.repaint();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ventana.setVisible(false);
            
        }finally{
            base.close();
        }
        
    }
    private void set_tabla_destino(){
        String datos[][] = null;
                
        base = new Base(url);
        try{
            datos = ((Ciudad)base).consultar_ciudades(text_destino.getText());
            JTable tabla_aux = Modelo_tabla.set_tabla_ciudad(datos);
            tabla_destino.setModel(tabla_aux.getModel());
            tabla_destino.setColumnModel(tabla_aux.getColumnModel());
            
            jPanel1.revalidate();
            jPanel1.repaint();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ventana.setVisible(false);
        }finally{
            base.close();
        }
        
    }
}
