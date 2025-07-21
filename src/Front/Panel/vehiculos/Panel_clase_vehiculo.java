package Front.Panel.vehiculos;

import Base.Clase_vehiculo;
import Front.Panel.Panel;
import Front.Vehiculos.Actualizar_tipo_vehiculo;
import Front.Vehiculos.Insertar_tipo_vehiculo;
import Utilidades.Modelo_tabla;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Panel_clase_vehiculo extends Panel{
    
    private Clase_vehiculo base_clase_vehiculo;

    public Panel_clase_vehiculo(){
        super();
    }
    
    @Override
    protected void cargar_datos_tabla() {

        DefaultTableModel modelo;
        String[][] datos = null;
        TableColumnModel cl_model;

        
        try{
            base_clase_vehiculo = new Clase_vehiculo();

            datos = base_clase_vehiculo.consultar_clase_vehiculo();
            modelo = Modelo_tabla.set_modelo_tablas(datos);
            tabla = new JTable(modelo);
            tabla.setComponentPopupMenu(pop_menu);
            tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tabla.setComponentPopupMenu(pop_menu);
            tabla.getTableHeader().setReorderingAllowed(false); 
            tabla.setCellSelectionEnabled(true);
            Modelo_tabla.add_mouse_listener(tabla);
            tabla.setShowGrid(true);  // Se encarga de mostrar las lineas de la tabla
            tabla.setGridColor(new Color(66, 73, 73));
            
            cl_model = tabla.getColumnModel();
            cl_model.getColumn(0).setPreferredWidth(35);
            cl_model.getColumn(1).setPreferredWidth(200);
        }catch(SQLException | IOException ex){
            
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);
            
            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_clase_vehiculo != null) base_clase_vehiculo.close();
        }


        
    }

    @Override
    protected void accion_text_busqueda() {
        
    }

    @Override
    protected void config_listener_pop_menu() {
        
        item_actualizar.addActionListener(_->{
            
            int numero = tabla.getSelectedRow();
            new Actualizar_tipo_vehiculo((JFrame)this.get_window(), ""+tabla.getValueAt(numero, 0));
            cargar_datos_tabla();
        });
        item_adicionar.addActionListener(_ ->{
            new Insertar_tipo_vehiculo((JFrame)this.get_window(), "");
            cargar_datos_tabla();

        });
        item_eliminar.addActionListener(_ ->{
            
            int number = tabla.getSelectedRow();
            String valor = "" + tabla.getValueAt(number, 0);

            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el item\n"+ valor, "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){
                
                try{
                    base_clase_vehiculo = new Clase_vehiculo();

                    base_clase_vehiculo.eliminar_clase_vehiculo(Integer.parseInt(valor));
                    JOptionPane.showMessageDialog(this, "Item eliminado correctamente");
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_clase_vehiculo != null) base_clase_vehiculo.close();
                }
                cargar_datos_tabla();
            }
                  
        });

    }
    
}
