package Front.Personas;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import Base.Base;
import java.awt.Dimension;

public class Insertar_persona extends Modales_personas{

    protected JButton boton_guardar;
    protected JComboBox<String> combo_departamento;
    protected JComboBox<String> combo_municipio;
    protected JComboBox<String> combo_tipo_documento;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    protected JPanel jPanel1;
    protected JTextField text_celular;
    protected JTextField text_direccion;
    protected JTextField text_documento;
    protected JTextField text_nombre;

    public Insertar_persona(JDialog padre, String url){
        super(padre,url,new Dimension(550,300));
        setVisible(true);
    }

    public Insertar_persona(JFrame padre, String url){
        super(padre,url,new Dimension(550,300));

    }
    
    @Override
    protected void iniciar_componentes(){

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        combo_tipo_documento = new JComboBox<>();
        jLabel2 = new JLabel();
        text_documento = new JTextField();
        jLabel3 = new JLabel();
        text_nombre = new JTextField();
        jLabel4 = new JLabel();
        text_celular = new JTextField();
        jLabel5 = new JLabel();
        combo_departamento = new JComboBox<>();
        jLabel6 = new JLabel();
        combo_municipio = new JComboBox<>();
        jLabel7 = new JLabel();
        text_direccion = new JTextField();
        boton_guardar = new JButton();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel1.setText("Tipo Documento");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(17, 6, 103, 16);

        base = new Base(url);
        try{
            combo_tipo_documento = new JComboBox<>(base.consultar_tipo_id(1));
            combo_departamento = new JComboBox<>(base.consultar_departamento());
            combo_municipio = new JComboBox<>(base.consultar_ciudad(""+combo_departamento.getSelectedItem(), 1));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            this.setVisible(false);
        }
        base.close();
        jPanel1.add(combo_tipo_documento);
        combo_tipo_documento.setBounds(17, 28, 103, 22);

        jLabel2.setText("Numero");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(166, 6, 69, 16);

        text_documento.setText("");
        jPanel1.add(text_documento);
        text_documento.setBounds(166, 28, 120, 22);

        jLabel3.setText("Nombre o Razon Social");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(17, 68, 150, 16);

        text_nombre.setText("");
        jPanel1.add(text_nombre);
        text_nombre.setBounds(17, 90, 220, 22);

        jLabel4.setText("Numero Celular");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(316, 6, 96, 16);

        text_celular.setText("");
        jPanel1.add(text_celular);
        text_celular.setBounds(316, 28, 90, 22);

        jLabel5.setText("Departamento");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(292, 68, 90, 16);

        combo_departamento.addActionListener(accion ->{
            base = new Base(url);
            try{
                String dato[] = base.consultar_ciudad((String)combo_departamento.getSelectedItem(), 1);
                combo_municipio.removeAllItems();
                for(String valor : dato){
                    combo_municipio.addItem(valor);
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                base.close();
            }
            base.close();
        });
        jPanel1.add(combo_departamento);
        combo_departamento.setBounds(292, 90, 120, 22);

        jLabel6.setText("Municipio");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(17, 124, 54, 16);

        combo_municipio.setMaximumRowCount(5);
        jPanel1.add(combo_municipio);
        combo_municipio.setBounds(17, 146, 124, 22);

        jLabel7.setText("Direccion");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(168, 124, 100, 16);

        text_direccion.setText("");
        jPanel1.add(text_direccion);
        text_direccion.setBounds(168, 146, 226, 22);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(17, 200, 100, 23);
        boton_guardar.addActionListener(accion ->{
            boolean band = true;
            String mostrar = "Los campos:\n";
            int tipo_documento,ciudad;
            long celular = 0, documento = 0;

            if(text_documento.getText().equals("")){
                band = false;
                mostrar.concat("Documento\n");
            }
            if(text_nombre.getText().equals("")){
                band = false;
                mostrar.concat("Nombre o Razon Social\n");
            }
            if(text_celular.getText().equals("")){
                band = false;
                mostrar.concat("Celular\n");
            }
            if(text_direccion.getText().equals("")){
                band = false;
                mostrar.concat("Direccion\n");
            }
            mostrar.concat("Son Obligatorios.");

            if(!band){
                JOptionPane.showMessageDialog(this, mostrar, "Error",JOptionPane.ERROR_MESSAGE);
                this.setVisible(false);
            }else{
                try{
                    celular = Long.parseLong(text_celular.getText());
                    documento = Long.parseLong(text_documento.getText());
                    tipo_documento = combo_tipo_documento.getSelectedIndex() + 1;
                    base = new Base(url);
                    try {
                        ciudad = Integer.parseInt(base.consultar_uno_ciudad((String)combo_municipio.getSelectedItem())[0]);
                        base.insertar_persona(text_documento.getText(),tipo_documento,text_nombre.getText(),text_celular.getText(),ciudad,text_direccion.getText());
                        base.close();
                        JOptionPane.showMessageDialog(this, "Persona Insertada correctamente");
                        this.setVisible(false);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        base.close();
                        this.setVisible(false);
                    }
                    
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "Los campos:\nDocumento y Celular\nDeben ser datos de tipo Entero","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        pack();
    }

}
