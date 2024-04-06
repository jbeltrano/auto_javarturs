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
import Base.Base;
import Front.Principal;
import Utilidades.Key_adapter;

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
    private String[][] datos;

    public Insertar_contratante(JFrame padre, String url){
        super(padre, url);

    }

    public Insertar_contratante(JDialog padre, String url){
        super(padre, url);

    }

    @Override
    protected void iniciar_componentes(){

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
            datos = base.consultar_persona();

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

        text_contratante.addKeyListener(new Key_adapter(text_contratante.getText()) {
            
            @Override
            public void accion(){

                base = new Base(url);
                try{
                    
                    datos = base.consultar_persona(get_text());
                    JTable aux = Principal.set_tabla_personas(datos);
                    tabla_contratante.setModel(aux.getModel());
                    tabla_contratante.setColumnModel(aux.getColumnModel());
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }
        });

        jPanel1.add(text_contratante);
        text_contratante.setBounds(POS_X, 28, 200, 22);
        
        text_responsable.addKeyListener(new Key_adapter(text_responsable.getText()) {
            
            @Override
            public void accion(){

                base = new Base(url);
                try{

                    datos = base.consultar_persona(get_text());
                    JTable aux = Principal.set_tabla_personas(datos);
                    tabla_responsable.setModel(aux.getModel());
                    tabla_responsable.setColumnModel(aux.getColumnModel());
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }
        });

        jPanel1.add(text_responsable);
        text_responsable.setBounds(315, 28, 200, 22);

        tabla_contratante = Principal.set_tabla_personas(datos);
        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){

                int row = tabla_contratante.getSelectedRow();
                text_contratante.setText("" + tabla_contratante.getValueAt(row, 0));
                text_responsable.setText("" + tabla_contratante.getValueAt(row, 0));
            }
        });

        jScrollPane1.setViewportView(tabla_contratante);
        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(POS_X, 77, 277, 140);

        tabla_responsable = Principal.set_tabla_personas(datos);
        tabla_responsable.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                int row = tabla_responsable.getSelectedRow();
                text_responsable.setText("" + tabla_responsable.getValueAt(row, 0));
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
                    Integer.parseInt(text_contratante.getText());
                    Integer.parseInt(text_responsable.getText());

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
}
