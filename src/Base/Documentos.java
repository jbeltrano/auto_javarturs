package Base;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class Documentos extends Base{

    /**
     * constructor de la clase.
     * 
     * @param url Url o ubicacion en el
     * sistema de archivos de la base de datos
     * para establecer la conexion.
     * @throws SQLException 
     * @throws IOException 
     */
    public Documentos() throws IOException, SQLException{
        super();
    }

    /**
     * Se encarga de insertar los documentos asociados a un vehiculo
     * 
     * @param placa Es la placa a la cual se le 
     * van a insertar los documentos.
     * 
     * @param fehca_soat Es la fecha de vencimiento
     * del soat.
     * 
     * @param fecha_rtm Es la fecha de vencimiento
     * de la tecnomecanica.
     * 
     * @param top Es el numero de tarjeta de operacion.
     * 
     * @param fecha_top Es la fecha de vencimiento
     * de la tarjeta de operacion.
     * 
     * @param fecha_polizas Es la fecha de vencimiento de
     * las poclisas contractual y extracontractual.
     * 
     * @param interno Es el numero interno del vehiculo.
     * 
     * @throws SQLException
     */
    public void insertar_documentos(String placa, String fehca_soat, String fecha_rtm, int top, String fecha_top, String fecha_polizas, int interno )throws SQLException{
        int var_auxiliar = 0;
        insertar = "insert into documento values (?,?,?,?,?,?,?,?)";
        consultar = "select count(veh_placa) from documento where veh_placa like \'" + placa + "\'";


        try{
            state = coneccion.createStatement();
            resultado = state.executeQuery(consultar);

            if(resultado.next()){
                var_auxiliar = resultado.getInt(1);
            }
            
            resultado.close();
            if(var_auxiliar == 1){
                SQLException ex = new SQLException("El vehiculo "+ placa + ", ya tiene documentos relacionados\nError 4.");
                throw ex;
            }

            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, placa);
            pstate.setInt(2,interno);
            pstate.setString(3, fehca_soat);
            pstate.setString(4, fecha_rtm);
            pstate.setString(5, fecha_polizas);
            pstate.setString(6,fecha_polizas);
            pstate.setInt(7,top);
            pstate.setString(8, fecha_top);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
            state.close();
        }
    }

    /**
     * Se encarga de insertar los documentos de un vehiculo
     * pero unicamente es valido para los vehiculos de
     * servicio particular.
     * 
     * @param placa Es la palca a la cual se le van
     * a insertar los documentos.
     * 
     * @param fehca_soat Es la fecha de vencimiento del soat
     * 
     * @param fecha_rtm Es la fecha de vencimeitno
     * de la tecnomecanica.
     * 
     * @throws SQLException
     */
    public void insertar_documentos(String placa, String fehca_soat, String fecha_rtm)throws SQLException{
        int var_auxiliar = 0;
        insertar = "insert into documento (veh_placa, doc_fecha_soat, doc_fecha_rtm) values (?,?,?)";
        consultar = "select count(veh_placa) from documento where veh_placa like \'" + placa + "\'";


        try{
            state = coneccion.createStatement();
            resultado = state.executeQuery(consultar);

            if(resultado.next()){
                var_auxiliar = resultado.getInt(1);
            }
            
            resultado.close();

            if(var_auxiliar == 1){
                SQLException ex = new SQLException("El vehiculo "+ placa + ", ya tiene documentos relacionados\nError 4.");
                throw ex;
            }

            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, placa);
            pstate.setString(2, fehca_soat);
            pstate.setString(3, fecha_rtm);
            

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            state.close();
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro de
     * documentos en la base de datos asociado
     * a una placa.
     * 
     * @param placa Es la placa a la cual se
     * le van a eliminar los documentos. 
     * 
     * @throws SQLException
     */
    public void eliminar_documento(String placa)throws SQLException{

        borrar = "delete from documento where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, placa);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }

    }
    
    /**
     * Se encarga de actualizar los documentos
     * de un vehiculo en general el metodo es para
     * los vehiculos de tipo de servicio particular
     * puesto que los demas documentos no son necesarios.
     * 
     * @param placa Es la placa a la cual se le
     * van a actualizar los documentos.
     * 
     * @param fehca_soat Es la fecha de vencimiento
     * del soat.
     * 
     * @param fecha_rtm Es la fecha de vencimiento
     * de la tecnomecanica.
     * 
     * @throws SQLException
     */
    public void actualizar_documento(String placa, String fehca_soat, String fecha_rtm)throws SQLException{

        actualizar = "update documento set doc_fecha_soat = ?, doc_fecha_rtm = ? where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, fehca_soat);
            pstate.setString(2, fecha_rtm);
            pstate.setString(3, placa);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Este metodo se encarga de actualiar todas las fechas
     * de las polizas de los vehiculos del parque automotor.
     * @param fecha_rcc_rce
     * @throws SQLException
     */
    public void actualizar_polisas_parque(String fecha_rcc_rce)throws SQLException{

        actualizar = "update documento set doc_fecha_rcc = ?, doc_fecha_rce = ? where veh_placa in (select veh_placa from vehiculo where veh_parque_automotor = 1)";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, fecha_rcc_rce);
            pstate.setString(2, fecha_rcc_rce);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de actualizar los documentos de un vehiculo
     * de servicio publico.
     * 
     * @param placa Es la placa a la cual se le 
     * van a actualizar los documentos.
     * 
     * @param fehca_soat Es la fecha de vencimiento
     * del soat.
     * 
     * @param fecha_rtm Es la fecha de vencimiento
     * de la tecnomecanica.
     * 
     * @param top Es el numero de tarjeta de operacion.
     * 
     * @param fecha_top Es la fecha de vencimiento
     * de la tarjeta de operacion.
     * 
     * @param fecha_polizas Es la fecha de vencimiento de
     * las poclisas contractual y extracontractual.
     * 
     * @param interno Es el numero interno del vehiculo.
     * 
     * @throws SQLException
     */
    public void actualizar_documento(String placa, String fehca_soat, String fecha_rtm, int top, String fecha_top, String fecha_polizas, int interno )throws SQLException{

        actualizar = "update documento set doc_interno = ?, doc_fecha_soat = ?, doc_fecha_rtm = ?, doc_fecha_rcc = ?, doc_fecha_rce = ?, doc_top = ?, doc_fecha_top = ? where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setInt(1, interno);
            pstate.setString(2, fehca_soat);
            pstate.setString(3, fecha_rtm);
            pstate.setString(4, fecha_polizas);
            pstate.setString(5, fecha_polizas);
            pstate.setInt(6, top);
            pstate.setString(7, fecha_top);
            pstate.setString(8, placa);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }
    
    /**
     * Realiza una consulta utilizando el parametro
     * {@code buscar} para realizar un filtro entre los
     * diferentes registros de la base de datos, para asi
     * retornar los diferentes registros con sus atributos.
     * 
     * @param buscar Es el parametro de busqueda, este puede
     * ser un aparte de la placa de un vehiculo.
     * 
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_documentos(String buscar)throws SQLException{

        datos = new String[1][10];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_documento where veh_placa like \'" + buscar +"%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_documento where veh_placa like \'" + buscar +"%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            datos = new String[cantidad+1][10];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "NUMERO INTERNO";
            datos[0][2] = "FECHA SOAT";
            datos[0][3] = "FECHA RTM";
            datos[0][4] = "FECHA RCC";
            datos[0][5] = "FECHA RCE";
            datos[0][6] = "TOP";
            datos[0][7] = "FECHA TOP";
            datos[0][8] = "TIPO ID";
            datos[0][9] = "ID PROPIETARIO";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);               // Placa del vehiculo
                datos[i][1] = ( resultado.getString(2) == null)?    // Numero interno del vehiculo
                                "NULL":resultado.getString(2);
                datos[i][2] = resultado.getString(3);               // Fecha final Soat del vehiculo
                datos[i][3] = resultado.getString(4);               // Fecha final Rtm del vehiculo
                datos[i][4] = ( resultado.getString(5) == null)?    // Fecha final Rcc del vehiculo
                                "NULL":resultado.getString(5);
                datos[i][5] = ( resultado.getString(6) == null)?    // Fecha final Rce del vehiculo
                                "NULL":resultado.getString(6);
                datos[i][6] = ( resultado.getString(7) == null)?    // Numero de top del vehiculo
                                "NULL":resultado.getString(7);
                datos[i][7] = ( resultado.getString(8) == null)?    // Fecha final top del vehiculo
                                "NULL":resultado.getString(8);
                datos[i][8] = resultado.getString(9);               // Tipo de id del propietario del vehiculo
                datos[i][9] = resultado.getString(10);              // Id del propietario del vehiculo
                

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
     * Se encarga de consultar unicamente un unico registro
     * de los documentos de un vehiculo retornado todos los
     * atributos de dicho registro en un arrgeglo {@code String[]}
     * 
     * @param id Es basicamente el id o placa
     * del vehiculo a consultar.
     * 
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_uno_documentos(String id)throws SQLException{

        dato = new String[8];
        for(int i = 0; i < dato.length; i++){
            dato[i] = null;
        }

        consultar = "select * from documento where veh_placa like ?";

        try{
            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, id);
            resultado = pstate.executeQuery();


            if (resultado.next()){

                dato[0] = resultado.getString(1);               // Placa del vehiculo
                dato[1] = ( resultado.getString(2) == null)?    // Numero interno del vehiculo
                                "NULL":resultado.getString(2);
                dato[2] = resultado.getString(3);               // Fecha final Soat del vehiculo
                dato[3] = resultado.getString(4);               // Fecha final Rtm del vehiculo
                dato[4] = ( resultado.getString(5) == null)?    // Fecha final Rcc del vehiculo
                                "NULL":resultado.getString(5);
                dato[5] = ( resultado.getString(6) == null)?    // Fecha final Rce del vehiculo
                                "NULL":resultado.getString(6);
                dato[6] = ( resultado.getString(7) == null)?    // Numero de top del vehiculo
                                "NULL":resultado.getString(7);
                dato[7] = ( resultado.getString(8) == null)?    // Fecha final top del vehiculo
                                "NULL":resultado.getString(8);
                                
                
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
        }
        return dato;

    }

    /**
     * Se encarga de consultar todos los vehiculos que no
     * tienen documentos siempre y cuanto dependan del parametro
     * {@code todos}.
     * 
     * @param todos Es un parametro de tipo {@code boolean}
     * el cual define si se consultan los del parque automotor
     * o todos los vehiculos registrados.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_vehiculo_sin_documento(boolean todos)throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;
        if(todos){
            consultar = "select * from vw_vehiculo_sin_documento";
        }
        else{
            consultar = "select * from vw_vehiculo_sin_documento where veh_parque_automotor = 1";
        }

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo_sin_documento");
            
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
     * Se encarga de consultar los vehiculos que no tienen documentos
     * haciendo un filtro con el parametro {@code buscar} para
     * encontrar un vehiculo en especifico.
     * 
     * @param buscar Es un parametro de busqueda
     * el cual puede ser la palca del vehiculo,
     * el id del propietario o incluso una parte del
     * nombre del propietario.
     * 
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_vehiculo_sin_documento(String buscar)throws SQLException{

        datos = new String[1][16];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_vehiculo_sin_documento where veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_vehiculo_sin_documento where veh_placa like\'%"+buscar+"%\' or per_id like \'%"+buscar+"%\' or per_nombre like \'%"+buscar+"%\'");
            
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

    @SuppressWarnings("deprecation")
    public void insertar_documento_link(String placa, String str_url)throws SQLException{

        try {
            new URL(str_url); // Intenta crear una instancia de URL
        } catch (Exception e) {
            throw new SQLException("La URL ingresada no es una URL valida");
        }

        insertar = "insert into documentos_vehiculo values (?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, placa);
            pstate.setString(2,str_url);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
            state.close();
        }
    }

    @SuppressWarnings("deprecation")
    public void actualizar_documento_link(String placa, String str_url)throws SQLException{

        try {
            new URL(str_url); // Intenta crear una instancia de URL
        } catch (Exception e) {
            throw new SQLException("La URL ingresada no es una URL valida");
        }

        actualizar = "update documentos_vehiculo set doc_link = ? where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, str_url);
            pstate.setString(2, placa);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
            state.close();
        }
    }

    public void eliminar_documento_link(String placa)throws SQLException{

        borrar = "delete from documentos_vehiculo where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, placa);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }

    }

    public String[][] consultar_documento_link(String placa)throws SQLException{

        datos = new String[1][2];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from documentos_vehiculo where veh_placa like\'%"+placa+"%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from documentos_vehiculo where veh_placa like\'%"+placa+"%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            datos[0][0] = "PLACA";
            datos[0][1] = "URL";

            datos = new String[cantidad+1][16];

            if(cantidad == 0){
                return datos;
            }            

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                datos[i][0] = resultado.getString("veh_placa");
                datos[i][1] = resultado.getString("doc_link");

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
