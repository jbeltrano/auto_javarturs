package Front.Panel.vehiculos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import Base.Vehiculo;
import Front.Panel.Panel;
import Front.Vehiculos.Actualizar_vehiculos;
import Front.Vehiculos.Insertar_vehiculos;
import Utilidades.Modelo_tabla;

public class Panel_vehiculos extends Panel{

    private Vehiculo base_Vehiculo;

    public Panel_vehiculos(String url){
        super(url);
    }
    
    @Override
    protected void cargar_datos_tabla() {
        base_Vehiculo = new Vehiculo(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_vehiculo( // Pone un formato para la tabla
                base_Vehiculo.consultar_vehiculo("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(this, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_Vehiculo.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_Vehiculo = new Vehiculo(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_vehiculo(
                base_Vehiculo.consultar_vehiculo(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_Vehiculo.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(accion ->{
            new Insertar_vehiculos((JFrame)window, url, "").setVisible(true);

            accion_text_busqueda();
        });
        
        item_actualizar.addActionListener(accion ->{
            int select_row = tabla.getSelectedRow();

            new Actualizar_vehiculos((JFrame)window, url, (String)tabla.getValueAt(select_row, 0));

            accion_text_busqueda();
        });
        item_eliminar.addActionListener(accion ->{

            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el vehiculo:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_Vehiculo = new Vehiculo(url);
                try{
                    base_Vehiculo.eliminar_vehiculo(valor);
                    JOptionPane.showMessageDialog(this, "Vehiculo eliminado correctamente");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_Vehiculo.close();
                }
                
                accion_text_busqueda();
            }
                  
        });

    }    

}