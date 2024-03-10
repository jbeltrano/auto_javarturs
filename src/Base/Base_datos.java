package Base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Base_datos{
    private String url = "jdbc:sqlite:";
    protected Connection coneccion;
    protected String insertar;
    protected String actualizar;
    protected String borrar;
    protected String consultar;
    protected Statement state;
    protected ResultSet resultado;
    protected PreparedStatement pstate;

    /**
     * Este es el constructor recibe como parametro la ubicacion
     * del archivo .db de sqlite3, y establece coneccion con
     * la base de datos.
     */
    public Base_datos(String url){
        this.url = this.url.concat(url);        //Guarda y da formato a la url que se va a utilizar
        iniciar_base();     // Establece coneccion con la base de datos
    }

    /**
     * Este metodo se encarga de inicializar la base de datos
     * y ver que no hayan errores al moento de establecer
     * coneccion con la misma
     * 
     * @see DriverManager
     */
    private void iniciar_base(){
        
        try{
            coneccion = DriverManager.getConnection(url);
        }catch(SQLException ex){
            System.out.println("Error en la coneccion con la base de datos");
        }
    }

    /**
     * Es el encargado de finalizar la conecicon con
     * la base de datos
     * 
     * @see Connection
     */
    public void close(){
        try{
            coneccion.close();      // Cierra la coneccion con la base de datos
        }catch(SQLException ex){
            System.out.println("No se puedo cerrar la coneccion correctamente");
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
    public Vector<String> get_vector_tabla(String nombre, int columna)throws SQLException{
        Vector<String> vector = new Vector<String>();

        consultar = "select * from "+nombre;        // Da formato para realizar la consulta

        try{

            state = coneccion.createStatement();
            resultado = state.executeQuery(consultar);      // Realiza la consulta y la guarda en resultado
            
            
            while(resultado.next()){
                vector.add(resultado.getString(columna));       // Itera sobre resultado y va guardando el el vector
            }
            
            
        }catch(SQLException ex){
            throw ex;
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
}
