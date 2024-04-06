package Utilidades;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;

public class Modelo_tabla {
    
    public static DefaultTableModel set_modelo_tablas(String [][] datos){
        DefaultTableModel modelo;

        
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas no sean editables
                return false;
            }
        };

        for(int i = 0; i < datos[0].length; i++){
            modelo.addColumn(datos[0][i]);
        }
    
        for(int i = 1; i < datos.length; i++){
                
            modelo.addRow(datos[i]);
    
        }
        return modelo;
    }
    
    public static void add_mouse_listener(JTable tabla){

        tabla.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Obtener la fila seleccionada
                    int filaSeleccionada = tabla.rowAtPoint(e.getPoint());
                    int columna = tabla.columnAtPoint(e.getPoint());

                    // Seleccionar la fila
                    tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                    tabla.setColumnSelectionInterval(columna,columna);

                    // Mostrar el menú contextual
                    
                }
                
            }
        });
    }

}
