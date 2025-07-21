package Front.Vehiculos;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import Base.Clase_vehiculo;


public class Insertar_tipo_vehiculo extends Modales_vehiculos{
    
    private Clase_vehiculo base;    // Variable que se va a utlizar para el manejo de la base de datos
    private JPanel panel;   // Este es el panel principal sobre el cual se va a trabajar
    private JLabel primero; // Este es el primer label, donde se indica el campo a rellenar
    private JButton boton_actualizacion;    // El boton a utilizar para guardar los datos
    private JTextField tipo_vehiculo;       // El text field donde se va a ingresar la informacion


    /**
     * Constructor de la clase, se encarga de generar el JDialog
     * para poder realizar la insercion de tipo de vehiculo,
     * y de paso hacer la insercion en la base de datos
     * @param frame determina el padre de donde se llama la ventana
     * @param url es la direccion de la base de datos
     * @param valor es un valor por defecto puede ser ""
     */
    public Insertar_tipo_vehiculo(JFrame frame, String valor){

        super(frame, valor);
        
        setVisible(true);
    }

    /**
     * Este metodo es el que se encarga de inicializar
     * todos los componentes necesarios para que se
     * renderice lo que se quiere mostrar en el panel
     */
    protected void iniciar_componentes(){

        panel = new JPanel(null);    // Panel principal

        primero = new JLabel("Ingrese el tipo de vehiculo");    // Inicializacion del JLabel
        primero.setBounds(25,10,250,20);    // Ubicacion dentro del panel

        tipo_vehiculo = new JTextField("");     // Inicializacion del TextField
        tipo_vehiculo.setBounds(25, 40,300,20);     // Ubicacion dentro del panel

        boton_actualizacion = new JButton("Guardar");   // Inicializacion del boton
        boton_actualizacion.setBounds(25,530,100,20);   // Ubicacion dentro del panel
        boton_actualizacion.addActionListener(_ ->{                      // Accion a realizar a precionar el boton
        

            
            
            if(tipo_vehiculo.getText().length() >0){        // Determina que haya algun valor ingresado dentro del textfield

                
                try{    
                    base = new Clase_vehiculo();     // Obtiene el accdeso a la base de datos, para hacer cambios en la tabla clase vehiculo

                    base.insertar_clase_vehiculo(tipo_vehiculo.getText().toUpperCase());    // Realiza la insercion del dato ingresado
                }catch(SQLException | IOException ex){    // En caso que haya problemas de coneccion a la base dadtos

                    // Muestra el siguiente mensaje de error lanzando la excepcion atrapada
                    JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);

                }finally{
                    if(base != null) base.close();   // Cierra la coneccion con la base de datos
                }

                // Muestra que la insercion se realizo correctamente
                JOptionPane.showMessageDialog(this, // Referencia a el padre
                                            "Tipo vehiculo insertado con exito.",   // Texto especifico a mostrar
                                            "Insercion",    // Informacion relevante en la ventana
                                            JOptionPane.INFORMATION_MESSAGE);   // Tipo de mensaje
                
                this.dispose(); // Cierra la ventana
            }
            
        });        

        // En este espacio se agregan los diferentes componentes a el panel
        panel.add(boton_actualizacion);
        panel.add(tipo_vehiculo);
        panel.add(primero);
        add(panel); // Agrega el panel a el JDialog
        pack();
    }
}
