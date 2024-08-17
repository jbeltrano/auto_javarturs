package Front.Personas;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.sql.SQLException;

import Base.Base;
import Base.Ciudad;
import Base.Tipo_id;

import java.awt.Dimension;

public class Insertar_persona extends Modales_personas{

    protected JButton boton_guardar;
    protected JComboBox<String> combo_departamento;
    protected JComboBox<String> combo_municipio;
    protected JComboBox<String> combo_tipo_documento;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel label_correo;
    protected JLabel label_contratante;
    protected JPanel jPanel1;
    protected JTextField text_celular;
    protected JTextField text_direccion;
    protected JTextField text_documento;
    protected JTextField text_nombre;
    protected JTextField text_correo;
    protected JRadioButton radio_contratante;

    public Insertar_persona(JDialog padre, String url){
        super(padre,url,new Dimension(550,320));
        setVisible(true);
    }

    public Insertar_persona(JFrame padre, String url){
        super(padre,url,new Dimension(550,320));

    }
    
    @Override
    protected void iniciar_componentes(){

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        combo_tipo_documento = new JComboBox<>();
        jLabel2 = new JLabel();
        text_documento = new JTextField();
        jLabel3 = new JLabel();
        text_nombre = new JTextField();
        jLabel4 = new JLabel();
        text_celular = new JTextField();
        jLabel5 = new JLabel();
        combo_departamento = new JComboBox<>();
        jLabel6 = new JLabel();
        combo_municipio = new JComboBox<>();
        jLabel7 = new JLabel();
        text_direccion = new JTextField();
        boton_guardar = new JButton();
        label_correo = new JLabel();
        text_correo = new JTextField();
        label_contratante = new JLabel();
        radio_contratante = new JRadioButton();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel1.setText("Tipo Documento");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(17, 6, 103, 16);

        base = new Tipo_id(url);
        Ciudad base_ciudad = new Ciudad(url);
        try{
            combo_tipo_documento = new JComboBox<>(((Tipo_id)base).consultar_tipo_id(1));
            combo_departamento = new JComboBox<>(base.consultar_departamento());
            combo_municipio = new JComboBox<>(base_ciudad.consultar_ciudad(""+combo_departamento.getSelectedItem(), 1));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            
            this.setVisible(false);
        }finally{
            base.close();
            base_ciudad.close();
        }
        
        jPanel1.add(combo_tipo_documento);
        combo_tipo_documento.setBounds(17, 28, 103, 22);

        jLabel2.setText("Numero de Identidad");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(166, 6, 120, 16);

        text_documento.setText("");
        jPanel1.add(text_documento);
        text_documento.setBounds(166, 28, 120, 22);

        jLabel3.setText("Nombre o Razon Social");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(17, 68, 150, 16);

        text_nombre.setText("");
        jPanel1.add(text_nombre);
        text_nombre.setBounds(17, 90, 220, 22);

        jLabel4.setText("Numero Celular");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(316, 6, 96, 16);

        text_celular.setText("");
        jPanel1.add(text_celular);
        text_celular.setBounds(316, 28, 90, 22);

        jLabel5.setText("Departamento");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(292, 68, 90, 16);

        combo_departamento.addActionListener(accion ->{
            base = new Ciudad(url);
            try{
                String dato[] = ((Ciudad)base).consultar_ciudad((String)combo_departamento.getSelectedItem(), 1);
                combo_municipio.removeAllItems();
                for(String valor : dato){
                    combo_municipio.addItem(valor);
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                
            }finally{
                base.close();
            }
        });
        combo_departamento.setSelectedItem("Meta");
        jPanel1.add(combo_departamento);
        combo_departamento.setBounds(292, 90, 120, 22);
        combo_departamento.setMaximumSize(new Dimension(200,100));
        combo_departamento.setMaximumRowCount(8);
        jLabel6.setText("Municipio");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(17, 124, 54, 16);

        combo_municipio.setMaximumRowCount(5);
        combo_municipio.setSelectedItem("Acacias");
        combo_municipio.setPrototypeDisplayValue("Placeholder");    // Se utiliza para que el tamaño no cambie

        jPanel1.add(combo_municipio);
        combo_municipio.setBounds(17, 146, 124, 22);

        jLabel7.setText("Direccion");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(168, 124, 100, 16);

        text_direccion.setText("");
        jPanel1.add(text_direccion);
        text_direccion.setBounds(168, 146, 226, 22);

        label_correo.setText("Correo Electronico");
        label_correo.setBounds(17,186,120,20);
        jPanel1.add(label_correo);

        text_correo.setText("");
        text_correo.setBounds(17,208,250,22);
        jPanel1.add(text_correo);

        label_contratante.setText("Marcar si es contratante");
        label_contratante.setBounds(300,186,150,22);
        jPanel1.add(label_contratante);

        radio_contratante.setSelected(false);
        radio_contratante.setBounds(295, 208, 20,20);
        jPanel1.add(radio_contratante);


        boton_guardar.setText("Guardar");
        jPanel1.add(boton_guardar);
        boton_guardar.setBounds(17, 250, 100, 23);
        boton_guardar.addActionListener(accion ->{
            guardar();
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        pack();
    }

    /**
     * Se encarga de guarar la informacion
     * del formulario una vez ingresada en su
     * totalidad
     */
    protected void guardar(){
        
        boolean band = true;    // Se marca falsa si hay algun error
        String mostrar = "Los campos:\n";   // Guarda todos los errores
        int tipo_documento,ciudad;          // Guarda el id del tipo de documento y ciudad

        /*
         * Esta estructura se encarga de verificar si
         * hay errores o no se han completado todos los
         * campos del formulario utilizando la bandera band
         */
        if(text_documento.getText().equals("")){
            band = false;
            mostrar = mostrar.concat("Documento\n");
        }
        if(text_nombre.getText().equals("")){
            band = false;
            mostrar = mostrar.concat("Nombre o Razon Social\n");
        }
        if(text_celular.getText().equals("")){
            band = false;
            mostrar = mostrar.concat("Celular\n");
        }
        if(text_direccion.getText().equals("")){
            band = false;
            mostrar = mostrar.concat("Direccion\n");
        }
        if(text_correo.getText().equals("")){
            text_correo.setText("Indefinido");
        }
        mostrar = mostrar.concat("Son Obligatorios.");

        if(!band){  // En caso de haber errores lo muestra en una ventana
            JOptionPane.showMessageDialog(  this,       // Padre al que pertenece la ventana
                                            mostrar,    // Mensaje que muestra la ventana
                                            "Error",    // Titulo de la ventana
                                            JOptionPane.ERROR_MESSAGE); // Icono de la ventana
            
        }else{  // En caso que no haya errores procede a guardar en la base de datos
            
            try{

                Long.parseLong(text_celular.getText());       // Obtiene el dato numerico para celular
                Long.parseLong(text_documento.getText());   // Obtiene el dato numerico para el tipo de documento
                tipo_documento = combo_tipo_documento.getSelectedIndex() + 1;   // Determina el id del tipo de documento

                base = new Base(url);   // Establece coneccion para la base de datos
                Ciudad base_ciudad = new Ciudad(url);   // Establece la coneccion para la bese de datos con las tablas ciudad
                try {
                    
                    ciudad = Integer.parseInt(          // Convierte el dato a entero
                            base_ciudad.consultar_uno_ciudad(  // Consulta en la base de datos la ciudad
                            (String)combo_municipio.getSelectedItem())[0]); // Pasa como parametro el nombre del municipio
                    
                    // Inserta a la persona con los datos que hay en el formulario
                    base.insertar_persona(  text_documento.getText(),   // Es el numero de documento de la persona
                                            tipo_documento,             // Es el tipo de documento de la persona
                                            text_nombre.getText(),      // Es el nombre de la persona
                                            text_celular.getText(),     // Es el numero de celular de la persona
                                            ciudad,                     // Es el id de la ciudad de la persona
                                            text_direccion.getText(),   // Es la direccion de residencia de la persona
                                            text_correo.getText());     // Es el correo electronico de la persona

                    
                    if(radio_contratante.isSelected()){ // Verifica si el radiobutton esta seleccionado
                        // En caso de estar seleccionado lo registra como un contratante
                        base.insertar_contratante(  text_documento.getText(),   // Es el numero de documento para el contratante
                                                    text_documento.getText());  // ES el numero de documento para el responsable

                    }
                    
                    // Imprime un mensaje que todo salio correcto
                    JOptionPane.showMessageDialog(this, "Persona Insertada correctamente");
                    
                } catch (SQLException e) {
                    // Imprime un mnesaje si hay errores
                    JOptionPane.showMessageDialog(  this,   // Es el padre al que pertenece la ventana
                                                    e.getMessage(), // Es el mensaje que apare en la ventana
                                                    "Error",    // Es el titulo de la ventana
                                                    JOptionPane.ERROR_MESSAGE); // Es el icono que aparece en la ventana

                }finally{
                    base.close();   // Cierra la coneccion con la base de datos
                    this.setVisible(false); // Hace invisible la ventana para despues cerrarla
                }
                
            }catch(NumberFormatException e){    // Si no hay datos de tipo numerico en los campos muestra los errores

                JOptionPane.showMessageDialog(  this,   // Padre de la ventana
                                                "Los campos:\nDocumento y Celular\nDeben ser datos de tipo Entero", // Mensaje en la ventana
                                                "Error",                // Titulo de la ventana
                                                JOptionPane.ERROR_MESSAGE); // Icono de la ventana
            }
        }
    }
}
