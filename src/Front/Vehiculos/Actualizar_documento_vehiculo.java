package Front.Vehiculos;

//import java.sql.Date;
import java.util.Date;
import java.sql.SQLException;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Base;

public class Actualizar_documento_vehiculo extends Insertar_documento_vehiculo{
    
    public Actualizar_documento_vehiculo(JFrame frame, String url, String valor){
        super(frame, url, valor);
        actualizar_documentos();
    }

    private void actualizar_documentos(){
        Rectangle posicion_boton = boton_guardar.getBounds();
        String []dato = null;
        base = new Base(url);
        try{
            dato = base.consultar_uno_documentos(valor);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
            base.close();
        }
        base.close();

        
        //Cargando los valores de los diferentes campos
        text_placa.setText(valor);
        text_numero_interno.setText(dato[1]);
        fecha_soat.setDate(java.sql.Date.valueOf(dato[2]));
        fecha_rtm.setDate(java.sql.Date.valueOf(dato[3]));
        fecha_polizas.setDate(java.sql.Date.valueOf(dato[4]));
        text_top.setText(dato[6]);
        fecha_top.setDate(java.sql.Date.valueOf(dato[7]));

        // Estableciendo los valores que no se van a habilitar
        text_placa.setEnabled(false);
        tabla_vehiculo.setEnabled(false);
        tabla_vehiculo.setDragEnabled(false);

        // Restablecciendo los addaciton listener del boton guardar para actualizar en vez de guardar o crear un nuevo registro
        jPanel1.remove(boton_guardar);
        boton_guardar = new JButton("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(posicion_boton);
        jPanel1.revalidate();
        jPanel1.repaint();
        boton_guardar.addActionListener(accion ->{
            Date fecha = null;
            String ffecha_soat = "";
            String ffecha_rtm = "";
            String ffecha_top = "";
            String ffecha_polizas = "";
            int top = 0;
            int interno = 0;

            fecha = fecha_soat.getDate();
            ffecha_soat = (fecha.getYear()+1900) + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();

            fecha = fecha_rtm.getDate();
            ffecha_rtm = (fecha.getYear()+1900) + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();

            fecha = fecha_top.getDate();
            ffecha_top = (fecha.getYear()+1900) + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();

            fecha = fecha_polizas.getDate();
            ffecha_polizas = (fecha.getYear()+1900) + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();

            try{

                top = Integer.parseInt(text_top.getText());
                interno = Integer.parseInt(text_numero_interno.getText());

                if(text_placa.getText().equals("")){
                    
                    JOptionPane.showMessageDialog(this, "El campo: Placa es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                
                }else{
                    base = new Base(url);
                    try{

                        base.actualizar_documento(valor, ffecha_soat, ffecha_rtm, top, ffecha_top, ffecha_polizas, interno);
                        base.close();
                        JOptionPane.showMessageDialog(this, "El Los documentos para el vehiculo " + text_placa.getText() +"\nFueron actualizados correctamente.","",JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                    
                    }catch(SQLException ex){
                    
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    
                    }
                    base.close(); 
                }

            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Los campos:\nTarjeda de Operacion\nNumero Interno\nDeben ser de tipo Numerico", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
    }
}
