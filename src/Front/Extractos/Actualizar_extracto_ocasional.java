package Front.Extractos;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Base;

public class Actualizar_extracto_ocasional extends Insertar_extracto_ocasional {
    
    private String url;
    private boolean is_plantilla;
    private String placa;
    private String consecutivo;
    private String contrato;

    public Actualizar_extracto_ocasional(JFrame frame, String url, String placa, String consecutivo, String contrato, boolean is_plantilla){
        super(frame, url);
        this.url = url;
        this.is_plantilla = is_plantilla;
        this.placa = placa;
        this.consecutivo = consecutivo;
        this.contrato = contrato;
        Modificar();
    }

    protected void Modificar(){
        
        // Como son pocos los campos, simplemente no se buscan los daots en la base de datos
        // Por lo tanto simplemente hacemos un preset de los datos en la tabla

        text_placa.setText(placa);
        text_contrato.setText(contrato);
        text_consecutivo.setEnabled(false);

        if(!is_plantilla){  // En caso de no ser una plantilla desabilita los componente s que se requieran desabilitar
            text_consecutivo.setText(consecutivo);
            text_placa.setEnabled(false);
            tabla_vehiculo.setEnabled(false);
            
        }else{
            text_consecutivo.setText(""+(Integer.parseInt(consecutivo) + 1));
        }
    }

    @Override
    protected boolean guardar_extracto_ocasional(){

        if(is_plantilla){
            return super.guardar_extracto_ocasional();
        }else{
            int consecutivo = 0;
            String vehiculo;
            int contrato;

            base = new Base(url);
            try{

                vehiculo = (text_placa.getText().compareTo("") == 0)?null: text_placa.getText();
                contrato = (text_contrato.getText().compareTo("") == 0)?0: Integer.parseInt(text_contrato.getText());
                consecutivo = (text_consecutivo.getText().compareTo("") == 0?0:Integer.parseInt(text_consecutivo.getText()));

                if(vehiculo != null && contrato != 0 && consecutivo != 0){

                    base.actualizar_extracto_ocasional(vehiculo, consecutivo, contrato);
                    JOptionPane.showMessageDialog(this, "Extracto ocasional Actualizado y guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
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
            }finally{
                base.close();
            }
        }
    }
}
