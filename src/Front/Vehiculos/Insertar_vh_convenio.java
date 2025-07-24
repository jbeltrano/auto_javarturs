package Front.Vehiculos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.io.IOException;

import Base.Persona;
import Base.Vehiculo;
import Base.Vh_convenio;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;

public class Insertar_vh_convenio extends Modales_vehiculos{

    private JPanel panel_principal;     // Panel principal donde se van a almacenar todos los componentes
    private JLabel label_placa;         // Label para el text field del vehiculo
    private JLabel label_empresa;       // label para el text field de la empresa
    private JTextField text_placa;      // text field para ingresar las busquedas del vehiculo
    private JTextField text_empresa;    // text field para ingresar las busquedas de la empresa
    private String[][] datos_vehiculo;  // Esta variable se encarga de almacenar la matriz de los datos del vehiculo
    private String[][] datos_empresa;   // Esta variable se encarga de almacenar la matriz de los datos de las empresas
    private JTable tabla_vehiculo;      // Tabla que mostrara los datos de los vehiculos
    private JTable tabla_empresa;       // Tabla que mostrara los datos de las empresas
    private JScrollPane scroll_vehiculo;    // Scroll donde se almacenara la tabla del vehiculo
    private JScrollPane scroll_empresa;     // Scroll donde se almacenara la tabla de la empresa
    private JButton boton_guardar;      // Boton que se encargara de cuardar los datos

    private static final int X = 20;    // Constante que fija la posicion x
    private static final int ALTO = 20; // Constante que fija la altura de los componentes

    private String vehiculo_precargado; // Este atributo se usa, cuando se requiere una pre inicializacion

    
    /**
     * Constructor principal, necesario para realizar la respectiva insercion
     * @param frame
     * @param url
     */
    public Insertar_vh_convenio(JFrame frame) {
        super(frame);
        this.setPreferredSize(new Dimension(800,300));
        vehiculo_precargado = "";
        pack();
        
    }

    
    /**
     * Este es un constructor secunadrio, hace la misma funcion
     * que el constructor primario, sin embargo, este se utiliza
     * para precargar un vehiculo seleccionado
     * @param frame
     * @param placa
     * @param url
     */
    public Insertar_vh_convenio(JFrame frame, String placa){
        super(frame);
        this.setPreferredSize(new Dimension(800,300));
        vehiculo_precargado = placa;
        pack();
    }

