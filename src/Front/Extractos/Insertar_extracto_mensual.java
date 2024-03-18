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
import Front.Principal;
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
    private JPanel jPanel1;
    private JScrollPane scroll_vehiculo;
    private JScrollPane scroll_contratante;
    private JScrollPane scroll_origen;
    private JScrollPane scroll_destino;
    private JTable tabla_vehiculo;
    private JTable tabla_contratante;
    private JTable tabla_origen;
    private JTable tabla_destino;
    private JTextField text_contratante;
    private JTextField text_origen;
    private JTextField text_destino;
    private JTextField text_placa;
    private JDialog ventana;
    private JDateChooser fecha_incial;
    private JDateChooser fecha_final;
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
        text_contratante = new JTextField();
        text_origen = new JTextField();
        text_destino = new JTextField();
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

        // incializando las tablas
        base = new Base(url);
        try{

            tabla_vehiculo = Principal.set_tabla_vehiculo(base.consultar_vehiculo(true));
            tabla_contratante = Principal.set_tabla_personas(base.consultar_contratos_mensuales(""));
            tabla_origen = Principal.set_tabla_ciudad(base.consultar_ciudades(""));
            tabla_destino = Principal.set_tabla_ciudad(base.consultar_ciudades(""));
            

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            setVisible(false);
        }
        base.close();
        
        label_vehiculo.setText("Vehiculo");
        jPanel1.add(label_vehiculo
);
        label_vehiculo.setBounds(POSICION_X, POSICION_X, 64, LONGITUD_Y);

        jPanel1.setLayout(null);
        jPanel1.add(text_placa);
        text_placa.setBounds(POSICION_X, label_vehiculo.getY()+ label_vehiculo.getHeight() + 10, 130, LONGITUD_Y);
        text_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String datos[][] = null;
                String variable_auxiliar = text_placa.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }
                base = new Base(url);
                try{
                    datos = base.consultar_vehiculo(variable_auxiliar);
                    tabla_vehiculo = Principal.set_tabla_vehiculo(datos);
                    tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
                        public void mouseClicked(MouseEvent evt){
                            int valor_auxilia = tabla_vehiculo.getSelectedRow();
                            text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));
                        }
            
                    });
                    scroll_vehiculo.setViewportView(tabla_vehiculo);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
            }
        });

        
        
        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_vehiculo.getSelectedRow();
                text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));
            }

        });

        scroll_vehiculo.setViewportView(tabla_vehiculo);

        jPanel1.add(scroll_vehiculo
);
        scroll_vehiculo.setBounds(10, 70, 210, 100);

        label_contratante.setText("Contratante");
        jPanel1.add(label_contratante);
        label_contratante.setBounds(260, POSICION_X, 90, LONGITUD_Y);
        jPanel1.add(text_contratante);
        text_contratante.setBounds(260, label_contratante.getY() + label_contratante.getHeight() +10 , 150, LONGITUD_Y);
        text_contratante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String datos[][] = null;
                String variable_auxiliar = text_contratante.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }
                base = new Base(url);
                try{
                    datos = base.consultar_contratos_mensuales(variable_auxiliar);
                    tabla_contratante = Principal.set_tabla_personas(datos);
                    tabla_contratante.addMouseListener(new MouseAdapter() {
            
                        public void mouseClicked(MouseEvent evt){
                            int valor_auxilia = tabla_contratante.getSelectedRow();
                            text_contratante.setText("" + tabla_contratante.getValueAt(valor_auxilia, 0));
                        }
                    });
                    scroll_contratante.setViewportView(tabla_contratante);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
            }
        });

        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_contratante.getSelectedRow();
                text_contratante.setText("" + tabla_contratante.getValueAt(valor_auxilia, 0));
            }
        });

        scroll_contratante.setViewportView(tabla_contratante);

        jPanel1.add(scroll_contratante);
        scroll_contratante.setBounds(260, 70, 240, 100);

        label_fecha_inicial.setText("Fecha inicial");
        jPanel1.add(label_fecha_inicial);
        label_fecha_inicial.setBounds(80, 200, 80, 16);

        fecha_incial.setDate(fecha_sistema);
        fecha_incial.setBounds(80, 230, 130, LONGITUD_Y);
        jPanel1.add(fecha_incial);

        fecha_final.setDate(fecha_sistema);
        label_fecha_final.setText("Fecha final");
        jPanel1.add(label_fecha_final);
        label_fecha_final.setBounds(250, 200, 90, 16);

        fecha_final.setBounds(250, 230, 130, LONGITUD_Y);
        
        jPanel1.add(fecha_final);

        label_origen.setText("Origen");
        label_origen.setBounds(POSICION_X, fecha_incial.getY() + LONGITUD_Y + 20, 130, LONGITUD_Y);
        jPanel1.add(label_origen);

        text_origen.setBounds(POSICION_X, label_origen.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_origen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String datos[][] = null;
                String variable_auxiliar = text_origen.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }
                base = new Base(url);
                try{
                    datos = base.consultar_ciudades(variable_auxiliar);
                    tabla_origen = Principal.set_tabla_ciudad(datos);
                    tabla_origen.addMouseListener(new MouseAdapter() {
            
                        public void mouseClicked(MouseEvent evt){
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
            }
        });

        jPanel1.add(text_origen);

        scroll_origen.setViewportView(tabla_origen);
        scroll_origen.setBounds(POSICION_X, text_origen.getY() + LONGITUD_Y + 10, 210, 100);
        jPanel1.add(scroll_origen);

        tabla_origen.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_origen.getSelectedRow();
                text_origen.setText("" + tabla_destino.getValueAt(valor_auxilia, 0));
            }
        });

        label_destino.setText("Destino");
        label_destino.setBounds(label_contratante.getX(), label_origen.getY(), 130, LONGITUD_Y);
        jPanel1.add(label_destino);

        text_destino.setBounds(label_destino.getX(), label_destino.getY() + LONGITUD_Y + 10, 130, LONGITUD_Y);
        text_destino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String datos[][] = null;
                String variable_auxiliar = text_destino.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }
                base = new Base(url);
                try{
                    datos = base.consultar_ciudades(variable_auxiliar);
                    tabla_destino = Principal.set_tabla_ciudad(datos);
                    tabla_destino.addMouseListener(new MouseAdapter() {
            
                        public void mouseClicked(MouseEvent evt){
                            int valor_auxilia = tabla_destino.getSelectedRow();
                            text_destino.setText("" + tabla_destino.getValueAt(valor_auxilia, 0));
                        }
                    });
                    scroll_destino.setViewportView(tabla_destino);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
            }
        });
        jPanel1.add(text_destino);

        tabla_destino.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_destino.getSelectedRow();
                text_destino.setText("" + tabla_destino.getValueAt(valor_auxilia, 0));
            }
        });        

        scroll_destino.setViewportView(tabla_destino);
        scroll_destino.setBounds(text_destino.getX(), text_destino.getY() + LONGITUD_Y + 10, 210, 100);
        jPanel1.add(scroll_destino);

        boton_exportar.setText("Guardar y Exportar");
        jPanel1.add(boton_exportar);
        boton_exportar.setBounds(150, 450, 140, 23);
        boton_exportar.addActionListener(accion ->{
            int con = g_insertar_extracto_mensual();
            if(con != 0){
                generar_extracto_excel(text_placa.getText(), con, this, url);
                setVisible(false);
            }else{
                JOptionPane.showMessageDialog(this, "No se pudo guardar el consecutivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(20, 450, 100, 23);
        boton_guardar.addActionListener(accion ->{
            g_insertar_extracto_mensual();
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
        
        return new Dimension(600,550);
    }

    private int g_insertar_extracto_mensual(){
        
        int consecutivo;
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
            destino = (text_destino.getText().compareTo("") == 0)?0: Integer.parseInt(text_destino.getText());
            
            if(vehiculo != null && contratante != 0 && origen != 0 && destino != 0){

                consecutivo = base.insertar_extracto_mensual(vehiculo, contratante, ffecha_inicial, ffecha_final, origen, destino);
                JOptionPane.showMessageDialog(this, "Extracto mensual guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
                base.close();
                return consecutivo;

            }else{
                JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos para continuar", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        base.close();
        return 0;
    }
    
}
