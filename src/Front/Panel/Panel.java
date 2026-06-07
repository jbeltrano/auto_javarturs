package Front.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Window;
import Utilidades.Key_adapter;
import Utilidades.Leer_config;

import javax.swing.JButton;
import java.awt.event.ActionListener;

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
    protected JTable tabla;
    protected Window window;
    protected int color;

    protected JButton newButton;
    private JLayeredPane floating_layer;
    private static final int FLOATING_MARGIN = 16;
    
    /**
     * Este constructor se encarga de establecer
     * un un formato para el panel a utilizar.
     */
    public Panel(){
        super();    // Llama la super clase de JPanel
        this.window = SwingUtilities.getWindowAncestor(this);

        color = (config.get_tema() == 0)?config.get_color_secundario():config.get_color_secundario_oscuro();    // Esto se encarga de configurar el color correctamente

        setLayout(new BorderLayout());  // Establece el Layout a utilizar
        scroll = new JScrollPane(); // Inicializa el scroll que se va a utilizar

        configuracion_panel_busqueda(); // Configura la busqueda

        config_pop_menu();  // Configura el pop_menu

        cargar_datos_tabla();   // Cara los datos a la tabla
        tabla.setComponentPopupMenu(pop_menu);  // Establece el pop_menu que se vera en la tabla

        scroll.setViewportView(tabla);  // Establece la tabla que se va a visualizar
        
        configNewButton();
        
        add(panel_busqueda, BorderLayout.NORTH);    // Agrega el componente al norte del panel
        floating_layer = new JLayeredPane(){
            @Override
            public void doLayout() {
                Dimension size = getSize();
                scroll.setBounds(0, 0, size.width, size.height);

                Dimension buttonSize = newButton.getPreferredSize();
                int x = Math.max(FLOATING_MARGIN, size.width - buttonSize.width - FLOATING_MARGIN);
                int y = Math.max(FLOATING_MARGIN, size.height - buttonSize.height - FLOATING_MARGIN);
                newButton.setBounds(x, y, buttonSize.width, buttonSize.height);
            }
        };
        floating_layer.add(scroll, Integer.valueOf(JLayeredPane.DEFAULT_LAYER));    // Agrega el scroll como base
        floating_layer.add(newButton, Integer.valueOf(JLayeredPane.PALETTE_LAYER));    // Agrega el boton flotante encima
        add(floating_layer, BorderLayout.CENTER);    // Agrega el contenedor al centro del panel
        
    }

    private void configNewButton() {
        newButton = new FloatingButton();   // Crea un nuevo boton para adicionar un nuevo registro
        newButton = new FloatingButton();   // Crea un nuevo boton flotante (icono)
        newButton.setFocusPainted(false);   // Elimina el borde que aparece al hacer click en el boton
        newButton.setPreferredSize(new Dimension(100,30));    // Establece el tamaño del boton
        newButton.setPreferredSize(new Dimension(56,56));    // Establece el tamaño del boton flotante
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

    protected void setActionButton(ActionListener actionListener){
        newButton.addActionListener(actionListener);
    }

    protected Window get_window(){
        return SwingUtilities.getWindowAncestor(this);
    }

    
    private class FloatingButton extends JButton {
       
        FloatingButton() {
            super("");
            initButton();
        }

        private void initButton() {
            setBorderPainted(false);
            setOpaque(true);
            setContentAreaFilled(false);
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth();
            int h = getHeight();

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // shadow
            g2.setColor(new Color(0,0,0,80));
            Ellipse2D shadow = new Ellipse2D.Double(2, 4, w-4, h-6);
            g2.fill(shadow);

            // circle background (dark)
            g2.setColor(new Color(51,51,51));
            Ellipse2D circle = new Ellipse2D.Double(0, 0, w-6, h-6);
            g2.fill(circle);

            // plus sign (white)
            g2.setColor(Color.WHITE);
            int thickness = Math.max(6, w/10);
            int len = Math.max(12, w/3);
            int cx = w/2 - 3;
            int cy = h/2 - 3;

            // vertical rectangle
            g2.fillRoundRect(cx - thickness/2, cy - len/2, thickness, len, thickness, thickness);
            // horizontal rectangle
            g2.fillRoundRect(cx - len/2, cy - thickness/2, len, thickness, thickness, thickness);

            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            int w = getWidth();
            int h = getHeight();
            double cx = w/2.0 - 3;
            double cy = h/2.0 - 3;
            double dx = x - cx;
            double dy = y - cy;
            double r = Math.min(w, h) / 2.0;
            return dx*dx + dy*dy <= r*r;
        }
    }
}