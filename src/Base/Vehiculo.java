package Base;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehiculo extends Base{

    /**
     * Metodo constructor.
     * 
     * @param url Es la url del archivo
     * para establecer la conexion con la
     * base de datos.
     * @throws SQLException 
     * @throws IOException 
     */
    public Vehiculo() throws IOException, SQLException{
        super();
    }

    /**
     * Sirve para determinar si un vehiculo
     * es de servicio particular o de serivicio
     * publico.
     * 
     * @param placa Es la placa del vehiculo a
     * consultar.
     * 
     * @return boolean.
     * 
     * @throws SQLException
     */
    public boolean is_particular(String placa)throws SQLException{
        boolean flag = false;
        consultar = "select ser_id from vehiculo where veh_placa like ?";

        try{
            pstate = coneccion.prepareStatement(consultar);
            
            pstate.setString(1, placa);

            resultado = pstate.executeQuery();

            if(resultado.next()){
                flag = (resultado.getInt(1) == 1);
            }else{
                throw new SQLException("No se encontro el resultado en la base de datos");
            }
        }catch(SQLException ex){
            throw ex;

        }finally{
            pstate.close();
            resultado.close();
        }

        return flag;
    }

    /**
     * Inserta un registro de vehiculo en la base de datos.
     * 
     * @param veh_placa Es la placa y identificador
     * del vehiculo.
     * 
     * @param veh_clase Es el id de la clase del
     * vehiculo a insertar.
     * 
     * @param veh_modelo Es un dato de tipo {@code int}
     * que define el modelo del vehiculo.
     * 
     * @param veh_marca Es la marca del vehiculo.
     * 
     * @param veh_linea Es la linea del vehiculo.
     * 
     * @param veh_cilindrada Es un dato de tipo {@code int}
     * que define la cilindrada del vehiculo.
     * 
     * @param veh_color Es el color del vehiculo.
     * 
     * @param servicio Es el id del tipo de servicio
     * que va a prestar el vehiculo.
     * 
     * @param veh_combustible Es el tipo de combustible
     * que va a utilizar el vehiculo.
     * 
     * @param veh_tipo_carroceria Es el tipo de carroceria que
     * va a utilizar el vehiculo.
     * 
     * @param veh_numero_motor Es un dato de tipo {@code String}
     * que define el numero o identificacion
     * dle motor del vehiculo.
     * 
     * @param veh_numero_chasis Es un dato de tipo {@code String}
     * que define el numero o identificacion
     * dle chasis del vehiculo.
     * @param veh_cantidad Es un dato de tipo {@code int}
     * que define la cantidad de pasajeros que
     * pueden haber en el vehiculo.
     * 
     * @param veh_propietario Es el id del propietario o
     * dueño del vehiculo, para posibles consultas en
     * bases de datos relacionadas al trancito.
     * 
     * @param veh_parque_automotor Es dato de tipo {@code boolean}
     * que define si el vehiculo pertenece al parque automotor
     * de la empresa o es un vehiculo con convenio.
     * 
     * @throws SQLException
     */
    public void insertar_vehiculo(String veh_placa, int veh_clase, int veh_modelo, String veh_marca,String veh_linea,int veh_cilindrada, String veh_color, int servicio, String veh_combustible,String veh_tipo_carroceria, String veh_numero_motor, String veh_numero_chasis,int veh_cantidad, String veh_propietario, boolean veh_parque_automotor)throws SQLException{

        insertar = "insert into vehiculo values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, veh_placa);
            pstate.setInt(2, veh_clase);
            pstate.setInt(3, veh_modelo);
            pstate.setString(4, veh_marca);
            pstate.setString(5,veh_linea);
            pstate.setInt(6, veh_cilindrada);
            pstate.setString(7, veh_color);
            pstate.setInt(8, servicio);
            pstate.setString(9, veh_combustible);
            pstate.setString(10, veh_tipo_carroceria);
            pstate.setString(11, veh_numero_motor);
            pstate.setString(12,veh_numero_chasis);
            pstate.setInt(13, veh_cantidad);
            pstate.setString(14, veh_propietario);
            pstate.setBoolean(15, veh_parque_automotor);

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
    public void eliminar_vehiculo(String id)throws SQLException{
        borrar = "delete from vehiculo where veh_placa = ?";

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

    /**
     * Se encarga de actualizar un registro de la tabla
     * vehiculo en la base de datos utilizando como
     * identificador del vehiculo el parametro {@code placa}
     * 
     * @param placa Es el id del vehiculo para poder
     * hacer la actualizacin en la base de datos.
     * 
     * @param cilindrada Es la cilindrada del vehiculo
     * 
     * @param color Es el color del vehiculo
     * 
     * @param numero_motor Es el numero de motor 
     * del vehiculo
     * 
     * @param numero_chasis Es el numeor de chasis
     * del vehiculo
     * 
     * @param pasajeros Es la cantidad de pasajeros
     * del vehiculo
     * 
     * @param propietario Es el id del propietario
     * o dueño del vehiculo.
     * 
     * @param parque_automotor Identifica si el vehiculo
     * pertenece a la base de datos.
     * 
     * @throws SQLException
     */
    public void actualizar_vehiculo(String placa, int cilindrada, String color, String numero_motor, String numero_chasis, int pasajeros, String propietario, boolean parque_automotor) throws SQLException{
        actualizar = "update vehiculo set veh_cilindrada = ?, veh_color = ?, veh_numero_motor = ?, veh_numero_chasis = ?, veh_cantidad = ?, veh_propietario = ?, veh_parque_automotor = ? where veh_placa like ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setInt(1, cilindrada);
            pstate.setString(2, color);
            pstate.setString(3, numero_motor);
            pstate.setString(4, numero_chasis);
            pstate.setInt(5, pasajeros);
            pstate.setString(6,propietario);
            pstate.setBoolean(7, parque_automotor);
            pstate.setString(8,placa);

            pstate.executeUpdate();
        }catch(SQLException ex){
            //SQLException e = new SQLException("No es posible realizar la actualizacion a vehiculos.\n Error 3");
            throw ex;
        }
    }
    
    /**
     * Consulta los registros de la tabla
     * vehiculo de la base de datos determinados
     * por el parametro {@code boolean todos}
     * y los retorna en un {@code String[][]}.
     * 
     * @param todos Determina si en la consulta
     * se van a retornar todos los vehiculos
     * o simplemente se van a retornar los
     * vehiculos del parque automotor.
     * @return
     * @throws SQLException
     */
    public String[][] consultar_vehiculo(boolean todos)throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;
        if(todos){
            consultar = "select * from vw_vehiculo";
        }
        else{
            consultar = "select * from vw_vehiculo where veh_parque_automotor = 1";
        }

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vehiculo");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][16];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "TIPO VEHICULO";
            datos[0][2] = "MODELO";
            datos[0][3] = "MARCA";
            datos[0][4] = "LINEA";
            datos[0][5] = "CILINDRADA";
            datos[0][6] = "COLOR";
            datos[0][7] = "SERVICIO";
            datos[0][8] = "COMBUSTIBLE";
            datos[0][9] = "TIPO CARROCERIA";
            datos[0][10] = "NUMERO MOTOR";
            datos[0][11] = "NUMERO CHASIS";
            datos[0][12] = "CANTIDAD";
            datos[0][13] = "ID PROPIETARIO";
            datos[0][14] = "NOMBRE PROPIETARIO";
            datos[0][15] = "PERTENECE JAVARTURS";



            while(resultado.next()){

                datos[i][0] = resultado.getString("veh_placa");
                datos[i][1] = resultado.getString("cla_nombre");
                datos[i][2] = ""+resultado.getInt("veh_modelo");
                datos[i][3] = resultado.getString("veh_marca");
                datos[i][4] = resultado.getString("veh_linea");
                datos[i][5] = resultado.getString("veh_cilindrada");
                datos[i][6] = resultado.getString("veh_color");
                datos[i][7] = resultado.getString("ser_nombre");
                datos[i][8] = resultado.getString("veh_combustible");
                datos[i][9] = resultado.getString("veh_tipo_carroceria");
                datos[i][10] = resultado.getString("veh_numero_motor");
                datos[i][11] = resultado.getString("veh_numero_chasis");
                datos[i][12] = resultado.getString("veh_cantidad");
                datos[i][13] = resultado.getString("tip_nombre");
                datos[i][14] = resultado.getString("per_id");
                datos[i][15] = resultado.getString("per_nombre");

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
     * Hace una consulta de la capacidad de
     * pasajeros que tiene un vehiculo.
     * 
     * @param buscar Es el id o placa del
     * vehiculo a consultar.
     * 
     * @return
     * @throws SQLException
     */
    public int consultar_capacidad_vehiculo(String buscar)throws SQLException{
        int auxiliar = 0;
        consultar = "select veh_cantidad from vehiculo where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, buscar);
    
            resultado = pstate.executeQuery();
    
            if(resultado.next()){
               auxiliar = resultado.getInt(1); 
            }else{
                
                throw new SQLException("No hay resultados para tu busqueda");
            }

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
        }
        

        return auxiliar;
    }

    /**
     * Retorna en una matrix los diferentes registros
     * de la tabla vehiculo que cumplan con la busqueda
     * a realizar.
     * 
     * @param buscar Es un parametro para buscar
     * en la base de datos el vehiculo
     * en este caso puede ser la placa, el id
     * del propietario u el nombre del propietario.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_vehiculo(String buscar)throws SQLException{

        datos = new String[1][16];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_vehiculo where veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo where veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][16];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "TIPO VEHICULO";
            datos[0][2] = "MODELO";
            datos[0][3] = "MARCA";
            datos[0][4] = "LINEA";
            datos[0][5] = "CILINDRADA";
            datos[0][6] = "COLOR";
            datos[0][7] = "SERVICIO";
            datos[0][8] = "COMBUSTIBLE";
            datos[0][9] = "TIPO CARROCERIA";
            datos[0][10] = "NUMERO MOTOR";
            datos[0][11] = "NUMERO CHASIS";
            datos[0][12] = "CANTIDAD";
            datos[0][13] = "ID PROPIETARIO";
            datos[0][14] = "NOMBRE PROPIETARIO";
            datos[0][15] = "PERTENECE JAVARTURS";



            while(resultado.next()){

                datos[i][0] = resultado.getString("veh_placa");
                datos[i][1] = resultado.getString("cla_nombre");
                datos[i][2] = ""+resultado.getInt("veh_modelo");
                datos[i][3] = resultado.getString("veh_marca");
                datos[i][4] = resultado.getString("veh_linea");
                datos[i][5] = resultado.getString("veh_cilindrada");
                datos[i][6] = resultado.getString("veh_color");
                datos[i][7] = resultado.getString("ser_nombre");
                datos[i][8] = resultado.getString("veh_combustible");
                datos[i][9] = resultado.getString("veh_tipo_carroceria");
                datos[i][10] = resultado.getString("veh_numero_motor");
                datos[i][11] = resultado.getString("veh_numero_chasis");
                datos[i][12] = resultado.getString("veh_cantidad");
                datos[i][13] = resultado.getString("tip_nombre");
                datos[i][14] = resultado.getString("per_id");
                datos[i][15] = resultado.getString("per_nombre");

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
    
    public String[][] consultar_vehiculo_externo(String buscar)throws SQLException{

        datos = new String[1][16];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_vehiculo where (veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\') and veh_placa in (select veh_placa from vehiculo where veh_parque_automotor = 0 and veh_placa not in (select veh_placa from vehiculo_externo))";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo where (veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\') and veh_placa in (select veh_placa from vehiculo where veh_parque_automotor = 0 and veh_placa not in (select veh_placa from vehiculo_externo))");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][16];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "TIPO VEHICULO";
            datos[0][2] = "MODELO";
            datos[0][3] = "MARCA";
            datos[0][4] = "LINEA";
            datos[0][5] = "CILINDRADA";
            datos[0][6] = "COLOR";
            datos[0][7] = "SERVICIO";
            datos[0][8] = "COMBUSTIBLE";
            datos[0][9] = "TIPO CARROCERIA";
            datos[0][10] = "NUMERO MOTOR";
            datos[0][11] = "NUMERO CHASIS";
            datos[0][12] = "CANTIDAD";
            datos[0][13] = "ID PROPIETARIO";
            datos[0][14] = "NOMBRE PROPIETARIO";
            datos[0][15] = "PERTENECE JAVARTURS";



            while(resultado.next()){

                datos[i][0] = resultado.getString("veh_placa");
                datos[i][1] = resultado.getString("cla_nombre");
                datos[i][2] = ""+resultado.getInt("veh_modelo");
                datos[i][3] = resultado.getString("veh_marca");
                datos[i][4] = resultado.getString("veh_linea");
                datos[i][5] = resultado.getString("veh_cilindrada");
                datos[i][6] = resultado.getString("veh_color");
                datos[i][7] = resultado.getString("ser_nombre");
                datos[i][8] = resultado.getString("veh_combustible");
                datos[i][9] = resultado.getString("veh_tipo_carroceria");
                datos[i][10] = resultado.getString("veh_numero_motor");
                datos[i][11] = resultado.getString("veh_numero_chasis");
                datos[i][12] = resultado.getString("veh_cantidad");
                datos[i][13] = resultado.getString("tip_nombre");
                datos[i][14] = resultado.getString("per_id");
                datos[i][15] = resultado.getString("per_nombre");

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
     * Consulta un unico registro de la tabla
     * vehiculo determinado por el parametro de
     * busqueda {@code id}.
     * 
     * @param id Es la placa del vehiculo
     * a consultar.
     * 
     * @return
     * @throws SQLException
     */
    public String[] consultar_uno_vehiculo(String id)throws SQLException{

        dato = new String[17];
        for(int i = 0; i < dato.length; i++){
            dato[i] = null;
        }

        consultar = "select * from vw_vehiculo where veh_placa like ?";
        String consutlar2 = "select veh_parque_automotor from vehiculo where veh_placa like ?";
        ResultSet resultado2 = null;

        try{
            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, id);
            resultado = pstate.executeQuery();

            

            pstate = coneccion.prepareStatement(consutlar2);
            pstate.setString(1, id);
            resultado2 = pstate.executeQuery();

            if (resultado.next() && resultado2.next()){

                dato[0] = resultado.getString("veh_placa");
                dato[1] = resultado.getString("cla_nombre");
                dato[2] = ""+resultado.getInt("veh_modelo");
                dato[3] = resultado.getString("veh_marca");
                dato[4] = resultado.getString("veh_linea");
                dato[5] = resultado.getString("veh_cilindrada");
                dato[6] = resultado.getString("veh_color");
                dato[7] = resultado.getString("ser_nombre");
                dato[8] = resultado.getString("veh_combustible");
                dato[9] = resultado.getString("veh_tipo_carroceria");
                dato[10] = resultado.getString("veh_numero_motor");
                dato[11] = resultado.getString("veh_numero_chasis");
                dato[12] = resultado.getString("veh_cantidad");
                dato[13] = resultado.getString("tip_nombre");
                dato[14] = resultado.getString("per_id");
                dato[15] = resultado.getString("per_nombre");
                dato[16] = ""+resultado2.getBoolean(1);
                
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado2.close();
            resultado.close();
            pstate.close();
        }
        return dato;

    }

    
    /**
     * Se encarga de retornar un arreglo
     * con los diferentes tipos de servicio
     * que puede tener un vehiculo
     * @return un String[] como
     * lista de los diferentes tipos de
     * servicio.
     * @throws SQLException
     */
    public String[] consultar_servicio()throws SQLException{
        dato = new String[2];
        dato[0] = null;
        dato[1] = null;

        consultar = "select * from servicio";

        try{
            
            state = coneccion.createStatement();
            resultado = state.executeQuery("select count(ser_id) as total from servicio");
            if(resultado.next()){
                dato = new String[resultado.getInt("total")];
                resultado.close();
            }
            
            resultado = state.executeQuery(consultar);

            for(int i = 0; resultado.next(); i++){
                dato[i] = resultado.getString(2);
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            state.close();
        }
        return dato;
    }
}
