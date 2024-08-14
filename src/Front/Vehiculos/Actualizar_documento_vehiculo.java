package Front.Vehiculos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Base;

public class Actualizar_documento_vehiculo extends Insertar_documento_vehiculo{
    
    public Actualizar_documento_vehiculo(JFrame frame, String url, String valor){
        super(frame, url, valor);
        actualizar_documentos();
    }

    private void actualizar_documentos(){
        String []dato = null;
        base = new Base(url);
        
        try{
            dato = base.consultar_uno_documentos(valor);
            
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base.close();
        }

        is_particular(valor);
        // Carga los documentos que siempre estaran para cualquier vehiculo
        text_placa.setText(valor);
        fecha_soat.setDate(java.sql.Date.valueOf(dato[2]));
        fecha_rtm.setDate(java.sql.Date.valueOf(dato[3]));

        if(!flag_is_particular){ // Si el vehiculo es de servicio publico carga estos documentos
            text_numero_interno.setText(dato[1]);
            fecha_polizas.setDate(java.sql.Date.valueOf(dato[4]));
            text_top.setText(dato[6]);
            fecha_top.setDate(java.sql.Date.valueOf(dato[7]));
        }

        // Estableciendo los valores que no se van a habilitar
        text_placa.setEnabled(false);
        tabla_vehiculo.setEnabled(false);
        tabla_vehiculo.setDragEnabled(false);
    }

    @Override
    protected void guardar(){

        String ffecha_soat = "";
        String ffecha_rtm = "";
        String ffecha_top = "";
        String ffecha_polizas = "";
        int top = 0;
        int interno = 0;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");
        
        if(flag_is_particular){
            ffecha_soat = formato.format(fecha_soat.getDate());
            ffecha_rtm = formato.format(fecha_rtm.getDate());
        }else{
            ffecha_soat = formato.format(fecha_soat.getDate());
            ffecha_rtm = formato.format(fecha_rtm.getDate());
            ffecha_polizas = formato.format(fecha_polizas.getDate());
            ffecha_top = formato.format(fecha_top.getDate());
        }
        try{
            
            if(!flag_is_particular){
                top = Integer.parseInt(text_top.getText());
                interno = Integer.parseInt(text_numero_interno.getText());    
            }
            
            if(text_placa.getText().equals("")){
                
                JOptionPane.showMessageDialog(this, "El campo: Placa es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            
            }else{
                base = new Base(url);
                try{

                    if(flag_is_particular){     // En caso de ser un vehiculo de servicio particular
                        
                        base.actualizar_documento(text_placa.getText(),  // Vehiculo al cual se le hace la insercion
                                                ffecha_soat,            // Fecha de vencimiento del soat
                                                ffecha_rtm);            // Fecha de vencimiento de la tecnomecanica
                        
                    }else{                      // En caso de ser un vehiculo de servicio publico
                        base.actualizar_documento(text_placa.getText(),  // Vehiculo al cual se le hace la insercion
                                                ffecha_soat,            // Fecha de vencimiento del soat
                                                ffecha_rtm,             // Fecha de vencimiento de la tecnomecanica
                                                top,                    // Numero de tarjeta de operacion
                                                ffecha_top,             // Fecha de vencimiento de la tarjeta de operacion
                                                ffecha_polizas,         // Fecha de vencimiento de las polizas rcc y rce
                                                interno);               // Numero interno del vehiculo
                    
                    }
                    JOptionPane.showMessageDialog(this, "El Los documentos para el vehiculo " + text_placa.getText() +"\nFueron insertados correctamente.","",JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                
                }catch(SQLException ex){
                
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                
                }finally{
                    base.close();
                }
                
            }
    
                
            
            
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Los campos:\nTarjeda de Operacion\nNumero Interno\nDeben ser de tipo Numerico", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    

}
