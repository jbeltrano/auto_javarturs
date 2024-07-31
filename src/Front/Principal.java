package Front;

import Base.Base;
import Front.Ciudades_departamentos.Actualizar_ciudad;
import Front.Ciudades_departamentos.Insertar_ciudad;
import Front.Extractos.Actualizar_contratante;
import Front.Extractos.Actualizar_contrato_ocasional;
import Front.Extractos.Actualizar_extracto_mensual;
import Front.Extractos.Actualizar_extracto_ocasional;
import Front.Extractos.Actualizar_todo_ext_mensual;
import Front.Extractos.Insertar_contratante;
import Front.Extractos.Insertar_contrato_mensual;
import Front.Extractos.Insertar_contrato_ocasional;
import Front.Extractos.Insertar_extracto_mensual;
import Front.Extractos.Insertar_extracto_ocasional;
import Front.Personas.Actualizar_conductor;
import Front.Personas.Actualizar_peronas;
import Front.Personas.Insertar_conductor;
import Front.Personas.Insertar_persona;
import Front.Vehiculos.Actualizar_documento_vehiculo;
import Front.Vehiculos.Actualizar_tipo_vehiculo;
import Front.Vehiculos.Actualizar_vehiculos;
import Front.Vehiculos.Insertar_documento_vehiculo;
import Front.Vehiculos.Insertar_tipo_vehiculo;
import Front.Vehiculos.Insertar_vehiculo_conductor;
import Front.Vehiculos.Insertar_vehiculos;
import Utilidades.Generar_extractos;
import Utilidades.Key_adapter;
import Utilidades.Leer_link;
import Utilidades.Modelo_tabla;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.LinkedList;

public class Principal extends JFrame{
    
