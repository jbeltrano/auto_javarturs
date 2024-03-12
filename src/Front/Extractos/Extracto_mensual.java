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
import java.sql.SQLException;
import javax.swing.JDialog;
import com.toedter.calendar.JDateChooser;

public class Extracto_mensual extends Modal_documento {

    private JButton boton_exportar;
    private JButton boton_guardar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable tabla_vehiculo;
    private JTable tabla_contratante;
    private JTextField text_contratante;
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
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jLabel2 = new JLabel();
        text_contratante = new JTextField();
        jScrollPane2 = new JScrollPane();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        boton_exportar = new JButton();
        boton_guardar = new JButton();
        tabla_vehiculo = new JTable();
        fecha_incial = new JDateChooser();
        fecha_final = new JDateChooser();

        // incializando las tablas
        base = new Base(url);
        try{

            tabla_vehiculo = Principal.set_tabla_vehiculo(base.consultar_vehiculo(true));
            tabla_contratante = Principal.set_tabla_personas(base.consultar_persona());
            tabla_contratante.addMouseListener(new MouseAdapter() {
            
                public void mouseClicked(MouseEvent evt){
                    int valor_auxilia = tabla_contratante.getSelectedRow();
                    text_contratante.setText("" + tabla_contratante.getValueAt(valor_auxilia, 0));
                }
            });

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            setVisible(false);
        }
        base.close();
        

        jPanel1.setLayout(null);
        jPanel1.add(text_placa);
        text_placa.setBounds(6, 36, 133, 22);
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
                    jScrollPane1.setViewportView(tabla_vehiculo);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
            }
        });

        jLabel1.setText("Vehiculo");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(6, 8, 64, 16);
        
        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_vehiculo.getSelectedRow();
                text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));
            }

        });

        jScrollPane1.setViewportView(tabla_vehiculo);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 70, 210, 100);

        jLabel2.setText("Contratante");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(260, 10, 90, 16);
        jPanel1.add(text_contratante);
        text_contratante.setBounds(260, 40, 150, 22);
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
                    jScrollPane2.setViewportView(tabla_contratante);
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    base.close();
                }
                base.close();
            }
        });

        
        jScrollPane2.setViewportView(tabla_contratante);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(260, 70, 240, 100);

        jLabel3.setText("Fecha inicial");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(80, 200, 80, 16);

        fecha_incial.setBounds(80, 230, 130, 20);
        jPanel1.add(fecha_incial);

        jLabel4.setText("Fecha final");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(250, 200, 90, 16);

        fecha_final.setBounds(250, 230, 130, 20);
        
        jPanel1.add(fecha_final);

        boton_exportar.setText("Exportar");
        jPanel1.add(boton_exportar);
        boton_exportar.setBounds(110, 430, 74, 23);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(20, 430, 72, 23);

        
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
        
        return new Dimension(600,500);
    }             
}
