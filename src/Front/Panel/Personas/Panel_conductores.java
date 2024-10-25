package Front.Panel.Personas;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import Base.Licencia;
import Front.Panel.Panel;
import Front.Personas.Actualizar_conductor;
import Front.Personas.Insertar_conductor;
import Utilidades.Modelo_tabla;

public class Panel_conductores extends Panel{
    
    private Licencia base_licencia;

    public Panel_conductores(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {
        base_licencia = new Licencia(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_conductores( // Pone un formato para la tabla
                base_licencia.consultar_licencia() // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_licencia.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_licencia = new Licencia(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_conductores(
                base_licencia.consultar_licencia(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(window), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_licencia.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_conductor((JFrame)window, url, tabla.getValueAt(select_row, 0) + "");
            accion_text_busqueda();
            

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_conductor((JFrame)window, url).setVisible(true);
            accion_text_busqueda();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String cat = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog(window, "Esta seguro de eliminar al conductor:\n"+ id, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_licencia = new Licencia(url);
                try{
                    number = Integer.parseInt(base_licencia.consultar_uno_categoria(cat) [0]);
                    base_licencia.eliminar_licencia(id, number);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_licencia.close();
                }
                
                JOptionPane.showMessageDialog(window, "Conductor eliminada correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
