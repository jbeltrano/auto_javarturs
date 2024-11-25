package Front.Panel.Ciudades;

import Base.Departamento;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.awt.event.WindowEvent;
import javax.swing.JTable;

public class Panel_departamento extends Panel{
    
    private Departamento base_Departamento;
    public Panel_departamento(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {
        base_Departamento = new Departamento(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_departamento(      // Pone un formato para la tabla
                base_Departamento.consultar_departamentos()   // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_Departamento.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_Departamento = new Departamento(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_departamento(
                base_Departamento.consultar_departamentos(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_Departamento.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {

    }

    
}
