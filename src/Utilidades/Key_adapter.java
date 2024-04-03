package Utilidades;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class Key_adapter extends KeyAdapter{
    private String variable_auxiliar;
    private char key;
    
    public Key_adapter(String text){
        super();
        variable_auxiliar = text;
    }

    public void set_text(String text){

        variable_auxiliar = text;
    }
    public String get_text(){
        return variable_auxiliar;
    }
    
    public void keyPressed(KeyEvent evt) {

        key = evt.getKeyChar();        
        if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE && evt.isControlDown()) {
            
            int lastIndex = variable_auxiliar.lastIndexOf(' ');

            if (lastIndex != -1) {
                variable_auxiliar = variable_auxiliar.substring(0, lastIndex);
            } else {

                variable_auxiliar = "";
            }
        
        } else if (Character.isLetterOrDigit(key) || key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_SPACE) {
            if (evt.getExtendedKeyCode() != KeyEvent.VK_BACK_SPACE) {
                variable_auxiliar = variable_auxiliar.concat(Character.toString(key));
            } else {
                if (!variable_auxiliar.isEmpty()) {
                    variable_auxiliar = variable_auxiliar.substring(0, variable_auxiliar.length() - 1);
                }
            }
        
            
        }
        accion();

    }

    abstract public void accion();
}
