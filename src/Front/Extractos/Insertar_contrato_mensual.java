package Front.Extractos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Base.Contratante;
import Base.Contrato_mensual;
import Base.BContrato_ocasional;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;

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
    private JLabel label_tipo_contrato;
    protected JComboBox<String> combo_tipo_contrato;
    protected Contratante base_contratante;
    protected Contrato_mensual base_cm;
    public Insertar_contrato_mensual(JFrame padre){

        super(padre);

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
        combo_tipo_contrato = new JComboBox<>();
        label_tipo_contrato = new JLabel();
        
        // Consultando los datos de los contratantes
        
        try{

            base = new BContrato_ocasional();
            base_contratante = new Contratante();
            base_cm = new Contrato_mensual();
        

            datos = base_contratante.consultar_contratante("");
            ultimo_contrato = "" + (Integer.parseInt(base_cm.consultar_ultimo_contrato_mensual()) +1);
            combo_tipo_contrato = new JComboBox<>(((BContrato_ocasional)base).consultar_tipo_contrato());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base != null) base.close();
            if(base_contratante != null) base_contratante.close();
            if(base_cm != null) base_cm.close();
        }
        panel.setLayout(null);

        label1.setText("Numero de contrato");
        panel.add(label1);
        label1.setBounds(POS_X, POS_X, 150, 20);

        label2.setText("Contratante");
        panel.add(label2);
        label2.setBounds(250, POS_X, 98, 20);

        

        panel.add(text_contrato);
        text_contrato.setText(ultimo_contrato);
        text_contrato.setBounds(POS_X, label1.getY() + label1.getHeight() + 10, 100, 20);
        
        text_contratante.addKeyListener(new Key_adapter() {
            
            @Override
            public void accion(){

                
                try{

                    base = new Contratante();

                    Modelo_tabla.updateTableModel(tabla_contratante, ((Contratante)base).consultar_contratante(text_contratante.getText())); // Actualiza el modelo de la tabla
                    
        
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base != null) base.close();
                }

            }

            @Override
            public void accion2(){}
        });

        panel.add(text_contratante);
        text_contratante.setBounds(label2.getX(), text_contrato.getY(), 200, 22);


        label_tipo_contrato.setText("Objeto del Contrato");
        label_tipo_contrato.setBounds(text_contratante.getX() + text_contratante.getWidth() + 20, label2.getY(), 120, 20);
        panel.add(label_tipo_contrato);

        combo_tipo_contrato.setBounds(label_tipo_contrato.getX(),text_contratante.getY(),120,20);
        combo_tipo_contrato.setSelectedItem("EMPRESARIAL");
        panel.add(combo_tipo_contrato);


        JDialog padre = this;
        tabla_contratante = Modelo_tabla.set_tabla_contratante(datos);
        tabla_contratante.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){
                    int row = tabla_contratante.getSelectedRow();
                    text_contratante.setText("" + tabla_contratante.getValueAt(row, 0));
                }else if(SwingUtilities.isRightMouseButton(e)){
                    new Insertar_contratante(padre).setVisible(true);
                }
                
            }
        });


        
        jScrollPane2.setViewportView(tabla_contratante);
        panel.add(jScrollPane2);
        jScrollPane2.setBounds(POS_X, 77, 590, 150);

        boton_guardar.setText("Guardar");
        panel.add(boton_guardar);
        boton_guardar.setBounds(POS_X, 250, 100, 23);
        boton_guardar.addActionListener(_ ->{
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
    
                    Double.parseDouble(text_contratante.getText());

                    guardar();

                    JOptionPane.showMessageDialog(this, "Contrante guardado correctamente", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Los campos:\n Numero de contrato, Contratante\nDeben ser numeros enteros.", "Error", JOptionPane.ERROR_MESSAGE);
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    
    protected void guardar()throws SQLException, IOException{
        int numero_contrato = Integer.parseInt(text_contrato.getText());
        
        try{

            base = new Contrato_mensual();

            ((Contrato_mensual)base).insertar_contrato_mensual((numero_contrato == 0)?1:numero_contrato, text_contratante.getText(), combo_tipo_contrato.getSelectedIndex()+1);

        }finally{
            if(base != null) base.close();
        }


    }
}
