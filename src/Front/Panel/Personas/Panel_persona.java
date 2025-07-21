package Front.Panel.Personas;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import java.io.IOException;

import Base.Persona;
import Front.Panel.Panel;
import Front.Personas.Actualizar_peronas;
import Front.Personas.Insertar_persona;
import Utilidades.Modelo_tabla;

public class Panel_persona extends Panel{
    
    private Persona base_persona;

    public Panel_persona(){
        super();
    }

    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_persona = new Persona();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_personas( // Pone un formato para la tabla
                base_persona.consultar_persona() // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_persona != null) base_persona.close(); // Cierra la coneccion a la base de datos
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_persona = new Persona();
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_personas(
                base_persona.consultar_persona(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_persona != null) base_persona.close(); // Cierra la coneccion a la base de datos
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_peronas((JFrame)this.get_window(), "" + tabla.getValueAt(select_row, 0)).setVisible(true);
            accion_text_busqueda();
            

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_persona((JFrame)this.get_window()).setVisible(true);
            accion_text_busqueda();

        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(window, "Esta seguro de eliminar la persona:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_persona = new Persona();
                    
                    base_persona.eliminar_persona(valor);
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_persona != null) base_persona.close(); // Cierra la coneccion a la base de datos
                }

                JOptionPane.showMessageDialog(window, "Persona eliminada correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
