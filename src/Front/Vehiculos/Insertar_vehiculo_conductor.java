package Front.Vehiculos;

import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Front.Personas.Insertar_conductor;
import Utilidades.Modelo_tabla;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import Base.Base;
import Base.Licencia;
import Base.Vehiculo;
import Base.Vehiculo_has_conductor;

public class Insertar_vehiculo_conductor extends Modales_vehiculos{
    
    private JButton boton_guardar;
    private JPanel jPanel1;
    private JLabel label_placa;
    private JLabel jLabel12;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable tabla_vehiculo;
    private JTable tabla_conductor;
    private JTextField text_conductor;
    private JTextField text_placa;
    private String[][] datos;
    private JFrame padre;
    protected Vehiculo base_Vehiculo;

    public Insertar_vehiculo_conductor(JFrame padre, String url, String valor){

        super(padre, url, valor);
        this.padre = padre;
        pack();
        setVisible(true);
    }

    protected void iniciar_componentes(){
        jPanel1 = new JPanel();
        label_placa = new JLabel();
        text_placa = new JTextField();
        jLabel12 = new JLabel();
        text_conductor = new JTextField();
        jScrollPane1 = new JScrollPane();
        tabla_vehiculo = new JTable();
        jScrollPane2 = new JScrollPane();
        tabla_conductor = new JTable();
        boton_guardar = new JButton();
        JDialog ventana = this;
        
        setPreferredSize(new Dimension(500,300));
        
        jPanel1.setLayout(null);

        label_placa.setText("Placa");
        jPanel1.add(label_placa);
        label_placa.setBounds(20, 10, 70, 20);

        text_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String datos[][] = null;
                String variable_auxiliar = text_placa.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }
                base = new Vehiculo(url);
                try{
                    datos = ((Vehiculo)base).consultar_vehiculo(variable_auxiliar);
                    tabla_vehiculo.setModel(Modelo_tabla.set_modelo_tablas(datos));
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                    
                }finally{
                    base.close();
                }
            }
        });
        jPanel1.add(text_placa);
        text_placa.setBounds(20, 40, 110, 20);

        jLabel12.setText("Conductor");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(230, 10, 100, 20);
        jPanel1.add(text_conductor);

        text_conductor.setBounds(230, 40, 130, 22);
        text_conductor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String datos[][] = null;
                String variable_auxiliar = text_conductor.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }
                base = new Licencia(url);
                try{
                    datos = ((Licencia)base).consultar_licencia(variable_auxiliar);
                    tabla_conductor.setModel(Modelo_tabla.set_modelo_tablas(datos));
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                }finally{
                    base.close();
                }
            }
        });

        // Configuracion de tablas modelo
        base = new Licencia(url);
        base_Vehiculo = new Vehiculo(url);
        try{
            tabla_vehiculo.setModel(Modelo_tabla.set_modelo_tablas(base_Vehiculo.consultar_vehiculo(true)));
            tabla_conductor.setModel(Modelo_tabla.set_modelo_tablas(((Licencia)base).consultar_licencia()));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }finally{
            base.close();
            base_Vehiculo.close();
        }
        
        //configuracion tabla vehiculo
        tabla_vehiculo.getTableHeader().setReorderingAllowed(false);
        tabla_vehiculo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla_vehiculo.getColumnModel().getColumn(0).setPreferredWidth(50);
        

        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_vehiculo.getSelectedRow();
                text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));
            }
        });
        
        //configuracion tabla conductor
        tabla_conductor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla_conductor.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabla_conductor.getTableHeader().setReorderingAllowed(false);

        tabla_conductor.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){

                if(SwingUtilities.isRightMouseButton(evt)){
                    new Insertar_conductor(padre,url).setVisible(true);

                    base = new Licencia(url);
                    try{
                        tabla_conductor.setModel(Modelo_tabla.set_modelo_tablas(((Licencia)base).consultar_licencia()));
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        setVisible(false);
                    }finally{
                        base.close();
                        
                    }
                }else{
                    int valor_auxilia = tabla_conductor.getSelectedRow();
                    text_conductor.setText("" + tabla_conductor.getValueAt(valor_auxilia, 0));    
                }
            }
        });
        

        jScrollPane1.setViewportView(tabla_vehiculo);
        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 90, 190, 110);
        
        jScrollPane2.setViewportView(tabla_conductor);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(230, 90, 230, 110);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(20, 230, 100, 23);
        boton_guardar.addActionListener(accion ->{
            boolean band = true;
            String enunciado = "Los campos:\n\n";
            if(text_placa.getText().compareTo("") == 0){
                band = false;
                enunciado += "Placa\n";
            }
            if(text_conductor.getText().compareTo("") == 0){
                band = false;
                enunciado += "Documento\n";
            }
            enunciado += "\nSon obligatorios";

            if(band == false){
                JOptionPane.showMessageDialog(this, enunciado, "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                    //En este caso verificamos que si se digito un numero de identidad valido
                    Double.parseDouble(text_conductor.getText());
                    base_Vehiculo = new Vehiculo(url);
                    datos = base_Vehiculo.consultar_vehiculo(text_placa.getText());
                    
                    // Esto es una forma de comprobar que los datos que diligencio el usuario pertenecen unicamente a un vehiculo y no hay mas concurrencias
                    if(datos.length == 2){
                        base = new Vehiculo_has_conductor(url);
                        ((Vehiculo_has_conductor)base).insertar_vehiuclo_has_conductor(text_conductor.getText(), datos[1][0]);
                        JOptionPane.showMessageDialog(this, "El conductor " + text_conductor.getText() + ", Fue asignado correctamente\nal vehiculo " + datos[1][0]);

                        this.setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(this, "No escribiste La placa correctamente.\nPor favor selecciona un vehiculo de la tabla");
                    }
                    
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "El campo conductor debe ser de tipo numerico","Error",JOptionPane.ERROR_MESSAGE);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    
                }finally{
                    base_Vehiculo.close();
                    base.close();
                }

            }
            
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );

        pack();
    }
}
