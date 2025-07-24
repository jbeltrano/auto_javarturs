package Front.Extractos;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Base.Ciudad;
import Base.Contratante;
import Base.BContrato_ocasional;
import Front.Ciudades_departamentos.Insertar_ciudad;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;
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

public class Insertar_contrato_ocasional extends Modal_extracto{
    
    private static final int POSICION_X = 10;
    private static final int LONGITUD_Y = 20;
    private JButton boton_guardar;
    protected JComboBox<String> combo_tipo_contrato;
    private JLabel label_numero_contrato;
    private JLabel label_contratante;
    private JLabel label_fecha_inicial;
    private JLabel label_fecha_final;
    private JLabel label_tipo_contrato;
    private JLabel label_origen;
    private JLabel label_destino;
    private JLabel label_valor_contrato;
    private JPanel jPanel1;
    private JScrollPane scroll_contratante;
    private JScrollPane scroll_origen;
    private JScrollPane scroll_destino;
    private JTable tabla_contratante;
    private JTable tabla_origen;
    private JTable tabla_destino;
    protected JTextField text_contratante;
    protected JTextField text_origen;
    protected JTextField text_destino;
    protected JTextField text_numero_contrato;
    protected JTextField text_valor_contrato;
    private JDialog ventana;
    protected JDateChooser fecha_incial;
    protected JDateChooser fecha_final;
    protected Date fecha_sistema;
    protected Ciudad base_ciudad;
    protected Contratante base_contratante;

    public Insertar_contrato_ocasional(JFrame padre){
        super(padre);
    }

