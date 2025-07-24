package Front;

import java.awt.Desktop;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.BorderLayout;
import Front.Panel.Ciudades.Panel_ciudad;
import Front.Panel.Ciudades.Panel_departamento;
import Front.Panel.Ciudades.Panel_ruta;
import Front.Panel.Extractos.Panel_contratante;
import Front.Panel.Extractos.Panel_contratos_mensuales;
import Front.Panel.Extractos.Panel_contratos_ocasionales;
import Front.Panel.Extractos.Panel_extractos_mensuales;
import Front.Panel.Extractos.Panel_extractos_ocasionales;
import Front.Panel.Personas.Panel_conductores;
import Front.Panel.Personas.Panel_persona;
import Front.Panel.vehiculos.Panel_clase_vehiculo;
import Front.Panel.vehiculos.Panel_documentos_vehiculos;
import Front.Panel.vehiculos.Panel_vehiculo_convenio;
import Front.Panel.vehiculos.Panel_vehiculo_has_conductor;
import Front.Panel.vehiculos.Panel_vehiculos;
import Utilidades.Leer_config;
import Utilidades.Leer_link;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import Estructuras_datos.Queue;


public class Principal extends JFrame{
    
    private static final int TAMAÑO_PANEL_SECUNDARIO = 70;
    private static final int TAMAÑO_PANEL_SECUNDARIO_ANCHO = 170;
    private static final int TAMAÑO_BOTON = 50;

    private JPanel panel_secundario;
    private JPanel panel_principal2;
    private JPanel panel_informacion;
    private JPanel pan;
    private JMenuBar barra_menu;
    private JLabel label_imagen;
    private JButton boton_boton_extractos_mensuales;
    private JButton boton_vehiculos;
    private JButton boton_extractos;
    private JButton boton_personas;
    private JButton boton_cuidad;
    private JButton boton_empleados;
    private ImageIcon imagen_ciudades;
    private ImageIcon imagen_vehiculos;
    private ImageIcon imagen_extractos;
    private ImageIcon imagen_personas;
    private ImageIcon imagen_empleados;
    private HashMap<String, Queue<String[]>> read_links;
    private HashSet<String> id_links;
    private int color_principal;
    private int color_secundario;

