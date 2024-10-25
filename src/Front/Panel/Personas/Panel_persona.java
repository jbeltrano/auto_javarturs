package Front.Panel.Personas;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import Base.Persona;
import Front.Panel.Panel;
import Front.Personas.Actualizar_peronas;
import Front.Personas.Insertar_persona;
import Utilidades.Modelo_tabla;

public class Panel_persona extends Panel{
    
    private Persona base_persona;

    public Panel_persona(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {
        base_persona = new Persona(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_personas( // Pone un formato para la tabla
                base_persona.consultar_persona() // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_persona.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_persona = new Persona(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_personas(
                base_persona.consultar_persona(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_persona.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_peronas((JFrame)window, url, "" + tabla.getValueAt(select_row, 0)).setVisible(true);
            accion_text_busqueda();
            

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_persona((JFrame)window, url).setVisible(true);
            accion_text_busqueda();

        });

        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(window, "Esta seguro de eliminar la persona:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_persona = new Persona(url);
                try{
                    base_persona.eliminar_persona(valor);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_persona.close();
                }

                JOptionPane.showMessageDialog(window, "Persona eliminada correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
