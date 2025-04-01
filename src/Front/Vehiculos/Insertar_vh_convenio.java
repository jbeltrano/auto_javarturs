package Front.Vehiculos;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import Base.Persona;
import Base.Vehiculo;
import Utilidades.Modelo_tabla;

public class Insertar_vh_convenio extends Modales_vehiculos{

    private JPanel panel_principal;
    private JLabel label_placa;
    private JLabel label_empresa;
    private JTextField text_placa;
    private JTextField text_empresa;
    private String[][] datos_vehiculo;
    private String[][] datos_empresa;
    private JTable tabla_vehiculo;
    private JTable tabla_empresa;
    private JButton boton_guardar;

    private static final int X = 20;
    private static final int ALTO = 20;

    public Insertar_vh_convenio(JFrame frame, String url) {
        super(frame, url);
        this.setPreferredSize(new Dimension(800,300));
        pack();
    }

    @Override
    protected void iniciar_componentes() {
        // Inicializacion panel principal
        panel_principal = new JPanel();
        panel_principal.setLayout(null);

        // Incializcion labels
        label_placa = new JLabel("Placa vehiculo:");
        label_empresa = new JLabel("Nit empresa con convenio:");
        
        // Inicializacion TextFields
        text_placa = new JTextField();
        text_empresa = new JTextField();

        // Inicializacion JTable
        tabla_vehiculo = new JTable();
        tabla_empresa = new JTable();

        // Inicializacion botones
        boton_guardar = new JButton();


        // Configuraciones

        // Configuracion labels
        label_placa.setBounds(X, 0, 100, ALTO);
        label_empresa.setBounds(X + label_placa.getWidth() + 250, label_placa.getY(), 200, ALTO);

        // Configuracion textfields
        text_placa.setBounds(X,label_placa.getY() + label_placa.getHeight() + 10, 100, ALTO);
        text_empresa.setBounds(label_empresa.getX(),label_empresa.getY() + label_empresa.getHeight() + 10, 100, ALTO);

        // Configuracion tablas
        // Obtencion de los datos

        Vehiculo base_vehiculo = new Vehiculo(url);
        Persona base_persona = new Persona(url);

        try{
            datos_vehiculo = base_vehiculo.consultar_vehiculo_externo("");
            datos_empresa = base_persona.consultar_empresa("");
        }catch(SQLException ex){

        }finally{
            base_vehiculo.close();
            base_persona.close();
        }
        // configuracion de los listener y inicalizacion de las tablas
        tabla_vehiculo = Modelo_tabla.set_tabla_vehiculo(datos_vehiculo);
        tabla_vehiculo.setBounds(X,text_placa.getX() + text_placa.getHeight() + 20, 40, 200);


        tabla_empresa = Modelo_tabla.set_tabla_personas(datos_empresa);

        // Adicionamiento en el panel
        panel_principal.add(label_placa);
        panel_principal.add(label_empresa);
        panel_principal.add(text_placa);
        panel_principal.add(text_empresa);
        panel_principal.add(tabla_vehiculo);
        panel_principal.add(tabla_empresa);

        // Estas lineas se utilizan para centrar el panel, en relacion a la posicion del JFrame Padre
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );

        pack();
    }
    
}
