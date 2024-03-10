package Front.Personas;

import java.awt.Rectangle;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Base;

public class Actualizar_conductor extends Insertar_conductor{
    
    private String id_busqueda;
    private String[] dato;
    private DateTimeFormatter formatter;
    private LocalDate fecha_licencia;
    
    public Actualizar_conductor(JDialog padre, String url, String id_busqueda){
        super(padre, url);
        this.id_busqueda = id_busqueda;

        actualizar_conductor();

        setVisible(true);
    }

    public Actualizar_conductor(JFrame padre, String url, String id_busqueda){
        super(padre, url);
        this.id_busqueda = id_busqueda;

        actualizar_conductor();

        setVisible(true);
    }

    private void actualizar_conductor(){
        Rectangle pos_boton_guardar = boton_guardar.getBounds();
        dato = null;
        base = new Base(url);

        formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
        fecha_licencia = null;
        try{
            dato = base.consultar_uno_licencia(id_busqueda);

            text_documento.setText(dato[0]);
            combo_conductor.setSelectedIndex(Integer.parseInt(dato[1])-1);
            fecha_licencia = LocalDate.parse(dato[2], formatter);
            buscar_fecha.setDate(new Date(fecha_licencia.getYear()-1900, fecha_licencia.getMonthValue()-1, fecha_licencia.getDayOfMonth()));
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            setVisible(false);
        }
        base.close();


        text_documento.setEnabled(false);
        tabla_persona.setEnabled(false);

        // Configuracion y adiccion del nuevo escuchador para el boton
        jPanel1.remove(boton_guardar);
        boton_guardar = new JButton("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(pos_boton_guardar);
        boton_guardar.addActionListener(accion ->{
            
            if(text_documento.getText().compareTo("") == 0){
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }else{

                try{
                    Double.parseDouble(text_documento.getText());
                    Date data = buscar_fecha.getDate();
                    base = new Base(url);
                    try{
                        base.actualizar_licencia(""+ text_documento.getText(), combo_conductor.getSelectedIndex()+1, (data.getYear()+1900) + "-" + (data.getMonth()+1) + "-" + data.getDate());
                        JOptionPane.showMessageDialog(this, "Licencia actualizada con Exito", "AL", JOptionPane.INFORMATION_MESSAGE);
                        this.setVisible(false);
                        base.close();
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        base.close();
                    }

                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "No ingresaste un dato numerico. \nPor favor selecciona un elemento de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

    }
}
