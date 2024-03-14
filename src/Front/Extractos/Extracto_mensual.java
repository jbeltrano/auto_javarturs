package Front.Extractos;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.lang.management.LockInfo;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Base.Base;
import Front.Principal;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import javax.swing.JDialog;
import com.toedter.calendar.JDateChooser;

public class Extracto_mensual extends Modal_documento {

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

    public Extracto_mensual(JFrame padre, String url){
        super(padre, url);
        ventana = this;

    }

    @Override
    protected void iniciar_componentes(){

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
            tabla_contratante = Principal.set_tabla_personas(base.consultar_persona());
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
                    datos = base.consultar_persona(variable_auxiliar);
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

        fecha_incial.setBounds(80, 230, 130, LONGITUD_Y);
        jPanel1.add(fecha_incial);

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

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(20, 450, 100, 23);

        
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
}
