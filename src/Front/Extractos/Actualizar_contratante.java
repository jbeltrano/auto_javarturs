package Front.Extractos;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import Base.Contratante;

public class Actualizar_contratante extends Insertar_contratante{

    private String id;
    public Actualizar_contratante(JFrame padre, String id){
        super(padre);
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

        try{
            base = new Contratante();

            ((Contratante)base).actualizar_contratante(text_contratante.getText(), text_responsable.getText());
        }catch(SQLException | IOException ex){
            throw new SQLException("Error al actualizar el contratante: " + ex.getLocalizedMessage());
            
        }finally{
            if(base != null) base.close();
        }
        

    }
}
