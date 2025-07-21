package Front.Extractos;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import Base.Base;

public abstract class Modal_documento extends JDialog{
    
    protected Base base;

    public Modal_documento(JFrame padre){
        super(padre, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        iniciar_componentes();
        setPreferredSize(set_dimension());
        pack();
        setLocationRelativeTo(padre);

    }

    protected abstract void iniciar_componentes();
    protected abstract Dimension set_dimension();
    
    
}
