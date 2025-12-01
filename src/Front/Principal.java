package Front;

import java.awt.Desktop;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.function.Consumer;
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
import javax.swing.Timer;
import javax.swing.SwingWorker;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import Base.Coneccion_base;
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
    private static final int TAMAÑO_PANEL_SECUNDARIO_ANCHO = 200;
    private static final int TAMAÑO_BOTON = 50;
    private static final int TAMAÑO_PANEL_IZ = 70;
    private static final int TAMAÑO_PANEL_IZ_ANCHO = 170;
    private static final int ANIMATION_DURATION = 100; // Duración de la animación en ms
    private static final int ANIMATION_STEPS = 30; // Número de pasos en la animación
    private static final int X_BOTONES_DESPLEGABLES = 20;
    private static final int X_BOTON_PRINCIPAL = 10;

    private Queue<JButton> cola_botones_desplegables = new Queue<>();
    private Queue<JLabel> cola_labels_desplegables = new Queue<>();

    private JPanel panel_secundario;
    private JPanel panel_principal2;
    private JPanel panel_informacion;
    private JPanel pan;
    private JPanel panel_izq;
    private JMenuBar barra_menu;
    private JLabel label_imagen;

    private JLabel label_principal;
    
    private JButton boton_extractos_mensuales;
    private JButton boton_extractos_ocasionales;
    private JButton boton_contratos_mensuales;
    private JButton boton_contratos_ocasionales;
    private JButton boton_contratante;

    private JButton boton_vehiculos;
    private JButton boton_extractos;
    private JButton boton_personas;
    private JButton boton_ciudad;
    private JButton boton_empleados;

    private JButton boton_ciudades;
    private JButton boton_Departamento;
    private JButton boton_ruta;

    private JButton tipo_vehiculo;
    private JButton vehiculos;
    private JButton conductores;
    private JButton documentos_vehiculos;
    private JButton vehiculos_convenio;

    private JButton boton_persona;
    private JButton boton_conductores;

    private ImageIcon imagen_ciudades;
    private ImageIcon imagen_vehiculos;
    private ImageIcon imagen_extractos;
    private ImageIcon imagen_personas;
    private ImageIcon imagen_empleados;

    private ImageIcon imagen_ciudad;
    private ImageIcon imagen_departamento;
    private ImageIcon imagen_ruta;

    private ImageIcon imagen_tipo_vehiculo;
    private ImageIcon imagen_vehiculo;
    private ImageIcon imagen_conductores;
    private ImageIcon imagen_documentos_vehiculos;
    private ImageIcon imagen_vehiculo_convenio;

    private ImageIcon imagen_extracto_mensual;
    private ImageIcon imagen_extracto_ocasional;
    private ImageIcon imagen_contrato_mensual;
    private ImageIcon imagen_contrato_ocasional;
    private ImageIcon imagen_contratante;

    private HashMap<String, Queue<String[]>> read_links;
    private HashSet<String> id_links;
    private int color_principal;
    private int color_secundario;
    
    private JLabel label_boton_extractos;
    private JLabel label_boton_personas;
    private JLabel label_boton_empleados;
    private JLabel label_boton_ciudades;
    private JLabel label_boton_vehiculos;

    // Variables para las animaciones
    private Timer animationTimerIzq;
    private Timer animationTimerSec;

    /**
     * Este es el constructor general para la clase Principal
     * se encarga de iniciar la gran mayoria de componentes y el JFrame como tal
     * @see JFrame
    */
    public Principal(){
        super("Javarturs");

        ConfigurarTema();

        EventoCerrarConexion();
        setPreferredSize(new Dimension(1200,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciar_componentes();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    /**
     * Este metodo se encarga de cerrar la coneccion
     * con la base de datos el momento de cerrar la ventana
     * 
     */
    private void EventoCerrarConexion(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    Coneccion_base.get_instancia().close_coneccion();
                } catch (Exception ex) {
                    System.out.println("Error al cerrar la conexion: " + ex.getMessage());
                }
            }
        });
    }
    
    /**
     * Configura el tema de la interfaz grafica
     * segun la configuracion establecida por el usuario
     * en el archivo de configuracion.
     */
    private void ConfigurarTema(){
    
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
    }

    /**
     * Carga una imagen de forma asíncrona usando SwingWorker.
     * @param ruta La ruta del recurso de la imagen
     * @param ancho El ancho deseado para la imagen
     * @param alto El alto deseado para la imagen
     * @param callback La función que se ejecutará cuando la imagen esté lista
     */
    private void cargar_imagen_asincrona(String ruta, int ancho, int alto, Consumer<ImageIcon> callback) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {

            @Override
            protected ImageIcon doInBackground() throws Exception {
                ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
                return new ImageIcon(icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
            }

            @Override
            protected void done() {
                try {
                    ImageIcon resultado = get();
                    callback.accept(resultado);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(Principal.this, 
                        "Error al cargar la imagen: " + ruta, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    /**
     * Esta funcion se encarga de Iniciar
     * todos los componentes necesarios para
     * el correcto funcionamiento del Programa
     * tales como imagenes, y otros archivos
     * importantes.
     */
    private void iniciar_componentes(){

        CargarImagenes();

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
     * Carga las imagenes necesarias para
     * el correcto funcionamiento del programa
     * y las asigna a las variables correspondientes
     */
    private void CargarImagenes(){

        ImageIcon icono = new ImageIcon(getClass().getResource("/Front/Recursos/Logo javarturs.jpg"));
        this.setIconImage(icono.getImage());

        ImageIcon imagen1 = new ImageIcon(getClass().getResource("/Front/Recursos/imagen_principal.png"));
        label_imagen = new JLabel(imagen1);

        // Carga de imagenes para los botones principales
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

        // Carga de imagenes para los botones desplegables
        cargar_imagen_asincrona("/Front/Recursos/Ciudades/Imagen_ciudad.png", 50, 50, imagen ->{imagen_ciudad = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Ciudades/Imagen_departamento.jpg", 40, 40, imagen ->{imagen_departamento = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Ciudades/Imagen_ruta.png", 40, 40, imagen ->{imagen_ruta = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Vehiculos/Imagen_tipo_vehiculo.png", 50, 50, imagen ->{imagen_tipo_vehiculo = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Vehiculos/Imagen_vehiculo.png", 50, 50, imagen ->{imagen_vehiculo = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Vehiculos/Imagen_conductor.png", 60, 60, imagen ->{imagen_conductores = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Vehiculos/Imagen_documentos.png", 50, 50, imagen ->{imagen_documentos_vehiculos = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Vehiculos/Imagen_convenio.png", 50, 50, imagen ->{imagen_vehiculo_convenio = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Extractos/Imagen_extracto_mensual.png", 50, 50, imagen ->{imagen_extracto_mensual = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Extractos/Imagen_extracto.png", 50, 50, imagen ->{imagen_extracto_ocasional = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Extractos/Imagen_contrato.png", 50, 50, imagen ->{imagen_contrato_mensual = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Extractos/Imagen_contrato_ocasional.png", 50, 50, imagen ->{imagen_contrato_ocasional = imagen;});
        cargar_imagen_asincrona("/Front/Recursos/Extractos/Imagen_contratante.png", 50, 50, imagen ->{imagen_contratante = imagen;});

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
     * Anima el cambio de tamaño de un panel de forma suave
     * @param panel El panel a animar
     * @param targetWidth El ancho objetivo
     * @param isLeftPanel true si es panel_izq, false si es panel_secundario
     */
    private void animatePanel(JPanel panel, int targetWidth, boolean isLeftPanel) {
        // Detener animación previa si existe
        Timer currentTimer = isLeftPanel ? animationTimerIzq : animationTimerSec;
        if (currentTimer != null && currentTimer.isRunning()) {
            currentTimer.stop();
        }
        
        int currentWidth = panel.getWidth();
        int diff = targetWidth - currentWidth;
        
        // Si la diferencia es muy pequeña, cambiar directamente
        if (Math.abs(diff) < 5) {
            panel.setPreferredSize(new Dimension(targetWidth, panel.getHeight()));
            panel.revalidate();
            panel.repaint();
            return;
        }
        
        // Calcular incremento por paso
        int delay = ANIMATION_DURATION / ANIMATION_STEPS;
        double increment = (double) diff / ANIMATION_STEPS;
        
        Timer timer = new Timer(delay, null);
        final int[] step = {0};
        
        timer.addActionListener(e -> {
            step[0]++;
            if (step[0] >= ANIMATION_STEPS) {
                panel.setPreferredSize(new Dimension(targetWidth, panel.getHeight()));
                panel.revalidate();
                panel.repaint();
                timer.stop();
            } else {
                int newWidth = currentWidth + (int) (increment * step[0]);
                panel.setPreferredSize(new Dimension(newWidth, panel.getHeight()));
                panel.revalidate();
                panel.repaint();
            }
        });
        
        // Guardar referencia del timer
        if (isLeftPanel) {
            animationTimerIzq = timer;
        } else {
            animationTimerSec = timer;
        }
        
        timer.start();
    }

    /**
     * Este metodo se encarga de establecer el ancho del panel secundario
     * cuando el mouse entra o sale de este, para que se vea mas amplio
     * @param tamaño_ancho El ancho que tendra el panel secundario cuando el mouse entre
     * @return
     */
    private MouseAdapter set_ancho_panel_izq(int tamaño_ancho){
        return new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                animatePanel(panel_izq, tamaño_ancho, true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                animatePanel(panel_izq, TAMAÑO_PANEL_IZ, true);
            }
        };
    }

    private MouseAdapter set_ancho_secundario(){
        return new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                animatePanel(panel_secundario, TAMAÑO_PANEL_SECUNDARIO_ANCHO, false);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                animatePanel(panel_secundario, TAMAÑO_PANEL_SECUNDARIO, false);
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
                boton.setBackground(javax.swing.UIManager.getColor("Button.background"));

            }
        });
    }

    public static void config_label(JLabel label, JButton boton){

        label.setBounds(
            boton.getX() + boton.getWidth() + 10,
            boton.getY() + (boton.getHeight() - 20)/2,
            170,
            20);

        label.setForeground(Color.black);

    }


    private void setPosicionInicialBotones(){

        boton_ciudad.setBounds(10,10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_vehiculos.setBounds(10,boton_ciudad.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_empleados.setBounds(10,boton_vehiculos.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_personas.setBounds(10,boton_empleados.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_extractos.setBounds(10,boton_personas.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        
    }

    private void setPosicionInicialLabels(){

        config_label(label_boton_ciudades, boton_ciudad);
        config_label(label_boton_vehiculos, boton_vehiculos);
        config_label(label_boton_empleados, boton_empleados);
        config_label(label_boton_personas, boton_personas);
        config_label(label_boton_extractos, boton_extractos);

    }
    
    private void setPosicionBotonesDependientes(JButton boton_inicial, JButton boton_final){

        boton_final.setBounds(boton_final.getX(),boton_inicial.getY() + boton_inicial.getHeight() + 10,boton_final.getWidth(),boton_final.getHeight());
    }

    private void setPosicionBotonesDependientes(JButton botones []){
        int boton_inical = 0;
        int boton_siguiente = 1;

        while(boton_siguiente < botones.length){
            
            setPosicionBotonesDependientes(botones[boton_inical], botones[boton_siguiente]);

            boton_inical++;
            boton_siguiente++;
        }

    }

    private void InicializarBotonCiudad(){
        boton_ciudad = new JButton();
        label_boton_ciudades = new JLabel("Ciudades");

        boton_ciudad.setIcon(imagen_ciudades);
        boton_ciudad.addActionListener(_ ->{
            eliminar_elementos_desplegables();
            configuracion_ciudad();
        });

        boton_ciudad.addMouseListener(set_ancho_secundario());
        back_ground_color(boton_ciudad);

        panel_secundario.add(boton_ciudad);
        panel_secundario.add(label_boton_ciudades);
    }

    private void InicializarBotonVehiculos(){
        boton_vehiculos = new JButton();
        label_boton_vehiculos = new JLabel("Vehiculos");

        boton_vehiculos.setIcon(imagen_vehiculos);
        boton_vehiculos.addActionListener(_ ->{
            eliminar_elementos_desplegables();
            configuracion_vehiculos();
        });

        boton_vehiculos.addMouseListener(set_ancho_secundario());
        back_ground_color(boton_vehiculos);

        panel_secundario.add(boton_vehiculos);
        panel_secundario.add(label_boton_vehiculos);
    }

    private void InicializarBotonEmpleados(){
        boton_empleados = new JButton();
        label_boton_empleados = new JLabel("Empleados");

        boton_empleados.setIcon(imagen_empleados);
        boton_empleados.addActionListener(_ ->{
            eliminar_elementos_desplegables();
            configuracion_empleados();
        });

        boton_empleados.addMouseListener(set_ancho_secundario());
        back_ground_color(boton_empleados);

        panel_secundario.add(boton_empleados);
        panel_secundario.add(label_boton_empleados);
    }

    private void InicializarBotonPersonas(){
        boton_personas = new JButton();
        label_boton_personas = new JLabel("Personas");

        boton_personas.setIcon(imagen_personas);
        boton_personas.addActionListener(_ ->{
            eliminar_elementos_desplegables();
            configuracion_personas();
        });

        boton_personas.addMouseListener(set_ancho_secundario());
        back_ground_color(boton_personas);

        panel_secundario.add(boton_personas);
        panel_secundario.add(label_boton_personas);
    }

    private void InicializarBotonExtractos(){
        boton_extractos = new JButton();
        label_boton_extractos = new JLabel("Extractos");

        boton_extractos.setIcon(imagen_extractos);
        boton_extractos.addActionListener(_ ->{
            eliminar_elementos_desplegables();
            configuracion_boton_extractos();
            boton_extractos_mensuales.doClick();
        });

        boton_extractos.addMouseListener(set_ancho_secundario());
        back_ground_color(boton_extractos);

        panel_secundario.add(boton_extractos);
        panel_secundario.add(label_boton_extractos);
    }

    
    /**
     * Se encarga de configurar el panel secundario
     * de la interfaz grafica, en este caso, la parte que
     * lleva los botones en la parte lateral izquierda de
     * la interfaz grafica.
     */
    private void configuracion_panel_secundario(){

        InicializarBotonCiudad();
        InicializarBotonVehiculos();
        InicializarBotonEmpleados();
        InicializarBotonPersonas();
        InicializarBotonExtractos();

        setPosicionInicialBotones();
        setPosicionInicialLabels();

        // Configuraciones del panel
        panel_secundario.setBackground(new Color(color_principal));
        panel_secundario.setPreferredSize(new Dimension(TAMAÑO_PANEL_SECUNDARIO,this.getHeight()));

        // Mouse listener para el ancho del panel secundario
        panel_secundario.addMouseListener(set_ancho_secundario());

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

    private void encolar_elementos_desplegables(JButton botones [], JLabel labels []){
        for(JButton boton: botones){
            cola_botones_desplegables.enqueue(boton);
        }
        for(JLabel label: labels){
            cola_labels_desplegables.enqueue(label);
        }
    }

    private void eliminar_elementos_desplegables(){
        while(!cola_botones_desplegables.isEmpty()){
            eliminar_boton_panel(cola_botones_desplegables.peek(), panel_secundario);
            cola_botones_desplegables.dequeue();
        }

        while(!cola_labels_desplegables.isEmpty()){
            eliminar_label_panel(cola_labels_desplegables.peek(), panel_secundario);
            cola_labels_desplegables.dequeue();
        }
    }
    private static void eliminar_boton_panel(JButton boton, JPanel panel){
        panel.remove(boton);
    }
    
    private static void eliminar_label_panel(JLabel label, JPanel panel){
        panel.remove(label);
    }

    private void configuracion_ciudad(){
    
        JButton[] botones_ciudad;
        JLabel[] labels_ciudad;
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        panel_izq = new JPanel(null); 
        label_principal = new JLabel("Configuracion Ciudades y Departamentos");
        boton_ciudades = new JButton();
        boton_Departamento = new JButton();
        boton_ruta = new JButton();

        // labels para los botones
        JLabel label_ciudad = new JLabel("Ciudades");
        JLabel label_departamento = new JLabel("Departamentos");
        JLabel label_ruta = new JLabel("Rutas");


        // Configuracion componentes
        boton_ciudades.setIcon(imagen_ciudad);
        boton_ciudades.setBounds(X_BOTONES_DESPLEGABLES, boton_ciudad.getY() + boton_ciudad.getHeight() + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
        boton_ciudades.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_ciudad();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
            
        });
        boton_ciudades.doClick();

        boton_Departamento.setIcon(imagen_departamento);
        boton_Departamento.setBounds(X_BOTONES_DESPLEGABLES, boton_ciudades.getY() + TAMAÑO_BOTON + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
        boton_Departamento.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_departamento();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        boton_ruta.setIcon(imagen_ruta);
        boton_ruta.setBounds(X_BOTONES_DESPLEGABLES, boton_Departamento.getY() + TAMAÑO_BOTON + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
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

        // Configuracion de los labels
        config_label(label_ciudad, boton_ciudades);
        config_label(label_ruta, boton_ruta);
        config_label(label_departamento, boton_Departamento);

        // mouse listener para los botones
        boton_ciudades.addMouseListener(set_ancho_secundario());
        boton_Departamento.addMouseListener(set_ancho_secundario());
        boton_ruta.addMouseListener(set_ancho_secundario());
        // Configuracion del color de los botones
        back_ground_color(boton_ciudades);
        back_ground_color(boton_Departamento);
        back_ground_color(boton_ruta);

        // Configuracion del panel_izq
        
        panel_secundario.add(boton_ciudades);
        panel_secundario.add(boton_Departamento);
        panel_secundario.add(boton_ruta);
        panel_secundario.add(label_ciudad);
        panel_secundario.add(label_departamento);
        panel_secundario.add(label_ruta);
        
        botones_ciudad = new JButton[]{boton_ciudades, boton_Departamento, boton_ruta};
        labels_ciudad = new JLabel[]{label_ciudad, label_departamento, label_ruta};

        
        // Prueba
        setPosicionInicialBotones();
        boton_vehiculos.setBounds(boton_vehiculos.getBounds().x, boton_ruta.getY() + boton_ruta.getHeight() +20, boton_vehiculos.getBounds().width, boton_vehiculos.getBounds().height);

        setPosicionBotonesDependientes(new JButton[]{boton_vehiculos, boton_empleados, boton_personas, boton_extractos});
        setPosicionInicialLabels();
        
        encolar_elementos_desplegables(botones_ciudad, labels_ciudad);
        // fin de prueba

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
        setPosicionInicialBotones();

        JButton botones_vehiculos[];
        JLabel labels_vehiculos[];

        // Restableciendo el panel a utilizar
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Inicializacion del label identificador del programa
        label_principal = new JLabel("Configuracion Vehiculos");
        panel_izq = new JPanel(null);
        pan = new JPanel(null);
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.add(pan,BorderLayout.EAST);

        // Labels para los botones
        JLabel label_tipo_vehiculo = new JLabel("Tipos de Vehiculos");
        JLabel label_vehiculos = new JLabel("Vehiculos");
        JLabel label_conductores = new JLabel("Conductores de los vehiculos");
        JLabel label_documentos_vehiculos = new JLabel("Documentos de los vehiculos");
        JLabel label_vehiculos_convenio = new JLabel("Vehiculos con convenio");


        // Creacion de los botones utilizados para esta parte de la interfaz
        tipo_vehiculo = new JButton();
        vehiculos = new JButton();
        conductores = new JButton();
        documentos_vehiculos = new JButton();
        vehiculos_convenio = new JButton();

        

        // Configuracion boton tipo_vehiculo
        tipo_vehiculo.setIcon(imagen_tipo_vehiculo);
        tipo_vehiculo.setBounds(X_BOTONES_DESPLEGABLES, boton_vehiculos.getY() + boton_vehiculos.getHeight() + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
        tipo_vehiculo.addActionListener(_ ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_clase_vehiculo();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        // Configuracion boton vehiculos
        vehiculos.setIcon(imagen_vehiculo);
        vehiculos.setBounds(X_BOTONES_DESPLEGABLES,tipo_vehiculo.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
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
        conductores.setIcon(imagen_conductores);
        conductores.setBounds(X_BOTONES_DESPLEGABLES,vehiculos.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        conductores.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_vehiculo_has_conductor();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });

        // Configuracion documentos vehiculos
        documentos_vehiculos.setIcon(imagen_documentos_vehiculos);
        documentos_vehiculos.setBounds(X_BOTONES_DESPLEGABLES,conductores.getY() + TAMAÑO_BOTON + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
        documentos_vehiculos.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_documentos_vehiculos();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        // Configuracion vehiculos con convenio
        vehiculos_convenio.setIcon(imagen_vehiculo_convenio);
        vehiculos_convenio.setBounds(X_BOTONES_DESPLEGABLES,documentos_vehiculos.getY() + TAMAÑO_BOTON + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
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

        // Mouse listener para los botones
        tipo_vehiculo.addMouseListener(set_ancho_secundario());
        vehiculos.addMouseListener(set_ancho_secundario());
        conductores.addMouseListener(set_ancho_secundario());
        documentos_vehiculos.addMouseListener(set_ancho_secundario());
        vehiculos_convenio.addMouseListener(set_ancho_secundario());

        // Configuracion del background de los botones
        back_ground_color(tipo_vehiculo);
        back_ground_color(vehiculos);
        back_ground_color(conductores);
        back_ground_color(documentos_vehiculos);
        back_ground_color(vehiculos_convenio);

        // Configuracion de los labels
        config_label(label_tipo_vehiculo, tipo_vehiculo);
        config_label(label_vehiculos, vehiculos);
        config_label(label_conductores, conductores);
        config_label(label_documentos_vehiculos, documentos_vehiculos);
        config_label(label_vehiculos_convenio, vehiculos_convenio);


        panel_secundario.add(tipo_vehiculo);
        panel_secundario.add(vehiculos);
        panel_secundario.add(conductores);
        panel_secundario.add(documentos_vehiculos);
        panel_secundario.add(vehiculos_convenio);
        panel_secundario.add(label_tipo_vehiculo);
        panel_secundario.add(label_vehiculos);
        panel_secundario.add(label_conductores);
        panel_secundario.add(label_documentos_vehiculos);
        panel_secundario.add(label_vehiculos_convenio);

        botones_vehiculos = new JButton[]{tipo_vehiculo, vehiculos, conductores, documentos_vehiculos, vehiculos_convenio};
        labels_vehiculos = new JLabel[]{label_tipo_vehiculo, label_vehiculos, label_conductores, label_documentos_vehiculos, label_vehiculos_convenio};
        // Prueba
        
        boton_empleados.setBounds(boton_empleados.getBounds().x, vehiculos_convenio.getHeight() + vehiculos_convenio.getY() +20, boton_empleados.getBounds().width, boton_empleados.getBounds().height);
        
        setPosicionBotonesDependientes(new JButton[]{boton_empleados, boton_personas, boton_extractos});
        setPosicionInicialLabels();

        encolar_elementos_desplegables(botones_vehiculos, labels_vehiculos);
        
        // fin de prueba

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
        label_principal = new JLabel("Configuracion Personas");
        panel_izq = new JPanel(null);

        boton_persona = new JButton();
        boton_conductores = new JButton();

        // Labels para los botones
        JLabel label_persona = new JLabel("Personas y Empresas");
        JLabel label_conductores = new JLabel("Conductores");

        // configuracion de botones panel izquierdo
        boton_persona.setBounds(10, 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
        boton_persona.setIcon(imagen_personas);
        boton_persona.addMouseListener(set_ancho_panel_izq(200));
        boton_persona.addActionListener(_ ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_persona();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        boton_persona.doClick();

        // Configuracion botones
        boton_conductores.setBounds(10,boton_persona.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_conductores.setIcon(imagen_conductores);
        boton_conductores.addMouseListener(set_ancho_panel_izq(200));
        boton_conductores.addActionListener(_->{
            panel_principal2.remove(panel_informacion);
            
            panel_informacion = new Panel_conductores();

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });
        
        // configuracion de los labels
        config_label(label_persona, boton_persona);
        config_label(label_conductores, boton_conductores);

        // configuracion del color de fondo de los botones
        back_ground_color(boton_persona);
        back_ground_color(boton_conductores);

        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(TAMAÑO_PANEL_IZ,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(color_secundario));
        panel_izq.addMouseListener(set_ancho_panel_izq(200));
        
        panel_izq.add(boton_persona);
        panel_izq.add(boton_conductores);
        panel_izq.add(label_persona);
        panel_izq.add(label_conductores);

        // Adicion de componentes al panel
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);

         // Prueba
        setPosicionInicialBotones();
        boton_extractos.setBounds(boton_extractos.getBounds().x, boton_extractos.getY() + boton_conductores.getY() +10, boton_extractos.getBounds().width, boton_extractos.getBounds().height);
        
        
        setPosicionInicialLabels();

        
        // fin de prueba

        // Mostrando los componentes en pantalla
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

/**
     * Este metodo se encarga de configurar
     * los diferentes botones y acciones que se deben
     * realizar en el espacio de extractos
     */
    private void configuracion_boton_extractos(){

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        panel_izq = new JPanel(null); 
        label_principal = new JLabel("Configuración boton_extractos");

        // Labeles para los botones
        JLabel label_extractos_mensuales = new JLabel("Extractos Mensuales");
        JLabel label_extractos_ocasionales = new JLabel("Extractos Ocasionales");
        JLabel label_contratos_mensuales = new JLabel("Contratos Mensuales");
        JLabel label_contratos_ocasionales = new JLabel("Contratos Ocasionales");
        JLabel label_contratante = new JLabel("Contratante");


        boton_extractos_mensuales = new JButton();
        boton_extractos_ocasionales = new JButton();
        boton_contratos_mensuales = new JButton();
        boton_contratos_ocasionales = new JButton();
        boton_contratante = new JButton();

        // Configuracion componentes
        boton_extractos_mensuales.setIcon(imagen_extracto_mensual);
        boton_extractos_mensuales.setBounds(10,10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_extractos_mensuales.addMouseListener(set_ancho_panel_izq(240));
        boton_extractos_mensuales.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            panel_informacion = new Panel_extractos_mensuales();
            
            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        
        boton_extractos_ocasionales.setIcon(imagen_extracto_ocasional);
        boton_extractos_ocasionales.setBounds(10,boton_extractos_mensuales.getY() + TAMAÑO_BOTON + 10,TAMAÑO_BOTON,TAMAÑO_BOTON);
        boton_extractos_ocasionales.addMouseListener(set_ancho_panel_izq(240));
        boton_extractos_ocasionales.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            panel_informacion = new Panel_extractos_ocasionales();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });

        boton_contratos_mensuales.setIcon(imagen_contrato_mensual);
        boton_contratos_mensuales.setBounds(10, boton_extractos_ocasionales.getY() + boton_extractos_ocasionales.getHeight() + 10, TAMAÑO_BOTON, TAMAÑO_BOTON);
        boton_contratos_mensuales.addMouseListener(set_ancho_panel_izq(240));
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

        boton_contratos_ocasionales.setIcon(imagen_contrato_ocasional);
        boton_contratos_ocasionales.setBounds(10, boton_contratos_mensuales.getY() + boton_contratos_mensuales.getHeight() + 10 ,TAMAÑO_BOTON, TAMAÑO_BOTON);
        boton_contratos_ocasionales.addMouseListener(set_ancho_panel_izq(240));
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

        boton_contratante.setIcon(imagen_contratante);
        boton_contratante.setBounds(10, boton_contratos_ocasionales.getY() + boton_contratos_ocasionales.getHeight() + 10 ,TAMAÑO_BOTON, TAMAÑO_BOTON);
        boton_contratante.addMouseListener(set_ancho_panel_izq(240));
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

        // Configuracion background de los botones
        back_ground_color(boton_extractos_mensuales);
        back_ground_color(boton_extractos_ocasionales);
        back_ground_color(boton_contratos_mensuales);
        back_ground_color(boton_contratos_ocasionales);
        back_ground_color(boton_contratante);

        // Configuracion de los labels
        config_label(label_extractos_mensuales, boton_extractos_mensuales);
        config_label(label_extractos_ocasionales, boton_extractos_ocasionales);
        config_label(label_contratos_mensuales, boton_contratos_mensuales);
        config_label(label_contratos_ocasionales, boton_contratos_ocasionales);
        config_label(label_contratante, boton_contratante);
        
        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(TAMAÑO_PANEL_IZ,panel_principal2.getHeight()));
        panel_izq.addMouseListener(set_ancho_panel_izq(240));
        panel_izq.setBackground(new Color(color_secundario));
        panel_izq.add(boton_extractos_mensuales);
        panel_izq.add(boton_extractos_ocasionales);
        panel_izq.add(boton_contratos_mensuales);
        panel_izq.add(boton_contratos_ocasionales);
        panel_izq.add(boton_contratante);
        panel_izq.add(label_extractos_mensuales);
        panel_izq.add(label_extractos_ocasionales);
        panel_izq.add(label_contratos_mensuales);
        panel_izq.add(label_contratos_ocasionales);
        panel_izq.add(label_contratante);
        

        // Agregacion a panel_principal2 y set panel_principal2
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

}