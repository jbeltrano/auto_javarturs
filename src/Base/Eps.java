package Base;

import java.sql.SQLException;

public class Eps extends Base{
    
    public Eps(String url){
        super(url);
    }

    /**
     * Se encarga de insertar las diferentes
     * eps en las que puede estar afiliado
     * un empleado utilizando el parametro
     * {@code eps_nombre} el cual contiene
     * el nombre de la eps.
     * 
     * @param eps_nombre
     * @throws SQLException
     */
    public void insertar_eps(String eps_nombre)throws SQLException{
        insertar = "insert into eps (eps_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, eps_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro de
     * eps en la base de datos identificandolo
     * con el parametro {@code id} que es de tipo
     * {@code int}.
     * 
     * @param id
     * @throws SQLException
     */
    public void eliminar_eps(int id)throws SQLException{
        borrar = "delete from eps where eps_id = ?";

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
     * Retorna una matrix de tipo {@code String[][]},
     * la cual contiene los diferentes registros que hay
     * en la base de datos sobre las eps.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_eps()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from eps";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from eps");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }
            resultado.close();

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "EPS";


            while(resultado.next()){

                datos[i][0] = "" + resultado.getInt("eps_id");
                datos[i][1] = resultado.getString("eps_nombre");
                
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
