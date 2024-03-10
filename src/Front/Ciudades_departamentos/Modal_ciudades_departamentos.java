package Front.Ciudades_departamentos;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JDialog;

public abstract class Modal_ciudades_departamentos extends JDialog{

    protected String url;

    public Modal_ciudades_departamentos(JFrame padre, String url){
        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(460,250));
        iniciar_componentes();
        setLocationRelativeTo(padre);
    }

    protected abstract void iniciar_componentes();
    
}
