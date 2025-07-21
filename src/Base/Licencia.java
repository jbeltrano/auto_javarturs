package Base;
import java.io.IOException;
import java.sql.SQLException;

public class Licencia extends Base{

    public Licencia() throws IOException, SQLException{
        super();
    }
    
    /**
     * Se encarga de insertar la licencia de una
     * persona en la base de datos.
     * 
     * @param per_id Es el que identifica la persona
     * a la cual se le va a registrar la lisencia.
     * 
     * @param categoria Es la categoria de la licnecia
     * que se va a insertar, puede ir desde A2 hata C3
     * 
     * @param fecha Es al fecha de vencimiento de la
     * licencia
     * 
     * @throws SQLException
     */
    public void insertar_licencia(String per_id, int categoria, String fecha)throws SQLException{
        
        insertar = "insert into licencia values (?,?,?)";
        try{
            pstate = coneccion.prepareStatement(insertar);
            pstate.setString(1, per_id);
            pstate.setInt(2, categoria);
            pstate.setString(3, fecha);

            pstate.executeUpdate();
            
        }catch(SQLException ex){
            SQLException e = new SQLException("No fue posible insertar licencia\nError 1.");
            throw e;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de actualizar la licencia
     * de una persona
     * 
     * @param per_id Es el id de la persona
     * a la cual se le va a ctualizar la licencia
     * 
     * @param categoria Es la categoria de la licencia
     * que se va a actualizar por si hay una recategorizacion.
     * 
     * @param fecha Es al fecha de vencimiento
     * de la licencia por si se realizo alguna
     * refrendacion.
     * 
     * @throws SQLException
     */
    public void actualizar_licencia(String per_id, int categoria, String fecha) throws SQLException{

        actualizar = "update licencia set lic_fecha = ?, cat_id = ? where per_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, fecha);
            pstate.setInt(2, categoria);
            pstate.setString(3,per_id);
            

            pstate.executeUpdate();
        }catch(SQLException ex){
            SQLException e = new SQLException("No es posible realizar la actualizacion.\nError 3");
            throw e;
        }finally{
            pstate.close();
        }
    }

    
    /**
     * Se encarga de eliminar un registro
     * de la licencia de algun conductor.
     * 
     * @param per_id Es el id que identifica
     * la licencia que se va a eliminar
     * 
     * @param categoria  Es la categoria de
     * la licencia para tambien identificar
     * la licencia que se va a eliminar.
     * 
     * @throws SQLException
     */
    public void eliminar_licencia(String per_id, int categoria)throws SQLException{
        
        borrar = "delete from licencia where per_id = ? and cat_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, per_id);
            pstate.setInt(2, categoria);

            pstate.executeUpdate();
        }catch(SQLException ex){
            SQLException e = new SQLException("No es posible eliminar el elemento.\nError 1");
            throw e;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de consultar los diferentes
     * registros que hay en la tabla licencia
     * en la base de ddatos utilizando un
     * filtro dado por el parametro {@code buscar}
     * 
     * @param buscar Es el parametro utilizado
     * para filtrar la informacion, este puede ser
     * el id de la persona o el nombre de la persona.
     * 
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_licencia(String buscar)throws SQLException{

        datos = new String[1][4];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_licencia where per_id like \'" + buscar + "%\' or per_nombre like \'%" + buscar + "%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_licencia where per_id like \'" + buscar + "%\' or per_nombre like \'%" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }
            
            resultado.close();

            datos = new String[cantidad+1][4];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "NUMERO";
            datos[0][1] = "NOMBRE";
            datos[0][2] = "CATEGORIA";
            datos[0][3] = "FECHA_VENCIMIENTO";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                

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
     * Consulta todos los registros que hayan
     * en la tabla licencia en la base de datos'
     * 
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_licencia()throws SQLException{

        datos = new String[1][4];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_licencia";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from licencia");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            datos = new String[cantidad+1][4];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "NUMERO";
            datos[0][1] = "NOMBRE";
            datos[0][2] = "CATEGORIA";
            datos[0][3] = "FECHA_VENCIMIENTO";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                

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
     * Se encarga de consultar una unica licencia
     * que retornara los atributos registrados
     * en la tabla licencia.
     * 
     * @param buscar Es el parametro que se utilizar
     * para identificar la licencia.
     * 
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_uno_licencia(String buscar) throws SQLException{
        dato = new String[3];

        consultar = "select * from licencia where per_id = \'" + buscar + "\'";

        try{
            state = coneccion.createStatement();
            resultado = state.executeQuery(consultar);

            if(resultado.next()){
                dato[0] = resultado.getString(1);
                dato[1] = resultado.getString(2);
                dato[2] = resultado.getString(3);
            }

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            state.close();
        }

        return dato;
    }

    public String[][] consultar_categoria()throws SQLException{

        datos = new String[1][2];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from categoria";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from categoria");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            datos = new String[cantidad+1][4];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "CATEGORIA";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;
    }

    /**
     * Se utiliza para consultar todos los
     * registros de la tabla categoria en cuyo
     * caso siendo filtrados por el parametro
     * {@code id}.
     * 
     * @param id Es el parametro que identifica
     * la categoria de una licencia.
     * 
     * @return String[]
     * 
     * @throws SQLException
     */
    public String[] consultar_uno_categoria(String id)throws SQLException{

        int id_numero = 0;
        boolean flag = false;
        dato = new String[2];
        for(int i = 0; i < dato.length; i++){
            dato[i] = null;
        }

        try{
            
            Integer.parseInt(id);
            consultar = "select * from categoria where cat_id = ?";
            flag = true;

        }catch(NumberFormatException ex){
            consultar = "select * from categoria where cat_categoria like ?";
        }
        

        try{
            pstate = coneccion.prepareStatement(consultar);

            if(flag){
                pstate.setInt(1, id_numero);
            }else{
                pstate.setString(1, id);
            }
            
            resultado = pstate.executeQuery();

            if (resultado.next()){

                dato[0] = resultado.getString(1);
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
}
