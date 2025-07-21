package Front.Vehiculos;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Base.Clase_vehiculo;

public class Actualizar_tipo_vehiculo extends Modales_vehiculos{
    
    private int id;  
    private Clase_vehiculo base;
    public Actualizar_tipo_vehiculo(JFrame padre, String valor){

        super(padre, valor);
        
        setVisible(true);
    }

    protected void iniciar_componentes(){
        JPanel panel = new JPanel(null);
        JLabel segundo;
        JButton boton_actualizacion;
        JTextField tipo_vehiculo;
        String [] valores;
        
        // incializacion de valores
        valores = new String[2];

        
        try{
            base = new Clase_vehiculo();
            valores = ((Clase_vehiculo)base).consultar_uno_clase_vehiculo(valor);
        }catch(SQLException | IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }finally{
            if(base != null) base.close();
        }

        id = Integer.parseInt(valores[0]);
        base.close();

        segundo = new JLabel("Tipo Vehiculo");
        segundo.setBounds(25,25,250,20);

        tipo_vehiculo = new JTextField(valores[1]);
        tipo_vehiculo.setBounds(25,segundo.getY() + segundo.getHeight() +10,300,20);
        

        boton_actualizacion = new JButton("Guardar");
        boton_actualizacion.setBounds(25,530,100,20);
        boton_actualizacion.addActionListener(_ ->{
            
            

            try{
                base = new Clase_vehiculo();
                base.actualizar_clase_vehiculo(id, tipo_vehiculo.getText().toUpperCase());
                JOptionPane.showMessageDialog(this, "Tipo de id: " + id + "\nActualizado con Exito.","Actualizacion", JOptionPane.INFORMATION_MESSAGE);
            

            }catch(SQLException | IOException ex){
                JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }finally{
                if(base != null) base.close();
            }
            this.dispose();
        });
        
        panel.add(boton_actualizacion);
        panel.add(segundo);
        panel.add(tipo_vehiculo);
        add(panel);

        pack();
    }
}
