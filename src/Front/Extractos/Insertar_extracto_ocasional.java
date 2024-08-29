package Front.Extractos;

import Base.Base;
import Base.BContrato_ocasional;
import Base.Extractos;
import Base.Vehiculo;
import Utilidades.Generar_extractos;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Insertar_extracto_ocasional extends Modal_documento{

    private static final int POS_X = 10;
    private static final int HEIGHT = 20;
    private JPanel panel;
    private JLabel label_vehiculo;
    private JLabel label_consecutivo;
    private JLabel label_contrato;
    private JScrollPane scroll_vehiculo;
    private JScrollPane scroll_contrato;
    protected JTable tabla_vehiculo;
    protected JTable tabla_contrato;
    protected JTextField text_placa;
    protected JTextField text_consecutivo;
    protected JTextField text_contrato;
    private JButton boton_guardar;
    private JButton boton_guardar_exportar;
    private JFrame ventana;

    public Insertar_extracto_ocasional(JFrame frame, String url){
        super(frame,url);
        ventana = frame;
    }

    @Override
    protected void iniciar_componentes() {
        panel = new JPanel(null);
        label_contrato = new JLabel("Contrato");
        label_vehiculo = new JLabel("Vehiculo");
        label_consecutivo = new JLabel("Consecutivo");
        text_placa = new JTextField();
        text_consecutivo = new JTextField();
        text_contrato = new JTextField();
        scroll_vehiculo = new JScrollPane();
        scroll_contrato = new JScrollPane();
        tabla_vehiculo = new JTable();
        tabla_contrato = new JTable();
        boton_guardar = new JButton("Guardar");
        boton_guardar_exportar = new JButton("Guardar y Exportar");

        // Inicializacion de los valores de las tablas
        base = new BContrato_ocasional(url);
        Vehiculo base_vehiculo = new Vehiculo(url);
        try{
            String[][] datos_vehiculo = base_vehiculo.consultar_vehiculo("");
            String[][] datos_contrato = ((BContrato_ocasional)base).consultar_contrato_ocasional("");

            tabla_vehiculo = Modelo_tabla.set_tabla_vehiculo(datos_vehiculo);
            tabla_contrato = Modelo_tabla.set_tabla_contratos_ocasionales(datos_contrato);

            tabla_contrato.changeSelection(0, 0, false, false);
            tabla_vehiculo.changeSelection(0,0,false,false);
            

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            base.close();
            base_vehiculo.close();
        }
        label_vehiculo.setBounds(POS_X,POS_X,100,HEIGHT);
        panel.add(label_vehiculo);

        text_placa.setBounds(POS_X, label_vehiculo.getY()+ HEIGHT + 10, 100, HEIGHT);
        text_placa.addKeyListener(new Key_adapter() {

            @Override
            public void accion(){
                String datos[][] = null;

                base = new Base(url);
                Vehiculo base_vehiculo = new Vehiculo(url);
                try{

                    datos = base_vehiculo.consultar_vehiculo(text_placa.getText());
                    JTable tabla_auxiliar = Modelo_tabla.set_tabla_vehiculo(datos);
                    tabla_vehiculo.setModel(tabla_auxiliar.getModel());
                    tabla_vehiculo.setColumnModel(tabla_auxiliar.getColumnModel());
                    scroll_vehiculo.setViewportView(tabla_vehiculo);


                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);
                }finally{
                    base.close();
                    base_vehiculo.close();
                }

                tabla_vehiculo.changeSelection(0, 0, false, false);

            }

            @Override
            public void accion2(){

                accion_tabla_vehiculo();

            }

        });
        panel.add(text_placa);

        label_consecutivo.setBounds(POS_X + label_vehiculo.getWidth() + 20,POS_X,100,HEIGHT);
        panel.add(label_consecutivo);

        text_consecutivo.setBounds(label_consecutivo.getX(), label_consecutivo.getY() + HEIGHT + 10, 100, HEIGHT);
        panel.add(text_consecutivo);

        label_contrato.setBounds(label_consecutivo.getX() + label_consecutivo.getWidth() + 160, POS_X,100,HEIGHT);
        panel.add(label_contrato);

        text_contrato.setBounds(label_contrato.getX(), label_contrato.getY() + HEIGHT + 10, 100, HEIGHT);
        text_contrato.addKeyListener(new Key_adapter() {

            @Override
            public void accion() {

                String datos[][] = null;

                base = new BContrato_ocasional(url);
                try{
                    datos = ((BContrato_ocasional)base).consultar_contrato_ocasional(text_contrato.getText());
                    JTable tabla_auxiliar = Modelo_tabla.set_tabla_contratos_ocasionales(datos);
                    tabla_contrato.setModel(tabla_auxiliar.getModel());
                    tabla_contrato.setColumnModel(tabla_auxiliar.getColumnModel());
                    scroll_contrato.setViewportView(tabla_contrato);

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ventana.setVisible(false);

                }finally {
                    base.close();
                }
                tabla_contrato.changeSelection(0, 0, false, false);

            }

            @Override
            public void accion2() {
                accion_tabla_contrato();
            }
        });
        panel.add(text_contrato);
        add(panel);

        tabla_vehiculo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                accion_tabla_vehiculo();
            }
        });

        scroll_vehiculo.setBounds(POS_X, text_placa.getY() + HEIGHT + 20, 350,180);
        scroll_vehiculo.setViewportView(tabla_vehiculo);
        panel.add(scroll_vehiculo);

        tabla_contrato.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                accion_tabla_contrato();
            }
        });

        scroll_contrato.setBounds(text_contrato.getX(), text_contrato.getY() + HEIGHT + 20, 350,180);
        scroll_contrato.setViewportView(tabla_contrato);
        panel.add(scroll_contrato);

        boton_guardar.setBounds(POS_X, scroll_vehiculo.getHeight() + scroll_vehiculo.getY() + 40, 100, HEIGHT);
        boton_guardar.addActionListener(accion ->{

            if(guardar_extracto_ocasional()){
                setVisible(false);
            }

        });
        panel.add(boton_guardar);

        boton_guardar_exportar.setBounds(boton_guardar.getX() + boton_guardar.getWidth() + 20, boton_guardar.getY(), 150, HEIGHT);
        boton_guardar_exportar.addActionListener(accion ->{

            boolean flag = guardar_extracto_ocasional();
            if(flag){
                try{
                    String ruta;
                    String comando[] = {System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\a.exe",
                                        System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\ConvertirPdf.ps1",
                                        System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Ocasionales"};
                    Runtime runtieme = Runtime.getRuntime();

                    ruta = Generar_extractos.generar_extracto_ocasional(text_placa.getText(), Integer.parseInt(text_consecutivo.getText()),Integer.parseInt(text_contrato.getText()), url);
                    runtieme.exec(comando);
                    JOptionPane.showMessageDialog(this, "Extracto guardado con exito.\nUbicacion: " + ruta + "\nEl contrato lo encontraras en la carpeta de contratos ocasionales", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, e.getMessage(),"Error",  JOptionPane.ERROR_MESSAGE);
                }
                
            }else{
                JOptionPane.showMessageDialog(this, "No se pudo exportar el extracto", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
        panel.add(boton_guardar_exportar);

        pack();
    }

    @Override
    protected Dimension set_dimension() {
        return new Dimension(800,400);
    }

    protected boolean guardar_extracto_ocasional(){

        int consecutivo = 0;
        String vehiculo;
        int contrato;

        base = new Extractos(url);
        try{

            vehiculo = (text_placa.getText().compareTo("") == 0)?null: text_placa.getText();
            contrato = (text_contrato.getText().compareTo("") == 0)?0: Integer.parseInt(text_contrato.getText());
            consecutivo = (text_consecutivo.getText().compareTo("") == 0?0:Integer.parseInt(text_consecutivo.getText()));

            if(vehiculo != null && contrato != 0 && consecutivo != 0){

                ((Extractos)base).insertar_extracto_ocasional(vehiculo, consecutivo, contrato);
                JOptionPane.showMessageDialog(this, "Extracto ocasional guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
                return true;

            }else{
                JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos para continuar\nRecuerde que si es el primer extracto del vehiculo\nDebe establecer el consecutivo", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }finally{
            base.close();
        }

    }

    private void accion_tabla_contrato(){
        int valor_auxilia = tabla_contrato.getSelectedRow();
        text_contrato.setText("" + tabla_contrato.getValueAt(valor_auxilia, 0));
    }

    private void accion_tabla_vehiculo(){

        int valor_auxilia = tabla_vehiculo.getSelectedRow();
        int consecutivo = 0;
        text_placa.setText("" + tabla_vehiculo.getValueAt(valor_auxilia, 0));
        base = new Extractos(url);
        try{

            consecutivo = ((Extractos)base).consultar_consecutivo_ocasional(text_placa.getText());
            if(consecutivo == 0){
                
                text_consecutivo.setText("");
            }else{
                
                text_consecutivo.setText("" + (consecutivo + 1));
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }finally{
            base.close();
        }
    }

    
}
