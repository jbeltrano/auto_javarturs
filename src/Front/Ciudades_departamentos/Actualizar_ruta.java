package Front.Ciudades_departamentos;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Base.Ciudad;
import Base.Ruta;
import Utilidades.Modelo_tabla;

public class Actualizar_ruta extends Insertar_ruta{
    
    private int id_destino;
    private int id_origen;
    private int distancia;
    private String[][] datos_origen;
    private String[][] datos_destino;

    public Actualizar_ruta(JDialog padre, String url, int id_origen, int id_destino, int distancia){
        super(padre, url);
        this.id_origen = id_origen;
        this.id_destino = id_destino;
        this.distancia = distancia;

        actualizar();
    }

    /**
     * Este es el constructor para un JFrame,
     * el cual se encarga de obtener los datos necesarios
     * para actualizar la informacion de una ruta
     * @param padre
     * @param url
     * @param id_origen
     * @param id_destino
     * @param distancia
     */
    public Actualizar_ruta(JFrame padre, String url, int id_origen, int id_destino, int distancia){
        super(padre, url);
        this.id_origen = id_origen;
        this.id_destino = id_destino;
        this.distancia = distancia;

        actualizar();
    }

    private void actualizar(){
        
        text_origen.setText("" + id_origen);
        text_destino.setText("" + id_destino);
        text_distancia.setText("" + distancia);

        base_ciudad = new Ciudad(url);

        try{
            datos_origen = base_ciudad.consultar_ciudades("" + id_origen);
            datos_destino = base_ciudad.consultar_ciudades("" + id_destino);
            
            JTable t1 = Modelo_tabla.set_tabla_ciudad(datos_origen);
            JTable t2 = Modelo_tabla.set_tabla_ciudad(datos_destino);

            tabla_origen.setModel(t1.getModel());
            tabla_origen.setColumnModel(t1.getColumnModel());
            
            tabla_destino.setModel(t2.getModel());
            tabla_destino.setColumnModel(t2.getColumnModel());

            tabla_origen.changeSelection(0, 0, false, false);
            tabla_destino.changeSelection(0, 0, false, false);

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base_ciudad.close();
        }

        pack();
    }
    @Override
    protected void accion_guardar(){
        base_ruta = new Ruta(url);

        try{
            
            int origen = Integer.parseInt((String) tabla_origen.getValueAt(tabla_origen.getSelectedRow(), 0));
            int destino = Integer.parseInt((String) tabla_destino.getValueAt(tabla_destino.getSelectedRow(), 0));
            int distancia = Integer.parseInt(text_distancia.getText());
            
            base_ruta.actualizar_ruta(id_origen,id_destino,origen, destino, distancia);

            JOptionPane.showMessageDialog(this, "Datos insertados correctamente.", null, JOptionPane.INFORMATION_MESSAGE);

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base_ruta.close();
        }

        
    }
}
