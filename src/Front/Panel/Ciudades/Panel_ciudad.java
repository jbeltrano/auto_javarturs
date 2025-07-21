package Front.Panel.Ciudades;

import java.sql.SQLException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import Base.Ciudad;
import Front.Ciudades_departamentos.Actualizar_ciudad;
import Front.Ciudades_departamentos.Insertar_ciudad;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;
import java.awt.event.WindowEvent;

public class Panel_ciudad extends Panel{
    
    private Ciudad base_ciudad;

    public Panel_ciudad(){
        super();
    }

    @Override
    protected void cargar_datos_tabla() {
        try{
            base_ciudad = new Ciudad();   // Hace una conexión a la base de datos
            tabla = Modelo_tabla.set_tabla_ciudad( // Pone un formato para la tabla
                base_ciudad.consultar_ciudad()     // Pasa los datos que va a tener la tabla
            );
        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa después del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
        }finally{
            if(base_ciudad != null) base_ciudad.close();
        }
    }

    @Override
    protected void accion_text_busqueda() {
        try{
            base_ciudad = new Ciudad();
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_ciudad(
                base_ciudad.consultar_ciudades(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());
        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_ciudad != null) base_ciudad.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            int select_row = tabla.getSelectedRow();

            
            new Actualizar_ciudad((JFrame)this.get_window(), (String) tabla.getValueAt(select_row, 1), (String) tabla.getValueAt(select_row, 2), Integer.parseInt((String) tabla.getValueAt(select_row, 0))).setVisible(true);
            accion_text_busqueda();
            

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_ciudad((JFrame)this.get_window()).setVisible(true);
            
            accion_text_busqueda();

        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar la ciudad:\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                try{
                    base_ciudad = new Ciudad();
                    base_ciudad.eliminar_ciudad(valor);
                    JOptionPane.showMessageDialog(this, "Ciudad eliminada correctamente");
                    accion_text_busqueda();
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_ciudad != null) base_ciudad.close();
                }
                
                JOptionPane.showMessageDialog(this, "Ciudad eliminada correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
