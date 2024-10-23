package Front.Panel.Ciudades;

import java.sql.SQLException;
import Base.Ruta;
import Front.Ciudades_departamentos.Actualizar_ruta;
import Front.Ciudades_departamentos.Insertar_ruta;
import Front.Panel.Panel;
import Utilidades.Modelo_tabla;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;

public class Panel_ruta extends Panel{

    private Ruta base_ruta;

    public Panel_ruta(String url){
        super(url);
    }
    
    @Override
    protected void cargar_datos_tabla() {
        base_ruta = new Ruta(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_ruta( // Pone un formato para la tabla
                base_ruta.consultar_ruta("")     // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_ruta.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_ruta = new Ruta(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_ruta(
                base_ruta.consultar_ruta(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_ruta.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {

        item_actualizar.addActionListener(accion->{
            int select_row = tabla.getSelectedRow();

            new Actualizar_ruta((JFrame)window, 
                                url, 
                                Integer.parseInt((String) tabla.getValueAt(select_row, 0)), 
                                Integer.parseInt((String) tabla.getValueAt(select_row, 2)),
                                Integer.parseInt((String) tabla.getValueAt(select_row, 4))
                                ).setVisible(true);

            accion_text_busqueda();

        });
        item_adicionar.addActionListener(accion ->{

            new Insertar_ruta((JFrame)window, url).setVisible(true);
            accion_text_busqueda();

        });
        item_eliminar.addActionListener(accion ->{
            
            int number = tabla.getSelectedRow();
            String origen = "" + tabla.getValueAt(number, 1);
            String destino = (String) tabla.getValueAt(number, 3);
            int id_origen = Integer.parseInt((String) tabla.getValueAt(number, 0));
            int id_destino = Integer.parseInt((String) tabla.getValueAt(number, 2));

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar la ruta con \norigen: "+ origen + ", y destino: " + destino + ".", "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_ruta = new Ruta(url);
                try{
                    base_ruta.eliminar_ruta(id_origen, id_destino);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_ruta.close();
                }
                
                JOptionPane.showMessageDialog(this, "Ruta eliminada correctamente eliminada correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
