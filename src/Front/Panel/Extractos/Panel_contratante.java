package Front.Panel.Extractos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import Base.Contratante;
import Front.Extractos.Actualizar_contratante;
import Front.Extractos.Insertar_contratante;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;

public class Panel_contratante extends Panel{
    
    private Contratante base_contratante;

    public Panel_contratante(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {
        base_contratante = new Contratante(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_contratante( // Pone un formato para la tabla
                base_contratante.consultar_contratante("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_contratante.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_contratante = new Contratante(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_contratante(
                base_contratante.consultar_contratante(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(window), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_contratante.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_contratante((JFrame)window, url,(String) tabla.getValueAt(select_row, 0)).setVisible(true);
            accion_text_busqueda();

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_contratante((JFrame)window, url).setVisible(true);

            accion_text_busqueda();

        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog((JFrame)window, "Esta seguro de eliminar al contratante:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_contratante = new Contratante(url);
                try{
                    base_contratante.eliminar_contratante(id);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog((JFrame)window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_contratante.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)window, "Contratante eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
