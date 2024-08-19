package Base;

import java.sql.SQLException;

public class Cesantias extends Base{
    
    public Cesantias(String url){
        super(url);
    }

    /**
     * Se encarga de insertar en la base de datos
     * las diferentes identidades que prestan el
     * servicio para las cesantias
     * @param ces_nombre es el que pasa el nombre
     * de la entidad a incertar
     * en la base de datos.
     * @throws SQLException
     */
    public void insertar_cesantias(String ces_nombre)throws SQLException{
        insertar = "insert into cesantias (ces_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, ces_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro en
     * la base de datos.
     * @param id Es el parametro que indica el
     * registro a eliminar especificamente el
     * id de la entidad de cesantias.
     * 
     * @throws SQLException
     */
    public void eliminar_cesantias(int id)throws SQLException{
        borrar = "delete from cesantias where ces_id = " + id;

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
     * Se encarga de hacer la consulta de todos los
     * registro que tiene la tabla Cesantias y retornar
     * un String[][] con todos los datos de la tabla
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_cesantias()throws SQLException{
        
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from cesantias";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from cesantias");
            
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

                datos[i][0] = resultado.getString("ces_id");
                datos[i][1] = resultado.getString("ces_nombre");

                
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
