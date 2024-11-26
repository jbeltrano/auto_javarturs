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
import Front.Panel.vehiculos.Panel_vehiculo_has_conductor;
import Front.Panel.vehiculos.Panel_vehiculos;
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
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;


public class Principal extends JFrame{
    
    private final String url;
    private JPanel panel_secundario;
    private JPanel panel_principal2;
    private JPanel panel_informacion;
    private JPanel pan;
    private JMenuBar barra_menu;
    private JLabel label_imagen;
    private JButton boton_extractos_mensuales;

    /**
     * Este es el constructor general para la clase Principal
     * se encarga de iniciar la gran mayoria de componentes y el JFrame como tal
     * @see JFrame
    */
    public Principal(String url){
        super("Javarturs");
        this.url = url;


        FlatLightLaf.setup();
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
     */
    private void iniciar_componentes(){
        // Carga los comandos necesarios

        // Carga de icono
        ImageIcon icono = new ImageIcon("src\\Front\\Recursos\\Logo javarturs.jpg");
        this.setIconImage(icono.getImage());
        // Carga de imagen principal
        ImageIcon imagen1 = new ImageIcon("src\\Front\\Recursos\\imagen_principal.png");
        label_imagen = new JLabel(imagen1);

        // Inicializacion de los componentes a utilizar
        JPanel panel_principal = new JPanel(new BorderLayout());
        barra_menu = new JMenuBar();
        panel_secundario = new JPanel(null);
        panel_principal2 = new JPanel();
        // Configuraicon de los diferentes componentes
        try{

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
     * Configura por defecto los diferentes botones
     * del menu que se necesitan en el programa
     * 
     * @see JMenu
     * @see JMenuItem
     */
    private void configuracion_barra_menu()throws IOException{
        // varibalesd especiales
        LinkedList<String> cola = Leer_link.get_runt();
        String link_porta = Leer_link.get_portafoleo();
        String link_runt_principal = cola.poll();
        String link_runt_vehiculo = cola.poll();
        String link_runt_persona = cola.poll();
        String link_runt_liquidacion = cola.poll();
        String link_runt_pagos = cola.poll();
        cola.clear();

        cola = Leer_link.get_seguridad_social();
        String link_ssocial_fosyga = cola.poll();
        String link_ssocial_aportes_en_linea = cola.poll();

        // Configuracion de los diferentes componentes
        JMenu menu_1 = new JMenu("Ayuda");
        JMenu menu_2 = new JMenu("Inicio");
        JMenu menu_3 = new JMenu("Runt");
        JMenu menu_4 = new JMenu("Portafoleo");
        JMenu menu_5 = new JMenu("Seg. Social");

        // Creacion de variables necesarias para el menu
        // Para ayuda
        JMenuItem documentacion = new JMenuItem("Documentacion");
        JMenuItem contacto = new JMenuItem("Contacto");

        // Para inicio
        JMenuItem inicio = new JMenuItem("Inicio");
        inicio.addActionListener(_ ->{
            configuracion_panel_pricipal2();
        });

        // Para Runt
        /* Para todos estos elementos lo que se esta haciendo es redirigir a un pagina web.
         * Sin embargo el navegador se abre el que tiene el sistema operativo por defecto
         */

        JMenuItem pag_principal = new JMenuItem("Pagina principal");
        pag_principal.addActionListener(_ ->{
            try{

                assert link_runt_principal != null;
                Desktop.getDesktop().browse(new URI(link_runt_principal));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
            
        });
        JMenuItem pag_vehiculos = new JMenuItem("Vehiculos");
        pag_vehiculos.addActionListener(_ ->{
            try{
                assert link_runt_vehiculo != null;
                Desktop.getDesktop().browse(new URI(link_runt_vehiculo));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
            
        });
        JMenuItem pag_personas = new JMenuItem("Personas");
        pag_personas.addActionListener(_ ->{
            try{
                assert link_runt_persona != null;
                Desktop.getDesktop().browse(new URI(link_runt_persona));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem pag_pagos = new JMenuItem("Pagos CUPL");
        pag_pagos.addActionListener(_ ->{
            try{

                assert link_runt_pagos != null;
                Desktop.getDesktop().browse(new URI(link_runt_pagos));

            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0", "Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem pag_liquidacion_web = new JMenuItem("LIQ. Web");
        pag_liquidacion_web.addActionListener(_ ->{
            try{

                assert link_runt_liquidacion != null;
                Desktop.getDesktop().browse(new URI(link_runt_liquidacion));

            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Para portafoleo
        JMenuItem pag_portafoleo = new JMenuItem("Portafoleo");
        pag_portafoleo.addActionListener(_ ->{
            try{
                Desktop.getDesktop().browse(new URI(link_porta));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JMenuItem fosyga = new JMenuItem("Fosyga");
        fosyga.addActionListener(_ ->{
            try{
                assert link_ssocial_fosyga != null;
                Desktop.getDesktop().browse(new URI(link_ssocial_fosyga));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem aportes_en_linea = new JMenuItem("Apt. en linea");
        aportes_en_linea.addActionListener(_ ->{

            try{
                assert link_ssocial_aportes_en_linea != null;
                Desktop.getDesktop().browse(new URI(link_ssocial_aportes_en_linea));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }

        });
        

        // Adicionamiento
        menu_1.add(documentacion);
        menu_1.add(contacto);

        menu_2.add(inicio);

        menu_3.add(pag_principal);
        menu_3.add(pag_vehiculos);
        menu_3.add(pag_personas);
        menu_3.add(pag_liquidacion_web);
        menu_3.add(pag_pagos);

        menu_4.add(pag_portafoleo);

        menu_5.add(fosyga);
        menu_5.add(aportes_en_linea);

        barra_menu.add(menu_2);
        barra_menu.add(menu_1);
        barra_menu.add(menu_3);
        barra_menu.add(menu_4);
        barra_menu.add(menu_5);
    }

    private void configuracion_panel_secundario(){
        // Creacio componentes auxiliares
        JButton extractos = new JButton();
        JButton base_personas = new JButton();
        JButton base_ciudad = new JButton();
        // Configuraciones del panel
        panel_secundario.setBackground(new Color(28, 96, 6));
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

            panel_informacion = new Panel_ciudad(url);

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
            
        });
        boton_ciudad.doClick();

        boton_Departamento.setBounds(10, 40, 120, 20);
        boton_Departamento.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_departamento(url);

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        boton_ruta.setBounds(10, 70, 120, 20);
        boton_ruta.addActionListener(_ ->{

            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_ruta(url);

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // Configuracion del panel_izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(52, 135, 25));
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

    private void configuracion_vehiculos(){
        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Cracion de componentes
        JLabel label_principal = new JLabel("Configuracion Vehiculos");
        JPanel panel_izq = new JPanel(null);
        pan = new JPanel(null);
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.add(pan,BorderLayout.EAST);

        JButton tipo_vehiculo = new JButton("Tipo Vehiculo");
        JButton vehiculos = new JButton("Vehiculos");
        JButton conductores = new JButton("Cond Vehiculos");
        JButton documentos_vehiculos = new JButton("Doc Vehiculos");
        JButton vehiculos_externos = new JButton("Veh Externos");

        // configuracion de botones panel izquierdo
        tipo_vehiculo.setBounds(10, 10, 120, 20);
        tipo_vehiculo.addActionListener(_ ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = new Panel_clase_vehiculo(url);

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        // Configuracion botones
        vehiculos.setBounds(10,40,120,20);
        vehiculos.addActionListener(_->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_vehiculos(url);

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        vehiculos.doClick();
        conductores.setBounds(10,70,120,20);
        conductores.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_vehiculo_has_conductor(url);

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });

        documentos_vehiculos.setBounds(10,100, 120, 20);
        documentos_vehiculos.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = new Panel_documentos_vehiculos(url);

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });

        vehiculos_externos.setBounds(10,130, 120, 20);
        vehiculos_externos.addActionListener(_ ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            //panel_informacion = ver_vehiculos_externos();

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();

        });
        
        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(52, 135, 25));
        panel_izq.add(tipo_vehiculo);
        panel_izq.add(vehiculos);
        panel_izq.add(conductores);
        panel_izq.add(documentos_vehiculos);

        // configuracion panel der

        // Adicion de componentes al panel
        //panel_principal2.add(label_principal,BorderLayout.NORTH);
        //panel_principal2.add(panel_izq,BorderLayout.WEST);

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

            panel_informacion = new Panel_persona(url);

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
            
            panel_informacion = new Panel_conductores(url);

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint();
        });
        
        // configuracion label principal
        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(52, 135, 25));
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
            
            panel_informacion = new Panel_extractos_mensuales(url);
            
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
            
            panel_informacion = new Panel_extractos_ocasionales(url);

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
            panel_informacion = new Panel_contratos_mensuales(url);

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
            panel_informacion = new Panel_contratos_ocasionales(url);

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
            panel_informacion = new Panel_contratante(url);

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.revalidate();
            panel_principal2.repaint(); 
        });

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // configuracion panel izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(52, 135, 25));
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