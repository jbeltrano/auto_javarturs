package Front.Extractos;

import java.sql.SQLException;

import javax.swing.JFrame;

import Base.Base;

public class Actualizar_contratante extends Insertar_contratante{

    private String id;
    public Actualizar_contratante(JFrame padre, String url, String id){
        super(padre, url);
        this.id = id;
        modificar();
    }
    
    private void modificar(){
        text_contratante.setText(id);

        text_contratante.setEnabled(false);
        tabla_contratante.setEnabled(false);
        
    }

    @Override
    protected void guardar()throws SQLException{

        base = new Base(url);

        base.actualizar_contratante(text_contratante.getText(), text_responsable.getText());

        base.close();

    }
}
