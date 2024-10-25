package Front.Panel.Extractos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Base.Contrato_mensual;
import java.awt.event.WindowEvent;
import Front.Extractos.Insertar_contrato_mensual;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;

public class Panel_contratos_mensuales extends Panel{
    
    private Contrato_mensual base_contrato;

    public Panel_contratos_mensuales(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {
        base_contrato = new Contrato_mensual(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_contratos_mensuales( // Pone un formato para la tabla
                base_contrato.consultar_contratos_mensuales("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_contrato.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {

        base_contrato = new Contrato_mensual(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_contratos_mensuales(
                base_contrato.consultar_contratos_mensuales(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_contrato.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(accion ->{

            new Insertar_contrato_mensual((JFrame)window, url).setVisible(true);

            accion_text_busqueda();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog((JFrame)window, "Esta seguro de eliminar el contrato:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_contrato = new Contrato_mensual(url);
                try{
                    base_contrato.eliminar_contrato_mensual(Integer.parseInt(id));
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog((JFrame)window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_contrato.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)window, "Contrato eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });

        pop_menu.remove(item_actualizar);

    }
}
