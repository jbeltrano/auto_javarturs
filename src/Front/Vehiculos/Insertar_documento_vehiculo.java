package Front.Vehiculos;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import Base.Documentos;
import Base.Vehiculo;
import Utilidades.Modelo_tabla;

public class Insertar_documento_vehiculo extends Modales_vehiculos{

    protected JButton boton_guardar;
    protected JLabel jLabel1;
    protected JLabel jLabel2;
    protected JLabel jLabel3;
    protected JLabel jLabel4;
    protected JLabel jLabel5;
    protected JLabel jLabel6;
    protected JLabel jLabel7;
    protected JPanel jPanel1;
    protected JScrollPane jScrollPane1;
    protected JTable tabla_vehiculo;
    protected JTextField text_numero_interno;
    protected JTextField text_placa;
    protected JTextField text_top;
    protected String datos[][];
    protected JDateChooser fecha_soat;
    protected JDateChooser fecha_rtm;
    protected JDateChooser fecha_top;
    protected JDateChooser fecha_polizas;
    protected JDialog ventana;
    protected boolean flag_is_particular;

    public Insertar_documento_vehiculo(JFrame frame, String url, String valor){
        super(frame, url, valor);
        this.setPreferredSize(new Dimension(500,500));
        pack();
    }
    
    @Override
    protected void iniciar_componentes() {

        ventana = this;
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        text_placa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        text_numero_interno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_vehiculo = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        text_top = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        boton_guardar = new javax.swing.JButton();
        Calendar calendar = Calendar.getInstance();
        Date fecha_actual = calendar.getTime();
        fecha_soat = new JDateChooser(fecha_actual);
        fecha_rtm = new JDateChooser(fecha_actual);
        fecha_polizas = new JDateChooser(fecha_actual);
        fecha_top = new JDateChooser(fecha_actual);
        flag_is_particular = false;


        // Configuracion de las barras de scroll

        jPanel1.setLayout(null);

        jLabel1.setText("Placa");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 50, 16);

        // Configuracion text_placa
        text_placa.setText("");
        jPanel1.add(text_placa);
        text_placa.setBounds(10, 30, 71, 22);
        text_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            // Cuando se presione una tecla va a buscar las concurrencias que hay en la base de datos para dar opciones
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String variable_auxiliar = text_placa.getText();
                
