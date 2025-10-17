package Base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import Utilidades.Leer_rutas;

public class Coneccion_base {
    private static Coneccion_base instancia = null;
    private Connection coneccion = null;
    private static String url = "jdbc:sqlite:";
    private static boolean band = true;
    
    static {    //Esta funcion solo se ejecuta una vez, y se hace cuando se carga la clase en memoria

        try{
            Leer_rutas ruta = new Leer_rutas();
            url = url.concat(ruta.get_ruta(Leer_rutas.DB));

        }catch(IOException ex){

            band = false;
        }
            
    }

    private Coneccion_base() throws IOException {
        if (!band) {      // Se encarga de revisar la bandera, en caso de ser negativo retorna un error
            throw new IOException("No es posible encontrar el archivo: Direccion.txt");
        }
        
        try {
            coneccion = java.sql.DriverManager.getConnection(url);
        } catch (Exception ex) {
            throw new IOException("No es posible establecer conexion con la base de datos", ex);
        }
    }

    public static Coneccion_base get_instancia() throws IOException, SQLException {
        if (instancia == null) {
            instancia = new Coneccion_base();
        }
        return instancia;
    }

    public Connection get_coneccion() {
        return coneccion;
    }

    public void close_coneccion() throws SQLException {
        if (coneccion != null && !coneccion.isClosed()) {
            coneccion.close();
            instancia = null;
        }
    }

    protected static SQLException no_base(SQLException ex){

        if(ex.getErrorCode() == 1){

            ex = new SQLException("No fue posible acceder a la base de datos, revisar que se encuentre en la ubicacion: " + url.split("jdbc:sqlite:")[1]);
        
        }

        return ex;
    }
}
