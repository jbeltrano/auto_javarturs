package Front.Personas;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import Base.Base;

public abstract class Modales_personas extends JDialog{
    
    protected String url;
    protected Base base;

    public Modales_personas(JDialog padre, String url, Dimension dimension){

        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(dimension);
        setResizable(false);
        iniciar_componentes();
        setLocationRelativeTo(padre);
        
    }

    public Modales_personas(JFrame padre, String url, Dimension dimension){

        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(dimension);
        setResizable(false);
        iniciar_componentes();
        setLocationRelativeTo(padre);

    }
    protected abstract void iniciar_componentes();
}