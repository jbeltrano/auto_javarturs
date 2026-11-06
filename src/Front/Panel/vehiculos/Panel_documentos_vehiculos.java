package Front.Panel.vehiculos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import com.toedter.calendar.JDateChooser;
import Base.Documentos;
import Base.Vehiculo;
import Front.Panel.Panel;
import Front.Vehiculos.Actualizar_documento_vehiculo;
import Front.Vehiculos.Insertar_documento_vehiculo;
import Utilidades.Modelo_tabla;
import Utilidades.ParserRunt;

public class Panel_documentos_vehiculos extends Panel{

    private Documentos base_documento;

    public Panel_documentos_vehiculos(){
        super();
        configRuntButton();
        configPolizasButton();
    }

    private void configPolizasButton() {
        ImageIcon icono_documento = new ImageIcon(
            getClass().getResource("/Front/Recursos/Vehiculos/Imagen_documentos.png")
        );
        Image imagen_escalada = icono_documento.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icono_escalado = new ImageIcon(imagen_escalada);

        thirdFloatingButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                int w = getWidth();
                int h = getHeight();
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 80));
                g2.fill(new java.awt.geom.Ellipse2D.Double(2, 4, w - 4, h - 6));

                g2.setColor(Color.WHITE);
                g2.fill(new java.awt.geom.Ellipse2D.Double(0, 0, w - 6, h - 6));

                int ix = (w - 6 - icono_escalado.getIconWidth()) / 2;
                int iy = (h - 6 - icono_escalado.getIconHeight()) / 2;
                g2.drawImage(icono_escalado.getImage(), ix, iy, null);

                g2.dispose();
            }

            @Override
            public boolean contains(int x, int y) {
                int w = getWidth();
                int h = getHeight();
                double cx = w / 2.0 - 3;
                double cy = h / 2.0 - 3;
                double dx = x - cx;
                double dy = y - cy;
                double r = Math.min(w, h) / 2.0;
                return dx * dx + dy * dy <= r * r;
            }
        };
        thirdFloatingButton.setPreferredSize(new Dimension(56, 56));
        thirdFloatingButton.setFocusPainted(false);
        thirdFloatingButton.setBorderPainted(false);
        thirdFloatingButton.setOpaque(false);
        thirdFloatingButton.setContentAreaFilled(false);
        thirdFloatingButton.setToolTipText("Actualizar RCC y RCE de todo el parque automotor");
        thirdFloatingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        thirdFloatingButton.addActionListener(_ -> abrirDialogoPolizas());

        floating_layer.add(thirdFloatingButton, Integer.valueOf(javax.swing.JLayeredPane.PALETTE_LAYER));
    }

    private void abrirDialogoPolizas() {
        Window win = SwingUtilities.getWindowAncestor(this);
        JFrame frame = (win instanceof JFrame) ? (JFrame) win : null;
        JDialog dialogoPolizas = new JDialog(frame, "Actualizar RCC y RCE", true);
        dialogoPolizas.setSize(430, 190);
        dialogoPolizas.setLocationRelativeTo(this);
        dialogoPolizas.setResizable(false);

        JPanel panel = new JPanel(null);

        JLabel label_instruccion = new JLabel("Fecha de vencimiento de las polizas RCC y RCE:");
        label_instruccion.setBounds(20, 20, 380, 20);
        panel.add(label_instruccion);

        JDateChooser fecha_polizas = new JDateChooser(new Date());
        fecha_polizas.setDateFormatString("dd/MM/yyyy");
        fecha_polizas.setBounds(20, 50, 150, 25);
        panel.add(fecha_polizas);

        JButton boton_actualizar = new JButton("Actualizar");
        boton_actualizar.setBounds(210, 120, 100, 25);
        panel.add(boton_actualizar);

        JButton boton_cancelar = new JButton("Cancelar");
        boton_cancelar.setBounds(320, 120, 90, 25);
        panel.add(boton_cancelar);

        boton_actualizar.addActionListener(_ -> {
            Date fecha = fecha_polizas.getDate();
            if (fecha == null) {
                JOptionPane.showMessageDialog(dialogoPolizas, "Debe seleccionar una fecha.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(dialogoPolizas,
                "Se actualizaran las fechas RCC y RCE de todos los vehiculos\n"
                + "pertenecientes al parque automotor.\n¿Desea continuar?",
                "Confirmar actualizacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmacion != JOptionPane.OK_OPTION) {
                return;
            }

            String fecha_texto = new SimpleDateFormat("dd/MM/yyyy").format(fecha);

            try {
                base_documento = new Documentos();
                base_documento.actualizar_polisas_parque(fecha_texto);

                JOptionPane.showMessageDialog(dialogoPolizas,
                    "Las fechas RCC y RCE del parque automotor fueron actualizadas\n"
                    + "con fecha " + fecha_texto + ".",
                    "", JOptionPane.INFORMATION_MESSAGE);
                dialogoPolizas.dispose();
                accion_text_busqueda();

            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(dialogoPolizas, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (base_documento != null) {
                    base_documento.close();
                }
            }
        });

        boton_cancelar.addActionListener(_ -> dialogoPolizas.dispose());

        dialogoPolizas.add(panel);
        dialogoPolizas.setVisible(true);
    }

    private void configRuntButton() {
        secondFloatingButton = new JButton("R") {
            @Override
            protected void paintComponent(Graphics g) {
                int w = getWidth();
                int h = getHeight();
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 80));
                g2.fill(new java.awt.geom.Ellipse2D.Double(2, 4, w - 4, h - 6));

                g2.setColor(new Color(40, 100, 180));
                g2.fill(new java.awt.geom.Ellipse2D.Double(0, 0, w - 6, h - 6));

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                java.awt.FontMetrics fm = g2.getFontMetrics();
                String texto = "R";
                int tx = (w - 6) / 2 - fm.stringWidth(texto) / 2;
                int ty = (h - 6) / 2 + fm.getAscent() / 2 - 2;
                g2.drawString(texto, tx, ty);

                g2.dispose();
            }

            @Override
            public boolean contains(int x, int y) {
                int w = getWidth();
                int h = getHeight();
                double cx = w / 2.0 - 3;
                double cy = h / 2.0 - 3;
                double dx = x - cx;
                double dy = y - cy;
                double r = Math.min(w, h) / 2.0;
                return dx * dx + dy * dy <= r * r;
            }
        };
        secondFloatingButton.setPreferredSize(new Dimension(56, 56));
        secondFloatingButton.setFocusPainted(false);
        secondFloatingButton.setBorderPainted(false);
        secondFloatingButton.setOpaque(false);
        secondFloatingButton.setContentAreaFilled(false);
        secondFloatingButton.setToolTipText("Cargar documentos desde RUNT");
        secondFloatingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        secondFloatingButton.addActionListener(_ -> abrirDialogoRunt());

        floating_layer.add(secondFloatingButton, Integer.valueOf(javax.swing.JLayeredPane.PALETTE_LAYER));
    }

    private void abrirDialogoRunt() {
        Window win = SwingUtilities.getWindowAncestor(this);
        JFrame frame = (win instanceof JFrame) ? (JFrame) win : null;
        JDialog dialogoRunt = new JDialog(frame, "Cargar documentos desde RUNT", true);
        dialogoRunt.setSize(550, 380);
        dialogoRunt.setLocationRelativeTo(this);
        dialogoRunt.setResizable(true);

        JPanel panel = new JPanel(null);

        JLabel label_instruccion = new JLabel("Pegue aqui el texto copiado de la pagina del RUNT:");
        label_instruccion.setBounds(10, 10, 400, 20);
        panel.add(label_instruccion);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(10, 35, 520, 250);
        panel.add(scroll);

        JButton boton_cargar = new JButton("Cargar");
        boton_cargar.setBounds(340, 295, 90, 25);
        panel.add(boton_cargar);

        JButton boton_cancelar = new JButton("Cancelar");
        boton_cancelar.setBounds(440, 295, 90, 25);
        panel.add(boton_cancelar);

        boton_cargar.addActionListener(_ -> {
            String texto = textArea.getText();
            if (texto == null || texto.trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogoRunt, "No hay texto para procesar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            HashMap<String, String> datos = ParserRunt.parsearDocumentos(texto);

            String placa = datos.get("placa");
            if (placa == null || placa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogoRunt, "No se pudo extraer la placa del texto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] doc = null;
            try {
                base_documento = new Documentos();
                doc = base_documento.consultar_uno_documentos(placa.trim());
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(dialogoRunt, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } finally {
                if (base_documento != null) base_documento.close();
            }

            if (doc == null || doc[0] == null) {
                JOptionPane.showMessageDialog(dialogoRunt,
                    "El vehiculo " + placa.trim() + " no tiene documentos registrados.\n"
                    + "Use el boton + para crear el registro primero.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean particular = false;
            try {
                Vehiculo v = new Vehiculo();
                particular = v.is_particular(placa.trim());
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(dialogoRunt, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String soat = datos.get("soat_fecha_fin");
            String rtm = datos.get("rtm_fecha_fin");
            String polizas = datos.get("polizas_fecha_fin");
            String topNum = datos.get("top_numero");
            String topFecha = datos.get("top_fecha_fin");

            if (soat == null && rtm == null) {
                JOptionPane.showMessageDialog(dialogoRunt,
                    "No se encontraron fechas de SOAT ni RTM en el texto.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                base_documento = new Documentos();

                if (particular) {
                    String fSoat = soat != null ? soat : doc[2];
                    String fRtm = rtm != null ? rtm : doc[3];
                    ((Documentos) base_documento).actualizar_documento_fechas(placa.trim(), fSoat, fRtm);
                } else {
                    String fSoat = soat != null ? soat : doc[2];
                    String fRtm = rtm != null ? rtm : doc[3];
                    String fPolizas = polizas != null ? polizas : (doc[4].equals("NULL") ? "01/01/2000" : doc[4]);
                    int top = 0;
                    if (topNum != null) {
                        try { top = Integer.parseInt(topNum.trim()); } catch (NumberFormatException e) { top = 0; }
                    }
                    if (top == 0 && !doc[6].equals("NULL")) {
                        try { top = Integer.parseInt(doc[6]); } catch (NumberFormatException e) { top = 0; }
                    }
                    String fTop = topFecha != null ? topFecha : (doc[7].equals("NULL") ? "01/01/2000" : doc[7]);

                    ((Documentos) base_documento).actualizar_documento_fechas(placa.trim(), fSoat, fRtm, top, fTop, fPolizas);
                }

                JOptionPane.showMessageDialog(dialogoRunt,
                    "Documentos de " + placa.trim() + " actualizados correctamente.",
                    "", JOptionPane.INFORMATION_MESSAGE);
                dialogoRunt.dispose();
                accion_text_busqueda();

            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(dialogoRunt, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (base_documento != null) base_documento.close();
            }
        });

        boton_cancelar.addActionListener(_ -> dialogoRunt.dispose());

        dialogoRunt.add(panel);
        dialogoRunt.setVisible(true);
    }

    @Override
    protected void cargar_datos_tabla() {

        try{
            base_documento = new Documentos();   // Hace una coneccion a la base de datos

            tabla = Modelo_tabla.set_tabla_documentos_vehiculos( // Pone un formato para la tabla
                base_documento.consultar_documentos("") // Pasa los datos que va a tener la tabla
            );

        }catch(SQLException | IOException ex){
            // En caso que haya un error, muestra este mensaje de error con el motivo
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage()+"\nCerrando el Programa", "Error", JOptionPane.ERROR_MESSAGE);

            // Esto se utiliza para cerrar el programa despues del error
            if (window != null) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }

        }finally{
            if(base_documento != null){
                base_documento.close(); // Cierra la coneccion a la base de datos
            }
        }
    }

    @Override
    protected void accion_text_busqueda() {

        try{
            base_documento = new Documentos();

            Modelo_tabla.updateTableModel(tabla, base_documento.consultar_documentos(text_busqueda.getText())); // Actualiza el modelo de la tabla

        }catch(SQLException | IOException ex){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            if(base_documento != null){
                base_documento.close(); // Cierra la coneccion a la base de datos
            }
        }
    }

    @Override
    protected void config_listener_pop_menu() {

        item_adicionar.addActionListener(_ ->{
            Insertar_documento_vehiculo doc_vehiculo = new Insertar_documento_vehiculo((JFrame)this.get_window(),  "");
            doc_vehiculo.setVisible(true);

            accion_text_busqueda();
        });

        item_actualizar.addActionListener(_ ->{
            int number = tabla.getSelectedRow();
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);
            Actualizar_documento_vehiculo doc_vehiculo = new Actualizar_documento_vehiculo((JFrame)this.get_window(),  placa_vehiculo);
            doc_vehiculo.setVisible(true);

            accion_text_busqueda();
        });
        item_eliminar.addActionListener(_ ->{

            int number = tabla.getSelectedRow();
            String placa_vehiculo = "" + tabla.getValueAt(number, 0);


            number = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el registro\n"+ placa_vehiculo +"|"+tabla.getValueAt(number, 3), "eliminar", JOptionPane.OK_CANCEL_OPTION);
            if(number == 0){

                try{
                    base_documento = new Documentos();
                    base_documento.eliminar_documento(placa_vehiculo);
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
                }catch(SQLException | IOException ex){
                    JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }finally{
                    if(base_documento != null){
                        base_documento.close(); // Cierra la coneccion a la base de datos
                    }
                }

                accion_text_busqueda();
            }

        });

    }

    @Override
    protected void actionNewButton() {
        Insertar_documento_vehiculo doc_vehiculo = new Insertar_documento_vehiculo((JFrame)this.get_window(),  "");
        doc_vehiculo.setVisible(true);
        accion_text_busqueda();
    }
}
