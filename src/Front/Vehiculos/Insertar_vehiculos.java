package Front.Vehiculos;


import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import Base.Base;
import Base.Clase_vehiculo;
import Base.Persona;
import Base.Vehiculo;
import Front.Personas.Insertar_persona;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;

public class Insertar_vehiculos extends Modales_vehiculos{
    
    protected Vector<String> vector;           
    protected JButton boton_guardar;
    protected JRadioButton boton_parque;
    protected JComboBox<String> combo_tipo_vehiculo;
    protected JLabel jLabel1;
    protected JLabel jLabel10;
    protected JLabel jLabel11;
    protected JLabel jLabel2;
    protected JLabel jLabel3;
    protected JLabel jLabel4;
    protected JLabel jLabel5;
    protected JLabel jLabel6;
    protected JLabel jLabel7;
    protected JLabel jLabel8;
    protected JLabel jLabel9;
    protected JPanel jPanel1;
    protected JLabel label_modelo;
    protected JLabel label_servicio;
    protected JLabel label_placa;
    protected JScrollPane scroll_tabla;
    protected JTable tab;
    protected JTextField text_carroceria;
    protected JTextField text_chasis;
    protected JTextField text_cilindrada;
    protected JTextField text_color;
    protected JTextField text_combustible;
    protected JTextField text_linea;
    protected JTextField text_marca;
    protected JTextField text_motor;
    protected JTextField text_pasajeros;
    protected JTextField text_placa;
    protected JTextField text_propietario;
    protected JTextField text_modelo;
    protected JComboBox<String> combo_servicio;
    protected String[][] datos;
    protected String[] dato;

    public Insertar_vehiculos(JFrame padre, String valor){
        
        super(padre,valor);
        pack();

    }
    
    @Override
    protected void iniciar_componentes(){
        JDialog padre = this;
        jPanel1 = new JPanel();
        label_placa = new JLabel();
        text_placa = new JTextField("");
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        text_marca = new JTextField("");
        jLabel3 = new JLabel();
        text_linea = new JTextField();
        combo_tipo_vehiculo = new JComboBox<>();
        jLabel4 = new JLabel();
        text_cilindrada = new JTextField("");
        jLabel5 = new JLabel();
        text_color = new JTextField("");
        jLabel6 = new JLabel();
        text_combustible = new JTextField("");
        jLabel7 = new JLabel();
        text_carroceria = new JTextField("");
        jLabel8 = new JLabel();
        text_motor = new JTextField("");
        jLabel9 = new JLabel();
        text_chasis = new JTextField("");
        jLabel10 = new JLabel();
        text_pasajeros = new JTextField("");
        boton_parque = new JRadioButton();
        jLabel11 = new JLabel();
        text_propietario = new JTextField("");
        scroll_tabla = new JScrollPane();
        tab = new JTable();
        boton_guardar = new JButton();
        label_modelo = new JLabel("Modelo");
        label_servicio = new JLabel("Servicio");
        combo_servicio = new JComboBox<>();
        text_modelo = new JTextField();

        jPanel1.setLayout(null);

        label_placa.setText("Placa");
        jPanel1.add(label_placa);
        label_placa.setBounds(20, 10, 40, 20);

        jPanel1.add(text_placa);
        text_placa.setBounds(20, 40, 64, 22);

        jLabel1.setText("Tipo Vehiculo");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(110, 10, 80, 16);

        jLabel2.setText("Marca");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(210, 10, 70, 16);
        jPanel1.add(text_marca);
        text_marca.setBounds(210, 40, 120, 22);

        jLabel3.setText("Linea");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(350, 10, 37, 16);
        
        jPanel1.add(text_linea);
        text_linea.setBounds(350, 40, 90, 22);

        label_modelo.setBounds(460,10,60,16);
        jPanel1.add(label_modelo);

        text_modelo.setBounds(460,40,90,22);
        jPanel1.add(text_modelo);


        
        try{
            base = new Clase_vehiculo();
            vector = ((Clase_vehiculo)base).consultar_clase_vehiculo(2);
        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR);
            Insertar_vehiculos.this.dispose();
        }finally{
            if(base != null) base.close();
        }
        
