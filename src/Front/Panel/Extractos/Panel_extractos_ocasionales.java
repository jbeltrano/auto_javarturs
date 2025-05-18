package Front.Panel.Extractos;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.WindowEvent;
import Base.Extractos;
import Front.Extractos.Actualizar_extracto_ocasional;
import Front.Extractos.Insertar_extracto_ocasional;
import Front.Panel.Panel_extractos;
import Utilidades.Generar_extractos;
import Utilidades.Modelo_tabla;

public class Panel_extractos_ocasionales extends Panel_extractos{
    
    private Extractos base_extracto;

    public Panel_extractos_ocasionales(String url){
        super(url);
    }


    @Override
    protected void cargar_datos_tabla() {
        base_extracto = new Extractos(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_extractos_ocasionales( // Pone un formato para la tabla
                base_extracto.consultar_vw_extracto_ocasional("")
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_extracto.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_extracto = new Extractos(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_extractos_ocasionales(
                base_extracto.consultar_vw_extracto_ocasional(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_extracto.close();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            int row = tabla.getSelectedRow();
            String placa = (String) tabla.getValueAt(row, 0);
            String consecutivo = (String) tabla.getValueAt(row, 1);
            String contrato = (String) tabla.getValueAt(row, 2);
            // actualizar_extracto
            new Actualizar_extracto_ocasional((JFrame)this.get_window(), url, placa, consecutivo, contrato, false).setVisible(true);
            accion_text_busqueda();
            

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_extracto_ocasional((JFrame)this.get_window(), url).setVisible(true);
            accion_text_busqueda();
        });
        item_plantilla.addActionListener(_ ->{
            int row = tabla.getSelectedRow();
            String placa = (String) tabla.getValueAt(row, 0);
            String consecutivo = (String) tabla.getValueAt(row, 1);
            String contrato = (String) tabla.getValueAt(row, 2);

            new Actualizar_extracto_ocasional((JFrame)this.get_window(), url, placa, consecutivo, contrato, true).setVisible(true);

            accion_text_busqueda();
        });
        item_exportar.addActionListener(_ ->{
            String[] opciones = {"Sí", "No"};
            int band = JOptionPane.showOptionDialog(null, 
                                                "¿Deceas que el contrato muestre la ruta\nmás corta entre el origen y el destnio?", 
                                                "Confirmación", 
                                                JOptionPane.YES_NO_OPTION, 
                                                JOptionPane.QUESTION_MESSAGE, 
                                                null, 
                                                opciones, 
                                                opciones[0]);
            
            
            if(band >= 0){
                boolean flag = (band == 0)?true:false;
                int select_row = tabla.getSelectedRow();
                try{
                    String ruta;
                    String comando_auxiliar = "powershell -ExecutionPolicy ByPass -File \"" + UBICACION_PS_CONVERTIRPDF + "\"" + " -parametro \""+ UBICACION_PS_EXTRACTOS_OCASIONALES + "\"";
                    ruta = Generar_extractos.generar_extracto_ocasional(Integer.parseInt((String) tabla.getValueAt(select_row, 2)), url, flag);
                    
                    runtime.exec(comando_auxiliar);
                    
                    JOptionPane.showMessageDialog((JFrame)this.get_window(), "Extracto guardado con exito.\nUbicacion: " + ruta, "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog((JFrame)this.get_window(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }   
            }
            
        });
        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String placa = "" + tabla.getValueAt(number, 0);
            String consecutivo = "" + tabla.getValueAt(number, 1);
            number = JOptionPane.showConfirmDialog((JFrame)this.get_window(), "Esta seguro de eliminar el extracto " + consecutivo + "\ndel vehiculo "+placa,  "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_extracto = new Extractos(url);
                try{
                    // realizando la eliminacion del registro
                    base_extracto.eliminar_extracto_ocasional(placa, Integer.parseInt(consecutivo));

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog((JFrame)this.get_window(),ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_extracto.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)this.get_window(), "Extracto eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });

        pop_menu.remove(item_exportar_todos);
        pop_menu.remove(item_actualizar_todos);
    }
}
