package Front.Panel.Extractos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Base.Contrato_mensual;
import java.awt.event.WindowEvent;
import java.io.IOException;

import Front.Extractos.Insertar_contrato_mensual;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;

public class Panel_contratos_mensuales extends Panel{
    
    private Contrato_mensual base_contrato;

    public Panel_contratos_mensuales(){
        super();
    }

    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_contrato = new Contrato_mensual();   // Hace una coneccion a la base de datos
            tabla = Modelo_tabla.set_tabla_contratos_mensuales( // Pone un formato para la tabla
                base_contrato.consultar_contratos_mensuales("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_contrato != null) base_contrato.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {

        
        try{
            base_contrato = new Contrato_mensual();

            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_contratos_mensuales(
                base_contrato.consultar_contratos_mensuales(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_contrato != null) base_contrato.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(_ ->{

            new Insertar_contrato_mensual((JFrame)this.get_window()).setVisible(true);

            accion_text_busqueda();

        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog((JFrame)this.get_window(), "Esta seguro de eliminar el contrato:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_contrato = new Contrato_mensual();
                    base_contrato.eliminar_contrato_mensual(Integer.parseInt(id));
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog((JFrame)this.get_window(),ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_contrato != null) base_contrato.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)this.get_window(), "Contrato eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });

        pop_menu.remove(item_actualizar);

    }
}
