package Front.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Window;
import Utilidades.Key_adapter;
import Utilidades.Leer_config;

public abstract class Panel extends JPanel{
    
    private static final Leer_config config = new Leer_config(); // Se establece de esta manera, para evitar la sobre ejecucion
    private JPanel panel_busqueda;
    private JLabel label_busqueda;
    protected JTextField text_busqueda;
    protected CustomPopupMenu pop_menu;
    protected JMenuItem item_actualizar;
    protected JMenuItem item_eliminar;
    protected JMenuItem item_adicionar;
    protected JScrollPane scroll;
    protected String url;
    protected JTable tabla;
    protected Window window;
    protected int color;
    
    /**
     * Este constructor se encarga de establecer
     * un un formato para el panel a utilizar.
     */
    public Panel(String url){
        super();    // Llama la super clase de JPanel
        this.url = url;
        this.window = SwingUtilities.getWindowAncestor(this);

        color = (config.get_tema() == 0)?config.get_color_secundario():config.get_color_secundario_oscuro();    // Esto se encarga de configurar el color correctamente

        setLayout(new BorderLayout());  // Establece el Layout a utilizar
        scroll = new JScrollPane(); // Inicializa el scroll que se va a utilizar

        configuracion_panel_busqueda(); // Configura la busqueda

        config_pop_menu();  // Configura el pop_menu

        cargar_datos_tabla();   // Cara los datos a la tabla
        tabla.setComponentPopupMenu(pop_menu);  // Establece el pop_menu que se vera en la tabla

        scroll.setViewportView(tabla);  // Establece la tabla que se va a visualizar
        
        add(panel_busqueda, BorderLayout.NORTH);    // Agrega el componente al norte del panel
        add(scroll,BorderLayout.CENTER);    // Agrega el componente al centro del panel
        
    }

    /**
     * Este metodo se encarga de configurar el text_field
     * para que realice una accion de buscar dependiendo la
     * configuracion del usuario.
     */

    private void configuracion_panel_busqueda(){
        panel_busqueda = new JPanel(null);
        label_busqueda = new JLabel("Buscar:");
        text_busqueda = new JTextField();

        // Configuracion panel busqueda
        label_busqueda.setBounds(10,2,50,20);
        text_busqueda.setBounds(label_busqueda.getX() + label_busqueda.getWidth() + 2 ,2,300,20);
        panel_busqueda.add(label_busqueda);
        panel_busqueda.add(text_busqueda);
        panel_busqueda.setPreferredSize(new Dimension(700,24));
        panel_busqueda.setBackground(new Color(color));
        

        // Establece el escuchador para la busqueda
        text_busqueda.addKeyListener(new Key_adapter() {
           
            @Override
            public void accion(){
                accion_text_busqueda();
            }

            @Override
            public void accion2(){}
        });
    }
    
    protected void config_pop_menu(){
        pop_menu = null;
        pop_menu = new CustomPopupMenu();

        item_actualizar = new JMenuItem("Modificar");
        item_adicionar = new JMenuItem("Adicionar");
        item_eliminar = new JMenuItem("Eliminar");

        pop_menu.add(item_adicionar);
        pop_menu.add(item_actualizar);
        pop_menu.add(item_eliminar);

        config_listener_pop_menu(); // Configura los excuchadores para los item del pop_menu
    }

    /**
     * Este metodo se tendra que encargar de cargar
     * los datos que se van a mostrar al usuario.
     */
    protected abstract void cargar_datos_tabla();

    /**
     * Este metodo se encarga de establecer la accion que se va
     * a  realizar cuando se detecte un evento en el text busqueda
     */
    protected abstract void accion_text_busqueda();

    /**
     * Este metodo se encarga de configurar los listener
     * que va a tener el popup menu
     */
    protected abstract void config_listener_pop_menu();

    protected Window get_window(){
        return SwingUtilities.getWindowAncestor(this);
    }
}




// En este caso, la idea es implementar el siguiente metodo
    // Si no hay informacion para mostrar
    // if(tabla.getRowCount() == 0 ){
    //     JButton boton_auxiliar = new JButton("Agregar");
    //     pan = new JPanel(null);
    //     boton_auxiliar.setBounds(10,10,100,20);
    //     boton_auxiliar.addActionListener(ac ->{
            
    //         new Insertar_vehiculos(this, url, "").setVisible(true);
    //         panel_principal2.remove(pan);
    //         vehiculos.doClick();
    //     });
    //     pan.add(boton_auxiliar);
    //     pan.setPreferredSize(new Dimension(120,40));
    //     panel_principal2.add(pan,BorderLayout.EAST);
    // }