        combo_tipo_vehiculo = new JComboBox<>(vector);
        jPanel1.add(combo_tipo_vehiculo);
        combo_tipo_vehiculo.setBounds(110, 40, 72, 22);

        jLabel4.setText("Cilindrada");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 90, 70, 16);
        jPanel1.add(text_cilindrada);
        text_cilindrada.setBounds(20, 120, 64, 22);

        jLabel5.setText("Color");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(110, 90, 50, 16);
        jPanel1.add(text_color);
        text_color.setText("BLANCO VERDE");
        text_color.setBounds(110, 120, 100, 22);

        jLabel6.setText("Combustible");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(240, 90, 75, 16);

        text_combustible.setText("DIESEL");
        jPanel1.add(text_combustible);
        text_combustible.setBounds(240, 120, 80, 22);

        jLabel7.setText("Carroceria");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(350, 90, 70, 16);

        text_carroceria.setText("CERRADA");
        jPanel1.add(text_carroceria);
        text_carroceria.setBounds(350, 120, 80, 22);

        label_servicio.setBounds(450,90,70,16);
        jPanel1.add(label_servicio);

        
        try{
            base = new Vehiculo();
            combo_servicio = new JComboBox<>(((Vehiculo)base).consultar_servicio());
            combo_servicio.setSelectedIndex(1);
        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            if(base != null) base.close();
            Insertar_vehiculos.this.dispose();
        }
        if(base != null) base.close();

        combo_servicio.setBounds(450,120,110,22);
        jPanel1.add(combo_servicio);

        jLabel8.setText("Numero Motor");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 170, 90, 16);
        jPanel1.add(text_motor);
        text_motor.setBounds(20, 200, 170, 22);

        jLabel9.setText("Numero Chasis");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(230, 170, 100, 16);
        jPanel1.add(text_chasis);
        text_chasis.setBounds(230, 200, 200, 22);

        jLabel10.setText("Pasajeros");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 250, 50, 16);

        text_pasajeros.setText("45");
        jPanel1.add(text_pasajeros);
        text_pasajeros.setBounds(20, 280, 64, 22);

        boton_parque.setText("Parque Automotor Javarturs");
        boton_parque.setSelected(true);
        jPanel1.add(boton_parque);
        boton_parque.setBounds(260, 280, 200, 21);

        jLabel11.setText("Propietario");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(130, 250, 90, 16);
        
        text_propietario.addKeyListener(new Key_adapter() {
            

            @Override
            public void accion() {
                
                Persona base_persona = null;
                try{
                    base_persona = new Persona();

                    Modelo_tabla.updateTableModel(tab, base_persona.consultar_persona(text_propietario.getText())); // Actualiza el modelo de la tabla
        
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(padre, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    padre.setVisible(false);
                }finally{
                    if(base_persona != null) base_persona.close();
                }
                
            }
            @Override
            public void accion2() {
                // TODO Auto-generated method stub
                
            }
        });
        
        jPanel1.add(text_propietario);
        text_propietario.setBounds(130, 280, 120, 22);

        
        try{
            base = new Persona();
            datos = ((Persona)base).consultar_persona();
        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base != null) base.close();
        }

        tab = Modelo_tabla.set_tabla_personas(datos);
        
        

        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){
                    // Carga un formulario para insertar personas
                    Insertar_persona persona = new Insertar_persona(padre);
                    persona.setVisible(false);
                    persona = null;
                    
                    try {
                        base = new Persona();
                        datos = ((Persona)base).consultar_persona();
                        JTable tabla_aux = Modelo_tabla.set_tabla_personas(datos);
                        tab.setModel(tabla_aux.getModel());
                        tab.setColumnModel(tabla_aux.getColumnModel());
                        
                    } catch (SQLException | IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        
                    }finally{
                        if(base != null) base.close();
                    }

                }else{
                    int valor_auxilia = tab.getSelectedRow();
                    text_propietario.setText("" + tab.getValueAt(valor_auxilia, 0));
                }
                
            }
        });
        
        scroll_tabla.setViewportView(tab);

        jPanel1.add(scroll_tabla);
        scroll_tabla.setBounds(10, 320, 550, 200);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(10, scroll_tabla.getY() + scroll_tabla.getHeight() + 10, 100, 23);

        boton_guardar.addActionListener(_ ->{

            guardar();

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
    
    protected boolean dialog_vh_externo(){
        return true;
    };

    protected void guardar(){
        boolean[] campos = new boolean[12];
        boolean confirmacion = false;
        String propietario = "";
        int cilindrada = 0;
        int pasajeros = 0;
        int modelo = 0;

        // guardamos si los diferentes campos estan llenos o no en el arreglo
        campos[0] = (text_placa.getText().compareTo("") == 0)? true:false;
        campos[1] = (text_marca.getText().compareTo("") == 0)? true:false;
        campos[2] = (text_linea.getText().compareTo("") == 0)? true:false;
        campos[3] = (text_cilindrada.getText().compareTo("") == 0)? true:false;
        campos[4] = (text_color.getText().compareTo("") == 0)? true:false;
        campos[5] = (text_combustible.getText().compareTo("") == 0)? true:false;
        campos[6] = (text_carroceria.getText().compareTo("") == 0)? true:false;
        campos[7] = (text_motor.getText().compareTo("") == 0)? true:false;
        campos[8] = (text_chasis.getText().compareTo("") == 0)? true:false;
        campos[9] = (text_pasajeros.getText().compareTo("") == 0)? true:false;
        campos[10] = (text_propietario.getText().compareTo("") == 0)? true:false;
        campos[11] = (text_propietario.getText().compareTo("") == 0)? true:false;
        
        // verificamos que campos tiene true y cuales false, para botar el respectivo error
        for(int i = 0; i< campos.length; i++){
            if(campos[i]){
                JOptionPane.showMessageDialog(this, "Faltan campos por llenar","",JOptionPane.INFORMATION_MESSAGE);
                confirmacion = false;
                break;
            }else{
                confirmacion = true;
            }
        }

        // verificamos que los campos que deben ser numeros si sean numeros y no otro tipo de datos
        if(confirmacion){
            try{

                cilindrada = Integer.parseInt(text_cilindrada.getText());
                pasajeros = Integer.parseUnsignedInt(text_pasajeros.getText());
                propietario = "" + Integer.parseInt(text_propietario.getText());
                modelo = Integer.parseInt(text_modelo.getText());


            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Los campos:\nCilindrada\nPasajeros\nPropietario\nModelo\nDeben ser valores enteros","Error", JOptionPane.ERROR_MESSAGE);
                confirmacion = false;
            }
            
        }
        if(confirmacion){
            
            Clase_vehiculo base_clase_vehiculo = null;
            Vehiculo base_vehiculo = null;

            try{
                base = new Base();
                base_clase_vehiculo = new Clase_vehiculo();
                base_vehiculo = new Vehiculo();
                dato = base_clase_vehiculo.consultar_uno_clase_vehiculo(combo_tipo_vehiculo.getSelectedItem()+"");
                base_vehiculo.insertar_vehiculo(text_placa.getText().toUpperCase(), Integer.parseInt(dato[0]),modelo,text_marca.getText().toUpperCase(), text_linea.getText().toUpperCase(), cilindrada, text_color.getText().toUpperCase(),combo_servicio.getSelectedIndex()+1, text_combustible.getText().toUpperCase(), text_carroceria.getText().toUpperCase(), text_motor.getText().toUpperCase(), text_chasis.getText().toUpperCase(), pasajeros, propietario, boton_parque.isSelected());
            }catch(SQLException | IOException ex){
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                
                Insertar_vehiculos.this.dispose();
            }finally{
                if(base != null) base.close();
                if(base_clase_vehiculo != null) base_clase_vehiculo.close();
                if(base_vehiculo != null) base_vehiculo.close();
            }
            JOptionPane.showMessageDialog(this, "Vehiculo guardado correctamente","",JOptionPane.QUESTION_MESSAGE);
            Insertar_vehiculos.this.dispose();
        }
    }
}