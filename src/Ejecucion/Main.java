package Ejecucion;

import javax.swing.SwingUtilities;
import Front.Principal;

public class Main {
    
    public static void main(String[] args){
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal();
            }
        });
    }
}