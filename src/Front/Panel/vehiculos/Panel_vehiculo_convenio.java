package Front.Panel.vehiculos;

import Front.Panel.Panel;
import Front.Vehiculos.Insertar_vh_convenio;

import java.sql.SQLException;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Base.Vh_convenio;
import Utilidades.Modelo_tabla;

public class Panel_vehiculo_convenio extends Panel{
    
    protected Vh_convenio base_vh_convenio;

    public Panel_vehiculo_convenio(String url){
        super(url);
        item_actualizar.setEnabled(false);
    }

    @Override
    protected void cargar_datos_tabla(){
        
        base_vh_convenio = new Vh_convenio(url);
        try{
            tabla = Modelo_tabla.set_tabla_vh_convenio( // Se encarga de establecer la tabla por personalizada a mostrar en el programa
                base_vh_convenio.consultar_vh_convenio("")    // Es el metodo que retorna los datos consultados
            );
        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_vh_convenio.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_vh_convenio = new Vh_convenio(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_vh_convenio(
                base_vh_convenio.consultar_vh_convenio(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_vh_convenio.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {

        item_adicionar.addActionListener(_ ->{

            new Insertar_vh_convenio((JFrame)window, url).setVisible(true);
            accion_text_busqueda();
        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String placa = "" + tabla.getValueAt(number, 0);
            String nombre_empresa = (String) tabla.getValueAt(number, 3);

            number = JOptionPane.showConfirmDialog((JFrame)window, "Esta seguro de eliminar el este convenio " + "\nVehiculo: "+placa + "\nEmpresa: " + nombre_empresa,  "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_vh_convenio = new Vh_convenio(url);
                try{
                    // realizando la eliminacion del registro
                    base_vh_convenio.eliminar_vh_convenio(placa);

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog((JFrame)window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_vh_convenio.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)window, "Extracto eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });
    }
}
