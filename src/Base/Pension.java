package Base;

import java.sql.SQLException;

public class Pension extends Base{
    
    public Pension(String url){
        super(url);
    }

    /**
     * Se encarga de insertar los diferentes
     * tipos de pensiones en los que los empleados
     * pueden estar afiliados utilizando el parametro
     * {@code pen_nombre} como el nombre de pension.
     * @param pen_nombre
     * @throws SQLException
     */
    public void insertar_pension(String pen_nombre)throws SQLException{
        insertar = "insert into pension (pen_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, pen_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro de
     * la base de datos de las pensiones,
     * utilizando el registro {@code id},
     * de tipo {@code int}, el cual identifica
     * el tipo o nombre de pension.
     * 
     * @param id
     * @throws SQLException
     */
    public void eliminar_pension(int id)throws SQLException{
        borrar = "delete from pension where pen_id = " + id;

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
     * Retorna una matrix de tipo {@code String[][]},
     * la cual contiene los diferentes registros de
     * pension en la base de datos.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_pension()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from pension";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from pension");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                
            }
            resultado.close();

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "AFP";

            while(resultado.next()){

                datos[i][0] = resultado.getString("pen_id");
                datos[i][1] = resultado.getString("pen_nombre");

                
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
