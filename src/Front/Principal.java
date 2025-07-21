package Front;

import java.awt.Desktop;
import java.awt.Color;
import java.awt.Font;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import Estructuras_datos.Queue;


public class Principal extends JFrame{
    
    private JPanel panel_secundario;
    private JPanel panel_principal2;
    private JPanel panel_informacion;
    private JPanel pan;
    private JMenuBar barra_menu;
    private JLabel label_imagen;
    private JButton boton_extractos_mensuales;
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
        // try{
        //     UIManager.setLookAndFeel(new FlatLightLaf());
        //     //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");


        //     }catch(Exception ex){
        //       System.out.println(ex);
        // }

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
     * Se encarga de configurar el panel secundario
     * de la interfaz grafica, en este caso, la parte que
     * lleva los botones en la parte lateral izquierda de
     * la interfaz grafica.
     */
    private void configuracion_panel_secundario(){

        // Creacio componentes auxiliares
        JButton extractos = new JButton();
        JButton base_personas = new JButton();
        JButton base_ciudad = new JButton();

        // Configuraciones del panel
        panel_secundario.setBackground(new Color(color_principal));
        panel_secundario.setPreferredSize(new Dimension(120,this.getHeight()));

        // Cre_ de componentes y configuracion de los mismos
        base_ciudad.setText("Ciudades");
        base_ciudad.setBounds(10,10,100,20);
        base_ciudad.addActionListener(_ ->{
            configuracion_ciudad();
        });

        JButton base_vehiculos = new JButton("Vehiculos");
        base_vehiculos.setBounds(10,40,100,20);
        
        base_vehiculos.addActionListener(_ ->{
            configuracion_vehiculos();
        });

        JButton base_empleados = new JButton("Empleados");
        base_empleados.setBounds(10,70,100,20);

        base_empleados.addActionListener(_ ->{
            configuracion_empleados();
        });

        base_personas.setText("Personas");
        base_personas.setBounds(10,100,100,20);

        base_personas.addActionListener(_ ->{
            configuracion_personas();
        });

        extractos.setText("Extractos");
        extractos.setBounds(10,130,100,20);

        extractos.addActionListener(_ ->{
            configuracion_extractos();
            boton_extractos_mensuales.doClick();
        });


        // Adicionamiento componentes
        panel_secundario.add(base_ciudad);
        panel_secundario.add(base_vehiculos);
        panel_secundario.add(base_empleados);
        panel_secundario.add(base_personas);
        panel_secundario.add(extractos);

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

    private void configuracion_extractos(){

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        JPanel panel_izq = new JPanel(null); 
        JLabel label_principal = new JLabel("Configuración Extractos");
        boton_extractos_mensuales = new JButton("Mensuales");
        JButton boton_extractos_ocasionales = new JButton("Ocasionales");
        JButton boton_contratos_mensuales = new JButton("C. Mensuales");
        JButton boton_contratos_ocasionales = new JButton("C. Ocasionales");
        JButton boton_contratante = new JButton("Contratante");

        // Configuracion componentes
        boton_extractos_mensuales.setBounds(10,10,120,20);
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
        

        boton_extractos_ocasionales.setBounds(10,40,120,20);
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

        boton_contratos_mensuales.setBounds(10, boton_extractos_ocasionales.getY() + boton_extractos_ocasionales.getHeight() + 10, 120, 20);
        boton_contratos_mensuales.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            // cambiar para ver extractos mensuales
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
            // cambiar para ver extractos ocasionales
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
        panel_izq.add(boton_extractos_mensuales);
        panel_izq.add(boton_extractos_ocasionales);
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