                if(evt.getExtendedKeyCode() != 8){
                    variable_auxiliar = variable_auxiliar.concat(evt.getKeyChar()+"");
                }else{
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length()-1);
                }

                datos_vehiculo(variable_auxiliar);

            }
        });


        jLabel2.setText("Numero Interno");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(100, 10, 100, 16);

        text_numero_interno.setText("");
        jPanel1.add(text_numero_interno);
        text_numero_interno.setBounds(100, 30, 71, 22);

        jLabel3.setText("Fecha Soat");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(210, 10, 80, 16);

        jLabel4.setText("Fecha RTM");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(320, 10, 80, 16);

        
        // Incializacion los datos de la tabla con los vehiculos que no tienen documentos registrados
        datos_vehiculo();

        // configuaracion tabla_vehiculo
        tabla_vehiculo.getTableHeader().setReorderingAllowed(false);
        tabla_vehiculo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent evt){
                int valor_auxilia = tabla_vehiculo.getSelectedRow();
                text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));

                is_particular(text_placa.getText());
            }

        });

        jScrollPane1.setViewportView(tabla_vehiculo);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 70, 420, 90);

        // Incializacion fechas
        fecha_soat.setDateFormatString("yyyy-MM-dd");
        fecha_polizas.setDateFormatString("yyyy-MM-dd");
        fecha_rtm.setDateFormatString("yyyy-MM-dd");
        fecha_top.setDateFormatString("yyyy-MM-dd");

        fecha_soat.setBounds(210, 30, 100, 22);
        fecha_polizas.setBounds(250, 200, 100, 22);
        fecha_rtm.setBounds(320, 30, 100, 22);
        fecha_top.setBounds(130, 200, 100, 22);

        jPanel1.add(fecha_polizas);
        jPanel1.add(fecha_rtm);
        jPanel1.add(fecha_soat);
        jPanel1.add(fecha_top);


        jLabel5.setText("Numero TOP");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 180, 90, 16);

        text_top.setText("");
        jPanel1.add(text_top);
        text_top.setBounds(10, 200, 64, 22);

        jLabel6.setText("Fecha TOP");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(130, 180, 80, 16);

        jLabel7.setText("Fecha RCC Y RCE");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(250, 180, 110, 16);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(10, 420, 90, 23);
        boton_guardar.addActionListener(_ ->{
            
            guardar();

        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

    }

    protected void guardar(){

        String ffecha_soat = "";
        String ffecha_rtm = "";
        String ffecha_top = "";
        String ffecha_polizas = "";
        int top = 0;
        int interno = 0;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");
        
        if(flag_is_particular){
            ffecha_soat = formato.format(fecha_soat.getDate());
            ffecha_rtm = formato.format(fecha_rtm.getDate());
        }else{
            ffecha_soat = formato.format(fecha_soat.getDate());
            ffecha_rtm = formato.format(fecha_rtm.getDate());
            ffecha_polizas = formato.format(fecha_polizas.getDate());
            ffecha_top = formato.format(fecha_top.getDate());
        }
        try{
            
            if(!flag_is_particular){
                top = Integer.parseInt(text_top.getText());
                interno = Integer.parseInt(text_numero_interno.getText());
            }

            if(text_placa.getText().equals("")){
                
                JOptionPane.showMessageDialog(this, "El campo: Placa es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            
            }else{
                base = new Documentos(url);
                try{

                    if(flag_is_particular){     // En caso de ser un vehiculo de servicio particular
                        
                        ((Documentos)base).insertar_documentos(text_placa.getText(),  // Vehiculo al cual se le hace la insercion
                                                ffecha_soat,            // Fecha de vencimiento del soat
                                                ffecha_rtm);            // Fecha de vencimiento de la tecnomecanica
                        
                    }else{                      // En caso de ser un vehiculo de servicio publico

                        ((Documentos)base).insertar_documentos(text_placa.getText(),  // Vehiculo al cual se le hace la insercion
                                                ffecha_soat,            // Fecha de vencimiento del soat
                                                ffecha_rtm,             // Fecha de vencimiento de la tecnomecanica
                                                top,                    // Numero de tarjeta de operacion
                                                ffecha_top,             // Fecha de vencimiento de la tarjeta de operacion
                                                ffecha_polizas,         // Fecha de vencimiento de las polizas rcc y rce
                                                interno);               // Numero interno del vehiculo
                    
                    }
                    JOptionPane.showMessageDialog(this, "El Los documentos para el vehiculo " + text_placa.getText() +"\nFueron insertados correctamente.","",JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                
                }catch(SQLException ex){
                
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                
                }finally{
                    base.close();
                }
                
            }
    
                
            
            
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Los campos:\nTarjeda de Operacion\nNumero Interno\nDeben ser de tipo Numerico", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    /**
     * Este metodo se encarga de verificar
     * si un vehiculo es particular, en caso de
     * serlo, modificara algunos atributos, para
     * evitar errores en la base de datos
     * @param placa
     */
    protected void is_particular(String placa){

        base = new Vehiculo(url);
        try{
            if(((Vehiculo)base).is_particular(placa)){
                flag_is_particular = true;
                text_numero_interno.setEnabled(false);
                fecha_polizas.setEnabled(false);
                fecha_top.setEnabled(false);
                text_top.setEnabled(false);
            }else{
                flag_is_particular = false;
                text_numero_interno.setEnabled(true);
                fecha_polizas.setEnabled(true);
                fecha_top.setEnabled(true);
                text_top.setEnabled(true);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base.close();
        }
    }
    protected void datos_vehiculo(){
        base = new Documentos(url);
        try{
            datos = ((Documentos)base).consultar_vehiculo_sin_documento(true);
            tabla_vehiculo.setModel(Modelo_tabla.set_modelo_tablas(datos));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base.close();
        }
    }

    protected void datos_vehiculo(String varible_busqueda){

        base = new Documentos(url);
        try{
            datos = ((Documentos)base).consultar_vehiculo_sin_documento(varible_busqueda);
            tabla_vehiculo.setModel(Modelo_tabla.set_modelo_tablas(datos));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base.close();
        }

    }
}