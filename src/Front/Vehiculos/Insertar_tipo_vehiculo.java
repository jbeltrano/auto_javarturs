package Front.Vehiculos;

import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import Base.Clase_vehiculo;


public class Insertar_tipo_vehiculo extends Modales_vehiculos{
    
    private Clase_vehiculo base;

    public Insertar_tipo_vehiculo(JFrame frame, String url, String valor){

        super(frame, url, valor);
        
        setVisible(true);
    }

    protected void iniciar_componentes(){
        JPanel panel = new JPanel(null);
        JLabel primero;
        JButton boton_actualizacion;
        JTextField tipo_vehiculo;

        primero = new JLabel("Ingrese el tipo de vehiculo");
        primero.setBounds(25,10,250,20);

        tipo_vehiculo = new JTextField("");
        tipo_vehiculo.setBounds(25, 40,300,20);

        boton_actualizacion = new JButton("Guardar");
        boton_actualizacion.setBounds(25,530,100,20);
        boton_actualizacion.addActionListener(_ ->{
        

            base = new Clase_vehiculo(url);
            
            if(tipo_vehiculo.getText().length() >0){

                try{
                    base.insertar_clase_vehiculo(tipo_vehiculo.getText().toUpperCase());
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }

                base.close();

                JOptionPane.showMessageDialog(this, "Tipo vehiculo insertado con exito.","Insercion", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
            
        });        

        panel.add(boton_actualizacion);
        panel.add(tipo_vehiculo);
        panel.add(primero);
        add(panel);
        pack();
    }
}
