package Front.Personas;


import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import java.util.Date;
import Base.Licencia;
import Base.Persona;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;

public class Insertar_conductor extends Modales_personas{
    
    protected JButton boton_guardar;
    protected JComboBox<String> combo_conductor;
    private JLabel jLabel1;
    private JLabel jLabel2;
    protected JPanel jPanel1;
    private JScrollPane jScrollPane1;
    protected JTable tabla_persona;
    protected JTextField text_documento;
    protected JDateChooser buscar_fecha;
    private String [][] datos;
    private JDialog padre;
    private Calendar calendar;
    private Date currentDate;

    public Insertar_conductor(JFrame padre, String url){
        super(padre, url, new Dimension(500,300));
    }

    public Insertar_conductor(JDialog padre, String url){
        super(padre, url, new Dimension(500,300));
    }

    @Override
    protected void iniciar_componentes() {
        
        padre = this;
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        text_documento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        combo_conductor = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_persona = new javax.swing.JTable();
        buscar_fecha = new JDateChooser();
        boton_guardar = new javax.swing.JButton();
        calendar = Calendar.getInstance();
        currentDate = calendar.getTime();


        buscar_fecha.setDate(currentDate);
        jPanel1.setLayout(null);

        jLabel1.setText("Numero Documento");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(6, 6, 123, 16);
        jPanel1.add(text_documento);
        text_documento.setBounds(6, 28, 123, 22);

        jLabel2.setText("Tipo Licencia");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(147, 6, 87, 16);


        jPanel1.add(combo_conductor);
        combo_conductor.setBounds(147, 28, 87, 22);

        base = new Persona(url);
        Licencia base_licencia = new Licencia(url);
        try{
            datos = ((Persona)base).consultar_persona_natural("");
            combo_conductor.setModel(new DefaultComboBoxModel<>(base.get_datos_tabla(base_licencia.consultar_categoria(), 1)));
            tabla_persona = Modelo_tabla.set_tabla_personas(datos);

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base.close();
            base_licencia.close();
        }

        tabla_persona.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent evt){
                if(SwingUtilities.isRightMouseButton(evt)){
                    new Insertar_persona(padre, url);
                    base = new Persona(url);
                try{
                    datos = ((Persona)base).consultar_persona_natural("");
                    JTable tab = Modelo_tabla.set_tabla_personas(datos);
                    tabla_persona.setModel(tab.getModel());
                    tabla_persona.setColumnModel(tab.getColumnModel());

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    padre.setVisible(false);
                    
                }finally{
                    base.close();
                }
                    
                }else{
                    int row = tabla_persona.getSelectedRow();
                    text_documento.setText("" + tabla_persona.getValueAt(row, 0));
                }
            }

        });

        text_documento.addKeyListener(new Key_adapter() {

            @Override
            public void accion() {
                // TODO Auto-generated method stub
                String variable_auxiliar = text_documento.getText();
                
                
                base = new Persona(url);
                try{
                    datos = ((Persona)base).consultar_persona_natural(variable_auxiliar);
                    JTable tab = Modelo_tabla.set_tabla_personas(datos);
                    tabla_persona.setModel(tab.getModel());
                    tabla_persona.setColumnModel(tab.getColumnModel());
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    padre.setVisible(false);
                    base.close();
                }
                base.close();
                tabla_persona.changeSelection(0, 0, false, false);
            }

            @Override
            public void accion2() {
                try{
                    int row = tabla_persona.getSelectedRow();
                    text_documento.setText("" + tabla_persona.getValueAt(row, 0));
                }catch(Exception ex){
                    
                }
                
                
            }
            

        });

        jScrollPane1.setViewportView(tabla_persona);
        

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(6, 62, 388, 100);

        buscar_fecha.setDateFormatString("yyyy-MM-dd");
        buscar_fecha.setBounds(250, 28, 100, 22);
        
        // Agregar el Jbuscar_fecha al JFrame
        jPanel1.add(buscar_fecha);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(10, 230, 90, 23);
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
        
        // En caso de haber campos vacios genera un error y no ejecuta el guardado
        if(text_documento.getText().compareTo("") == 0){
            JOptionPane.showMessageDialog(padre, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        }else{  // Si todos los campos estan llenos entonces procede a guardar
            
            try{
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");    // Formato para la fecha
                String fecha = formato.format(buscar_fecha.getDate());      // Establece el Date en un String con el formato
                Double.parseDouble(text_documento.getText());       // Verifica si el numero de documento es un numero                     

                base = new Licencia(url);   // Abre un objeto de tipo base para hacer consultas u otro tipo de acciones a la base de datos
                
                try{
                    // Inserta los datos en la base de datos
                    ((Licencia)base).insertar_licencia( text_documento.getText(),               // Numero de documento del conductor
                                            combo_conductor.getSelectedIndex()+1,   // Tipo de licencia de conduccion
                                            fecha);                                 // Fecha de vencimiento de la licencia

                    // El mensaje saldra si todo salio con exito
                    JOptionPane.showMessageDialog(padre,    // Padre al que pertenece la ventana emergente
                                            "Licencia incertada con Exito", // Mensage que muestra la ventana
                                            "IE",                             // Titulo de la ventana  
                                            JOptionPane.INFORMATION_MESSAGE);       // Tipo de icono que aparece en la ventana
                
                    // Hace invicible la ventana antes de cerrarla
                    padre.setVisible(false);
                }catch(SQLException ex){
                    // Si hay un error lo muestra en una ventana modal
                    JOptionPane.showMessageDialog(  padre,  // Padre al que pertenece la ventana
                                                    ex.getMessage(),    // Mensaje de error que se muestra en la ventana
                                                    "Error",      // Titulo de la ventana
                                                    JOptionPane.ERROR_MESSAGE); // Tipo de icono en la ventana

                }finally{
                    base.close();   // Finaliza la coneccion con la base de datos
                }

            }catch(NumberFormatException e){
                // Muestra un mensaje si el tipo de dato no es numerico
                JOptionPane.showMessageDialog(  padre,  // Padre al que pertenece la ventana
                                                "No ingresaste un dato numerico. \nPor favor selecciona un elemento de la tabla",   // Mensaje que muestra la ventana
                                                "Error",              // Titulo de la ventana
                                                JOptionPane.ERROR_MESSAGE); // Tipo de icono que aparece en la ventana
            }
        }
    }

}

