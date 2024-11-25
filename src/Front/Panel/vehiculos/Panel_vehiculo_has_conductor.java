package Front.Panel.vehiculos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import Base.Vehiculo_has_conductor;
import Front.Panel.Panel;
import Front.Vehiculos.Insertar_vehiculo_conductor;
import Utilidades.Modelo_tabla;
import java.awt.event.WindowEvent;

public class Panel_vehiculo_has_conductor extends Panel{

    private Vehiculo_has_conductor base_vehiculo_has_conductor;

    public Panel_vehiculo_has_conductor(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {

        base_vehiculo_has_conductor = new Vehiculo_has_conductor(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_vehiculo_has_conductor(             // Pone un formato para la tabla
                base_vehiculo_has_conductor.consultar_conductor_has_vehiculo() // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(this, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_vehiculo_has_conductor.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_vehiculo_has_conductor = new Vehiculo_has_conductor(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_documentos_vehiculos(
                base_vehiculo_has_conductor.consultar_conductor_has_vehiculo(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_vehiculo_has_conductor.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(_ ->{
            // Cuando se adicione un valor, simplemente se llama al metodo que se encarga de la insercion
            new Insertar_vehiculo_conductor((JFrame)window, url, "");
            
            // Posteriormente se cargan los datos teniendo en cuenta la ultima busqueda del usuario
            accion_text_busqueda();
        });
        
        item_eliminar.addActionListener(_ ->{
            
            /**
             * Cuando se vaya a eliminar, simplemente se selecciona la fila a aliminar
             * Una vez se tenga eso se pasan los identificadores del registro para realizar
             * la eliminacion en la base de datos.
             */
            int number = tabla.getSelectedRow();    // Obtiene la fila seleccionada
            String conductor_id = "" + tabla.getValueAt(number, 2); // Obtiene el id del conductor
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);   // Obtiene el id del vehiculo

            // Determina si enserio el usuario esta seguro de querer eliminar dicho registro
            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el registro\n"+ placa_vehiculo +"|"+tabla.getValueAt(number, 3), "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){    // En caso de ser un si, se procede con la eliminacion del registro
                
                base_vehiculo_has_conductor = new Vehiculo_has_conductor(url);
                try{
                    base_vehiculo_has_conductor.eliminar_vehiculo_has_conductor(conductor_id,placa_vehiculo);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");

                    accion_text_busqueda();

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_vehiculo_has_conductor.close();
                }

            }
                  
        });
    }

}
