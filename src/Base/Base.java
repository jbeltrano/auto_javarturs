package Base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Utilidades.Leer_rutas;

public class Base{

    protected String[] dato;
    protected String[][] datos;
    private static String url = "jdbc:sqlite:";
    private static boolean band = true;
    protected Connection coneccion = null;
    protected String insertar;
    protected String actualizar;
    protected String borrar;
    protected String consultar;
    protected Statement state = null;
    protected ResultSet resultado = null;
    protected PreparedStatement pstate = null;

    static {    //Esta funcion solo se ejecuta una vez, y se hace cuando se carga la clase en memoria

        try{
            Leer_rutas ruta = new Leer_rutas();
            url = url.concat(ruta.get_ruta(Leer_rutas.DB));

        }catch(IOException ex){

            band = false;
        }
            
    }

    /**
     * Este es el constructor recibe como parametro la ubicacion
     * del archivo .db de sqlite3, y establece coneccion con
     * la base de datos.
     * @throws IOException 
     * @throws SQLException 
     */
    public Base() throws IOException, SQLException {
        if (!band) {      // Se encarga de revisar la bandera, en caso de ser negativo retorna un error
            throw new IOException("No es posible encontrar el archivo: Direccion.txt");
        }
        
        try {
            coneccion = DriverManager.getConnection(url);
            state = coneccion.createStatement();
            state.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ex) {
            close(); // Cierra cualquier recurso que se haya abierto
            throw new SQLException("No es posible establecer conexion con la base de datos", ex);
        }
    }


    /**
     * Es el encargado de finalizar la conecicon con
     * la base de datos
     * 
     * @see Connection
     */
    public void close() {
        try {
            if (resultado != null) resultado.close();
            if (pstate != null) pstate.close();
            if (state != null) state.close();
            if (coneccion != null) coneccion.close();
        } catch(SQLException ex) {
            System.out.println("Error al cerrar los recursos de base de datos: " + ex.getMessage());
        }
    }

    /**
     * Esta funcion se utiliza para generar un vector arbitrario
     * Utiliza el nombre de la tabla y la columna de los valores
     * que se desean obtener en el vector
     * 
     * @param nombre
     * @param columna
     * @return en formato Vector<String>
     * @throws SQLException
     */
    public Vector<String> get_vector_tabla(String nombre, int columna) throws SQLException {
        Vector<String> vector = new Vector<String>();
        Statement localState = null;
        ResultSet localResultado = null;

        consultar = "select * from " + nombre;        // Da formato para realizar la consulta

        try {
            localState = coneccion.createStatement();
            localResultado = localState.executeQuery(consultar);      // Realiza la consulta y la guarda en resultado
            
            while(localResultado.next()) {
                vector.add(localResultado.getString(columna));       // Itera sobre resultado y va guardando el el vector
            }
            
        } catch(SQLException ex) {
            throw ex;
        } finally {
            if (localResultado != null) {
                try {
                    localResultado.close();
                } catch (SQLException e) {
                    // log error
                }
            }
            if (localState != null) {
                try {
                    localState.close();
                } catch (SQLException e) {
                    // log error
                }
            }
        }

        return vector;
    }

    /**
     * Esta funcion se encarga de convertir los daots de una matriz
     * la cual viene con un ecabezado (Los nombre de las tablas) y 
     * lo convierte a un arreglo de tipo String con los datos de
     * la columna seleccionada
     * 
     * @param tabla
     * @param columna
     * @return en formato String[]
     */
    public String[] get_datos_tabla(String[][] tabla,int columna){
        
        String[] dato = new String[tabla.length -1];        // Delimita el encabezado para que no quede entre los demas datos y reserva memoria

        for(int i = 1; i < tabla.length; i++){      // Itera sobre las columnas de las tablas y guarda en arreglo
            dato[i-1] = tabla[i][columna];
        }

        return dato; 
    }

    protected static SQLException no_base(SQLException ex){

        if(ex.getErrorCode() == 1){

            ex = new SQLException("No fue posible acceder a la base de datos, revisar que se encuentre en la ubicacion: " + url.split("jdbc:sqlite:")[1]);
        
        }

        return ex;
    }
}
