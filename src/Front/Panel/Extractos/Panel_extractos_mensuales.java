package Front.Panel.Extractos;

import java.sql.SQLException;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Extractos;
import Front.Extractos.Actualizar_extracto_mensual;
import Front.Extractos.Actualizar_todo_ext_mensual;
import Front.Extractos.Insertar_extracto_mensual;
import Front.Panel.Panel_extractos;
import Utilidades.Generar_extractos;
import Utilidades.Modelo_tabla;
import java.io.IOException;

public class Panel_extractos_mensuales extends Panel_extractos{
    
    protected Extractos base_extractos;

    public Panel_extractos_mensuales(){
        super();
    }


    @Override
    protected void cargar_datos_tabla() {
        
        try{
            base_extractos = new Extractos();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_extractos_mensuales( // Pone un formato para la tabla
                base_extractos.consultar_vw_extracto_mensual("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_extractos != null) base_extractos.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        
        try{
            base_extractos = new Extractos();

            Modelo_tabla.updateTableModel(tabla, base_extractos.consultar_vw_extracto_mensual(text_busqueda.getText()));

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(window, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_extractos != null) base_extractos.close();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            int row = tabla.getSelectedRow();
            // actualizar_extracto
            new Actualizar_extracto_mensual((JFrame)this.get_window(),(String) tabla.getValueAt(row, 0), Integer.parseInt((String)tabla.getValueAt(row, 1)),false).setVisible(true);
            accion_text_busqueda();

        });
        item_plantilla.addActionListener(_ ->{

            int row = tabla.getSelectedRow();
            // actualizar_extracto
            new Actualizar_extracto_mensual((JFrame)this.get_window(),(String) tabla.getValueAt(row, 0), Integer.parseInt((String)tabla.getValueAt(row, 1)),true).setVisible(true);
            accion_text_busqueda();

        });
        item_adicionar.addActionListener(_ ->{

            new Insertar_extracto_mensual((JFrame)this.get_window()).setVisible(true);
            accion_text_busqueda();
        });

        item_exportar.addActionListener(_ ->{
            int select_row = tabla.getSelectedRow();

            try{
                String ruta;
                ruta = Generar_extractos.generar_extracto_mensual_excel((String) tabla.getValueAt(select_row, 0),Integer.parseInt((String) tabla.getValueAt(select_row, 1)));
                String comando_auxiliar = "powershell -ExecutionPolicy ByPass -File \"" + UBICACION_PS_CONVERTIRPDF + "\"" + " -parametro \""+ UBICACION_PS_EXTRACTOS_MENSUALES + "\"";
                    
                Process proceso = runtime.exec(comando_auxiliar);
                // implementar funcion que muestre que esperar por favor mientras carga la barra de proceso<
                JOptionPane.showMessageDialog(null, "Iniciando la exportacion\nPor favor espere...");
                proceso.waitFor();
                JOptionPane.showMessageDialog((JFrame)this.get_window(), "Extracto guardado con exito.\nUbicacion: " + ruta, "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog((JFrame)this.get_window(), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String placa = "" + tabla.getValueAt(number, 0);
            String consecutivo = "" + tabla.getValueAt(number, 1);
            number = JOptionPane.showConfirmDialog((JFrame)this.get_window(), "Esta seguro de eliminar el extracto " + consecutivo + "\ndel vehiculo "+placa,  "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_extractos = new Extractos();

                    // realizando la eliminacion del registro
                    base_extractos.eliminar_extracto_mensual(placa, Integer.parseInt(consecutivo));

                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog((JFrame)this.get_window(),ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_extractos != null) base_extractos.close();
                }
                
                JOptionPane.showMessageDialog((JFrame)this.get_window(), "Extracto eliminado correctamente");
                accion_text_busqueda();
            }
                  
        });

        item_exportar_todos.addActionListener(_ ->{
            String placa;
            String consecutivo;
            try{

                for(int i = 0; i < tabla.getRowCount(); i++){
                    placa = (String) tabla.getValueAt(i, 0);
                    consecutivo = (String) tabla.getValueAt(i, 1);
                    Generar_extractos.generar_extracto_mensual_excel(placa, Integer.parseInt(consecutivo));
                }
                String comando_auxiliar = "powershell -ExecutionPolicy ByPass -File \"" + UBICACION_PS_CONVERTIRPDF + "\"" + " -parametro \""+ UBICACION_PS_EXTRACTOS_MENSUALES + "\"";
                runtime.exec(comando_auxiliar);
                
                JOptionPane.showMessageDialog((JFrame)this.get_window(), "Extractos guardados con exito.", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog((JFrame)this.get_window(), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        });

        item_actualizar_todos.addActionListener(_ -> {

            new Actualizar_todo_ext_mensual((JFrame)this.get_window()).setVisible(true);

            accion_text_busqueda();

        });

    }
}
