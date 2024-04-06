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
import Front.Principal;
import Utilidades.Key_adapter;

public class Insertar_contrato_mensual extends Modal_extracto{

    private final int POS_X = 10;
    private JPanel panel;
    private JLabel label1;
    private JLabel label2;
    protected JTextField text_contrato;
    protected JTextField text_contratante;
    private JScrollPane jScrollPane2;
    private JTable tabla_contratante;
    protected JButton boton_guardar;
    private String[][] datos;
    private String ultimo_contrato; 

    public Insertar_contrato_mensual(JFrame padre, String url){

        super(padre, url);

    }

    @Override
    protected void iniciar_componentes(){

        panel = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        text_contrato = new javax.swing.JTextField();
        text_contratante = new javax.swing.JTextField();
        tabla_contratante = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_contratante = new javax.swing.JTable();
        boton_guardar = new javax.swing.JButton();

        // Consultando los datos de los contratantes
        base = new Base(url);
        
        try{
            datos = base.consultar_contratante("");
            ultimo_contrato = base.consultar_ultimo_contrato_mensual();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        base.close();
        panel.setLayout(null);

        label1.setText("Numero de contrato");
        panel.add(label1);
        label1.setBounds(POS_X, POS_X, 150, 16);

        label2.setText("Contratante");
        panel.add(label2);
        label2.setBounds(315, POS_X, 98, 16);

        

        panel.add(text_contrato);
        text_contrato.setText(ultimo_contrato);
        text_contrato.setBounds(POS_X, 28, 100, 22);
        
        text_contratante.addKeyListener(new Key_adapter(text_contratante.getText()) {
            
            @Override
            public void accion(){

                base = new Base(url);
                try{

                    datos = base.consultar_contratante(get_text());
                    JTable aux = Principal.set_tabla_contratante(datos);
                    tabla_contratante.setModel(aux.getModel());
                    tabla_contratante.setColumnModel(aux.getColumnModel());
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                base.close();

            }
        });

        panel.add(text_contratante);
        text_contratante.setBounds(315, 28, 200, 22);

        JDialog padre = this;
        tabla_contratante = Principal.set_tabla_contratante(datos);
        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){
                    int row = tabla_contratante.getSelectedRow();
                    text_contratante.setText("" + tabla_contratante.getValueAt(row, 0));
                }else if(SwingUtilities.isRightMouseButton(e)){
                    new Insertar_contratante(padre, url).setVisible(true);
                }
                
            }
        });


        
        jScrollPane2.setViewportView(tabla_contratante);
        panel.add(jScrollPane2);
        jScrollPane2.setBounds(POS_X, 77, 590, 150);

        boton_guardar.setText("Guardar");
        panel.add(boton_guardar);
        boton_guardar.setBounds(POS_X, 250, 100, 23);
        boton_guardar.addActionListener(accion ->{
            String errores = "Los datos:\n";
            boolean band = true;
            if(text_contrato.getText().equals("")){
                band = false;
                errores += "Numero de contrato\n";
                
            }
            if(text_contratante.getText().equals("")){
                band = false;
                errores += "Contratante\n";
            }
            errores += "\nSon necesarios";

            if(band){
                try{
    
                    Integer.parseInt(text_contratante.getText());

                    guardar();

                    JOptionPane.showMessageDialog(this, "Contrante guardado correctamente", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Los campos:\n Numero de contrato, Contratante\nDeben ser numeros enteros.", "Error", JOptionPane.ERROR_MESSAGE);
                }catch(SQLException ex){
                    base.close();
                    JOptionPane.showMessageDialog(this,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, errores, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);

        pack();

    }

    @Override
    protected Dimension set_dimension(){

        return new Dimension(700,350);
    }
    
    protected void guardar()throws SQLException{
        int numero_contrato = Integer.parseInt(text_contrato.getText());
        base = new Base(url);

        base.insertar_contrato_mensual((numero_contrato == 0)?1:numero_contrato, text_contratante.getText());

        base.close();

    }
}
