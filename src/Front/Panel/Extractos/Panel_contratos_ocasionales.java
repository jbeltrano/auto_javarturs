package Front.Panel.Extractos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.WindowEvent;
import Base.BContrato_ocasional;
import Front.Extractos.Actualizar_contrato_ocasional;
import Front.Extractos.Insertar_contrato_ocasional;
import Front.Panel.Panel_extractos;
import Utilidades.Generar_extractos;
import Utilidades.Modelo_tabla;

public class Panel_contratos_ocasionales extends Panel_extractos{
    
    private BContrato_ocasional base_contrato;

    public Panel_contratos_ocasionales(String url){
        super(url);
    }

    @Override
    protected void cargar_datos_tabla() {
        base_contrato = new BContrato_ocasional(url);   // Hace una coneccion a la base de datos
        try{
            tabla = Modelo_tabla.set_tabla_contratos_ocasionales( // Pone un formato para la tabla
                base_contrato.consultar_contrato_ocasional("")
            );

        }catch(SQLException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(window, ex.getMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            base_contrato.close();
        }   
    }

    @Override
    protected void accion_text_busqueda() {
        base_contrato = new BContrato_ocasional(url);
        try{
            // Obtiene los datos y crea una tabla auxiliar con los datos proporcionados por el text Field
            JTable tabla_aux = Modelo_tabla.set_tabla_contratos_ocasionales(
                base_contrato.consultar_contrato_ocasional(text_busqueda.getText())
            );

            // Estos metodos se encargan que el formato de la tabla se aplique sin afectar sus propiedades
            tabla.setModel(tabla_aux.getModel());
            tabla.setColumnModel(tabla_aux.getColumnModel());

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base_contrato.close();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void config_listener_pop_menu() {
        
        item_adicionar.addActionListener(_ ->{

            new Insertar_contrato_ocasional((JFrame)window, url).setVisible(true);

            accion_text_busqueda();

        });

        item_actualizar.addActionListener(_ ->{
            int number = tabla.getSelectedRow();
            int id = Integer.parseInt((String)tabla.getValueAt(number, 0));

            new Actualizar_contrato_ocasional((JFrame)window, url, id, false).setVisible(true);

            accion_text_busqueda();

        });

        item_exportar.addActionListener(_ ->{

            int row = tabla.getSelectedRow();
            int num_contrato = Integer.parseInt((String)tabla.getValueAt(row, 0));
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
                try{
                    String ruta = Generar_extractos.generar_extracto_ocasional(num_contrato, url, flag);
                    JOptionPane.showMessageDialog((JFrame)window, "Exportando el contrato N° " + num_contrato + ", Junto \na sus extractos correspondientes. \n\nPor favor espere...");
                    
                    String comando_auxiliar = "powershell -ExecutionPolicy ByPass -File \"" + UBICACION_PS_CONVERTIRPDF + "\"" + " -parametro \""+ UBICACION_PS_EXTRACTOS_OCASIONALES + "\"";
                    
                    int proceso = runtime.exec(comando_auxiliar).waitFor();
    
                    if(proceso == 0){
                        JOptionPane.showMessageDialog((JFrame)window, "Proceso finalizado con exito.\nRuta de los documentos: "+ ruta);
                    }else{
                        JOptionPane.showMessageDialog((JFrame)window, "El proceso no pudo ser finalizado con exito. Error code: " + proceso);
                    }
                    
    
                }catch(Exception ex){
                    JOptionPane.showMessageDialog((JFrame)window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
            
        });
        item_plantilla.addActionListener(_ ->{

            int number = tabla.getSelectedRow();
            int id = Integer.parseInt((String)tabla.getValueAt(number, 0));

            new Actualizar_contrato_ocasional((JFrame)window, url, id, true).setVisible(true);

            accion_text_busqueda();
        });

        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String id = "" + tabla.getValueAt(number, 0);
            String nombre = "" + tabla.getValueAt(number, 2);
            number = JOptionPane.showConfirmDialog((JFrame)window, "Esta seguro de eliminar el contrato:\n"+ id + ", " + nombre, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                base_contrato = new BContrato_ocasional(url);
                try{
                    base_contrato.eliminar_contrato_ocasional(Integer.parseInt(id));
                    JOptionPane.showMessageDialog((JFrame)window, "Contrato eliminado correctamente");
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog((JFrame)window,ex,"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    base_contrato.close();
                    accion_text_busqueda();
                }
            }   
        });


        pop_menu.remove(item_exportar_todos);
        pop_menu.remove(item_actualizar_todos);
    }
}
