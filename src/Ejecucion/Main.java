package Ejecucion;

import java.io.IOException;
import javax.swing.SwingUtilities;
import Front.Principal;

public class Main {
    
    public static void main(String[] args) throws IOException{

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal("src\\DB\\base.db");
            }
        });

    }
}