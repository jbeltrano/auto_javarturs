package Front.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import java.awt.Window;
import Utilidades.Key_adapter;

public abstract class Panel extends JPanel{
    
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
    
    /**
     * Este constructor se encarga de establecer
     * un un formato para el panel a utilizar.
     */
    public Panel(String url){
        super();    // Llama la super clase de JPanel
        this.url = url;
        this.window = SwingUtilities.getWindowAncestor(this);

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
        panel_busqueda.setBackground(new Color(52, 135, 25));

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
}

/*
 * Esta clase se encarga de personalizar un popup menu,
 * para no tener errores con las ventanas en cualquier
 * entorno, puesto que al parecer al salirce de las ventanas
 * el programa presenta bugs graficos.
 */
class CustomPopupMenu extends JPopupMenu {

    @Override
    public void show(Component invoker, int x, int y) {
        if (invoker != null && invoker.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) invoker.getParent();
            Dimension viewportSize = viewport.getSize();
            Point viewportPosition = viewport.getViewPosition();
            Dimension popupSize = this.getPreferredSize();

            // Ajustar la posición para que el menú emergente esté dentro del viewport
            if (x + popupSize.width > viewportPosition.x + viewportSize.width) {
                x = (viewportPosition.x + viewportSize.width) - popupSize.width;
            }
            if (y + popupSize.height > viewportPosition.y + viewportSize.height) {
                y = (viewportPosition.y + viewportSize.height) - popupSize.height;
            }

            // Ajustar la posición para no mostrar el popup fuera del JScrollPane
            if (x < viewportPosition.x) {
                x = viewportPosition.x;
            }
            if (y < viewportPosition.y) {
                y = viewportPosition.y;
            }
        }

        // Llamar al método original para mostrar el popup
        super.show(invoker, x, y);
    }
}