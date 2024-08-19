package Front.Ciudades_departamentos;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import Base.Base;
import Base.Ciudad;
import Base.Departamento;

public class Insertar_ciudad extends Modal_ciudades_departamentos{

    protected Base base;
    private final int POSICION_X = 10;
    private final int ALTURA_Y = 20;
    private final int SEPARACION_Y = 10;
    private final int SEPARACION_X = 40;
    protected JButton boton_guardar;
    protected JComboBox<String> combo_departamento;
    private JLabel jLabel1;
    private JLabel jLabel2;
    protected JPanel panel;
    protected JTextField text_ciudad;


    public Insertar_ciudad(JFrame padre, String url){
        super(padre ,url);
    }

    public Insertar_ciudad(JDialog padre, String url){
        super(padre ,url);
    }
    
    @Override               
    protected void iniciar_componentes() {
    
        panel = new JPanel();
        boton_guardar = new JButton();
        jLabel1 = new JLabel();
        combo_departamento = new JComboBox<>();
        jLabel2 = new JLabel();
        text_ciudad = new JTextField();
    
        panel.setLayout(null);
    
    
        jLabel1.setText("Departamento");
        panel.add(jLabel1);
        jLabel1.setBounds(POSICION_X, POSICION_X, 96, ALTURA_Y);
    
        // Cargando los datos necesarios para el comobo_departamento
        base = new Departamento(url);
        try{
            combo_departamento.setModel(new DefaultComboBoxModel<>(((Departamento)base).consultar_departamento()));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base.close();
        }
        
        combo_departamento.setSelectedItem("Meta");
        combo_departamento.setMaximumRowCount(7);
        panel.add(combo_departamento);
        combo_departamento.setBounds(POSICION_X, jLabel1.getY() + ALTURA_Y + SEPARACION_Y, 100, ALTURA_Y);
    
        jLabel2.setText("Municipio o localidad de un municipio");
        panel.add(jLabel2);
        jLabel2.setBounds(jLabel1.getX() + jLabel1.getWidth() + SEPARACION_X, POSICION_X, 230, ALTURA_Y);
        panel.add(text_ciudad);


        text_ciudad.setBounds(jLabel2.getX(), jLabel2.getY() + ALTURA_Y + SEPARACION_Y, 205, ALTURA_Y);
    

        boton_guardar.setText("Guardar");
        panel.add(boton_guardar);
        boton_guardar.setBounds(POSICION_X, 179, 90, ALTURA_Y);
        add_listener_boton_guardar();


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    
        pack();
    }                 
                 
    protected void add_listener_boton_guardar(){
        boton_guardar.addActionListener(accion ->{
            if(text_ciudad.getText().compareTo("") == 0){
                JOptionPane.showMessageDialog(this, "Por favor diligenciar el campo Ciudad");
            }else{
                
                base = new Ciudad(url);
                try{

                    ((Ciudad)base).insertar_ciudad(text_ciudad.getText(), (String) combo_departamento.getSelectedItem());
                    base.close();
                    JOptionPane.showMessageDialog(this, "Ciudad insertada con exito");
                    setVisible(false);

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    base.close();
                }
            }
        });
    }
    
}

