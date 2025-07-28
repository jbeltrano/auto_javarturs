package Front.Panel.vehiculos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import java.io.IOException;
import Base.Vehiculo;
import Front.Panel.Panel;
import Front.Vehiculos.Actualizar_vehiculos;
import Front.Vehiculos.Insertar_vehiculos;
import Utilidades.Modelo_tabla;

public class Panel_vehiculos extends Panel{

    private Vehiculo base_Vehiculo;

    public Panel_vehiculos(){
        super();
    }
    
    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_Vehiculo = new Vehiculo();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_vehiculo( // Pone un formato para la tabla
                base_Vehiculo.consultar_vehiculo("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_Vehiculo != null) base_Vehiculo.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_Vehiculo = new Vehiculo();
            
            Modelo_tabla.updateTableModel(tabla, base_Vehiculo.consultar_vehiculo(text_busqueda.getText())); // Actualiza el modelo de la tabla

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_Vehiculo != null) base_Vehiculo.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(_ ->{
            new Insertar_vehiculos((JFrame)this.get_window(), "").setVisible(true);

            accion_text_busqueda();
        });
        
        item_actualizar.addActionListener(_ ->{
            int select_row = tabla.getSelectedRow();

            new Actualizar_vehiculos((JFrame)this.get_window(), (String)tabla.getValueAt(select_row, 0));

            accion_text_busqueda();
        });
        item_eliminar.addActionListener(_ ->{

            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el vehiculo:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_Vehiculo = new Vehiculo();

                    base_Vehiculo.eliminar_vehiculo(valor);
                    JOptionPane.showMessageDialog(this, "Vehiculo eliminado correctamente");
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_Vehiculo != null) base_Vehiculo.close();
                }
                
                accion_text_busqueda();
            }
                  
        });

    }    

}