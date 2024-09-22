package Front.Ciudades_departamentos;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JDialog;

public abstract class Modal_ruta extends JDialog{

    protected String url;

    public Modal_ruta(JFrame padre, String url){
        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        set_dimension();
        iniciar_componentes();
        setLocationRelativeTo(padre);
    }

    public Modal_ruta(JDialog padre, String url){
        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        set_dimension();
        iniciar_componentes();
        setLocationRelativeTo(padre);
    }
    
    protected abstract void iniciar_componentes();
    
    protected void set_dimension(){
        setPreferredSize(new Dimension(730,360));
    }
}