    private String url;
    private JPanel panel_principal;
    private JPanel panel_secundario;
    private JPanel panel_principal2;
    private JPanel panel_informacion;
    private JPanel panel_busqueda;
    private JLabel label_busqueda;
    private JPanel pan;
    private JTextField text_busqueda;
    private JMenu menu_1;
    private JMenu menu_2;
    private JMenu menu_3;
    private JMenu menu_4;
    private JMenuBar barra_menu;
    private ImageIcon imagen1;
    private ImageIcon icono;
    private JLabel limagen1;
    private JTable tabla;
    private JPopupMenu pop_menu;
    private JMenuItem item_adicionar;
    private JMenuItem item_actualizar;
    private JMenuItem item_plantilla;
    private JMenuItem item_exportar;
    private JMenuItem item_eliminar;
    private JMenuItem item_actualizar_todos;
    private JMenuItem item_exportar_todos;
    private JButton base_vehiculos;
    private JButton base_empleados;
    private JButton tipo_vehiculo;
    private JButton vehiculos;
    private JButton conductores;
    private JButton documentos_vehiculos;
    private JButton vehiculos_externos;
    private JButton boton_conductores;
    private JButton boton_personas;
    private Base base;
    private JButton boton_ciudad;
    private JButton boton_Departamento;
    private JButton boton_extractos_ocasionales; 
    private JButton boton_extractos_mensuales;
    private JButton boton_contratos_mensuales;
    private JButton boton_contratos_ocasionales;
    private JButton boton_contratante;
    private static final Runtime runtime = Runtime.getRuntime();
    private static final String comando[] = {System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\a.exe",
                                            System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\ConvertirPdf.ps1",
                                            System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Mensuales"};
    private static final String comando2[] = {System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\a.exe",
                                            System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\ConvertirPdf2.ps1",
                                            System.getProperty("user.home") + "\\Desktop\\Extractos\\Contratos Ocasionales"};
    
    /** 
     * Este es el constructor general para la clase Principal
     * se encarga de iniciar la gran mayoria de componentes y el JFrame como tal
     * @see JFrame
    */
    public Principal(String url){
        super("Javarturs");
        this.url = url;

        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch(Exception ex){
            System.out.println(ex);
        }

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
        icono = new ImageIcon("src\\Front\\Recursos\\Logo javarturs.jpg");
        this.setIconImage(icono.getImage());
        // Carga de imagen principal
        imagen1 = new ImageIcon("src\\Front\\Recursos\\imagen_principal.png");
        limagen1 = new JLabel(imagen1);

        // Inicializacion de los componentes a utilizar
        panel_principal = new JPanel(new BorderLayout());
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

        // Configuracion de los diferentes componentes
        menu_1 = new JMenu("Ayuda");
        menu_2 = new JMenu("Inicio");
        menu_3 = new JMenu("Runt");
        menu_4 = new JMenu("Portafoleo");
        
        

        // Creacion de variables necesarias para el menu
        // Para ayuda
        JMenuItem documentacion = new JMenuItem("Documentacion");
        JMenuItem contacto = new JMenuItem("Contacto");

        // Para inicio
        JMenuItem inicio = new JMenuItem("Inicio");
        inicio.addActionListener(accion ->{
            configuracion_panel_pricipal2();
        });

        // Para Runt
        /* Para todos estos elementos lo que se esta haciendo es redirigir a un pagina web.
         * Sin embargo el navegador se abre el que tiene el sistema operativo por defecto
         */

        JMenuItem pag_principal = new JMenuItem("Pagina principal");
        pag_principal.addActionListener(accion ->{
            try{
                
                Desktop.getDesktop().browse(new URI(link_runt_principal));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
            
        });
        JMenuItem pag_vehiculos = new JMenuItem("Vehiculos");
        pag_vehiculos.addActionListener(accion ->{
            try{
                Desktop.getDesktop().browse(new URI(link_runt_vehiculo));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
            
        });
        JMenuItem pag_personas = new JMenuItem("Personas");
        pag_personas.addActionListener(accon ->{
            try{
                Desktop.getDesktop().browse(new URI(link_runt_persona));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem pag_pagos = new JMenuItem("Pagos CUPL");
        pag_pagos.addActionListener(accion ->{
            try{

                Desktop.getDesktop().browse(new URI(link_runt_pagos));

            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0", "Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem pag_liquidacion_web = new JMenuItem("LIQ. Web");
        pag_liquidacion_web.addActionListener(accion ->{
            try{

                Desktop.getDesktop().browse(new URI(link_runt_liquidacion));

            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No fue posible abrir el navegador\nError 0","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Para portafoleo
        JMenuItem pag_portafoleo = new JMenuItem("Portafoleo");
        pag_portafoleo.addActionListener(accion ->{
            try{
                Desktop.getDesktop().browse(new URI(link_porta));
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

        barra_menu.add(menu_2);
        barra_menu.add(menu_1);
        barra_menu.add(menu_3);
        barra_menu.add(menu_4);
    }

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
    }

    private void configuracion_panel_secundario(){
        // Creacio componentes auxiliares
        JButton extractos = new JButton();
        JButton base_personas = new JButton();
        JButton base_ciudad = new JButton();
        // Configuraciones del panel
        panel_secundario.setBackground(new Color(28, 96, 6));
        panel_secundario.setPreferredSize(new Dimension(120,this.getHeight()));

        // Creaccion de componentes y configuracion de los mismos
        base_ciudad.setText("Ciudades");
        base_ciudad.setBounds(10,10,100,20);
        base_ciudad.addActionListener(accion ->{
            configuracion_ciudad();
        });

        base_vehiculos = new JButton("Vehiculos");
        base_vehiculos.setBounds(10,40,100,20);
        
        base_vehiculos.addActionListener(accion ->{
            configuracion_vehiculos();
        });

        base_empleados = new JButton("Empleados");
        base_empleados.setBounds(10,70,100,20);

        base_empleados.addActionListener(accion ->{
            configuracion_empleados();
        });

        base_personas.setText("Personas");
        base_personas.setBounds(10,100,100,20);

        base_personas.addActionListener(accion ->{
            configuracion_personas();
        });

        extractos.setText("Extractos");
        extractos.setBounds(10,130,100,20);

        extractos.addActionListener(accion ->{
            configuracion_extractos();
            boton_extractos_mensuales.doClick();
        });


        // Adicionamiento componentes
        panel_secundario.add(base_ciudad);
        panel_secundario.add(base_vehiculos);
        panel_secundario.add(base_empleados);
        panel_secundario.add(base_personas);
        panel_secundario.add(extractos);

        panel_secundario.repaint();
        panel_secundario.revalidate();
        
    }

    private void configuracion_panel_pricipal2(){
        
        // configuracion del panel central
        panel_principal2.setLayout(new BorderLayout());
        panel_principal2.removeAll();
        panel_principal2.add(limagen1,BorderLayout.CENTER);
        panel_principal2.repaint();
        panel_principal2.revalidate();

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
        boton_ciudad = new JButton("Ciudades");
        boton_Departamento = new JButton("Departamentos");

        // Configuracion componentes
        boton_ciudad.setBounds(10, 10, 120, 20);
        boton_ciudad.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);

            panel_informacion = ver_ciudad();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();
        });
        boton_ciudad.doClick();

        boton_Departamento.setBounds(10, 40, 120, 20);
        boton_Departamento.addActionListener(accion ->{

            panel_principal2.remove(panel_informacion);

            panel_informacion = ver_departamento();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();

        });

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // Configuracion del panel_izq
        panel_izq.setPreferredSize(new Dimension(140,panel_principal2.getHeight()));
        panel_izq.setBackground(new Color(52, 135, 25));
        panel_izq.add(boton_ciudad);
        panel_izq.add(boton_Departamento);
        

        // Agregacion a panel_principal2 y set panel_principal2
        panel_principal2.add(label_principal,BorderLayout.NORTH);
        panel_principal2.add(panel_izq,BorderLayout.WEST);
        panel_principal2.add(panel_informacion, BorderLayout.CENTER);
        panel_principal2.repaint();
        panel_principal2.revalidate();

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
        
        tipo_vehiculo = new JButton("Tipo Vehiculo");
        vehiculos = new JButton("Vehiculos");
        conductores = new JButton("Cond Vehiculos");
        documentos_vehiculos = new JButton("Doc Vehiculos");
        vehiculos_externos = new JButton("Veh Externos");

        // configuracion de botones panel izquierdo
        tipo_vehiculo.setBounds(10, 10, 120, 20);
        tipo_vehiculo.addActionListener(accion ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = ver_clase_vehiculo();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();

        });

        // Configuracion botones
        vehiculos.setBounds(10,40,120,20);
        vehiculos.addActionListener(accoin->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = ver_vehiculo();
            
            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{
                    
                    new Insertar_vehiculos(this, url, "").setVisible(true);
                    panel_principal2.remove(pan);
                    vehiculos.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal.revalidate();
        });
        vehiculos.doClick();
        conductores.setBounds(10,70,120,20);
        conductores.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = ver_vehiculo_has_conductor();
            

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{

                    new Insertar_vehiculo_conductor(this, url, "");
                    panel_principal2.remove(pan);
                    conductores.doClick();

                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();
        });

        documentos_vehiculos.setBounds(10,100, 120, 20);
        documentos_vehiculos.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            panel_informacion = ver_documentos_vehiculos();

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{

                    new Insertar_documento_vehiculo(this, url, "");
                    panel_principal2.remove(pan);
                    conductores.doClick();

                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();

        });

        vehiculos_externos.setBounds(10,130, 120, 20);
        vehiculos_externos.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);
            panel_principal2.remove(pan);
            //panel_informacion = ver_vehiculos_externos();

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{

                    //new Insertar_vehiculo_externo(this, url, "");
                    panel_principal2.remove(pan);
                    conductores.doClick();

                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();

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
        panel_principal2.repaint();
        panel_principal2.revalidate();

    }

    private void configuracion_empleados(){
        System.out.println("inicio empleados");
    }

    private void configuracion_personas(){

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Cracion de componentes
        JLabel label_principal = new JLabel("Configuracion Personas");
        JPanel panel_izq = new JPanel(null);
        
        boton_personas = new JButton("Personas");
        boton_conductores = new JButton("Conductores");

        // configuracion de botones panel izquierdo
        boton_personas.setBounds(10, 10, 120, 20);
        boton_personas.addActionListener(accion ->{
            
            panel_principal2.remove(panel_informacion);

            panel_informacion = ver_personas();

           // Agregacion al panel
            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();

        });
        boton_personas.doClick();

        // Configuracion botones
        boton_conductores.setBounds(10,40,120,20);
        boton_conductores.addActionListener(accoin->{
            panel_principal2.remove(panel_informacion);
            
            panel_informacion = ver_conductores();
            
            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                JPanel pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{
                    
                    new Insertar_conductor(this, url).setVisible(true);
                    boton_conductores.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion,BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal.revalidate();
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
        panel_principal2.repaint();
        panel_principal2.revalidate();

    }

    private void configuracion_extractos(){

        panel_informacion = new JPanel();
        panel_principal2.removeAll();
        panel_principal2.setLayout(new BorderLayout());

        // Creacion de componentes auxiliares
        JPanel panel_izq = new JPanel(null); 
        JLabel label_principal = new JLabel("Configuración Extractos");
        boton_extractos_mensuales = new JButton("Mensuales");
        boton_extractos_ocasionales = new JButton("Ocasionales");
        boton_contratos_mensuales = new JButton("C. Mensuales");
        boton_contratos_ocasionales = new JButton("C. Ocasionales");
        boton_contratante = new JButton("Contratante");

        // Configuracion componentes
        boton_extractos_mensuales.setBounds(10,10,120,20);
        boton_extractos_mensuales.addActionListener(accion ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            panel_informacion = ver_extractos_mensuales();
            
            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{

                    new Insertar_extracto_mensual(this, url).setVisible(true);
                    panel_principal2.remove(pan);
                    boton_extractos_mensuales.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }
            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();

        });
        

        boton_extractos_ocasionales.setBounds(10,40,120,20);
        boton_extractos_ocasionales.addActionListener(accion ->{

            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            panel_informacion = ver_extractos_ocasionales();

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{

                    new Insertar_extracto_ocasional(this, url).setVisible(true);
                    panel_principal2.remove(pan);
                    boton_extractos_ocasionales.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate();
        });

        boton_contratos_mensuales.setBounds(10, boton_extractos_ocasionales.getY() + boton_extractos_ocasionales.getHeight() + 10, 120, 20);
        boton_contratos_mensuales.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            // cambiar para ver extractos mensuales
            panel_informacion = ver_contratos_mensuales();

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{
                    
                    new Insertar_contrato_mensual(this, url).setVisible(true);
                    panel_principal2.remove(pan);
                    boton_contratos_mensuales.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate(); 
        });

        boton_contratos_ocasionales.setBounds(10, boton_contratos_mensuales.getY() + boton_contratos_mensuales.getHeight() + 10 ,120, 20);
        boton_contratos_ocasionales.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            // cambiar para ver extractos ocasionales
            panel_informacion = ver_contratos_ocasionales();

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{
                    
                    new Insertar_contrato_ocasional(this, url).setVisible(true);
                    panel_principal2.remove(pan);
                    boton_contratos_ocasionales.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate(); 
        });

        boton_contratante.setBounds(10, boton_contratos_ocasionales.getY() + boton_contratos_ocasionales.getHeight() + 10 ,120, 20);
        boton_contratante.addActionListener(accion ->{
            panel_principal2.remove(panel_informacion);
            if(panel_principal2.getComponentCount() > 2){
                panel_principal2.remove(pan);
            }
            
            
            // cambiar para ver ver contratatnes
            panel_informacion = ver_contratante();

            if(tabla.getRowCount() == 0 ){
                JButton boton_auxiliar = new JButton("Agregar");
                pan = new JPanel(null);
                boton_auxiliar.setBounds(10,10,100,20);
                boton_auxiliar.addActionListener(ac ->{
                    
                    new Insertar_contratante(this, url).setVisible(true);
                    panel_principal2.remove(pan);
                    boton_contratante.doClick();
                });
                pan.add(boton_auxiliar);
                pan.setPreferredSize(new Dimension(120,40));
                panel_principal2.add(pan,BorderLayout.EAST);
            }

            panel_principal2.add(panel_informacion, BorderLayout.CENTER);
            panel_principal2.repaint();
            panel_principal2.revalidate(); 
        });

        label_principal.setFont(new Font("britannic bold", Font.BOLD, 20));
        label_principal.setHorizontalAlignment(JLabel.CENTER);

        // Configuracion del panel_izq
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
        panel_principal2.repaint();
        panel_principal2.revalidate();

    }

    // Metodos relacionados con los vehiculos
    
    private JPanel ver_documentos_vehiculos(){

        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;

        configuracion_panel_busqueda();
        config_pop_menu();

        base = new Base(url);
        try{
            datos = base.consultar_documentos("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            this.dispose();
        }
        base.close();

        // Implementacion para que la tabla cambie de colores dependiendo el valor que tiene la celda
        tabla = Modelo_tabla.set_tabla_documentos_vehiculos(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        
        

        item_adicionar.addActionListener(accion ->{
            Insertar_documento_vehiculo doc_vehiculo = new Insertar_documento_vehiculo(this, url, "");
            doc_vehiculo.setVisible(true);

            documentos_vehiculos.doClick();
        });
        
        item_actualizar.addActionListener(accion ->{
            int number = tabla.getSelectedRow();
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);
            Actualizar_documento_vehiculo doc_vehiculo = new Actualizar_documento_vehiculo(this, url, placa_vehiculo);
            doc_vehiculo.setVisible(true);

            documentos_vehiculos.doClick();
        });
        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);


            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el registro\n"+ placa_vehiculo +"|"+tabla.getValueAt(number, 3), "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_documento(placa_vehiculo);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }

                base.close();
                documentos_vehiculos.doClick();
            }
                  
        });

        Component padre = this;
        text_busqueda.addKeyListener(new Key_adapter() {
           
            @Override
            public void accion(){
                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_documentos_vehiculos(base.consultar_documentos(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla);
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        return panel;
    }
    
    
    private JPanel ver_vehiculo_has_conductor(){

        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;

        config_pop_menu();
        pop_menu.remove(1);
        base = new Base(url);
        try{
            datos = base.consultar_conductor_has_vehiculo();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();
        
        tabla = Modelo_tabla.set_tabla_vehiculo_has_conductor(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        item_adicionar.addActionListener(accion ->{
            new Insertar_vehiculo_conductor(this, url, "");
            
            base = new Base(url);
            try{

                tabla = Modelo_tabla.set_tabla_vehiculo_has_conductor(base.consultar_conductor_has_vehiculo(text_busqueda.getText()));
                tabla.setComponentPopupMenu(pop_menu);
                scroll.setViewportView(tabla);
        

            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
            base.close();
        });
        
        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String conductor_id = "" + tabla.getValueAt(number, 2);
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);


            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el registro\n"+ placa_vehiculo +"|"+tabla.getValueAt(number, 3), "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_vehiculo_has_conductor(conductor_id,placa_vehiculo);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");

                    tabla = Modelo_tabla.set_tabla_vehiculo_has_conductor(base.consultar_conductor_has_vehiculo(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla);
        


                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }

                base.close();

            }
                  
        });

        JFrame padre = this;
        
        text_busqueda.addKeyListener(new Key_adapter() {
            @Override
            public void accion(){
                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_vehiculo_has_conductor(base.consultar_conductor_has_vehiculo(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla);
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel ver_clase_vehiculo(){

        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        DefaultTableModel modelo;
        String[][] datos = null;
        TableColumnModel cl_model;
        
        config_pop_menu();

        base = new Base(url);
        try{
            datos = base.consultar_clase_vehiculo();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }

        base.close();

        modelo = Modelo_tabla.set_modelo_tablas(datos);
        tabla = new JTable(modelo);
        tabla.setComponentPopupMenu(pop_menu);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setComponentPopupMenu(pop_menu);
        tabla.getTableHeader().setReorderingAllowed(false); 
        tabla.setCellSelectionEnabled(true);
        Modelo_tabla.add_mouse_listener(tabla);
        
        
        cl_model = tabla.getColumnModel();
        cl_model.getColumn(0).setPreferredWidth(35);
        cl_model.getColumn(1).setPreferredWidth(200);

        item_actualizar.addActionListener(accion->{
            
            int numero = tabla.getSelectedRow();
            new Actualizar_tipo_vehiculo(this, url, ""+tabla.getValueAt(numero, 0));


            tipo_vehiculo.doClick();

        });
        item_adicionar.addActionListener(accion ->{
            new Insertar_tipo_vehiculo(this, url, "");
            

            tipo_vehiculo.doClick();

        });
        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el item\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_clase_vehiculo(Integer.parseInt(valor));
                    JOptionPane.showMessageDialog(this, "Item eliminado correctamente");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }

                base.close();
                tipo_vehiculo.doClick();
            }
                  
        });

        scroll.setViewportView(tabla);
        
        panel.add(scroll,BorderLayout.CENTER);
        
        return panel;
    }
    
    
    private JPanel ver_vehiculo(){
        // inicializacion de componentes
        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu();

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_vehiculo(true);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_vehiculo(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        
        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            new Actualizar_vehiculos(this, url, (String)tabla.getValueAt(select_row, 0));
            vehiculos.doClick();

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_vehiculos(this, url, "").setVisible(true);
            vehiculos.doClick();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el vehiculo:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_vehiculo(valor);
                    JOptionPane.showMessageDialog(this, "Vehiculo eliminado correctamente");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                vehiculos.doClick();
            }
                  
        });

        JFrame padre = this;

        text_busqueda.addKeyListener(new Key_adapter() {
            @Override
            public void accion(){
                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_vehiculo(base.consultar_vehiculo(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        
        return panel;
    }
    

    /**
     * Esta funcion se encarga de retornar un JPanel
     * con el panel configurado correctamente para mostrar
     * la tabla ciudad y realizar actualizacion y insercion
     * dentro de la misma, adicionalmente configura la barra
     * de busqueda para el propocito de busqueda que requiera
     * @see JPanel
     * @return JPanel
     */
    private JPanel ver_ciudad(){

        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        

        // Inicializaicon pop_menu
        config_pop_menu();
        pop_menu.remove(2); // La idea es que el usuario no pueda remover la ciudad

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_ciudad();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_ciudad(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_ciudad(this, url, (String) tabla.getValueAt(select_row, 1), (String) tabla.getValueAt(select_row, 2), Integer.parseInt((String) tabla.getValueAt(select_row, 0))).setVisible(true);
            base = new Base(url);
            try{
                
                tabla = Modelo_tabla.set_tabla_ciudad(base.consultar_ciudades(text_busqueda.getText()));
                tabla.setComponentPopupMenu(pop_menu);
                scroll.setViewportView(tabla);
        
                base.close();

            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                base.close();
            }
            

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_ciudad(this, url).setVisible(true);
            
            base = new Base(url);
            try{

                tabla = Modelo_tabla.set_tabla_ciudad(base.consultar_ciudades(text_busqueda.getText()));
                tabla.setComponentPopupMenu(pop_menu);
                scroll.setViewportView(tabla);
        
                base.close();

            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                base.close();
            }

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar la ciudad:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_ciudad(valor);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Ciudad eliminada correctamente");
                boton_ciudad.doClick();
            }
                  
        });

        JFrame padre = this;
        
        text_busqueda.addKeyListener(new Key_adapter() {
        
            @Override
            public void accion(){
                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_ciudad(base.consultar_ciudades(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            }

            @Override
            public void accion2(){}

        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    
    private JPanel ver_departamento(){

        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_departamentos();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_departamento(datos);
        scroll.setViewportView(tabla);
        

        JFrame padre = this;
        
        text_busqueda.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion(){
                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_departamento(base.consultar_departamentos(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    // Metodos relacionados con Personas y conductores
    
    private JPanel ver_personas(){

        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        tabla = null;
        
        // Inicializaicon pop_menu
        config_pop_menu();

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_persona();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_personas(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_peronas(this, url, "" + tabla.getValueAt(select_row, 0)).setVisible(true);
            boton_personas.doClick();
            

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_persona(this, url).setVisible(true);
            boton_personas.doClick();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar la persona:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_persona(valor);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Persona eliminada correctamente");
                boton_personas.doClick();
            }
                  
        });

        JFrame padre = this;
        text_busqueda.addKeyListener(new Key_adapter() {
            @Override
            public void accion(){
                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_personas(base.consultar_persona(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    
    // metodos para conductores
    private JPanel ver_conductores(){

        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu();

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_licencia();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_conductores(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_conductor(this, url, tabla.getValueAt(select_row, 0) + "");
            boton_conductores.doClick();
            

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_conductor(this, url).setVisible(true);
            boton_conductores.doClick();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String cat = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar al conductor:\n"+ id, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    number = Integer.parseInt(base.consultar_uno_categoria(cat) [0]);
                    base.eliminar_licencia(id, number);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Conductor eliminada correctamente");
                boton_conductores.doClick();
            }
                  
        });

        JFrame padre = this;
        text_busqueda.addKeyListener(new Key_adapter(){
            @Override
            public void accion(){

                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_conductores(base.consultar_licencia(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }

            @Override
            public void accion2(){}
        });
       

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    // Metodos relacionados a extractos
    
    private JPanel ver_extractos_mensuales(){
        
        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu_extractos();

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_vw_extracto_mensual("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_extractos_mensuales(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            int row = tabla.getSelectedRow();
            // actualizar_extracto
            new Actualizar_extracto_mensual(this, url,(String) tabla.getValueAt(row, 0), Integer.parseInt((String)tabla.getValueAt(row, 1)),false).setVisible(true);
            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_extractos_mensuales(base.consultar_vw_extracto_mensual(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            

        });
        item_plantilla.addActionListener(accion ->{

            int row = tabla.getSelectedRow();
            // actualizar_extracto
            new Actualizar_extracto_mensual(this, url,(String) tabla.getValueAt(row, 0), Integer.parseInt((String)tabla.getValueAt(row, 1)),true).setVisible(true);
            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_extractos_mensuales(base.consultar_vw_extracto_mensual(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_extracto_mensual(this, url).setVisible(true);
            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_extractos_mensuales(base.consultar_vw_extracto_mensual(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                    
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
        });

        item_exportar.addActionListener(accion ->{
            int select_row = tabla.getSelectedRow();

            try{
                String ruta;
                ruta = Generar_extractos.generar_extracto_mensual_excel((String) tabla.getValueAt(select_row, 0),Integer.parseInt((String) tabla.getValueAt(select_row, 1)), url);
                
                Process proceso = runtime.exec(comando);
                // implementar funcion que muestre que esperar por favor mientras carga la barra de proceso<
                JOptionPane.showMessageDialog(null, "Iniciando la exportacion\nPor favor espere...");
                proceso.waitFor();
                JOptionPane.showMessageDialog(this, "Extracto guardado con exito.\nUbicacion: " + ruta, "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            base = new Base(url);
            try{
                tabla = Modelo_tabla.set_tabla_extractos_mensuales(base.consultar_vw_extracto_mensual(text_busqueda.getText()));
                tabla.setComponentPopupMenu(pop_menu);
                scroll.setViewportView(tabla );
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            base.close();


        });
        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String placa = "" + tabla.getValueAt(number, 0);
            String consecutivo = "" + tabla.getValueAt(number, 1);
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el extracto " + consecutivo + "\ndel vehiculo "+placa,  "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    // realizando la eliminacion del registro
                    base.eliminar_extracto_mensual(placa, Integer.parseInt(consecutivo));

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Extracto eliminado correctamente");
                boton_extractos_mensuales.doClick();
            }
                  
        });

        item_exportar_todos.addActionListener(accion ->{
            String placa;
            String consecutivo;
            try{

                for(int i = 0; i < tabla.getRowCount(); i++){
                    placa = (String) tabla.getValueAt(i, 0);
                    consecutivo = (String) tabla.getValueAt(i, 1);
                    Generar_extractos.generar_extracto_mensual_excel(placa, Integer.parseInt(consecutivo), url);
                }

                runtime.exec(comando);
                
                JOptionPane.showMessageDialog(this, "Extractos guardados con exito.", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        });

        item_actualizar_todos.addActionListener(accion -> {

            new Actualizar_todo_ext_mensual(this, url).setVisible(true);

            base = new Base(url);
            try{
                tabla = Modelo_tabla.set_tabla_extractos_mensuales(base.consultar_vw_extracto_mensual(text_busqueda.getText()));
                tabla.setComponentPopupMenu(pop_menu);
                scroll.setViewportView(tabla );
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            base.close();

        });
        JFrame padre = this;
        
        text_busqueda.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion(){

                base = new Base(url);
                    try {
                        tabla = Modelo_tabla.set_tabla_extractos_mensuales(base.consultar_vw_extracto_mensual(text_busqueda.getText()));
                        tabla.setComponentPopupMenu(pop_menu);
                        scroll.setViewportView(tabla);
        
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    base.close();

            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    
    private JPanel ver_contratos_mensuales(){
        
        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu();
        pop_menu.remove(1);

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_contratos_mensuales("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_contratos_mensuales(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        
        item_adicionar.addActionListener(accion ->{

            new Insertar_contrato_mensual(this, url).setVisible(true);

            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratos_mensuales(base.consultar_contratos_mensuales(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            base.close();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el contrato:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_contrato_mensual(Integer.parseInt(id));
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Contrato eliminado correctamente");
                boton_contratos_mensuales.doClick();
            }
                  
        });

        JFrame padre = this;
        text_busqueda.addKeyListener(new Key_adapter(){
            @Override
            public void accion(){

                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratos_mensuales(base.consultar_contratos_mensuales(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }

            @Override
            public void accion2(){}
        });
       

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    private JPanel ver_contratos_ocasionales(){
        
        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu_extractos();

        
        pop_menu.remove(item_exportar_todos);
        pop_menu.remove(item_actualizar_todos);


        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_contrato_ocasional("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_contratos_ocasionales(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        
        item_adicionar.addActionListener(accion ->{

            new Insertar_contrato_ocasional(this, url).setVisible(true);

            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratos_ocasionales(base.consultar_contrato_ocasional(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            base.close();

        });

        item_actualizar.addActionListener(accion ->{
            int number = tabla.getSelectedRow();
            int id = Integer.parseInt((String)tabla.getValueAt(number, 0));

            new Actualizar_contrato_ocasional(this, url, id, false).setVisible(true);

            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratos_ocasionales(base.consultar_contrato_ocasional(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            base.close();

        });

        item_exportar.addActionListener(accion ->{

            int row = tabla.getSelectedRow();
            int num_contrato = Integer.parseInt((String)tabla.getValueAt(row, 0));
            
            try{
                String ruta = Generar_extractos.generar_extracto_ocasional(num_contrato, url);
                JOptionPane.showMessageDialog(this, "Exportando el contrato N° " + num_contrato + ", Junto \na sus extractos correspondientes. \n\nPor favor espere...");
                
                comando[2] = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Ocasionales";
                runtime.exec(comando);
                
                int proceso = runtime.exec(comando).waitFor();

                if(proceso == 0){
                    JOptionPane.showMessageDialog(this, "Proceso finalizado con exito.\nRuta de los documentos: "+ ruta);
                }else{
                    JOptionPane.showMessageDialog(this, "El proceso no pudo ser finalizado con exito. Error code: " + proceso);
                }
                

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }finally{
                comando[2] = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Mensuales";
            }
            
        });
        item_plantilla.addActionListener(accion ->{

            int number = tabla.getSelectedRow();
            int id = Integer.parseInt((String)tabla.getValueAt(number, 0));

            new Actualizar_contrato_ocasional(this, url, id, true).setVisible(true);

            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratos_ocasionales(base.consultar_contrato_ocasional(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            base.close();
        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el contrato:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_contrato_ocasional(Integer.parseInt(id));
                    JOptionPane.showMessageDialog(this, "Contrato eliminado correctamente");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base.close();
                    boton_contratos_ocasionales.doClick();
                }
                
            }
                  
        });

        JFrame padre = this;
        text_busqueda.addKeyListener(new Key_adapter(){
            @Override
            public void accion(){

                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratos_ocasionales(base.consultar_contrato_ocasional(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }

            @Override
            public void accion2(){}
        });
       

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    // Metodo para ver los extractos ocasionales
    private JPanel ver_extractos_ocasionales(){
        
        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu_extractos();
        pop_menu.remove(item_actualizar_todos);
        pop_menu.remove(item_exportar_todos);

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_vw_extracto_ocasional("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_extractos_ocasionales(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            int row = tabla.getSelectedRow();
            String placa = (String) tabla.getValueAt(row, 0);
            String consecutivo = (String) tabla.getValueAt(row, 1);
            String contrato = (String) tabla.getValueAt(row, 2);
            // actualizar_extracto
            new Actualizar_extracto_ocasional(this, url, placa, consecutivo, contrato, false).setVisible(true);
            base = new Base(url);
                try{
                    JTable aux_tabla = Modelo_tabla.set_tabla_extractos_ocasionales(base.consultar_vw_extracto_ocasional(text_busqueda.getText()));
                    
                    tabla.setModel(aux_tabla.getModel());
                    tabla.setColumnModel(aux_tabla.getColumnModel());

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
            

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_extracto_ocasional(this, url).setVisible(true);
            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_extractos_ocasionales(base.consultar_vw_extracto_ocasional(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();
        });
        item_plantilla.addActionListener(accion ->{
            int row = tabla.getSelectedRow();
            String placa = (String) tabla.getValueAt(row, 0);
            String consecutivo = (String) tabla.getValueAt(row, 1);
            String contrato = (String) tabla.getValueAt(row, 2);

            new Actualizar_extracto_ocasional(this, url, placa, consecutivo, contrato, true).setVisible(true);

            base = new Base(url);
            try{
                JTable tab_aux = Modelo_tabla.set_tabla_extractos_ocasionales(base.consultar_vw_extracto_ocasional(text_busqueda.getText()));
                tabla.setModel(tab_aux.getModel());
                tabla.setColumnModel(tab_aux.getColumnModel());
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }finally{
                base.close();
            }
        });
        item_exportar.addActionListener(accion ->{
            int select_row = tabla.getSelectedRow();
            try{
                String ruta;
                comando[2] = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Ocasionales";
                ruta = Generar_extractos.generar_extracto_ocasional(Integer.parseInt((String) tabla.getValueAt(select_row, 2)), 
                                                                    url);
                runtime.exec(comando);
                
                JOptionPane.showMessageDialog(this, "Extracto guardado con exito.\nUbicacion: " + ruta, "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }finally{

                comando[2] = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Mensuales";
                
            }

        });
        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String placa = "" + tabla.getValueAt(number, 0);
            String consecutivo = "" + tabla.getValueAt(number, 1);
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el extracto " + consecutivo + "\ndel vehiculo "+placa,  "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    // realizando la eliminacion del registro
                    base.eliminar_extracto_ocasional(placa, Integer.parseInt(consecutivo));

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Extracto eliminado correctamente");
                boton_extractos_ocasionales.doClick();
            }
                  
        });
        
        JFrame padre = this;
        
        text_busqueda.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion(){

                base = new Base(url);
                    try {
                        JTable tab_aux = Modelo_tabla.set_tabla_extractos_ocasionales(base.consultar_vw_extracto_ocasional(text_busqueda.getText()));
                        tabla.setModel(tab_aux.getModel());
                        tabla.setColumnModel(tab_aux.getColumnModel());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }finally{
                        base.close();
                    }

            }

            @Override
            public void accion2(){}
        });
        

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    // Metodos relacionados con contratante

    private JPanel ver_contratante(){
        
        configuracion_panel_busqueda();
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane();
        String[][] datos = null;
        
        // Inicializaicon pop_menu
        config_pop_menu();
        

        // Obteniendo datos de la base de datos
        base = new Base(url);
        try{
            datos = base.consultar_contratante("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        base.close();

        // Configuracion de la visualizacion y opciones de la tabla

        tabla = Modelo_tabla.set_tabla_contratante(datos);
        tabla.setComponentPopupMenu(pop_menu);
        scroll.setViewportView(tabla);
        

        // Configuracion de los item 
        item_actualizar.addActionListener(accion->{
            panel.revalidate();
            panel.repaint();
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_contratante(this, url,(String) tabla.getValueAt(select_row, 0)).setVisible(true);
            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratante(base.consultar_contratante(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            base.close();

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_contratante(this, url).setVisible(true);

            base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratante(base.consultar_contratante(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            base.close();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar al contratante:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base = new Base(url);
                try{
                    base.eliminar_contratante(id);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }
                
                base.close();
                JOptionPane.showMessageDialog(this, "Contratante eliminado correctamente");
                boton_contratante.doClick();
            }
                  
        });

        JFrame padre = this;
        text_busqueda.addKeyListener(new Key_adapter(){
            @Override
            public void accion(){

                base = new Base(url);
                try{
                    tabla = Modelo_tabla.set_tabla_contratante(base.consultar_contratante(text_busqueda.getText()));
                    tabla.setComponentPopupMenu(pop_menu);
                    scroll.setViewportView(tabla );
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }

            @Override
            public void accion2(){}
        });
       

        panel.add(panel_busqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;

    }

    // Metodos Auxiliares
    private void config_pop_menu(){
        item_actualizar = new JMenuItem("Actualizar");
        item_adicionar = new JMenuItem("Adicionar");
        item_eliminar = new JMenuItem("Eliminar");

        pop_menu = new JPopupMenu();
        
        pop_menu.add(item_adicionar);
        pop_menu.add(item_actualizar);
        pop_menu.add(item_eliminar);

    }  
    
    private void config_pop_menu_extractos(){
        item_actualizar = new JMenuItem("Modificar");
        item_adicionar = new JMenuItem("Adicionar");
        item_plantilla = new JMenuItem("Plantilla");
        item_exportar = new JMenuItem("Exportar");
        item_eliminar = new JMenuItem("Eliminar");
        item_exportar_todos = new JMenuItem("Exportar todos");
        item_actualizar_todos = new JMenuItem("Actualizar todos");

        pop_menu = new JPopupMenu();
        
        pop_menu.add(item_adicionar);
        pop_menu.add(item_actualizar);
        pop_menu.add(item_plantilla);
        pop_menu.add(item_exportar);
        pop_menu.add(item_eliminar);
        pop_menu.add(item_exportar_todos);
        pop_menu.add(item_actualizar_todos);
    }

    
    
    
}
