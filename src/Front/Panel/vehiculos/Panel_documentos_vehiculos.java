package Front.Panel.vehiculos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import Base.Documentos;
import Front.Panel.Panel;
import Front.Vehiculos.Actualizar_documento_vehiculo;
import Front.Vehiculos.Insertar_documento_vehiculo;
import Utilidades.Modelo_tabla;
import java.io.IOException;

public class Panel_documentos_vehiculos extends Panel{
    
    private Documentos base_documento;

    public Panel_documentos_vehiculos(){
        super();
    }
    
    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_documento = new Documentos();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_documentos_vehiculos( // Pone un formato para la tabla
                base_documento.consultar_documentos("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_documento != null){
                base_documento.close(); // Cierra la coneccion a la base de datos
            }
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_documento = new Documentos();
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_documentos_vehiculos(
                base_documento.consultar_documentos(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_documento != null){
                base_documento.close(); // Cierra la coneccion a la base de datos
            }
        }
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(_ ->{
            Insertar_documento_vehiculo doc_vehiculo = new Insertar_documento_vehiculo((JFrame)this.get_window(),  "");
            doc_vehiculo.setVisible(true);

            accion_text_busqueda();
        });
        
        item_actualizar.addActionListener(_ ->{
            int number = tabla.getSelectedRow();
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);
            Actualizar_documento_vehiculo doc_vehiculo = new Actualizar_documento_vehiculo((JFrame)this.get_window(),  placa_vehiculo);
            doc_vehiculo.setVisible(true);

            accion_text_busqueda();
        });
        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);


            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el registro\n"+ placa_vehiculo +"|"+tabla.getValueAt(number, 3), "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_documento = new Documentos();
                    base_documento.eliminar_documento(placa_vehiculo);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_documento != null){
                        base_documento.close(); // Cierra la coneccion a la base de datos
                    }
                }

                accion_text_busqueda();
            }
                  
        });

    }    
}