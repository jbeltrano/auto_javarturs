package Front.Vehiculos;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Base;

public class Actualizar_vehiculos extends Insertar_vehiculos{

    public Actualizar_vehiculos(JFrame frame, String url, String valor){
        
        super(frame,url,valor);
        actualizar_vehiculo();
        pack();
        setVisible(true);

    }

    private void actualizar_vehiculo(){

        text_placa.setEnabled(false);
        text_modelo.setEnabled(false);
        combo_tipo_vehiculo.setEnabled(false);
        text_marca.setEnabled(false);
        text_linea.setEnabled(false);
        text_carroceria.setEnabled(false);
        text_combustible.setEnabled(false);
        
        base = new Base(url);
        try{
            dato = base.consultar_uno_vehiculo(valor);
            text_placa.setText(dato[0]);
            combo_tipo_vehiculo.setSelectedItem(dato[1]);
            text_modelo.setText(dato[2]);
            text_marca.setText(dato[3]);
            text_linea.setText(dato[4]);
            text_cilindrada.setText(dato[5]);
            text_color.setText(dato[6]);
            text_combustible.setText(dato[7]);
            text_carroceria.setText(dato[8]);
            combo_servicio.setSelectedItem(dato[9]);
            text_motor.setText(dato[10]);
            text_chasis.setText(dato[11]);
            text_pasajeros.setText(dato[12]);
            text_propietario.setText(dato[14]);
            if(Boolean.parseBoolean(dato[16])){
                boton_parque.setSelected(true);
            }else{
                boton_parque.setSelected(false);
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            base.close();
            this.setVisible(false);
        }
        base.close();
        

    }

    @Override
    protected void guardar(){

        boolean[] campos = new boolean[11];
            boolean confirmacion = false;
            String propietario = "";
            int cilindrada = 0;
            int pasajeros = 0;

            // guardamos si los diferentes campos estan llenos o no en el arreglo
            campos[0] = (text_placa.getText().compareTo("") == 0)? true:false;
            campos[1] = (text_marca.getText().compareTo("") == 0)? true:false;
            campos[2] = (text_linea.getText().compareTo("") == 0)? true:false;
            campos[3] = (text_cilindrada.getText().compareTo("") == 0)? true:false;
            campos[4] = (text_color.getText().compareTo("") == 0)? true:false;
            campos[5] = (text_combustible.getText().compareTo("") == 0)? true:false;
            campos[6] = (text_carroceria.getText().compareTo("") == 0)? true:false;
            campos[7] = (text_motor.getText().compareTo("") == 0)? true:false;
            campos[8] = (text_chasis.getText().compareTo("") == 0)? true:false;
            campos[9] = (text_pasajeros.getText().compareTo("") == 0)? true:false;
            campos[10] = (text_propietario.getText().compareTo("") == 0)? true:false;
            
            // verificamos que campos tiene true y cuales false, para botar el respectivo error
            for(int i = 0; i< campos.length; i++){
                if(campos[i]){
                    JOptionPane.showMessageDialog(this, "Faltan campos por llenar","",JOptionPane.INFORMATION_MESSAGE);
                    confirmacion = false;
                    break;
                }else{
                    confirmacion = true;
                }
            }

            // verificamos que los campos que deben ser numeros si sean numeros y no otro tipo de datos
            if(confirmacion){
                try{

                    cilindrada = Integer.parseInt(text_cilindrada.getText());
                    pasajeros = Integer.parseUnsignedInt(text_pasajeros.getText());
                    propietario = "" + Integer.parseInt(text_propietario.getText());

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Los campos:\nCilindrada\nPasajeros\nPropietario\nDeben ser valores enteros","Error", JOptionPane.ERROR_MESSAGE);
                    confirmacion = false;
                }
                
            }
            if(confirmacion){
                base = new Base(url);
                try{
                    dato = base.consultar_uno_clase_vehiculo(combo_tipo_vehiculo.getSelectedItem()+"");
                    base.actualizar_vehiculo(text_placa.getText(),cilindrada, text_color.getText(), text_motor.getText(), text_chasis.getText(), pasajeros, propietario, boton_parque.isSelected());
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    base.close();
                    this.setVisible(false);
                }
                base.close();
                JOptionPane.showMessageDialog(this, "Actualizacion para vehiculo: "+ text_placa.getText() + "\nRealizado correctamente.","",JOptionPane.QUESTION_MESSAGE);
                this.setVisible(false);
            }

    }

}