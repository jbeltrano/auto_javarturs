package Front.Extractos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Base.Base;
import Front.Personas.Insertar_persona;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;

public class Insertar_contratante extends Modal_extracto{
    
    private JButton boton_guardar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    protected JTable tabla_contratante;
    protected JTable tabla_responsable;
    protected JTextField text_contratante;
    protected JTextField text_responsable;
    private final int POS_X = 10;
    private String[][] datos_tabla_responsable;
    private String[][] datos_tabla_contratante;
    private JDialog padre;

    public Insertar_contratante(JFrame padre, String url){
        super(padre, url);

    }

    public Insertar_contratante(JDialog padre, String url){
        super(padre, url);

    }

    @Override
    protected void iniciar_componentes(){
        padre = this;
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_contratante = new javax.swing.JTextField();
        text_responsable = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_contratante = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_responsable = new javax.swing.JTable();
        boton_guardar = new javax.swing.JButton();

        // Consultando los datos de los contratantes
        base = new Base(url);
        try{
            datos_tabla_contratante = base.consultar_no_contratante("");
            datos_tabla_responsable = base.consultar_persona();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        base.close();
        jPanel1.setLayout(null);

        jLabel1.setText("Contratante");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(POS_X, POS_X, 83, 16);

        jLabel2.setText("Responsable");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(315, POS_X, 98, 16);

        text_contratante.addKeyListener(new Key_adapter() {

            @Override
            public void accion(){
                set_tabla_contratante();
                tabla_contratante.changeSelection(0, 0, false, false);
            }

            @Override
            public void accion2(){

                accion_tabla_contratante();
            
            }
        });

        jPanel1.add(text_contratante);
        text_contratante.setBounds(POS_X, 28, 200, 22);
        text_responsable.addKeyListener(new Key_adapter() {
            

            @Override
            public void accion(){
                
                set_tabla_responsable();
                tabla_responsable.changeSelection(0, 0, false, false);

            }

            @Override
            public void accion2(){
                accion_tabla_responsable();
                boton_guardar.doClick();
            }
        });

        jPanel1.add(text_responsable);
        text_responsable.setBounds(315, 28, 200, 22);

        tabla_contratante = Modelo_tabla.set_tabla_personas(datos_tabla_contratante);
        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){
                    accion_tabla_contratante();
                }else{
                    new Insertar_persona(padre, url);
                    set_tabla_contratante();
                }
                
                
            }
        });

        jScrollPane1.setViewportView(tabla_contratante);
        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(POS_X, 77, 277, 140);

        tabla_responsable = Modelo_tabla.set_tabla_personas(datos_tabla_responsable);
        tabla_responsable.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){
                    accion_tabla_responsable();
                }else{
                    new Insertar_persona(padre, url);
                    set_tabla_responsable();
                }
                
                
            }
        });
        jScrollPane2.setViewportView(tabla_responsable);
        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(315, 77, 277, 140);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(POS_X, 250, 100, 23);
        boton_guardar.addActionListener(accion ->{
            String errores = "Los datos:\n";
            boolean band = true;
            if(text_contratante.getText().equals("")){
                band = false;
                errores += "Contratante\n";
                
            }
            if(text_responsable.getText().equals("")){
                band = false;
                errores += "Responsable\n";
            }
            errores += "\nSon necesarios";

            if(band){
                try{
                    Double.parseDouble(text_contratante.getText());
                    Double.parseDouble(text_responsable.getText());

                    guardar();

                    JOptionPane.showMessageDialog(this, "Contrante guardado correctamente", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Los campos:\n Documento, Responsable\nDeben ser numeros enteros.", "Error", JOptionPane.ERROR_MESSAGE);
                }catch(SQLException ex){
                    base.close();
                    JOptionPane.showMessageDialog(this,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, errores, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(jPanel1);

        pack();

    }

    @Override
    protected Dimension set_dimension(){
        return new Dimension(630,350);
    }

    protected void guardar()throws SQLException{

        base = new Base(url);

        base.insertar_contratante(text_contratante.getText(), text_responsable.getText());

        base.close();

    }

    private void accion_tabla_contratante(){

        int row = tabla_contratante.getSelectedRow();
        text_contratante.setText("" + tabla_contratante.getValueAt(row, 0));
        text_responsable.setText("" + tabla_contratante.getValueAt(row, 0)); 
        set_tabla_responsable();
        tabla_responsable.changeSelection(0, 0, false, false);


    }

    private void accion_tabla_responsable(){

        int row = tabla_responsable.getSelectedRow();
        text_responsable.setText("" + tabla_responsable.getValueAt(row, 0));

    }

    public void set_tabla_contratante(){
        base = new Base(url);
        try{
                    
            datos_tabla_contratante = base.consultar_no_contratante(text_contratante.getText());
            JTable aux = Modelo_tabla.set_tabla_personas(datos_tabla_contratante);
            tabla_contratante.setModel(aux.getModel());
            tabla_contratante.setColumnModel(aux.getColumnModel());
                    
        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        base.close();
    }

    public void set_tabla_responsable(){

        base = new Base(url);
        try{
            
            datos_tabla_responsable = base.consultar_persona(text_responsable.getText());
            JTable aux = Modelo_tabla.set_tabla_personas(datos_tabla_responsable);
            tabla_responsable.setModel(aux.getModel());
            tabla_responsable.setColumnModel(aux.getColumnModel());
                    
        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        base.close();

    }
}
