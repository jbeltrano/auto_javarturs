package Front.Extractos;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import Base.Extractos;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Actualizar_todo_ext_mensual extends Modal_extracto{
    
    private static final int POS_X = 120;
    private JPanel panel;
    private JDateChooser fecha_inicial;
    private JDateChooser fecha_final;
    private Date fecha_sistema;
    private JButton boton_guardar;
    private JLabel label_inicial;
    private JLabel label_final;

    public Actualizar_todo_ext_mensual(JFrame padre){
        super(padre);

    }

    protected void iniciar_componentes(){
        fecha_sistema = Calendar.getInstance().getTime();
        panel = new JPanel(null);
        fecha_inicial = new JDateChooser(fecha_sistema);
        fecha_final = new JDateChooser(fecha_sistema);
        boton_guardar = new JButton();
        label_inicial = new JLabel();
        label_final = new JLabel();

        // configuracion jlabel inicial
        label_inicial.setText("Fecha de Inicial");
        label_inicial.setBounds(POS_X, 20, 100, 20);
        panel.add(label_inicial);

        // Configuracion de jcalendar fecha_inicial
        fecha_inicial.setBounds(POS_X,label_inicial.getY() + label_inicial.getHeight() + 10,100,20);
        panel.add(fecha_inicial);


        // Configuracion de label_final
        label_final.setText("Fecha de Final");
        label_final.setBounds(label_inicial.getX() + label_inicial.getWidth() + 40, label_inicial.getY(), 100,20);
        panel.add(label_final);

        // Configuracion de jcalendar fecha_final
        fecha_final.setBounds(label_final.getX(), label_final.getY() + label_final.getHeight() + 10, 100, 20);
        panel.add(fecha_final);
        

        // Configuracion de jbutton boton_guardar
        boton_guardar.setText("Guardar");
        boton_guardar.setBounds(10, 250, 100, 20);
        boton_guardar.addActionListener(_ ->{
            actualizar();
        });
        panel.add(boton_guardar);


        add(panel);
        pack();
    }
    
    protected Dimension set_dimension(){
        return new Dimension(500, 325);
    }
    
    private void actualizar(){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-M-d");
        String ffecha_inicial;
        String ffecha_final;

        ffecha_inicial = formato.format(fecha_inicial.getDate());
        ffecha_final = formato.format(fecha_final.getDate());
        
        try{
            base = new Extractos();

            ((Extractos)base).actualizar_todos_extractos_mensuales(ffecha_inicial, ffecha_final);
            JOptionPane.showMessageDialog(this, "Extractos actualizados con exito", "Transaccion exitosa", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base != null) base.close();
        }
    }
}
