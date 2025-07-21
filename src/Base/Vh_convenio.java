package Base;

import java.io.IOException;
import java.sql.SQLException;

public class Vh_convenio extends Base {

    public Vh_convenio() throws IOException, SQLException {
        super();
        
    }
    
    /**
     * Este metodo se utiliza para visualizar
     * los vehiculos que estan por convenio, y
     * son utilizados por la empresa
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[][] consultar_vh_convenio(String buscar)throws SQLException{

        datos = new String[1][4];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_vehiculo_externo where veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\'";
        
        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo_externo where veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\'");
        
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][4];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "TIPO DOCUMENTO";
            datos[0][2] = "N° DOCUMENTO";
            datos[0][3] = "NOMBRE EMPRESA";


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
     * Este metodo se utiliza para realizar una insercion
     * de un registro en la base de datos, especificamente
     * en la tabla vehiculo_externo, donde reposan los vehiculos
     * que cuentan con convenio en la empresa
     * 
     * @param veh_placa Este es el parametro que identifica
     * el vehiculo en la base de datos
     * 
     * @param veh_propietario Este es el parametro que identifica a la empresa
     * con la que se tiene el convenio
     * 
     * @throws SQLException
     */
    public void insertar_vh_convenio(String veh_placa, String veh_propietario)throws SQLException{

        insertar = "insert into vehiculo_externo values (?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, veh_placa);
            pstate.setString(2, veh_propietario);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de elmiminar un registro en la
     * base de datos.
     * 
     * @param id Es el identificador del vehiculo
     * para poder elminarlo de la base de datos.
     * 
     * @throws SQLException
     */
    public void eliminar_vh_convenio(String id)throws SQLException{
        borrar = "delete from vehiculo_externo where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

}
