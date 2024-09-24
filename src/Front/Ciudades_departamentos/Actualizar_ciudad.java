package Front.Ciudades_departamentos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Ciudad;

public class Actualizar_ciudad extends Insertar_ciudad{

    private int id_ciudad;
    private String ciudad;
    private String departamento;

    public Actualizar_ciudad(JFrame padre, String url, String ciudad, String departamento, int id_ciudad){
        super(padre, url);

        this.ciudad = ciudad;
        this.departamento = departamento;
        this.id_ciudad = id_ciudad;

        reconfigurar();

    }

    private void reconfigurar(){

        combo_departamento.setSelectedItem(departamento);
        //combo_departamento.setEnabled(false);

        text_ciudad.setText(ciudad);

        // Implementar la parte para el boton guardar en la ventana actualizar

    }
    
    @Override
    protected void add_listener_boton_guardar(){
        boton_guardar.addActionListener(accion ->{
            if(text_ciudad.getText().compareTo("") == 0){
                JOptionPane.showMessageDialog(this, "Por favor diligenciar el campo Ciudad");
            }else{
                
                base = new Ciudad(url);
                try{

                    ((Ciudad)base).actualizar_ciudad(id_ciudad, text_ciudad.getText());
                    ((Ciudad)base).actualizar_ciudad(id_ciudad, text_ciudad.getText(), (String) combo_departamento.getSelectedItem());
                    base.close();
                    JOptionPane.showMessageDialog(this, "Ciudad actualizada con exito");
                    setVisible(false);

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    base.close();
                }
            }
        });
    }
    
}