    /**
     * Este es el constructor general para la clase Principal
     * se encarga de iniciar la gran mayoria de componentes y el JFrame como tal
     * @see JFrame
    */
    public Principal(){
        super("Javarturs");
        Leer_config config = new Leer_config();
        
        
        if(config.get_tema() == 0){
            FlatLightLaf.setup();
            color_principal = config.get_color_principal();
            color_secundario = config.get_color_secundario();

        }else {
            FlatDarkLaf.setup();
            color_principal = config.get_color_principal_oscuro();
            color_secundario = config.get_color_secundario_oscuro();
        }

        config = null;

        setPreferredSize(new Dimension(1200,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciar_componentes();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Llama a un método que maneje la lógica de cierre
                dispose();
            }
        });
    }

    /**
     * Esta funcion se encarga de Iniciar
     * todos los componentes necesarios para
     * el correcto funcionamiento del Programa
     * tales como imagenes, y otros archivos
     * importantes.
     */
    private void iniciar_componentes(){

        // Carga de icono
        ImageIcon icono = new ImageIcon(getClass().getResource("/Front/Recursos/Logo javarturs.jpg"));
        this.setIconImage(icono.getImage());
        // Carga de imagen principal
        ImageIcon imagen1 = new ImageIcon(getClass().getResource("/Front/Recursos/imagen_principal.png"));
        label_imagen = new JLabel(imagen1);

        // Carga de imagenes para los botones
        imagen_ciudades = new ImageIcon(getClass().getResource("/Front/Recursos/Imagen_ciudades.png"));
        imagen_ciudades = new ImageIcon(imagen_ciudades.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        imagen_vehiculos = new ImageIcon(getClass().getResource("/Front/Recursos/Imagen_vehiculos.png"));
        imagen_vehiculos = new ImageIcon(imagen_vehiculos.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        imagen_extractos = new ImageIcon(getClass().getResource("/Front/Recursos/Imagen_extractos.png"));
        imagen_extractos = new ImageIcon(imagen_extractos.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        imagen_personas = new ImageIcon(getClass().getResource("/Front/Recursos/Imagen_personas.png"));
        imagen_personas = new ImageIcon(imagen_personas.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        imagen_empleados = new ImageIcon(getClass().getResource("/Front/Recursos/Imagen_empleados.png"));
        imagen_empleados = new ImageIcon(imagen_empleados.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));


        // Inicializacion de los componentes a utilizar
        JPanel panel_principal = new JPanel(new BorderLayout());
        barra_menu = new JMenuBar();
        panel_secundario = new JPanel(null);
        panel_principal2 = new JPanel();
        
        // Configuraicon de los diferentes componentes
        try{
            read_links = Leer_link.get_links();
            id_links = Leer_link.get_set();
            configuracion_barra_menu();

        }catch(IOException ex){
            read_links = new HashMap<>();
            id_links = new HashSet<>();
            
            JOptionPane.showMessageDialog(this, "Error al cargar archivos importantes\n Error 5", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        
        configuracion_panel_secundario();
        configuracion_panel_pricipal2();

        // Agregacion al panel secundario
        panel_principal.add(panel_secundario, BorderLayout.WEST);
        panel_principal.add(panel_principal2, BorderLayout.CENTER);

        // Agregacion al JFrame
        setJMenuBar(barra_menu);
        add(panel_principal);

    }

    /**
     * Configura por defecto los diferentes botones
     * del menu que se necesitan en el programa
     * 
     * @see JMenu
     * @see JMenuItem
     */
    private void configuracion_barra_menu()throws IOException{

        // Configuracion de los diferentes componentes
        JMenu menu_1 = new JMenu("Ayuda");
        JMenu menu_2 = new JMenu("Inicio");


        // Creacion de variables necesarias para el menu
        // Para ayuda
        JMenuItem documentacion = new JMenuItem("Documentacion");
        JMenuItem contacto = new JMenuItem("Contacto");

        // Para inicio
        JMenuItem inicio = new JMenuItem("Inicio");
        inicio.addActionListener(_ ->{
            configuracion_panel_pricipal2();
        });



        // Adicionamiento
        menu_1.add(documentacion);
        menu_1.add(contacto);
        menu_2.add(inicio);


        barra_menu.add(menu_2);
        barra_menu.add(menu_1);
        for(String id: id_links){
            barra_menu.add(extraer_menu(id, read_links.get(id)));
        }
    }

    /**
     * Esta funcion se encarga, de extraer de ciertamanera,
     * los jmenu item, y asociarlos a un menu, utilizando una cola
     * con los parametros necesarios y el identificador
     * @param identificador
     * @param cola
     * @return
     */
    private JMenu extraer_menu(String identificador, Queue<String[]> cola){

        JMenu menu = new JMenu(identificador);
        JMenuItem item = null;

        while(!cola.isEmpty()){
            String aux[] = cola.dequeue();

            item = new JMenuItem(aux[0]);
            item.addActionListener(_ ->{

                try{
                    assert aux[1] != null;
                    Desktop.getDesktop().browse(new URI(aux[1]));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
                }
    
            });

            menu.add(item);
        }


        return menu; 
    }

    /**
     * Este metodo se encarga de establecer el ancho del panel secundario
     * cuando el mouse entra o sale de este, para que se vea mas amplio
     * @return
     */
    private MouseAdapter set_ancho_secundario(){
        return new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                panel_secundario.setPreferredSize(new Dimension(TAMAÑO_PANEL_SECUNDARIO_ANCHO,panel_secundario.getHeight()));
                panel_secundario.revalidate();
                panel_secundario.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panel_secundario.setPreferredSize(new Dimension(TAMAÑO_PANEL_SECUNDARIO,panel_secundario.getHeight()));
                panel_secundario.revalidate();
                panel_secundario.repaint();
            }
        };
    }

    /**
     * Este metodo se encarga de cambiar el color de fondo
     * de los botones cuando el mouse entra o sale de este
     * @param boton El boton al que se le cambiara el color de fondo
     */
    public static void back_ground_color(JButton boton){
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(new Color(0x000000));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(null);

            }
        });
    }

    public static void config_label(JLabel label, JButton boton){

        label.setBounds(
            boton.getX() + boton.getWidth() + 10,
            boton.getY() + (boton.getHeight() - 20)/2,
            100,
            20);

        label.setForeground(Color.black);

    }

    /**
     * Se encarga de configurar el panel secundario
     * de la interfaz grafica, en este caso, la parte que
     * lleva los botones en la parte lateral izquierda de
     * la interfaz grafica.
     */
    private void configuracion_panel_secundario(){

        // Creacio componentes auxiliares
        boton_extractos = new JButton();
        boton_personas = new JButton();
        boton_cuidad = new JButton();
        boton_vehiculos = new JButton();
        boton_empleados = new JButton();

        // Labels para los botones
        JLabel label_boton_extractos = new JLabel("Extractos");
        JLabel label_boton_personas = new JLabel("Personas");
        JLabel label_boton_empleados = new JLabel("Empleados");
        JLabel label_boton_ciudad = new JLabel("Ciudades");
        JLabel label_boton_vehiculos = new JLabel("Vehiculos");

        // Configuraciones del panel
        panel_secundario.setBackground(new Color(color_principal));
        panel_secundario.setPreferredSize(new Dimension(TAMAÑO_PANEL_SECUNDARIO,this.getHeight()));
        

        // Cre_ de componentes y configuracion de los mismos
        boton_cuidad.setIcon(imagen_ciudades);
        boton_cuidad.setBounds(10,10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_cuidad.addActionListener(_ ->{
            configuracion_ciudad();
        });

        boton_vehiculos.setIcon(imagen_vehiculos);
        boton_vehiculos.setBounds(10,boton_cuidad.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        
        boton_vehiculos.addActionListener(_ ->{
            configuracion_vehiculos();
        });

        boton_empleados.setIcon(imagen_empleados);
        boton_empleados.setBounds(10,boton_vehiculos.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);

        boton_empleados.addActionListener(_ ->{
            configuracion_empleados();
        });

        boton_personas.setIcon(imagen_personas);
        boton_personas.setBounds(10,boton_empleados.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);

        boton_personas.addActionListener(_ ->{
            configuracion_personas();
        });

        boton_extractos.setIcon(imagen_extractos);
        boton_extractos.setBounds(10,boton_personas.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);

        boton_extractos.addActionListener(_ ->{
            configuracion_boton_extractos();
            boton_boton_extractos_mensuales.doClick();
        });

        // Mouse listener para el ancho del panel secundario
        panel_secundario.addMouseListener(set_ancho_secundario());
        boton_cuidad.addMouseListener(set_ancho_secundario());
        boton_vehiculos.addMouseListener(set_ancho_secundario());
        boton_empleados.addMouseListener(set_ancho_secundario());
        boton_personas.addMouseListener(set_ancho_secundario());
        boton_extractos.addMouseListener(set_ancho_secundario());


        // Mouse listener para el cambio de color de fondo de botones
        back_ground_color(boton_cuidad);
        back_ground_color(boton_vehiculos);
        back_ground_color(boton_empleados);
        back_ground_color(boton_personas);
        back_ground_color(boton_extractos);

        // Configuracion de los labels
        config_label(label_boton_ciudad, boton_cuidad);
        config_label(label_boton_vehiculos, boton_vehiculos);
        config_label(label_boton_extractos, boton_extractos);
        config_label(label_boton_personas, boton_personas);
        config_label(label_boton_empleados, boton_empleados);

        
        // Adicionamiento componentes
        panel_secundario.add(boton_cuidad);
        panel_secundario.add(boton_vehiculos);
        panel_secundario.add(boton_empleados);
        panel_secundario.add(boton_personas);
        panel_secundario.add(boton_extractos);

        // Adicionamiento de labels
        panel_secundario.add(label_boton_ciudad);
        panel_secundario.add(label_boton_vehiculos);
        panel_secundario.add(label_boton_extractos);
        panel_secundario.add(label_boton_personas);
        panel_secundario.add(label_boton_empleados);

        
        panel_secundario.revalidate();
        panel_secundario.repaint();
        
    }

    private void configuracion_panel_pricipal2(){
        
        // configuracion del panel central
        panel_principal2.setLayout(new BorderLayout());
        panel_principal2.removeAll();
        panel_principal2.add(label_imagen,BorderLayout.CENTER);
        panel_principal2.revalidate();
        panel_principal2.repaint();

        panel_secundario.removeAll();
        configuracion_panel_secundario();
        

    }

    private void configuracion_ciudad(){
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        JPanel panel_izq = new JPanel(null); 
        JLabel label_principal = new JLabel("Configuracion Ciudades y Departamentos");
        JButton boton_ciudad = new JButton("Ciudades");
        JButton boton_Departamento = new JButton("Departamentos");
        JButton boton_ruta = new JButton("Rutas");

        // Configuracion componentes
        boton_ciudad.setBounds(10, 10, 120, 20);
        boton_ciudad.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_ciudad();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
            
        });
        boton_ciudad.doClick();

        boton_Departamento.setBounds(10, 40, 120, 20);
        boton_Departamento.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_departamento();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        boton_ruta.setBounds(10, 70, 120, 20);
        boton_ruta.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_ruta();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // Configuracion del panel_izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(color_secundario));
        panel_izq.add(boton_ciudad);
        panel_izq.add(boton_Departamento);
        panel_izq.add(boton_ruta);
        

        // Agregacion a panel_principal2 y set panel_principal2
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

    /**
     * Este metodo se encarga de configurar
     * las diferentes acciones y botones que se deben
     * cargar en el espacio de vehiculos
     */
    private void configuracion_vehiculos(){

        // Restableciendo el panel a utilizar
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Inicializacion del label identificador del programa
        JLabel label_principal = new JLabel("Configuracion Vehiculos");
        JPanel panel_izq = new JPanel(null);
        pan = new JPanel(null);
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.add(pan,BorderLayout.EAST);

        // Creacion de los botones utilizados para esta parte de la interfaz
        JButton tipo_vehiculo = new JButton("Tipo Vehiculo");
        JButton vehiculos = new JButton("Vehiculos");
        JButton conductores = new JButton("Cond Vehiculos");
        JButton documentos_vehiculos = new JButton("Doc Vehiculos");
        JButton vehiculos_convenio = new JButton("Veh Convenio");

        // Configuracion boton tipo_vehiculo
        tipo_vehiculo.setBounds(10, 10, 120, 20);
        tipo_vehiculo.addActionListener(_ ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_clase_vehiculo();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        // Configuracion boton vehiculos
        vehiculos.setBounds(10,40,120,20);
        vehiculos.addActionListener(_->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_vehiculos();

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        vehiculos.doClick();

        // Configuracion boton conductores
        conductores.setBounds(10,70,120,20);
        conductores.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_vehiculo_has_conductor();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });

        // Configuracion documentos vehiculos
        documentos_vehiculos.setBounds(10,100, 120, 20);
        documentos_vehiculos.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_documentos_vehiculos();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        // Configuracion vehiculos con convenio
        vehiculos_convenio.setBounds(10,130, 120, 20);
        vehiculos_convenio.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_vehiculo_convenio();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        
        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(color_secundario));
        panel_izq.add(tipo_vehiculo);
        panel_izq.add(vehiculos);
        panel_izq.add(conductores);
        panel_izq.add(documentos_vehiculos);
        panel_izq.add(vehiculos_convenio);

        // Mostrando los componentes en pantalla
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

    private void configuracion_empleados(){
        JOptionPane.showMessageDialog(this, "En este momento el portal de empleados\nno se encuentra habilitado", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void configuracion_personas(){

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Cracion de componentes
        JLabel label_principal = new JLabel("Configuracion Personas");
        JPanel panel_izq = new JPanel(null);

        JButton boton_personas = new JButton("Personas");
        JButton boton_conductores = new JButton("Conductores");

        // configuracion de botones panel izquierdo
        boton_personas.setBounds(10, 10, 120, 20);
        boton_personas.addActionListener(_ ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_persona();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        boton_personas.doClick();

        // Configuracion botones
        boton_conductores.setBounds(10,40,120,20);
        boton_conductores.addActionListener(_->{
            panel_principal2.remove(panel_informacion);
            
            panel_informacion = new Panel_conductores();

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });
        
        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(color_secundario));
        panel_izq.add(boton_personas);
        panel_izq.add(boton_conductores);

        // Adicion de componentes al panel
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);

        // Mostrando los componentes en pantalla
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

    private void configuracion_boton_extractos(){

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        JPanel panel_izq = new JPanel(null); 
        JLabel label_principal = new JLabel("Configuración boton_extractos");
        boton_boton_extractos_mensuales = new JButton("Mensuales");
        JButton boton_boton_extractos_ocasionales = new JButton("Ocasionales");
        JButton boton_contratos_mensuales = new JButton("C. Mensuales");
        JButton boton_contratos_ocasionales = new JButton("C. Ocasionales");
        JButton boton_contratante = new JButton("Contratante");

        // Configuracion componentes
        boton_boton_extractos_mensuales.setBounds(10,10,120,20);
        boton_boton_extractos_mensuales.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            panel_informacion = new Panel_extractos_mensuales();
            
            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        

        boton_boton_extractos_ocasionales.setBounds(10,40,120,20);
        boton_boton_extractos_ocasionales.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            panel_informacion = new Panel_extractos_ocasionales();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });

        boton_contratos_mensuales.setBounds(10, boton_boton_extractos_ocasionales.getY() + boton_boton_extractos_ocasionales.getHeight() + 10, 120, 20);
        boton_contratos_mensuales.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            // cambiar para ver boton_extractos mensuales
            panel_informacion = new Panel_contratos_mensuales();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint(); 
        });

        boton_contratos_ocasionales.setBounds(10, boton_contratos_mensuales.getY() + boton_contratos_mensuales.getHeight() + 10 ,120, 20);
        boton_contratos_ocasionales.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            // cambiar para ver boton_extractos ocasionales
            panel_informacion = new Panel_contratos_ocasionales();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint(); 
        });

        boton_contratante.setBounds(10, boton_contratos_ocasionales.getY() + boton_contratos_ocasionales.getHeight() + 10 ,120, 20);
        boton_contratante.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            
            // cambiar para ver ver contratatnes
            panel_informacion = new Panel_contratante();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint(); 
        });

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(color_secundario));
        panel_izq.add(boton_boton_extractos_mensuales);
        panel_izq.add(boton_boton_extractos_ocasionales);
        panel_izq.add(boton_contratos_mensuales);
        panel_izq.add(boton_contratos_ocasionales);
        panel_izq.add(boton_contratante);
        

        // Agregacion a panel_principal2 y set panel_principal2
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

}