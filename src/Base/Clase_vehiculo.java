package Base;

import java.sql.SQLException;
import java.util.Vector;

public class Clase_vehiculo extends Base{

    private static final int NUMERO_ATRIBUTOS_TABLA = 2;
    private static final String[] NOMBRES_CABEZA_TABLA = {"ID", "TIPO VEHICULO"};

    /**
     * Metodo constructor
     * @param url Es la ruta del archivo
     * donde se encuentra la base de datos
     */
    public Clase_vehiculo(String url){
        super(url);
    }

    /**
     * Este metodo se utiliza para insertar
     * las diferentes clases de vehiculos
     * que se requieran en la base de datos
     * @param cla_nombre
     * @throws SQLException
     */
    public void insertar_clase_vehiculo(String cla_nombre)throws SQLException{
        insertar = "insert into clase_vehiculo (cla_nombre) values (?)";
        try{

            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, cla_nombre);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }
    
    /**
     * Actualiza en la base de datos
     * el nombre o tipo de clase de vehiculo
     * dependiendo el id ingresado
     * @param id
     * @param nombre
     * @throws SQLException
     */
    public void actualizar_clase_vehiculo(int id, String nombre)throws SQLException{
        
        actualizar = "update clase_vehiculo set cla_nombre = ? where cla_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, nombre);
            pstate.setInt(2, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Actualiza la clase o tipo de
     * vehiculo mediante la busqueda por 
     * el nombre
     * @param nombre_inicial
     * @param nombre_final
     * @throws SQLException
     */
    public void actualizar_clase_vehiculo(String nombre_inicial, String nombre_final)throws SQLException{
        actualizar = "update clase_vehiculo set cla_nombre = ? where cla_nombre like ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, nombre_final);
            pstate.setString(2, nombre_inicial);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }
    
    /**
     * Elimina la clase o tipo de vehiculo
     * mediante el id ingresado
     * @param id
     * @throws SQLException
     */
    public void eliminar_clase_vehiculo(int id)throws SQLException{
        borrar = "delete from clase_vehiculo where cla_id = ?";

        try{

            pstate = coneccion.prepareStatement(borrar);
                
            pstate.setInt(1, id);

            pstate.executeUpdate();
            
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Consulta todas las clases de vehiculo
     * y las retorna en forma de vector,
     * dependiendo la columna que se pase como
     * parametro
     * @param columna
     * @return
     * @throws SQLException
     */
    public Vector <String> consultar_clase_vehiculo(int columna)throws SQLException{
        Vector<String> vector = new Vector<>();

        int cantidad = 0;
        
        consultar = "select * from clase_vehiculo";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from clase_vehiculo");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return vector;
            }

            resultado = state.executeQuery(consultar);

            if(columna == 1){
                while(resultado.next()){
                    vector.add(""+resultado.getInt("cla_id"));
                }
            }
            else if(columna == 2){
                while(resultado.next()){
                    vector.add(resultado.getString("cla_nombre"));
                }
            }
            else{
                while(resultado.next()){
                    vector.add(resultado.getInt("cla_id") + "|" + resultado.getString("cla_nombre"));
                }
            }

        }catch(SQLException ex){
            throw ex;
        }finally{
            state.close();
            resultado.close();
        }


        return vector;
    }

    /**
     * Retorna todos los registros que hay
     * en la clase de vehiculo en forma de matriz
     * @return
     * @throws SQLException
    */
    public String[][] consultar_clase_vehiculo()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;
        
        consultar = "select * from clase_vehiculo";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from clase_vehiculo");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            set_nombres_cabecera();

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("cla_id"));
                datos[i][1] = resultado.getString("cla_nombre");
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            state.close();
        }

        return datos;
    }

    /**
     * Consulta un la clase de un vehiculo pasando como paramentro
     * el id del vehiculo
     * @param id de tipo String, como identificacion de la clase del vehiculo
     * @return
     * @throws SQLException
     */
    public String[] consultar_uno_clase_vehiculo(String id)throws SQLException{
        dato = new String[NUMERO_ATRIBUTOS_TABLA];
        dato[0] = null;
        dato[1] = null;

        consultar = "select * from clase_vehiculo where cla_id = ?";

        try{
            pstate = coneccion.prepareStatement(consultar);
            try{
                pstate.setInt(1, Integer.parseInt(id));
            }catch(NumberFormatException ex){
                consultar = "select * from clase_vehiculo where cla_nombre like ?";
                pstate = coneccion.prepareStatement(consultar);
                pstate.setString(1, id);
            }

            resultado = pstate.executeQuery();

            if (resultado.next()){
                dato[0] = "" + resultado.getInt(1);
                dato[1] = resultado.getString(2);
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
        }
        return dato;
    }

    @Override
    protected void set_nombres_cabecera(){
        datos[0] = NOMBRES_CABEZA_TABLA;
    }

}
