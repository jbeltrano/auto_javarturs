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
import java.io.IOException;

public class Panel_ruta extends Panel{

    private Ruta base_ruta;

    public Panel_ruta(){
        super();
    }
    
    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_ruta = new Ruta();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_ruta( // Pone un formato para la tabla
                base_ruta.consultar_ruta("")     // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_ruta != null) base_ruta.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_ruta = new Ruta();
            
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_ruta(
                base_ruta.consultar_ruta(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_ruta != null) base_ruta.close();
        }
    }

    @Override
    protected void config_listener_pop_menu() {

        item_actualizar.addActionListener(_->{
            int select_row = tabla.getSelectedRow();

            new Actualizar_ruta((JFrame)this.get_window(),  // Obtiene el JFrame del programa principal
                                Integer.parseInt((String) tabla.getValueAt(select_row, 0)), // Este es el id de el origen
                                Integer.parseInt((String) tabla.getValueAt(select_row, 2)), // Este es el id del destino
                                Integer.parseInt((String) tabla.getValueAt(select_row, 4))  // Esta es la distancia entre origen y destino
                                ).setVisible(true);

            accion_text_busqueda();

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_ruta((JFrame)this.get_window()).setVisible(true);
            accion_text_busqueda();

        });
        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String origen = "" + tabla.getValueAt(number, 1);
            String destino = (String) tabla.getValueAt(number, 3);
            int id_origen = Integer.parseInt((String) tabla.getValueAt(number, 0));
            int id_destino = Integer.parseInt((String) tabla.getValueAt(number, 2));

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar la ruta con \norigen: "+ origen + ", y destino: " + destino + ".", "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_ruta = new Ruta();

                    base_ruta.eliminar_ruta(id_origen, id_destino);
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_ruta != null) base_ruta.close();
                }
                
                JOptionPane.showMessageDialog(this, "Ruta eliminada correctamente eliminada correctamente");
                accion_text_busqueda();
            }
                  
        });

    }
}
