package Front.Ciudades_departamentos;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JDialog;

public abstract class Modal_ciudades_departamentos extends JDialog{


    public Modal_ciudades_departamentos(JFrame padre){
        super(padre, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(460,250));
        iniciar_componentes();
        setLocationRelativeTo(padre);
    }

    public Modal_ciudades_departamentos(JDialog padre){
        super(padre, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(460,250));
        iniciar_componentes();
        setLocationRelativeTo(padre);
    }
    protected abstract void iniciar_componentes();
    
}
