package Front.Extractos;

import javax.swing.JDialog;
import javax.swing.JFrame;
public abstract class Modal_documento extends JDialog{
    
    protected String url;

    public Modal_documento(JFrame padre, String url){
        super(padre, true);
        this.url = url;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        iniciar_componentes();
        setLocationRelativeTo(padre);

    }

    protected abstract void iniciar_componentes();
    
}
