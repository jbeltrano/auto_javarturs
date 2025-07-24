package Front.Panel.Extractos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import java.io.IOException;

import Base.Contratante;
import Front.Extractos.Actualizar_contratante;
import Front.Extractos.Insertar_contratante;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;

public class Panel_contratante extends Panel{
    
    private Contratante base_contratante;

    public Panel_contratante(){
        super();
    }

    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_contratante = new Contratante();   // Hace una coneccion a la base de datos
            tabla = Modelo_tabla.set_tabla_contratante( // Pone un formato para la tabla
                base_contratante.consultar_contratante("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_contratante != null) base_contratante.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_contratante = new Contratante();

            Modelo_tabla.updateTableModel(tabla, base_contratante.consultar_contratante(text_busqueda.getText()));

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(window), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_contratante != null) base_contratante.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_contratante((JFrame)this.get_window(),(String) tabla.getValueAt(select_row, 0)).setVisible(true);
            accion_text_busqueda();

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_contratante((JFrame)this.get_window()).setVisible(true);

            accion_text_busqueda();

        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog((JFrame)this.get_window(), "Esta seguro de eliminar al contratante:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_contratante = new Contratante();
                    base_contratante.eliminar_contratante(id);
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog((JFrame)this.get_window(),ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_contratante != null) base_contratante.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)this.get_window(), "Contratante eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
