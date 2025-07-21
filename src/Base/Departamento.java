package Base;

import java.io.IOException;
import java.sql.SQLException;

public class Departamento extends Base{
    
    public Departamento() throws IOException, SQLException{
        super();
    }

    /**
     * Retorna un {@code String[]} con cont
     * todos los nombres de los departamentos
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_departamento()throws SQLException{
        dato = new String[1];
        int cantidad = 0;
        int i = 0;

        consultar = "select * from departamento";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from departamento");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return dato;
            }

            dato = new String[cantidad];

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                dato[i] = resultado.getString(2);
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            state.close();
        }

        return dato;
    }

    /**
     * Retorna una matrix de {@code String[][]},
     * donde aparecen los nombre y id de los
     * diferentes departamentos registrados en la
     * base de datos
     * @return
     * @throws SQLException
     */
    public String[][] consultar_departamentos()throws SQLException{
        datos = new String[1][2];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from departamento";

        try{

            

            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from departamento");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad +1][2];

            datos[0][0] = "ID";
            datos[0][1] = "DEPARTAMENTO";

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                
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
     * Retorna una matrix de {@code String[][]},
     * con los id y nombres de los departamentos, usando
     * como parametro de busqueda {@code buscar}
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[][] consultar_departamentos(String buscar)throws SQLException{
        datos = new String[1][2];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from departamento where dep_nombre like \'" + buscar + "%\'";

        try{

            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from departamento where dep_nombre like \'" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad +1][2];

            datos[0][0] = "ID";
            datos[0][1] = "DEPARTAMENTO";

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                
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
     * Retorna el id del tepartamento con un
     * dato de tipo {@code int}, utilizando
     * como parametro de busqueda el nombre exacto del
     * departamento usando el parametro {@code departamento}
     * @param departamento
     * @return int
     * @throws SQLException
     */
    public int consultar_id_departamento(String departamento) throws SQLException{

        consultar = "select dep_id from departamento where dep_nombre = ?";
        try{

            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, departamento);

            resultado = pstate.executeQuery();

            if(resultado.next()){
                return resultado.getInt(1);
            }else{
                SQLException ex = new SQLException("No hay resultados para tu consulta sobre departamento");
                throw ex;
            }
        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
        }
    }
    
}
