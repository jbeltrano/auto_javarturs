package Front.Extractos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Base.BContrato_ocasional;

public class Actualizar_contrato_ocasional extends Insertar_contrato_ocasional{
    
    private String[] datos;
    private int id;
    private boolean is_plantilla;

    public Actualizar_contrato_ocasional(JFrame padre, String url, int id, boolean is_plantilla){
        super(padre, url);
        this.id = id;
        this.is_plantilla = is_plantilla;
        modificar();
    }
    public void modificar(){

        if(!is_plantilla){
            text_numero_contrato.setEnabled(false);
        }
        
        base = new BContrato_ocasional(url);
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");      // Establece el formato de la fecha
            LocalDate fInicial;
            LocalDate fFinal;
            datos = ((BContrato_ocasional)base).consultar_uno_contrato_ocasional(id);

            if(!is_plantilla){
                text_numero_contrato.setText(datos[0]);
            }
            
            text_contratante.setText(datos[1]);
            text_origen.setText(datos[4]);
            text_destino.setText(datos[5]);
            text_valor_contrato.setText(datos[6]);
            combo_tipo_contrato.setSelectedIndex(Integer.parseInt(datos[datos.length -1]) -1);

            fInicial = LocalDate.parse(datos[2],formatter);
            fFinal = LocalDate.parse(datos[3],formatter); 

            fecha_incial.setDate(Date.from(fInicial.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            fecha_final.setDate(Date.from(fFinal.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
        }finally{
            base.close();
        }
        
    }

    @Override
    public void guardar(){
        if(!is_plantilla){
            int numero_contrato = 0;
            int tipo_contrato = 0;
            String contratante;
            String ffecha_inicial;
            String ffecha_final;
            int origen;
            int destino;
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");
            double valor_contrato;
            text_valor_contrato.setText(text_valor_contrato.getText().replaceAll(",", "."));
            
            base = new BContrato_ocasional(url);
            try{
                Integer.parseInt(text_contratante.getText());
                numero_contrato = (text_numero_contrato.getText().compareTo("") == 0)?null: Integer.parseInt(text_numero_contrato.getText());
                valor_contrato = (text_valor_contrato.getText().compareTo("") == 0)?0:Double.parseDouble(text_valor_contrato.getText());
                contratante = (text_contratante.getText().compareTo("") == 0)?null: text_contratante.getText();
                ffecha_inicial = formato.format(fecha_incial.getDate());
                ffecha_final = formato.format(fecha_final.getDate());
                tipo_contrato = combo_tipo_contrato.getSelectedIndex() + 1;
                origen = (text_origen.getText().compareTo("") == 0)?0: Integer.parseInt(text_origen.getText());
                destino = (text_destino.getText().compareTo("") == 0)?0: Integer.parseInt(text_destino.getText());
                
                if(contratante != null && origen != 0 && destino != 0 && numero_contrato != 0 && valor_contrato != 0){
    
                    ((BContrato_ocasional)base).actualizar_contrato_ocasional(numero_contrato, 
                                                       contratante,
                                                       ffecha_inicial, 
                                                       ffecha_final, 
                                                       origen, 
                                                       destino, 
                                                       valor_contrato,
                                                       tipo_contrato);
    
                    JOptionPane.showMessageDialog(this, "Contrato Ocasional guardado correctamente", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
    
                }else{
                    JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos para continuar...", "Error", JOptionPane.ERROR_MESSAGE);
                }
    
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Los campos: \nNumero de contrato\nContratante\norigen y destino\nDeben ser de tipo numerico", "Error", JOptionPane.ERROR_MESSAGE);
            }finally{
                base.close();
            }
        }else{
            super.guardar();
        }
        
    }
}
