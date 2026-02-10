package Front;

import java.awt.Desktop;
import java.awt.Color;
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
import javax.swing.Timer;
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
    private static final int TAMAÑO_PANEL_SECUNDARIO_ANCHO = 230;
    private static final int ANIMATION_DURATION = 100; // Duración de la animación en ms
    private static final int X_BOTONES_DESPLEGABLES = 80;

    private Queue<GenericButton> cola_botones_desplegables = new Queue<>();

    private JPanel panel_secundario;
    private JPanel panel_principal2;
    private JPanel panel_informacion;
    private JPanel pan;
    private JMenuBar barra_menu;
    private JLabel label_imagen;

    private JLabel label_principal;

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
    

    // Variables para las animaciones
    private Timer animationTimerIzq;
    private Timer animationTimerSec;

    // Botones principales
    private GenericButton genericCiudadPrincipal;
    private GenericButton genericVehiculosPrincipal;
    private GenericButton genericEmpleadosPrincipal;
    private GenericButton genericPersonasPrincipal;
    private GenericButton genericExtractosPrincipal;
    
    // Botones Secundarios
    private GenericButton genericCiudad;
    private GenericButton genericDepartamento;
    private GenericButton genericRuta;

    private GenericButton genericBotonTipoVehiculo;
    private GenericButton genericBotonVehicuos;
    private GenericButton genericBotonConductores;
    private GenericButton genericBotonDocumentosVehiculos;
    private GenericButton genericBotonVehiculosConvenio;

    private GenericButton genericBotonPersonas;
    private GenericButton genericBotonPConductores;

    private GenericButton genericExtractosMensuales;
    private GenericButton genericExtractosOcasionales;
    private GenericButton genericContratosMensuales;
    private GenericButton genericContratosOcasionales;
    private GenericButton genericContratante;

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

    private void iniciarBotonesDesplegables(){
        iniciarBotonesCiudad();
        iniciarBotonesVehiuclo();
        iniciarBotonesPersonas();
        iniciarBotonesEmpleados();
        iniciarBotonesExtractos();
    }

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
    
    private void ConfigurarTema(){
    
        Leer_config config = new Leer_config();
    
        if(config.get_tema() == 0){
            FlatLightLaf.setup();
            color_principal = config.get_color_principal();

        }else {
            FlatDarkLaf.setup();
            color_principal = config.get_color_principal_oscuro();
        }

        config = null;
    }

    private void iniciar_componentes(){

        CargarImagenes();

        // Inicializacion de los componentes a utilizar
        JPanel panel_principal = new JPanel(new BorderLayout());
        barra_menu = new JMenuBar();
        panel_secundario = new JPanel(null);
        panel_principal2 = new JPanel();
        
        configuracionBarraMenu();
        
        configuracion_panel_secundario();
        configuracion_panel_pricipal2();

        iniciarBotonesDesplegables();

        // Agregacion al panel secundario
        panel_principal.add(panel_secundario, BorderLayout.WEST);
        panel_principal.add(panel_principal2, BorderLayout.CENTER);

        // Agregacion al JFrame
        setJMenuBar(barra_menu);
        add(panel_principal);

    }

    private void configuracionBarraMenu(){
        try{
            read_links = Leer_link.get_links();
            id_links = Leer_link.get_set();
            iniciarBarraMenu();

        }catch(IOException ex){
            
            JOptionPane.showMessageDialog(this, "Error al cargar archivos importantes\n Error 5", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

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
        imagen_ciudad = new ImageIcon(getClass().getResource("/Front/Recursos/Ciudades/Imagen_ciudad.png"));
        imagen_ciudad = new ImageIcon(imagen_ciudad.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_departamento = new ImageIcon(getClass().getResource("/Front/Recursos/Ciudades/Imagen_departamento.jpg"));
        imagen_departamento = new ImageIcon(imagen_departamento.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        
        imagen_ruta = new ImageIcon(getClass().getResource("/Front/Recursos/Ciudades/Imagen_ruta.png"));
        imagen_ruta = new ImageIcon(imagen_ruta.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        
        imagen_tipo_vehiculo = new ImageIcon(getClass().getResource("/Front/Recursos/Vehiculos/Imagen_tipo_vehiculo.png"));
        imagen_tipo_vehiculo = new ImageIcon(imagen_tipo_vehiculo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_vehiculo = new ImageIcon(getClass().getResource("/Front/Recursos/Vehiculos/Imagen_vehiculo.png"));
        imagen_vehiculo = new ImageIcon(imagen_vehiculo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_conductores = new ImageIcon(getClass().getResource("/Front/Recursos/Vehiculos/Imagen_conductor.png"));
        imagen_conductores = new ImageIcon(imagen_conductores.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        
        imagen_documentos_vehiculos = new ImageIcon(getClass().getResource("/Front/Recursos/Vehiculos/Imagen_documentos.png"));
        imagen_documentos_vehiculos = new ImageIcon(imagen_documentos_vehiculos.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_vehiculo_convenio = new ImageIcon(getClass().getResource("/Front/Recursos/Vehiculos/Imagen_convenio.png"));
        imagen_vehiculo_convenio = new ImageIcon(imagen_vehiculo_convenio.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_extracto_mensual = new ImageIcon(getClass().getResource("/Front/Recursos/Extractos/Imagen_extracto_mensual.png"));
        imagen_extracto_mensual = new ImageIcon(imagen_extracto_mensual.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_extracto_ocasional = new ImageIcon(getClass().getResource("/Front/Recursos/Extractos/Imagen_extracto.png"));
        imagen_extracto_ocasional = new ImageIcon(imagen_extracto_ocasional.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_contrato_mensual = new ImageIcon(getClass().getResource("/Front/Recursos/Extractos/Imagen_contrato.png"));
        imagen_contrato_mensual = new ImageIcon(imagen_contrato_mensual.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_contrato_ocasional = new ImageIcon(getClass().getResource("/Front/Recursos/Extractos/Imagen_contrato_ocasional.png"));
        imagen_contrato_ocasional = new ImageIcon(imagen_contrato_ocasional.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        
        imagen_contratante = new ImageIcon(getClass().getResource("/Front/Recursos/Extractos/Imagen_contratante.png"));
        imagen_contratante = new ImageIcon(imagen_contratante.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

    }

    private void iniciarBarraMenu()throws IOException{

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
            barra_menu.add(extraerMenu(id, read_links.get(id)));
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
    private JMenu extraerMenu(String identificador, Queue<String[]> cola){

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
            return;
        }
        
        // Usar tiempo basado en lugar de pasos fijos
        long startTime = System.currentTimeMillis();
        
        Timer timer = new Timer(16, null); // ~60 FPS (16ms por frame)
        timer.addActionListener(e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = Math.min(1.0, (double) elapsed / ANIMATION_DURATION);
            
            // Easing out cubic para una animación más suave
            progress = 1 - Math.pow(1 - progress, 3);
            
            if (elapsed >= ANIMATION_DURATION) {
                panel.setPreferredSize(new Dimension(targetWidth, panel.getHeight()));
                panel.revalidate();
                timer.stop();
            } else {
                int newWidth = currentWidth + (int) (diff * progress);
                panel.setPreferredSize(new Dimension(newWidth, panel.getHeight()));
                panel.revalidate();
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



    private void setPosicionInicialBotones(){
    
        genericCiudadPrincipal.setLocation(10, 10);
        genericVehiculosPrincipal.setLocation(10, genericCiudadPrincipal.getY() + genericCiudadPrincipal.getButton().getHeight() + 10);
        genericEmpleadosPrincipal.setLocation(10, genericVehiculosPrincipal.getY() + genericVehiculosPrincipal.getButton().getHeight() + 10);
        genericPersonasPrincipal.setLocation(10, genericEmpleadosPrincipal.getY() + genericEmpleadosPrincipal.getButton().getHeight() + 10);
        genericExtractosPrincipal.setLocation(10, genericPersonasPrincipal.getY() + genericPersonasPrincipal.getButton().getHeight() + 10);

    }

    
    private void setPosicionBotonesDependientes(GenericButton boton_inicial, GenericButton boton_final){
    
        boton_final.setLocation(boton_final.getX(), boton_inicial.getY() + boton_inicial.getButton().getHeight() + 20);
        
    }

    private void setPosicionBotonesDependientes(GenericButton botones []){
        int boton_inical = 0;
        int boton_siguiente = 1;

        while(boton_siguiente < botones.length){
            
            setPosicionBotonesDependientes(botones[boton_inical], botones[boton_siguiente]);

            boton_inical++;
            boton_siguiente++;
        }

    }


    private void InicializarBotonCiudad(){

        genericCiudadPrincipal = new GenericButton(
            () -> {
                
                eliminar_elementos_desplegables();
                configuracion_ciudad();
                genericCiudad.getButton().doClick();
                
            },
            imagen_ciudades,
            "Ciudades"
        );

        genericCiudadPrincipal.addMouseListener(set_ancho_secundario());

        panel_secundario.add(genericCiudadPrincipal.getButton());
        panel_secundario.add(genericCiudadPrincipal.getLabel());
    }

    private void InicializarBotonVehiculos(){

        genericVehiculosPrincipal = new GenericButton(
            () -> {
                eliminar_elementos_desplegables();
                configuracion_vehiculos();
                genericBotonVehicuos.getButton().doClick();
            },
            imagen_vehiculos,
            "Vehiculos"
        );

        genericVehiculosPrincipal.addMouseListener(set_ancho_secundario());

        panel_secundario.add(genericVehiculosPrincipal.getButton());
        panel_secundario.add(genericVehiculosPrincipal.getLabel());
    }

    private void InicializarBotonEmpleados(){
        genericEmpleadosPrincipal = new GenericButton(
            () -> {
                eliminar_elementos_desplegables();
                configuracion_empleados();
            },
            imagen_empleados,
            "Empleados"
        );

        genericEmpleadosPrincipal.addMouseListener(set_ancho_secundario());

        panel_secundario.add(genericEmpleadosPrincipal.getButton());
        panel_secundario.add(genericEmpleadosPrincipal.getLabel());
    }

    private void InicializarBotonPersonas(){
        genericPersonasPrincipal = new GenericButton(
            () -> {
                eliminar_elementos_desplegables();
                configuracion_personas();
                genericBotonPersonas.getButton().doClick();
            },
            imagen_personas,
            "Personas"
        );

        genericPersonasPrincipal.addMouseListener(set_ancho_secundario());

        panel_secundario.add(genericPersonasPrincipal.getButton());
        panel_secundario.add(genericPersonasPrincipal.getLabel());
    }

    private void InicializarBotonExtractos(){

        genericExtractosPrincipal = new GenericButton(
            () -> {
                eliminar_elementos_desplegables();
                configuracion_extractos();
                genericExtractosMensuales.getButton().doClick();
            },
            imagen_extractos,
            "Extractos"
        );
        
        genericExtractosPrincipal.addMouseListener(set_ancho_secundario());

        panel_secundario.add(genericExtractosPrincipal.getButton());
        panel_secundario.add(genericExtractosPrincipal.getLabel());
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

    private void encolar_elementos_desplegables(GenericButton botones []){
        for(GenericButton boton: botones){
            cola_botones_desplegables.enqueue(boton);
        }
    }

    private void eliminar_elementos_desplegables(){
    
        while(!cola_botones_desplegables.isEmpty()){
            eliminar_boton_panel(cola_botones_desplegables.peek().getButton(), panel_secundario);
            eliminar_label_panel(cola_botones_desplegables.peek().getLabel(), panel_secundario);
            cola_botones_desplegables.dequeue();
        }
        
        // Refrescar el panel después de eliminar elementos
        panel_secundario.revalidate();
        panel_secundario.repaint();
    }
    private static void eliminar_boton_panel(JButton boton, JPanel panel){
        panel.remove(boton);
    }
    private static void eliminar_label_panel(JLabel label, JPanel panel){
        panel.remove(label);
    }
    

    private void set_panel_principal2(JPanel panel){
        panel_principal2.removeAll();
        panel_principal2.add(panel, BorderLayout.CENTER);
        panel_principal2.revalidate();
        panel_principal2.repaint();
    }

    /**
     * Este metodo se encarga de configurar
     * las diferentes acciones y botones que se deben
     * cargar en el espacio de ciudades
     */
    private void configuracion_ciudad(){

        GenericButton[] botones_ciudad;
        
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        label_principal = new JLabel("Configuracion Ciudades y Departamentos");
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // Configuracion del panel_izq
        
        panel_secundario.add(genericCiudad.getButton());
        panel_secundario.add(genericDepartamento.getButton());
        panel_secundario.add(genericRuta.getButton());
        panel_secundario.add(genericCiudad.getLabel());
        panel_secundario.add(genericDepartamento.getLabel());
        panel_secundario.add(genericRuta.getLabel());
        
        botones_ciudad = new GenericButton[]{genericCiudad, genericDepartamento, genericRuta};
        
        // Prueba
        setPosicionInicialBotones();
        genericVehiculosPrincipal.setLocation(genericVehiculosPrincipal.getX(), genericRuta.getButton().getY() + genericRuta.getButton().getHeight()+20);
        
        setPosicionBotonesDependientes(new GenericButton[]{genericVehiculosPrincipal, genericEmpleadosPrincipal, genericPersonasPrincipal, genericExtractosPrincipal});
        
        encolar_elementos_desplegables(botones_ciudad);
        // fin de prueba

        // Agregacion a panel_principal2 y set panel_principal2
        panel_principal2.add(label_principal,BorderLayout.NORTH);
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
        GenericButton[] botones_vehiculos;

        // Restableciendo el panel a utilizar
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Inicializacion del label identificador del programa
        label_principal = new JLabel("Configuracion Vehiculos");
        pan = new JPanel(null);
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.add(pan,BorderLayout.EAST);

        
        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        panel_secundario.add(genericBotonTipoVehiculo.getButton());
        panel_secundario.add(genericBotonVehicuos.getButton());
        panel_secundario.add(genericBotonConductores.getButton());
        panel_secundario.add(genericBotonDocumentosVehiculos.getButton());
        panel_secundario.add(genericBotonVehiculosConvenio.getButton());
        panel_secundario.add(genericBotonTipoVehiculo.getLabel());
        panel_secundario.add(genericBotonVehicuos.getLabel());
        panel_secundario.add(genericBotonConductores.getLabel());
        panel_secundario.add(genericBotonDocumentosVehiculos.getLabel());
        panel_secundario.add(genericBotonVehiculosConvenio.getLabel());

        botones_vehiculos = new GenericButton[]{genericBotonTipoVehiculo,genericBotonVehicuos, genericBotonConductores, genericBotonDocumentosVehiculos, genericBotonVehiculosConvenio};
        // Prueba
        setPosicionInicialBotones();
        genericEmpleadosPrincipal.setLocation(genericEmpleadosPrincipal.getX(), genericBotonVehiculosConvenio.getButton().getHeight() + genericBotonVehiculosConvenio.getButton().getY() +20);
        
        setPosicionBotonesDependientes(new GenericButton[]{genericEmpleadosPrincipal, genericPersonasPrincipal, genericExtractosPrincipal});


        encolar_elementos_desplegables(botones_vehiculos);
        
        // fin de prueba

        // Mostrando los componentes en pantalla
        panel_principal2.revalidate();
        panel_principal2.repaint();

    }

    private void configuracion_empleados(){
        JOptionPane.showMessageDialog(this, "En este momento el portal de empleados\nno se encuentra habilitado", "Error", JOptionPane.ERROR_MESSAGE);
        setPosicionInicialBotones();
    }

    private void configuracion_personas(){

        GenericButton[] botones_persona;

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Cracion de componentes
        label_principal = new JLabel("Configuracion Personas");


        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);
        
        panel_secundario.add(genericBotonPersonas.getButton());
        panel_secundario.add(genericBotonPConductores.getButton());
        panel_secundario.add(genericBotonPersonas.getLabel());
        panel_secundario.add(genericBotonPConductores.getLabel());

        // Adicion de componentes al panel
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);

        botones_persona = new GenericButton[]{genericBotonPersonas, genericBotonPConductores};

        // Prueba
        setPosicionInicialBotones();
        genericExtractosPrincipal.setLocation(genericExtractosPrincipal.getX(), genericBotonPConductores.getButton().getY() + genericBotonPConductores.getButton().getHeight() +20);
        
        
        encolar_elementos_desplegables(botones_persona);
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
    private void configuracion_extractos(){

        GenericButton[] botones_extractos;

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());


        label_principal = new JLabel("Configuración boton_extractos");
        

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);


        panel_secundario.add(genericExtractosMensuales.getButton());
        panel_secundario.add(genericExtractosOcasionales.getButton());
        panel_secundario.add(genericContratosMensuales.getButton());
        panel_secundario.add(genericContratosOcasionales.getButton());
        panel_secundario.add(genericContratante.getButton());
        panel_secundario.add(genericExtractosMensuales.getLabel());
        panel_secundario.add(genericExtractosOcasionales.getLabel());
        panel_secundario.add(genericContratosMensuales.getLabel());
        panel_secundario.add(genericContratosOcasionales.getLabel());
        panel_secundario.add(genericContratante.getLabel());
        

        botones_extractos = new GenericButton[]{genericExtractosMensuales, genericExtractosOcasionales, genericContratosMensuales, genericContratosOcasionales, genericContratante};

        // Prueba
        setPosicionInicialBotones();
        
        encolar_elementos_desplegables(botones_extractos);
        
        // Agregacion a panel_principal2 y set panel_principal2
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.revalidate();
        panel_principal2.repaint();
        
        // fin de prueba

    }

    private void iniciarBotonesCiudad(){

        genericCiudad = new GenericButton(
            () -> set_panel_principal2(new Panel_ciudad()),
            imagen_ciudad,
            "Ciudades"
        );
        genericCiudad.addMouseListener(set_ancho_secundario());
        genericCiudad.setLocation(X_BOTONES_DESPLEGABLES,genericCiudadPrincipal.getY() + genericCiudadPrincipal.getButton().getHeight() + 10);

        
        genericDepartamento = new GenericButton(
            () -> set_panel_principal2(new Panel_departamento()),
            imagen_departamento,
            "Departamentos"
        );
        genericDepartamento.addMouseListener(set_ancho_secundario());
        genericDepartamento.setLocation(X_BOTONES_DESPLEGABLES,genericCiudad.getButton().getY() + genericCiudad.getButton().getHeight() + 10);


        genericRuta = new GenericButton(
            () -> set_panel_principal2(new Panel_ruta()),
            imagen_ruta,
            "Rutas"
        );
        genericRuta.addMouseListener(set_ancho_secundario());
        genericRuta.setLocation(X_BOTONES_DESPLEGABLES,genericDepartamento.getButton().getY() + genericDepartamento.getButton().getHeight() + 10);


    }

    private void iniciarBotonesVehiuclo(){
        genericBotonTipoVehiculo = new GenericButton(
            () -> set_panel_principal2(new Panel_clase_vehiculo()),
            imagen_tipo_vehiculo,
            "<html><p>Tipo<br>Vehiculo</p></html>"
        );
        genericBotonTipoVehiculo.addMouseListener(set_ancho_secundario());
        genericBotonTipoVehiculo.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericVehiculosPrincipal.getY() + genericVehiculosPrincipal.getButton().getHeight() + 10);

        genericBotonVehicuos = new GenericButton(
            () -> set_panel_principal2(new Panel_vehiculos()),
            imagen_vehiculo,
            "Vehiculos"
        );
        genericBotonVehicuos.addMouseListener(set_ancho_secundario());
        genericBotonVehicuos.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericBotonTipoVehiculo.getButton().getY() + genericBotonTipoVehiculo.getButton().getHeight() + 10
        );

        genericBotonConductores = new GenericButton(
            () -> set_panel_principal2(new Panel_vehiculo_has_conductor()),
            imagen_conductores,
            "Conductores"
        );
        genericBotonConductores.addMouseListener(set_ancho_secundario());
        genericBotonConductores.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericBotonVehicuos.getButton().getY() + genericBotonVehicuos.getButton().getHeight() + 10
        );

        genericBotonDocumentosVehiculos = new GenericButton(
            () -> set_panel_principal2(new Panel_documentos_vehiculos()),
            imagen_documentos_vehiculos,
            "<html><p>Documentos<br>Vehiculos</p></html>"
        );
        genericBotonDocumentosVehiculos.addMouseListener(set_ancho_secundario());
        genericBotonDocumentosVehiculos.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericBotonConductores.getButton().getY() + genericBotonConductores.getButton().getHeight() + 10
        );

        genericBotonVehiculosConvenio = new GenericButton(
            () -> set_panel_principal2(new Panel_vehiculo_convenio()),
            imagen_vehiculo_convenio,
            "<html><p>Vehiculos<br>Convenio</p></html>"
        );
        genericBotonVehiculosConvenio.addMouseListener(set_ancho_secundario());
        genericBotonVehiculosConvenio.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericBotonDocumentosVehiculos.getButton().getY() + genericBotonDocumentosVehiculos.getButton().getHeight() + 10
        );

    }

    private void iniciarBotonesEmpleados(){
        // Por implementar
    }

    private void iniciarBotonesPersonas(){

        genericBotonPersonas = new GenericButton(
            () -> set_panel_principal2(new Panel_persona()),
            imagen_personas,
            "Personas"
        );
        genericBotonPersonas.addMouseListener(set_ancho_secundario());
        genericBotonPersonas.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericPersonasPrincipal.getY() + genericPersonasPrincipal.getButton().getHeight() + 10
        );

        genericBotonPConductores = new GenericButton(
            () -> set_panel_principal2(new Panel_conductores()),
            imagen_conductores,
            "Conductores"
        );
        genericBotonPConductores.addMouseListener(set_ancho_secundario());
        genericBotonPConductores.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericBotonPersonas.getButton().getY() + genericBotonPersonas.getButton().getHeight() + 10
        );

    }

    private void iniciarBotonesExtractos(){
        genericExtractosMensuales = new GenericButton(
            () -> set_panel_principal2(new Panel_extractos_mensuales()),
            imagen_extracto_mensual,
            "<html><p>Extractos<br>Mensuales</p></html>"
        );
        genericExtractosMensuales.addMouseListener(set_ancho_secundario());
        genericExtractosMensuales.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericExtractosPrincipal.getY() + genericExtractosPrincipal.getButton().getHeight() + 10
        );

        genericExtractosOcasionales = new GenericButton(
            () -> set_panel_principal2(new Panel_extractos_ocasionales()),
            imagen_extracto_ocasional,
            "<html><p>Extractos<br>Ocasionales</p></html>"
        );
        genericExtractosOcasionales.addMouseListener(set_ancho_secundario());
        genericExtractosOcasionales.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericExtractosMensuales.getButton().getY() + genericExtractosMensuales.getButton().getHeight() + 10
        );

        genericContratosMensuales = new GenericButton(
            () -> set_panel_principal2(new Panel_contratos_mensuales()),
            imagen_contrato_mensual,
            "<html><p>Contratos<br>Mensuales</p></html>"
        );
        genericContratosMensuales.addMouseListener(set_ancho_secundario());
        genericContratosMensuales.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericExtractosOcasionales.getButton().getY() + genericExtractosOcasionales.getButton().getHeight() + 10
        );

        genericContratosOcasionales = new GenericButton(
            () -> set_panel_principal2(new Panel_contratos_ocasionales()),
            imagen_contrato_ocasional,
            "<html><p>Contratos<br>Ocasionales</p></html>"
        );
        genericContratosOcasionales.addMouseListener(set_ancho_secundario());
        genericContratosOcasionales.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericContratosMensuales.getButton().getY() + genericContratosMensuales.getButton().getHeight() + 10
        );

        genericContratante = new GenericButton(
            () -> set_panel_principal2(new Panel_contratante()),
            imagen_contratante,
            "Contratante"
        );
        genericContratante.addMouseListener(set_ancho_secundario());
        genericContratante.setLocation(
            X_BOTONES_DESPLEGABLES,
            genericContratosOcasionales.getButton().getY() + genericContratosOcasionales.getButton().getHeight() + 10
        );

    }
}