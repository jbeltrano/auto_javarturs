package Front.Extractos;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Base.Base;
import Utilidades.Extracto;

public abstract class Modal_documento extends JDialog{
    
    protected String url;
    protected Base base;

    public Modal_documento(JFrame padre, String url){
        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        iniciar_componentes();
        setPreferredSize(set_dimension());
        pack();
        setLocationRelativeTo(padre);

    }

    protected abstract void iniciar_componentes();
    protected abstract Dimension set_dimension();
    
    public static void generar_extracto_excel(String placa, int consecutivo, Component padre, String url){
        Extracto extracto;
        Base base = new Base(url);
        String[] datos_extracto;
        String[] datos_vehiculo;
        String[] datos_contratante;
        String[] datos_origen;
        String[] datos_destino;
        String[][] datos_conductores;
        String año;

        try{
            // inicializacion del objeto para modificar la plantilla de extractos
            extracto = new Extracto("src\\Formatos\\Extracto.xlsx");
            datos_extracto = base.consultar_uno_extracto_mensual(placa, consecutivo);
            datos_contratante = base.consultar_uno_contrato_mensual(Integer.parseInt(datos_extracto[2]));
            datos_conductores = base.consultar_conductor_has_vehiculo(placa);
            datos_origen = base.consultar_uno_ciudades(datos_extracto[5]);
            datos_destino = base.consultar_uno_ciudades(datos_extracto[6]);
            datos_vehiculo = base.consultar_uno_vw_vehiculo_extracto(placa);

            if(datos_vehiculo[0] == null){
                NullPointerException ex = new NullPointerException("El vehiculo " + placa + ".\nNo posee documentos");
                throw ex;
            }
            año = datos_extracto[4].split("-")[0];

            // Modificando como tal el documento
            extracto.set_numero_principal(año, datos_extracto[2], datos_extracto[1]);
            extracto.set_contrato(datos_extracto[2]);
            extracto.set_contratante(datos_contratante[3], datos_contratante[1], datos_contratante[2]);
            extracto.set_tipo_contrato(Extracto.TIPO_CONTRATO_EMPRESARIAL);
            extracto.set_origen_destino(datos_origen[1], datos_origen[2], datos_destino[1], datos_destino[2]);
            extracto.set_fecha_inicial(datos_extracto[3]);
            extracto.set_fecha_final(datos_extracto[4]);
            extracto.set_datos_vehiculo(placa, Integer.parseInt(datos_vehiculo[1]), datos_vehiculo[2], datos_vehiculo[3], Integer.parseInt(datos_vehiculo[4]), Integer.parseInt(datos_vehiculo[5]));


            if(datos_conductores.length > 1){
                extracto.set_conductor1(datos_conductores[1][3], datos_conductores[1][2], datos_conductores[1][5]);
            } 
            if(datos_conductores.length > 2){
                extracto.set_conductor2(datos_conductores[2][3], datos_conductores[2][2], datos_conductores[2][5]);
            } 
            if(datos_conductores.length > 3){
                extracto.set_conductor3(datos_conductores[3][3], datos_conductores[3][2], datos_conductores[3][5]);
            }
            if(datos_conductores.length < 2){
                
                NullPointerException ex = new NullPointerException("El vehiculo " + placa + ".\nNo tiene conductores registrados");
                throw ex;
                
            }

            //extracto.set_responsable(nombre, document0, celular, direccion);
            extracto.set_responsable(datos_contratante[5], datos_contratante[4], datos_contratante[6], datos_contratante[7]);

            extracto.guardar("C:\\Users\\juanp\\Desktop");
            JOptionPane.showMessageDialog(padre, "Extracto guardado con exito.\nUbicacion: " + "C:\\Users\\juanp\\Desktop", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(IOException ex){
            JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            base.close();
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        base.close();
    }
}
