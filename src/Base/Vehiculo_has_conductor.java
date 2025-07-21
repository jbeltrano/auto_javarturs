package Base;

import java.io.IOException;
import java.sql.SQLException;

public class Vehiculo_has_conductor extends Base{
    
    /**
     * constructor de la clase Vehiculo_has_conductor
     * @param url
     * @throws SQLException 
     * @throws IOException 
     */
    public Vehiculo_has_conductor() throws IOException, SQLException{
        super();
    }

    /**
     * Se encarga de insertar o asiganar un conductor
     * a un vehiculo, generalmente el maximo de conductores
     * que puede tener un vehiculo son 3, por ese motivo se limita
     * la cantidad de conductores a insertar desde este metodo.
     * 
     * @param conductor Es el id o numero de docuemnto del
     * conductor a asignar al vehiculo.
     * 
     * @param placa Es el vehiculo al cual se le va a 
     * hacer la asignacion del conductor.
     * 
     * @throws SQLException
     */
    public void insertar_vehiuclo_has_conductor(String conductor, String placa)throws SQLException{
        
        insertar = "insert into vehiculo_has_conductor values (?,?)";   // Es el comando a realizar

        try{
            state = coneccion.createStatement();    // Se crea un Statement para ingresar los datos de forma segura
            resultado = state.executeQuery("select count(veh_placa) from vehiculo_has_conductor where veh_placa like \'" + placa +"\'");

            if(resultado.next()){   // Se verifica el resultado de la miniconsulta
                if(resultado.getInt(1) < 3){    // Si hay menos de 3 conductores procede a alistar el comando
                    
                    // Alista los atributos para hacer la isercion en la base de datos.
                    pstate = coneccion.prepareStatement(insertar);

                    pstate.setString(1, placa);
                    pstate.setString(2, conductor);
        
                    pstate.executeUpdate(); // Ejecuta el comando en la base de datos.

                }else{  // Si llegan a haber mas de 3 conductores pasa a realizar una excepcion comunicandolo
                    SQLException ex = new SQLException("Imposible realizar la insercion porque\nEl vehiculo " + placa + " Ya tiene 3 conductores");
                    throw ex;
                }
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
            state.close();
            resultado.close();
        }
    }

    /**
     * Se encarga de eliminar un conductor asignado
     * a un vehiculo en la base de datos.
     * 
     * @param conductor Es el id del conductor a eliminar.
     * 
     * @param placa Es la placa del vehiculo al cual
     * esta asigando el conductor.
     * 
     * @throws SQLException
     */
    public void eliminar_vehiculo_has_conductor(String conductor, String placa)throws SQLException{
        borrar = "delete from vehiculo_has_conductor where veh_placa = ? and per_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, placa);
            pstate.setString(2, conductor);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }
    
    /**
     * Realiza una consulta de todos los registros
     * de la tabla vehiculo_has_conductor en la base
     * de datos, retornando un String[][] con los
     * diferentes atributos de los registros.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_conductor_has_vehiculo()throws SQLException{

        datos = new String[1][6];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_vehiculo_has_conductor";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo_has_conductor");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            datos = new String[cantidad+1][6];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "TIPO";
            datos[0][2] = "DOCUMENTO";
            datos[0][3] = "NOMBRE";
            datos[0][4] = "CATEGORIA";
            datos[0][5] = "VENCIMIENTO";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                

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
     * Se encarga de hacer una consulta con un parametro
     * {@code buscar}, el cual va a servir de filtro
     * para encontrar ciertos registros de la base de datos.
     * 
     * @param buscar Puede ser la placa del vehiculo,
     * el id de la persona o conductor o simplemente
     * una fraccion del nombre de la persona.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_conductor_has_vehiculo(String buscar)throws SQLException{

        datos = new String[1][6];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_vehiculo_has_conductor where veh_placa like \'"+ buscar + "%\' or per_id like \'"+ buscar + "%\' or per_nombre like \'%"+ buscar + "%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo_has_conductor where veh_placa like \'"+ buscar + "%\' or per_id like \'"+ buscar + "%\' or per_nombre like \'%"+ buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();
            datos = new String[cantidad+1][6];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "TIPO";
            datos[0][2] = "DOCUMENTO";
            datos[0][3] = "NOMBRE";
            datos[0][4] = "CATEGORIA";
            datos[0][5] = "VENCIMIENTO";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                

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
