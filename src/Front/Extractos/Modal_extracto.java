package Front.Extractos;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import Base.Base;

public abstract class Modal_extracto extends JDialog{
    
    protected Base base;

    public Modal_extracto(JFrame padre){
        super(padre, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        iniciar_componentes();
        setPreferredSize(set_dimension());
        pack();
        setLocationRelativeTo(padre);
        revalidate();
        repaint();

    }

    public Modal_extracto(JDialog padre){
        super(padre, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        iniciar_componentes();
        setPreferredSize(set_dimension());
        pack();
        setLocationRelativeTo(padre);
        revalidate();
        repaint();

    }

    protected abstract void iniciar_componentes();
    protected abstract Dimension set_dimension();

}
