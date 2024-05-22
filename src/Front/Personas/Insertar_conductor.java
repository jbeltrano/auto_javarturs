package Front.Personas;


import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import java.util.Date;
import Base.Base;
import Utilidades.Key_adapter;
import Utilidades.Modelo_tabla;
import Utilidades.Windows_bar;

public class Insertar_conductor extends Modales_personas{
    
    protected JButton boton_guardar;
    protected JComboBox<String> combo_conductor;
    private JLabel jLabel1;
    private JLabel jLabel2;
    protected JPanel jPanel1;
    private JScrollPane jScrollPane1;
    protected JTable tabla_persona;
    protected JTextField text_documento;
    protected JDateChooser buscar_fecha;
    private String [][] datos;
    private JDialog padre;
    private Calendar calendar;
    private Date currentDate;

    public Insertar_conductor(JFrame padre, String url){
        super(padre, url, new Dimension(500,300));
    }

    public Insertar_conductor(JDialog padre, String url){
        super(padre, url, new Dimension(500,300));
    }

    @Override
    protected void iniciar_componentes() {
        
        padre = this;
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        text_documento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        combo_conductor = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_persona = new javax.swing.JTable();
        buscar_fecha = new JDateChooser();
        boton_guardar = new javax.swing.JButton();
        calendar = Calendar.getInstance();
        currentDate = calendar.getTime();


        buscar_fecha.setDate(currentDate);
        jPanel1.setLayout(null);

        jLabel1.setText("Numero Documento");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(6, 6, 123, 16);
        jPanel1.add(text_documento);
        text_documento.setBounds(6, 28, 123, 22);

        jLabel2.setText("Tipo Licencia");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(147, 6, 87, 16);


        jPanel1.add(combo_conductor);
        combo_conductor.setBounds(147, 28, 87, 22);

        base = new Base(url);
        try{
            datos = base.consultar_persona_natural("");
            combo_conductor.setModel(new DefaultComboBoxModel<>(base.get_datos_tabla(base.consultar_categoria(), 1)));
            tabla_persona = Modelo_tabla.set_tabla_personas(datos);

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            base.close();
        }

        tabla_persona.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent evt){
                if(SwingUtilities.isRightMouseButton(evt)){
                    new Insertar_persona(padre, url);
                    base = new Base(url);
                try{
                    datos = base.consultar_persona_natural("");
                    JTable tab = Modelo_tabla.set_tabla_personas(datos);
                    tabla_persona.setModel(tab.getModel());
                    tabla_persona.setColumnModel(tab.getColumnModel());

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    padre.setVisible(false);
                    
                }finally{
                    base.close();
                }
                    
                }else{
                    int row = tabla_persona.getSelectedRow();
                    text_documento.setText("" + tabla_persona.getValueAt(row, 0));
                }
            }

        });

        text_documento.addKeyListener(new Key_adapter() {

            @Override
            public void accion() {
                // TODO Auto-generated method stub
                String variable_auxiliar = text_documento.getText();
                
                
                base = new Base(url);
                try{
                    datos = base.consultar_persona_natural(variable_auxiliar);
                    JTable tab = Modelo_tabla.set_tabla_personas(datos);
                    tabla_persona.setModel(tab.getModel());
                    tabla_persona.setColumnModel(tab.getColumnModel());
                    
        
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    padre.setVisible(false);
                    base.close();
                }
                base.close();
                tabla_persona.changeSelection(0, 0, false, false);
            }

            @Override
            public void accion2() {
                try{
                    int row = tabla_persona.getSelectedRow();
                    text_documento.setText("" + tabla_persona.getValueAt(row, 0));
                }catch(Exception ex){
                    
                }
                
                
            }
            

        });

        jScrollPane1.setViewportView(tabla_persona);
        jScrollPane1.getVerticalScrollBar().setUI(new Windows_bar());
        jScrollPane1.getHorizontalScrollBar().setUI(new Windows_bar());
        

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(6, 62, 388, 100);

        buscar_fecha.setDateFormatString("yyyy-MM-dd");
        buscar_fecha.setBounds(250, 28, 100, 22);
        
        // Agregar el Jbuscar_fecha al JFrame
        jPanel1.add(buscar_fecha);

        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(10, 230, 90, 23);
        boton_guardar.addActionListener(accion ->{
            
            if(text_documento.getText().compareTo("") == 0){
                JOptionPane.showMessageDialog(padre, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }else{

                try{
                    Double.parseDouble(text_documento.getText());
                    Date data = buscar_fecha.getDate();
                    base = new Base(url);
                    try{
                        base.insertar_licencia(""+ text_documento.getText(), combo_conductor.getSelectedIndex()+1, (data.getYear()+1900) + "-" + (data.getMonth()+1) + "-" + data.getDate());
                        JOptionPane.showMessageDialog(padre, "Licencia incertada con Exito", "IE", JOptionPane.INFORMATION_MESSAGE);
                        padre.setVisible(false);
                        base.close();
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(padre, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        base.close();
                    }

                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(padre, "No ingresaste un dato numerico. \nPor favor selecciona un elemento de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

    }


}

