package Front.Ciudades_departamentos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Base.Ciudad;
import Base.Ruta;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;
import java.sql.SQLException;
import Estructuras_datos.Queue;

public class Insertar_ruta extends Modal_ruta{
    
    protected JPanel panel;
    private JLabel label_origen;
    private JLabel label_destino;
    private JLabel label_distancia;
    protected JTextField text_origen;
    protected JTextField text_destino;
    protected JTextField text_distancia;
    protected JTable tabla_origen;
    protected JTable tabla_destino;
    private JScrollPane scroll_origen;
    private JScrollPane scroll_destino;
    protected Ciudad base_ciudad;
    protected Ruta base_ruta;
    private JButton boton_guardar;
    private JDialog ventana;
    private Queue<String> error;

    public Insertar_ruta(JDialog padre, String url){
        super(padre, url);
        ventana = this;
        
    }

    public Insertar_ruta(JFrame padre, String url){
        super(padre, url);
        ventana = this;

    }

    protected void iniciar_componentes(){
        panel = new JPanel(null);
        label_origen = new JLabel("Origen");
        label_destino = new JLabel("Destino");
        label_distancia = new JLabel("Distancia:");
        text_origen = new JTextField();
        text_destino = new JTextField();
        text_distancia = new JTextField("");
        scroll_origen = new JScrollPane();
        scroll_destino = new JScrollPane();
        tabla_origen = new JTable();
        tabla_destino = new JTable();
        boton_guardar = new JButton("Guardar");
        error = new Queue<String>();

        // Cargando datos con respecto a la tabla
        try{
            base_ciudad = new Ciudad(url);
            String datos[][] = base_ciudad.consultar_ciudad();
            tabla_origen = Modelo_tabla.set_tabla_ciudad(datos);
            tabla_destino = Modelo_tabla.set_tabla_ciudad(datos);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
        }finally{
            base_ciudad.close();
        }

        // Configuracion de los label
        label_origen.setBounds(20,10,100,20);
        label_destino.setBounds(label_origen.getX() + label_destino.getWidth() + 350,
                                10,
                                100,
                                20);

        // Configuracion de los textField
        text_origen.setBounds(  label_origen.getX(),
                                label_origen.getY() + label_origen.getHeight() + 10,
                                150,
                                20);

        text_origen.addKeyListener(new Key_adapter() {

            @Override
            public void accion() {
                try{
                    base_ciudad = new Ciudad(url);
                    String datos[][] = base_ciudad.consultar_ciudades(text_origen.getText());
                    JTable tabla = Modelo_tabla.set_tabla_ciudad(datos);
                    tabla_origen.setModel(tabla.getModel());
                    tabla_origen.setColumnModel(tabla.getColumnModel());
                    tabla_origen.changeSelection(0, 0, false, false);

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);

                }finally{
                    base_ciudad.close();
                }
            }

            @Override
            public void accion2() {
                text_origen.setText((String)tabla_origen.getValueAt(tabla_origen.getSelectedRow(),0));
            }
            
        });

        text_destino.setBounds( label_destino.getX(),
                                text_origen.getY(),
                                150,
                                20);

        text_destino.addKeyListener(new Key_adapter() {

            @Override
            public void accion() {
                try{
                    base_ciudad = new Ciudad(url);
                    String datos[][] = base_ciudad.consultar_ciudades(text_destino.getText());
                    JTable tabla = Modelo_tabla.set_tabla_ciudad(datos);
                    tabla_destino.setModel(tabla.getModel());
                    tabla_destino.setColumnModel(tabla.getColumnModel());
                    tabla_destino.changeSelection(0, 0, false, false);

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);

                }finally{
                    base_ciudad.close();
                }
            }

            @Override
            public void accion2() {
                text_destino.setText((String)tabla_origen.getValueAt(tabla_origen.getSelectedRow(),0));
            }
            
        });

        // Configuracion de los listener de la tabla
        tabla_origen.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){

                    int row = tabla_origen.getSelectedRow();
                    text_origen.setText((String) tabla_origen.getValueAt(row, 0));
                }
                
                
            }
        });

        tabla_destino.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){

                    int row = tabla_destino.getSelectedRow();
                    text_destino.setText((String) tabla_destino.getValueAt(row, 0));
                }
                
                
            }
        });
        // Configuracion de los scrollPane
        scroll_origen.setBounds(text_origen.getX(),
                                text_origen.getY() + text_origen.getHeight() + 10,
                                300,
                                150);

        scroll_origen.setViewportView(tabla_origen);

        scroll_destino.setBounds(text_destino.getX(),
                                 text_destino.getY() + text_destino.getHeight() + 10,
                                 300,
                                 150);

        scroll_destino.setViewportView(tabla_destino);


        // Configuracion del jlabel de distancia
        label_distancia.setBounds(  scroll_origen.getX(),
                                    scroll_origen.getY() + scroll_origen.getHeight() + 20,
                                    60,
                                    20);

        // Configuracion del text distancia
        text_distancia.setBounds(   label_distancia.getX() + label_distancia.getWidth() + 10, 
                                    label_distancia.getY(), 
                                    50, 
                                    20);

        boton_guardar.setBounds(label_distancia.getX(), 
                                label_distancia.getY() + label_distancia.getHeight() + 20,
                                100,
                                20);

        boton_guardar.addActionListener(accion ->{
            if(tabla_origen.getSelectedRow() < 0){
                error.enqueue("No hay un valor seleccionado en Origen");
            }
            if(tabla_destino.getSelectedRow() < 0){
                error.enqueue("No hay un valor seleccionado en Destino");
            }
            try{
                Integer.parseInt(text_distancia.getText());
            }catch(NumberFormatException ex){
                error.enqueue("El valor en el campo distancia debe ser un numero entero");
            }

            if(error.isEmpty()){
                accion_guardar();
            }else{
                String formato_errores = "No es posible realizar el registro, se presentan los siguientes errores: \n\n";
                System.out.println(error.size());
                while(!error.isEmpty()){
                    formato_errores = formato_errores.concat("      " + error.dequeue() + "\n");
                }
                formato_errores = formato_errores.concat("\nPor favor corrigelos antes de contiunar.");
                JOptionPane.showMessageDialog(this, formato_errores,"Error",JOptionPane.ERROR_MESSAGE);
            }
            
        });

        panel.add(label_origen);
        panel.add(label_destino);
        panel.add(text_origen);
        panel.add(text_destino);
        panel.add(scroll_origen);
        panel.add(scroll_destino);
        panel.add(label_distancia);
        panel.add(text_distancia);
        panel.add(boton_guardar);

        add(panel);
        pack();
    }

    /**
     * Sirve para modificar lo que se va
     * a hacer cuando se presione el
     * boton_guardar
     */
    protected void accion_guardar(){
        base_ruta = new Ruta(url);

        try{
            
            int origen = Integer.parseInt((String) tabla_origen.getValueAt(tabla_origen.getSelectedRow(), 0));
            int destino = Integer.parseInt((String) tabla_destino.getValueAt(tabla_destino.getSelectedRow(), 0));
            int distancia = Integer.parseInt(text_distancia.getText());
            
            base_ruta.insertar_ruta(origen, destino, distancia);

            JOptionPane.showMessageDialog(this, "Datos insertados correctamente.", null, JOptionPane.INFORMATION_MESSAGE);

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base_ruta.close();
        }

        
    }
}
