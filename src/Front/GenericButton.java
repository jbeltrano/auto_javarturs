package Front;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import Utilidades.GenericCallback;

public class GenericButton{
    
    public static final int x_INICIAL = 10;
    public static final int y_INICIAL = 10;
    public static final int ANCHO = 50;
    public static final int ALTO = 50;

    private GenericCallback function;
    private JButton button;
    private JLabel label;
    private int x;
    private int y;
    private int ancho;
    private int alto;

    public GenericButton(GenericCallback function, ImageIcon icon, String labelText) {
        this.button = new JButton();
        this.label = new JLabel();
        this.function = function;

        setTextLabel(labelText);
        setIcon(icon);

        x = x_INICIAL;
        y = y_INICIAL;
        ancho = ANCHO;
        alto = ALTO;

        initButton();
    }
    
    public GenericButton(GenericCallback function) {

        this.button = new JButton();
        this.button.setToolTipText(null);
        this.label = new JLabel();
        this.function = function;

        x = x_INICIAL;
        y = y_INICIAL;
        ancho = ANCHO;
        alto = ALTO;

        initButton();
    }
    
    public void initButton(){
        setLocation();
        setSize();
        setBackGroundColor();
        configLabel();
        button.addActionListener(_ -> function.callback());
    }

    public void setIcon(ImageIcon icon){
        button.setIcon(icon);
    }

    public void setSize(){
        button.setSize(ancho, alto);
    }

    public void setSize(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        button.setSize(ancho, alto);
        configLabel();
    }

    public void setLocation(){
        button.setLocation(x, y);
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
        button.setLocation(x, y);
        configLabel();
    }
    
    public void setTextLabel(String text){
        label.setText(text);
    }

    public void addMouseListener(MouseListener mouseListener){
        button.addMouseListener(mouseListener);
    }

    public JButton getButton() {
        return button;
    }

    public JLabel getLabel() {
        return label;
    }

    private void setBackGroundColor(){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0x000000));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(javax.swing.UIManager.getColor("Button.background"));

            }
        });
    }

    private void configLabel(){
        label.setLocation(x + ancho +10, y + (alto-20)/2);
        label.setSize(170, 40);
    }
}