    @Override
    protected void iniciar_componentes(){

        fecha_sistema = Calendar.getInstance().getTime();
        jPanel1 = new JPanel();
        text_numero_contrato = new JTextField();
        label_numero_contrato = new JLabel();
        scroll_origen = new JScrollPane();
        scroll_destino = new JScrollPane();
        label_contratante = new JLabel();
        text_contratante = new JTextField();
        label_valor_contrato = new JLabel();
        text_valor_contrato = new JTextField();
        text_origen = new JTextField();
        text_destino = new JTextField();
        scroll_contratante = new JScrollPane();
        label_fecha_inicial = new JLabel();
        label_tipo_contrato = new JLabel();
        label_fecha_final = new JLabel();
        label_origen = new JLabel();
        label_destino = new JLabel();
        boton_guardar = new JButton();
        tabla_origen = new JTable();
        tabla_destino = new JTable();
        fecha_incial = new JDateChooser();
        fecha_final = new JDateChooser();
        combo_tipo_contrato = new JComboBox<>();

        // incializando las tablas
        
        

        try{
            base = new BContrato_ocasional();
            base_ciudad = new Ciudad();
            base_contratante = new Contratante();
            text_numero_contrato.setText("" + (((BContrato_ocasional)base).consultar_maximo_contrato_ocasional()+1));
            tabla_contratante = Modelo_tabla.set_tabla_contratante(base_contratante.consultar_contratante(""));
            tabla_origen = Modelo_tabla.set_tabla_ciudad(base_ciudad.consultar_ciudades(""));
            tabla_destino = Modelo_tabla.set_tabla_ciudad(base_ciudad.consultar_ciudades(""));
            combo_tipo_contrato = new JComboBox<>(((BContrato_ocasional)base).consultar_tipo_contrato());
            

        }catch(SQLException | IOException ex){

            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            setVisible(false);
        }finally{
            base.close();
            base_ciudad.close();
            base_contratante.close();
        }
        
        label_numero_contrato.setText("Numero de Contrato");
        jPanel1.add(label_numero_contrato);
        label_numero_contrato.setBounds(POSICION_X, POSICION_X, 130, LONGITUD_Y);

        jPanel1.setLayout(null);
        jPanel1.add(text_numero_contrato);
        text_numero_contrato.setBounds(POSICION_X, label_numero_contrato.getY()+ label_numero_contrato.getHeight() + 10, 130, LONGITUD_Y);

        label_contratante.setText("Contratante");
        jPanel1.add(label_contratante);
        label_contratante.setBounds(260, POSICION_X, 90, LONGITUD_Y);
        jPanel1.add(text_contratante);
        text_contratante.setBounds(260, label_contratante.getY() + label_contratante.getHeight() +10 , 150, LONGITUD_Y);
        text_contratante.addKeyListener(new Key_adapter() {
            

            @Override
            public void accion(){

                set_tabla_contratante();
                tabla_contratante.changeSelection(0, 0, false, false);

            }

            @Override
            public void accion2(){
                accion_tabla_contratante();
            }
        });



        label_valor_contrato.setText("Valor del contrato");
        label_valor_contrato.setBounds(label_contratante.getX()+ label_contratante.getWidth() + 80, POSICION_X, 150, LONGITUD_Y);
        jPanel1.add(label_valor_contrato);

        text_valor_contrato.setText("");
        text_valor_contrato.setBounds(label_valor_contrato.getX(), label_contratante.getY() + LONGITUD_Y + 10, 100, LONGITUD_Y);
        jPanel1.add(text_valor_contrato);

        JDialog padre = this;
        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                if(SwingUtilities.isLeftMouseButton(evt)){
                    accion_tabla_contratante();
                }else{
                    new Insertar_contratante(padre).setVisible(true);
                    set_tabla_contratante();
                }
                
            }
        });

        scroll_contratante.setViewportView(tabla_contratante);

        jPanel1.add(scroll_contratante);
        scroll_contratante.setBounds(POSICION_X, 70, 610, 120);


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

        label_tipo_contrato.setText("Objeto del Contrato");
        label_tipo_contrato.setBounds(text_contratante.getX() + text_contratante.getWidth() + 60, label_fecha_final.getY(), 120, 20);
        jPanel1.add(label_tipo_contrato);

        combo_tipo_contrato.setBounds(label_tipo_contrato.getX(),fecha_final.getY(),120,20);
        combo_tipo_contrato.setSelectedItem("PARTICULAR");
        jPanel1.add(combo_tipo_contrato);

        label_origen.setText("Origen");
        label_origen.setBounds(POSICION_X, fecha_incial.getY() + LONGITUD_Y + 20, 130, LONGITUD_Y);
        jPanel1.add(label_origen);

        text_origen.setBounds(POSICION_X, label_origen.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_origen.addKeyListener(new Key_adapter() {
            
            
            @Override
            public void accion() {

                
                try{
                    base = new Ciudad();
                    String[][] datos = ((Ciudad)base).consultar_ciudades(text_origen.getText());
                    Modelo_tabla.updateTableModel(tabla_origen, datos);
        
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Insertar_contrato_ocasional.this.dispose();
                }finally{
                    if(base != null) base.close();
                }
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
                if(SwingUtilities.isLeftMouseButton(evt)){
                    accion_tabla_origen();
                }else{
                    new Insertar_ciudad(ventana).setVisible(true);
                    set_tabla_origen();
                }
                
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
                
                
                try{
                    base = new Ciudad();
                    datos = ((Ciudad)base).consultar_ciudades(text_destino.getText());
                    Modelo_tabla.updateTableModel(tabla_destino, datos);
                    
                    scroll_destino.setViewportView(tabla_destino);
                    
        
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Insertar_contrato_ocasional.this.dispose();
                }finally {
                    if(base != null) base.close();
                }
                tabla_destino.changeSelection(0, 0, false, false);

            }
            @Override
            public void accion2() {
                
                accion_tabla_destino();
                
            }
        });
        jPanel1.add(text_destino);

        tabla_destino.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent evt){
                
                if(SwingUtilities.isLeftMouseButton(evt)){
                    accion_tabla_destino();
                }else{
                    new Insertar_ciudad(ventana).setVisible(true);
                    set_tabla_destino();
                }
            }
        });        

        scroll_destino.setViewportView(tabla_destino);
        scroll_destino.setBounds(text_destino.getX(), text_destino.getY() + LONGITUD_Y + 10, 300, 100);
        jPanel1.add(scroll_destino);

        

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(20, 450, 100, 23);
        boton_guardar.addActionListener(_ ->{
            guardar();

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

    private void set_tabla_contratante(){
        String [][] datos = null;
        
        try{
            base = new Contratante();
            datos = ((Contratante)base).consultar_contratante(text_contratante.getText());
            Modelo_tabla.updateTableModel(tabla_contratante, datos);
            scroll_contratante.setViewportView(tabla_contratante);
            

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(ventana, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Insertar_contrato_ocasional.this.dispose();
        }finally{
            if(base != null) 
                base.close();
        }

    }

    protected void guardar(){
        int numero_contrato = 0;
        int tipo_contrato = 0;
        String contratante;
        String ffecha_inicial;
        String ffecha_final;
        int origen;
        int destino;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");
        double valor_contrato;
        text_valor_contrato.setText(text_valor_contrato.getText().replaceAll(",", "."));
        
        
        try{
            base = new BContrato_ocasional();

            Integer.parseInt(text_contratante.getText());
            numero_contrato = (text_numero_contrato.getText().compareTo("") == 0)?null: Integer.parseInt(text_numero_contrato.getText());
            valor_contrato = (text_valor_contrato.getText().compareTo("") == 0)?0:Double.parseDouble(text_valor_contrato.getText());
            contratante = (text_contratante.getText().compareTo("") == 0)?null: text_contratante.getText();
            ffecha_inicial = formato.format(fecha_incial.getDate());
            ffecha_final = formato.format(fecha_final.getDate());
            tipo_contrato = combo_tipo_contrato.getSelectedIndex() +1;
            origen = (text_origen.getText().compareTo("") == 0)?0: Integer.parseInt(text_origen.getText());
            destino = (text_destino.getText().compareTo("") == 0)?0: Integer.parseInt(text_destino.getText());
            
            if(contratante != null && origen != 0 && destino != 0 && numero_contrato != 0 && valor_contrato != 0){

                ((BContrato_ocasional)base).insertar_contrato_ocasional(numero_contrato, 
                                                 contratante, 
                                                 ffecha_inicial, 
                                                 ffecha_final, 
                                                 origen, 
                                                 destino, 
                                                 valor_contrato,
                                                 tipo_contrato);
                JOptionPane.showMessageDialog(this, "Contrato Ocasional guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);

            }else{
                JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos para continuar...", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Los campos: \nNumero de contrato\nContratante\norigen y destino\nDeben ser de tipo numerico", "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base != null) base.close();
        }
    }

    private void set_tabla_origen(){
        
        try{
            base = new Ciudad();
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable auxiliar = Modelo_tabla.set_tabla_ciudad(((Ciudad)base).consultar_ciudades(text_origen.getText()));
            tabla_origen.setModel(auxiliar.getModel());
            tabla_origen.setColumnModel(auxiliar.getColumnModel());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(ventana, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Insertar_contrato_ocasional.this.dispose();
            
        }finally{
            if(base != null) base.close();
        }
    }

    private void set_tabla_destino(){
        
        try{
            base = new Ciudad();
            JTable auxiliar = Modelo_tabla.set_tabla_ciudad(((Ciudad)base).consultar_ciudades(text_destino.getText()));
            tabla_destino.setModel(auxiliar.getModel());
            tabla_destino.setColumnModel(auxiliar.getColumnModel());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(ventana, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Insertar_contrato_ocasional.this.dispose();
        }finally{
            if(base != null) base.close();
        }
    }
}
