package Front.Panel.Ciudades;

import Base.Departamento;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.awt.event.WindowEvent;

import java.io.IOException;

public class Panel_departamento extends Panel{
    
    private Departamento base_Departamento;
    public Panel_departamento(){
        super();
    }

    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_Departamento = new Departamento();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_departamento(      // Pone un formato para la tabla
                base_Departamento.consultar_departamentos()   // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_Departamento != null) base_Departamento.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_Departamento = new Departamento();
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field

            Modelo_tabla.updateTableModel(tabla, base_Departamento.consultar_departamentos(text_busqueda.getText()));
            

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_Departamento != null) base_Departamento.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        /*
         * Esto se hace para que en la itnerfaz no aparesca
         * habilitado estas cosas, puesto que los departamentos
         * van a mantener constantes, a menos que por fuerza mayor
         * haya una clase de mofificacion en la divisicon politica
         * de colombia
         */
        item_actualizar.setEnabled(false);
        item_adicionar.setEnabled(false);
        item_eliminar.setEnabled(false);
    }

    
}
