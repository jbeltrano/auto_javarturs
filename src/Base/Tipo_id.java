package Base;

import java.io.IOException;
import java.sql.SQLException;

public class Tipo_id extends Base{

    public Tipo_id() throws IOException, SQLException{
        super();
    }

    /**
     * Inserta el tipo de id para una persona o entidad
     * dijitando el nombre a ocupar como tipo de id
     * @param tip_nombre
     * @throws SQLException
     */
    public void insertar_tipo_id(String tip_nombre)throws SQLException{
        insertar = "insert into tipo_id (tip_nombre) values (?)";
        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, tip_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Actualiza el tipo de id, especificamente
     * el nombre del tipo de id.
     * Utiliza a {@code id} para identificar el tipo de id
     * y lo remplaza con {@code tip_nombre} en la base de datos
     * @param id
     * @param tip_nombre
     * @throws SQLException
     */
    public void actualizar_tipo_id(int id, String tip_nombre)throws SQLException{
        actualizar = "update tipo_id set tip_nombre = ? where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, tip_nombre);
            pstate.setInt(2, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Elimina el registro en la base de datos
     * identificado con el parametro {@code id}
     * @param id
     * @throws SQLException
     */
    public void eliminar_tipo_id(int id)throws SQLException{
        borrar = "delete from tipo_id where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setInt(1, id);

            pstate.executeQuery();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Retorna un {@code String[]} dependiendo
     * de la columna que se pase en el parametro {@code columna}
     * siempre y cuando la columna exista en la base de datos
     * @param columna
     * @return
     * @throws SQLException
     */
    public String[] consultar_tipo_id(int columna)throws SQLException{
        
        datos = consultar_tipo_id();
        dato = new String[datos.length-1];
        for(int i = 0; i < datos.length-1; i++){
            dato[i] = datos[i+1][columna];
        }
        return dato;
    }

    /**
     * Retorna una matrix con los registros
     * que se encuentren en la base de datos
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_tipo_id()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;
        
        consultar = "select * from tipo_id";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from tipo_id");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO DOCUMENTO";

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("tip_id"));
                datos[i][1] = resultado.getString("tip_nombre");
                
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
     * Retorna un arreglo con un registro
     * completo de la base de datos identificado
     * con el parametro {@code id}
     * @param id
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_uno_tipo_id(int id)throws SQLException{
        dato = new String[2];
        dato[0] = null;
        dato[1] = null;

        consultar = "select * from tipo_id where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(consultar);

            pstate.setInt(1, id);

            resultado = pstate.executeQuery();

            if (resultado.next()){
                dato[0] = "" + resultado.getInt("tip_id");
                dato[1] = resultado.getString("tip_id");
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
        }
        return dato;
    }
}