    @Override
    protected void iniciar_componentes() {
        // Inicializacion panel principal
        panel_principal = new JPanel();
        panel_principal.setLayout(null);

        // Incializcion labels
        label_placa = new JLabel("Placa vehiculo:");
        label_empresa = new JLabel("Nit empresa con convenio:");
        
        // Inicializacion TextFields
        text_placa = new JTextField("");
        text_empresa = new JTextField("");

        // Inicializacion Empresa
        scroll_vehiculo = new JScrollPane();
        scroll_empresa = new JScrollPane();

        // Inicializacion JTable
        tabla_vehiculo = new JTable();
        tabla_empresa = new JTable();

        // Inicializacion botones
        boton_guardar = new JButton();


        // Configuraciones

        // Configuracion labels
        label_placa.setBounds(  X,    // Posicion X en el panel
                                0,  // Posicion Y en el panel
                                100,    // Ancho del elemento
                                ALTO);  // Alto del elemento

        label_empresa.setBounds(X + label_placa.getWidth() + 280,   // Posicion X en el panel
                                label_placa.getY(),                 // Posicion y en el panel
                                200,                            // Ancho del label
                                ALTO);                              // Alto del label

        // Configuracion textfields
        text_placa.setBounds(X,                                                 // Posicion X en el panel
                            label_placa.getY() + label_placa.getHeight() + 10,  // Posicion Y en el panel
                            100,                                            // Ancho del elemento
                            ALTO);                                              // Alto del elemento
        
        text_placa.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion() {
                
                set_tabla_vehiculo();
                tabla_vehiculo.changeSelection(0, 0, false, false);
                panel_principal.revalidate();
                panel_principal.repaint();

            }
            @Override
            public void accion2() {
                
                accion_tabla_vehiculo();
                
            }
        });

        text_empresa.setBounds(label_empresa.getX(),                                    // Posicion x en el panel
                                label_empresa.getY() + label_empresa.getHeight() + 10,  // Posicion Y en el panel
                                100,                                                // Ancho del elemento
                                ALTO);                                                  // Alto del elemento
        text_empresa.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion() {
                
                set_tabla_empresa();
                tabla_empresa.changeSelection(0, 0, false, false);
                panel_principal.revalidate();
                panel_principal.repaint();

            }
            @Override
            public void accion2() {
                
                accion_tabla_empresa();
                
            }
        });
        // Configuracion tablas
        // Obtencion de los datos

        Vehiculo base_vehiculo = null;
        Persona base_persona = null;

        try{
            base_vehiculo = new Vehiculo();
            base_persona = new Persona();
            // Intenta realizar la consulta a la base de datos
            datos_vehiculo = base_vehiculo.consultar_vehiculo_externo("");
            datos_empresa = base_persona.consultar_empresa("");

        }catch(SQLException|IOException ex){    // Si hay algun error con los datos, lanza un mensaje de error
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Cierra la ventana
            
        }finally{   // Finalmente limpia las conexiones con la base de datos
            if(base_vehiculo != null) base_vehiculo.close();
            if(base_persona != null) base_persona.close();
        }


        // configuracion de los listener y inicalizacion de las tablas
        tabla_vehiculo = Modelo_tabla.set_tabla_vehiculo(datos_vehiculo);   //Carga el formato de la tabla con los datos dados
        tabla_vehiculo.addMouseListener(new MouseAdapter() {    // Escuchador del mouse para cargar informacion en el text vehiculo
            
            public void mousePressed(MouseEvent evt){   // Se acciona si el boton derecho del mouse es presionado
                
                accion_tabla_vehiculo();
                
            }

        });
        tabla_vehiculo.changeSelection(0, 0, false, false); // Este comando se utiliza para que la tabla siempre tenga una seleccion

        tabla_empresa = Modelo_tabla.set_tabla_personas(datos_empresa); //Carga el formato de la tabla con los datos dados
        tabla_empresa.addMouseListener(new MouseAdapter() {     // Escuchador del mouse para cargar informacion en el text empresa
            
            public void mousePressed(MouseEvent evt){   // Se acciona si el boton derecho del mouse es presionado
                
                accion_tabla_empresa();
                
            }

        });
        tabla_empresa.changeSelection(0, 0, false, false);  // Este comando se utiliza para que la tabla siempre tenga una seleccion
        // Configuracion de los scroll
        scroll_vehiculo.setBounds(  X,                                                  // Posicion x en el panel
                                    text_placa.getY() + text_placa.getHeight() + 20,    // Posicion Y en el panel
                                    350,                                            // Ancho en el que se va a mostrar la tabla
                                    100);                                           // Alto en el que se va a mostrar la tabla
        
        scroll_vehiculo.setViewportView(tabla_vehiculo);    // Carga la tabla de vheiculos en el Scrollpane

        scroll_empresa.setBounds(   text_empresa.getX(),                                    // Posicion x en el panel
                                    text_empresa.getY() + text_empresa.getHeight() + 20,    // Posicion y en el panel
                                    350,                                                // Ancho de la tabla que se va a mostrar
                                    100);                                               // Alto de la tabala que se va a mostrar

        scroll_empresa.setViewportView(tabla_empresa);  // CArga la tabla de empresas en el scrollpane


        // Configuracion del boton
        boton_guardar.setText("Guardar");
        boton_guardar.setBounds(X,                                                          // Posicion x en el panel
                                scroll_vehiculo.getY() + scroll_vehiculo.getHeight() + 30,  // Posicion y en el panel
                                100,                                                    // Ancho del boton
                                ALTO);                                                      // Alto del boton

        boton_guardar.addActionListener(_ ->{   // Se encarga de establecer la accion que se realiza al oprimir el boton
            guardar(); 
        });

        // Adicionamiento en el panel
        panel_principal.add(label_placa);
        panel_principal.add(label_empresa);
        panel_principal.add(text_placa);
        panel_principal.add(text_empresa);
        panel_principal.add(scroll_vehiculo);
        panel_principal.add(scroll_empresa);
        panel_principal.add(boton_guardar);

        // Estas lineas se utilizan para centrar el panel, en relacion a la posicion del JFrame Padre
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );

        pack();
    }

    /**
     * Este metodo se encarga de verificar que no hayan errores
     * por ejemplo que existan los datos y que no sean incoerentes
     */
    private void verificacion(){

        // Mensaje base
        String mensaje = "Los datos ingresados son erroneos, Informe:\n\n";
        boolean flag = false;

        if(tabla_vehiculo.getRowCount() == 0){
            mensaje += "No hay datos seleccionados en la tabla para el vehiculo.\n";
            flag = true;
        }
        if(tabla_empresa.getRowCount() == 0){
            mensaje += "No hay datos seleccionados en la tabla para empresa.\n";
            flag = true;
        }

        if(flag){
            NullPointerException ex = new NullPointerException(mensaje);
            throw ex;
        }
        
    }
    /**
     * Esta funcion se utiliza para guardar la informacion
     * diligenciada en el formualro, dicha informacion
     * la gurada en la base de datos
     */
    private void guardar(){
        Vh_convenio base_convenio = null;
        try{
            verificacion();
            base_convenio = new Vh_convenio();
            String dato_placa = (String) tabla_vehiculo.getValueAt(tabla_vehiculo.getSelectedRow(), 0);
            String dato_empresa = (String) tabla_empresa.getValueAt(tabla_empresa.getSelectedRow(), 0);
            int opcion = 0;
            
            try{

                base_convenio.insertar_vh_convenio(dato_placa, dato_empresa);   // Guarda la informacion en la base de datos
                opcion = JOptionPane.showConfirmDialog(this,
                                                        "Se ha incertado correctamente el convenio\nPlaca: " + dato_placa + 
                                                        "\nNit: "+ dato_empresa+"\n\nDeceas seguir incertando convenios\n",
                                                        "Operacion Exitosa",
                                                        JOptionPane.YES_NO_OPTION);

                if(opcion == 1){    // En caso que el usuario, ponga que no

                    this.dispose(); // Esta funcion cierra la ventana
                    
                }else{  // En caso que el usuario seleccione que si
                    
                    text_empresa.setText("");   // Limpiara la seleccion de la empresa
                    text_placa.setText("");     // Limpieara la seleccion del vehiculo
                    set_tabla_vehiculo();       // Carga nuevamente el valor de la tabla del vehiculo
                    set_tabla_empresa();        // Carga nuevametne los valores por defecto de la tabla empresa  

                }
                
    
            }catch(SQLException ex){
                
                JOptionPane.showMessageDialog(  // Muestra un mensaje en pantalla
                                            this,   // Utiliza como apdre esta misma ventana
                                            ex.getLocalizedMessage(),    // Muestra el mensaje de error
                                            "Error",    // Titulo
                                            JOptionPane.ERROR_MESSAGE); // Tipo de Joptionpane 
                this.setVisible(false); // Cierra la ventana
    
            }finally{
    
                base_convenio.close();
    
            }
            
        }catch(NullPointerException | IOException | SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Esta funcion se encarga de realizar una accion
     * en la tabla vehiculo, cuando se presiona enter
     */
    private void accion_tabla_vehiculo(){

        int valor_auxilia = tabla_vehiculo.getSelectedRow();    // Se selecciona el valor de la fila seleccionada
        text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));   // Se reestablece el valor del text fiel por el seleccionado
        panel_principal.revalidate();   // Recarga el panel
        panel_principal.repaint();  // Repinta el panel

    }

    /**
     * Esta funcion se encarga de cargar los datos de la tabla
     * vehiculo, cuando se ingrese algun velor en el text fiel
     * vehiculo, para poder hacer busquedas en la base de datos
     */
    private void set_tabla_vehiculo(){
                
        Vehiculo base_vehiculo = null; // Abre coneccion con la base de datos

        try{    // Pueden haber problemas, por eso esta esto aqui

            base_vehiculo = new Vehiculo();

            Modelo_tabla.updateTableModel(tabla_vehiculo, base_vehiculo.consultar_vehiculo_externo(text_placa.getText())); // Actualiza el modelo de la tabla



        }catch(SQLException | IOException ex){    // En caso de un error
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);   // Muestra el error en pantalla
            this.setVisible(false); // Cierra la ventana
        }finally{
            if(base_vehiculo != null) base_vehiculo.close();  // Cierra la conexion con la base de datos
        }
        
    }


    /**
     * Esta funcion se utiliza para cuando se le da enter al
     * text fiel de la empresa, y realiza una accion en la tabla
     */
    private void accion_tabla_empresa(){
        int valor_auxilia = tabla_empresa.getSelectedRow(); // Obtiene el valor de la fila seleccionada
        text_empresa.setText("" + tabla_empresa.getValueAt(valor_auxilia, 0));  // Obtiene el valor y lo establece en el textfield
        panel_principal.revalidate();   // Revalida el panel
        panel_principal.repaint();      // Repinta el panel
    }

    /**
     * Esta funcion se utiliza para establecer nuevos datos
     * en la tabla empresa, principalmente utilizado en conjunto
     * con el textfield, utilizado para buscar en la base de datos
     */
    private void set_tabla_empresa(){
        String datos[][] = null;    // Son los datos a utilizar
                
        Persona base_empresa = null;    // Establece conexion con la base de datos

        try{    // Por si hay algun tipo de error

            base_empresa = new Persona();

            Modelo_tabla.updateTableModel(tabla_empresa, base_empresa.consultar_empresa(text_empresa.getText())); // Actualiza el modelo de la tabla empresa

        }catch(SQLException | IOException ex){    // Por si hay algun tipo de problema

            JOptionPane.showMessageDialog(  // Muestra un mensaje en pantalla
                                        this,   // Utiliza como apdre esta misma ventana
                                        ex.getLocalizedMessage(),    // Muestra el mensaje de error
                                        "Error",    // Titulo
                                        JOptionPane.ERROR_MESSAGE); // Tipo de Joptionpane 
            this.setVisible(false); // Cierra la ventana
        }finally{
            if(base_empresa != null) base_empresa.close();   // Cierra al base de datos
        }
        
    }
}
