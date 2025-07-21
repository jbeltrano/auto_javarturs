package Front.Vehiculos;

import javax.swing.JDialog;
import javax.swing.JFrame;
import Base.Base;

import java.awt.Dimension;

public abstract class Modales_vehiculos extends JDialog{
    
    protected String valor;
    protected Base base;

    public Modales_vehiculos(JFrame frame, String valor){
        super(frame, true);
        this.valor = valor;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600,600));
        setResizable(false);

        iniciar_componentes();
        setLocationRelativeTo(frame);
        //setLocation(frame.getLocation());
        
    }

    public Modales_vehiculos(JFrame frame){
        super(frame, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600,600));
        setResizable(false);

        iniciar_componentes();
        setLocationRelativeTo(frame);
        //setLocation(frame.getLocation());
        
    }

    protected abstract void iniciar_componentes();

}