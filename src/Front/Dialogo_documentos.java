package Front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dialogo_documentos extends JDialog {

    private JPanel Dialogo_documentosPanel;
    private JScrollPane scrollPane;
    private List<String[]> Lista_vehiculos;
    
    public Dialogo_documentos(Frame parent) {
        super(parent, "Vencimientos documentos", true);
        Lista_vehiculos = new ArrayList<>();
        initializeComponents();
        setupLayout();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        Dialogo_documentosPanel = new JPanel();
        Dialogo_documentosPanel.setLayout(new BoxLayout(Dialogo_documentosPanel, BoxLayout.Y_AXIS));
        Dialogo_documentosPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        Dialogo_documentosPanel.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(Dialogo_documentosPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        JLabel titleLabel = new JLabel("Vencimientos de Vehículos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(_ -> dispose());
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void addVehicle(String[] vehicle) {
        Lista_vehiculos.add(vehicle);
        refreshDisplay();
    }
    
    private void refreshDisplay() {
        Dialogo_documentosPanel.removeAll();
        
        for (String novedad_vehiculo[] : Lista_vehiculos) {
            JPanel vehiclePanel = createVehiclePanel(novedad_vehiculo);
            Dialogo_documentosPanel.add(vehiclePanel);
            Dialogo_documentosPanel.add(Box.createVerticalStrut(10));
        }
        
        Dialogo_documentosPanel.revalidate();
        Dialogo_documentosPanel.repaint();
    }
    
    private JPanel createVehiclePanel(String[] vehicle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(new Color(248, 248, 248));
        
        // Título del vehículo
        JLabel titleLabel = new JLabel(vehicle[0]);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        
        // Items de vencimiento
        
        JLabel itemLabel = new JLabel("<html>" + vehicle[1].replaceAll("\n", "<br>") + "</html>");
        itemLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        itemLabel.setForeground(new Color(80, 80, 80));
        panel.add(itemLabel);
        
        return panel;
    }

}