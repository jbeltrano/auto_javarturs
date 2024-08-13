package Front.Personas;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Base.Base;

public class Actualizar_peronas extends Insertar_persona{
    
    private String buscar;
    private String dato[];
    public Actualizar_peronas(JFrame padre, String url, String buscar){
        super(padre, url);
        this.buscar = buscar; 

        set_componentes();

    }

    private void set_componentes(){

        text_documento.setEnabled(false);
        radio_contratante.setVisible(false);
        label_contratante.setVisible(false);

        base = new Base(url);

        try{
            
            dato = base.consultar_uno_persona(buscar);

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
            setVisible(false);

        }
        base.close();

        // Inicializando componentes
        
        text_documento.setText(dato[0]);
        combo_tipo_documento.setSelectedItem(dato[1]);
        text_nombre.setText(dato[2]);
        text_celular.setText(dato[3]);
        combo_departamento.setSelectedItem(dato[5]);
        combo_municipio.setSelectedItem(dato[4]);
        text_direccion.setText(dato[6]);
        text_correo.setText(dato[7]);

        // reconfigurando el boton
        jPanel1.remove(boton_guardar);
        boton_guardar = new JButton();
        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(17, 250, 100, 23);
        boton_guardar.addActionListener(accion ->{
            boolean band = true;
            String mostrar = "Los campos:\n";
            int tipo_documento,ciudad;
            

            if(text_documento.getText().equals("")){
                band = false;
                mostrar.concat("Documento\n");
            }
            if(text_nombre.getText().equals("")){
                band = false;
                mostrar.concat("Nombre o Razon Social\n");
            }
            if(text_celular.getText().equals("")){
                band = false;
                mostrar.concat("Celular\n");
            }
            if(text_direccion.getText().equals("")){
                band = false;
                mostrar.concat("Direccion\n");
            }
            mostrar.concat("Son Obligatorios.");

            if(!band){
                JOptionPane.showMessageDialog(this, mostrar, "Error",JOptionPane.ERROR_MESSAGE);
                this.setVisible(false);
            }else{
                try{
                    
                    Long.parseLong(text_celular.getText());
                    Long.parseLong(text_documento.getText());
                    tipo_documento = combo_tipo_documento.getSelectedIndex() + 1;
                    base = new Base(url);
                    try {
                        ciudad = Integer.parseInt(base.consultar_uno_ciudad((String)combo_municipio.getSelectedItem())[0]);
                        base.actualizar_persona(text_documento.getText(), tipo_documento, text_nombre.getText(), text_celular.getText(), ciudad, text_direccion.getText(), text_correo.getText());
                        base.close();
                        JOptionPane.showMessageDialog(this, "Persona actualizada correctamente");
                        this.setVisible(false);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        base.close();
                        this.setVisible(false);
                    }
                    
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "El campo: Celular\nDebe ser un dato Entero","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
