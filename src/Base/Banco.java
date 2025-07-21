package Base;

import java.io.IOException;
import java.sql.SQLException;

public class Banco extends Base{
    
    public Banco()throws IOException, SQLException{
        super();
    }

    /**
     * Inserta un registro en la base de datos
     * con el nombre del banco utilizando el
     * parametro {@code ban_nombre}
     * @param ban_nombre Pasa el nombre del banco
     * para que este se inserte en
     * la base de datos.
     * 
     * @throws SQLException
     */
    public void insertar_banco(String ban_nombre)throws SQLException{
        insertar = "insert into banco(ban_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, ban_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro
     * de la base de datos utilizando el 
     * parametro {@code id}
     * @param id Es el identificador del
     * banco a elminar en la base de datos
     * 
     * @throws SQLException
     */
    public void eliminar_banco(int id)throws SQLException{
        borrar = "delete from banco where ban_id = " + id;

        try{
            state = coneccion.createStatement();
            state.executeUpdate(borrar);
        }catch(SQLException ex){
            throw ex;
        }finally{
            state.close();
        }
    }

    /**
     * Consulta y retorna un String[][]
     * donde se encuentran los datos y
     * registros que hay en la tabla
     * Banco
     * @return String[][] con los
     * datos consultados en Banco
     * @throws SQLException
     */
    public String[][] consultar_banco()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from banco";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from banco");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "CESANTIAS";

            while(resultado.next()){

                datos[i][0] = resultado.getString("ban_id");
                datos[i][1] = resultado.getString("ban_nombre");

                
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

}
