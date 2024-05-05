package Front.Extractos;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Base.Base;

public class Actualizar_extracto_mensual extends Insertar_extracto_mensual{
    
    private String placa;
    private int consecutivo;
    private String datos[];
    private boolean is_plantilla;

    public Actualizar_extracto_mensual(JFrame padre, String url, String placa, int consecutivo, boolean is_plantilla){
        super(padre, url);
        this.placa = placa;
        this.consecutivo = consecutivo;
        this.is_plantilla = is_plantilla;

        modificar();
    }

    private void modificar(){
        // Modificando los campos que no se pueden editar
        if(!is_plantilla){
            text_placa.setEnabled(false);
            tabla_vehiculo.setEnabled(false);
        }
        text_consecutivo.setEnabled(false);


        // Cargando la informacion
        base = new Base(url);
        try{

            // inicializando los valores
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            datos = base.consultar_uno_extracto_mensual(placa, consecutivo);
            text_placa.setText(placa);
            text_consecutivo.setText(datos[1]);
            text_contratante.setText(datos[2]);
            text_origen.setText(datos[5]);
            text_destino.setText(datos[6]);
            fecha_incial.setDate(formato.parse(datos[3]));
            fecha_final.setDate(formato.parse(datos[4]));

            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }catch(ParseException ex){
            JOptionPane.showMessageDialog(this, "Problemas al realizar conversiones", "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base.close();
        }
    }

    @Override
    protected boolean guardar_extracto_mensual(){
        
        if(!is_plantilla){
            int consecutivo = 0;
            String vehiculo;
            int contratante;
            String ffecha_inicial;
            String ffecha_final;
            int origen;
            int destino;
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");
    
            base = new Base(url);
            try{
    
                vehiculo = (text_placa.getText().compareTo("") == 0)?null: text_placa.getText();
                contratante = (text_contratante.getText().compareTo("") == 0)?0: Integer.parseInt(text_contratante.getText());
                ffecha_inicial = formato.format(fecha_incial.getDate());
                ffecha_final = formato.format(fecha_final.getDate());
                origen = (text_origen.getText().compareTo("") == 0)?0: Integer.parseInt(text_origen.getText());
                destino = (text_destino.getText().compareTo("") == -1)?0: Integer.parseInt(text_destino.getText());
                consecutivo = (text_consecutivo.getText().compareTo("") == 0?0:Integer.parseInt(text_consecutivo.getText()));
                
                if(vehiculo != null && contratante != 0 && origen != 0 && destino != -1 && consecutivo != 0){
    
                    base.actualizar_extracto_mensual(vehiculo, consecutivo, contratante, ffecha_inicial, ffecha_final, origen, destino);
                    JOptionPane.showMessageDialog(this, "Extracto mensual guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
                    base.close();
                    return true;
    
                }else{
                    JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos para continuar\nRecuerde que si es el primer extracto del vehiculo\nDebe establecer el consecutivo", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
    
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        
        }else{
            return super.guardar_extracto_mensual();
        }
        
        
    }

}
