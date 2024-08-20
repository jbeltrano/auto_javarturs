package Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

public class Base extends Base_datos{

    
    protected String[] dato;
    protected String[][] datos;


    /* Construtor basico ingresando url base de datos con la plantilla estandar */
    public Base(String url){
        super(url);
    }

    protected void set_nombres_cabecera(){
        
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

    // Metodos para CONDUCTOR_HAS_VEHICULO
    public void insertar_vehiuclo_has_conductor(String conductor, String placa)throws SQLException{
        
        insertar = "insert into vehiculo_has_conductor values (?,?)";

        try{
            state = coneccion.createStatement();
            resultado = state.executeQuery("select count(veh_placa) from vehiculo_has_conductor where veh_placa like \'" + placa +"\'");

            if(resultado.next()){
                if(resultado.getInt(1) < 3){
                    pstate = coneccion.prepareStatement(insertar);

                    pstate.setString(1, placa);
                    pstate.setString(2, conductor);
        
                    pstate.executeUpdate();
                }else{
                    SQLException ex = new SQLException("Imposible realizar la insercion porque\nEl vehiculo " + placa + " Ya tiene 3 conductores");
                    throw ex;
                }
            }
            

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_vehiculo_has_conductor(String conductor, String placa)throws SQLException{
        borrar = "delete from vehiculo_has_conductor where veh_placa = ? and per_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, placa);
            pstate.setString(2, conductor);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }
    
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
        }

        return datos;

    }

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
        }

        return datos;

    }

    
    // Metodos para DOCUMENTOS

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
        }
    }

    /**
     * Se encarga de insertar los documentos de un vehiculo
     * pero unicamente es valido para los vehiculos de
     * servicio particular.
     * @param placa
     * @param fehca_soat
     * @param fecha_rtm
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

    public void eliminar_documento(String placa)throws SQLException{

        borrar = "delete from documento where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, placa);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }

    }
    
    /**
     * Se encarga de actualizar los documentos
     * de un vehiculo en general el metodo es para
     * los vehiculos de tipo de servicio particular
     * puesto que los demas documentos no son necesarios
     * @param placa
     * @param fehca_soat
     * @param fecha_rtm
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
        }
    }
    
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
        }

        return datos;

    }

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
        }
        return dato;

    }

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
        }

        return flag;
    }
    // Metodos para vehiculos sin doucumentos
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
        }

        return datos;

    }

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
        }

        return datos;

    }

    // Metodos para licencia
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
        }
    }

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
        }
    }

    public void actualizar_licencia(String per_id, int categoria_antigua, int categoria_nueva, String fecha)throws SQLException{

        actualizar = "update licencia set lic_fecha = ?, cat_id = ? where per_id = ? and cat_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setDate(1, Date.valueOf(fecha));
            pstate.setInt(2, categoria_nueva);
            pstate.setString(3,per_id);
            pstate.setInt(4, categoria_antigua);

            pstate.executeUpdate();
        }catch(SQLException ex){
            SQLException e = new SQLException("No es posible realizar la actualizacion.\nError 3");
            throw e;
        }

    }

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
        }
    }

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
        }

        return datos;
    }
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
        }

        return datos;
    }

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
        }

        return dato;
    }
    
    // Metodos para consultar la categoria de la licencia de conduccion
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
        }
        return dato;

    }

    // Metodos para relacionados con extractos mensuales

    public int consultar_consecutivo_mensual(String buscar)throws SQLException{

        int consecutivo = 0;

        consultar = "select * from consecutivo_extracto_mensual where con_placa = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, buscar);

        resultado = pstate.executeQuery();
        if(resultado.next()){
            consecutivo = resultado.getInt(2); 
            return consecutivo;
        }else{
            return 0;
        }

    }

    public String[][] consultar_vw_extracto_mensual(String buscar) throws SQLException{
        datos = new String[1][12];
        int cantidad = 0;
        int i = 1;
        
        consultar = "select * from vw_extracto_mensual where veh_placa like \'%" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'";
            
        state = coneccion.createStatement();
        resultado = state.executeQuery("select count(*) as total from vw_extracto_mensual where veh_placa like \'%" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'");
            

        if(resultado.next()){
            cantidad = resultado.getInt(1);
        }

        datos = new String[cantidad+1][12];
        
        datos[0][0] = "PLACA";
        datos[0][1] = "CONSECUTIVO";
        datos[0][2] = "N. CONTRATO";
        datos[0][3] = "CONTRATANTE";
        datos[0][4] = "TIPO ID CONT";
        datos[0][5] = "NOMBRE CONTRATANTE";
        datos[0][6] = "FECHA INICIAL";
        datos[0][7] = "FECHA FINAL";
        datos[0][8] = "C. ORIGEN";
        datos[0][9] = "D. ORIGEN";
        datos[0][10] = "C. DESTINO";
        datos[0][11] = "D. DESTINO";

        if(cantidad == 0){
            return datos;
        }

        resultado = state.executeQuery(consultar);        

        while(resultado.next()){

            datos[i][0] = resultado.getString(1);
            datos[i][1] = resultado.getString(2);
            datos[i][2] = resultado.getString(3);
            datos[i][3] = resultado.getString(4);
            datos[i][4] = resultado.getString(5);
            datos[i][5] = resultado.getString(6);
            datos[i][6] = resultado.getString(7);
            datos[i][7] = resultado.getString(8);
            datos[i][8] = resultado.getString(9);
            datos[i][9] = resultado.getString(10);
            datos[i][10] = resultado.getString(11);
            datos[i][11] = resultado.getString(12);
            i++;
        }

        return datos;

    }

    public int consultar_uno_consecutivo_extracto_ocasional(String placa, int contrato)throws SQLException{
        try{
            consultar = "select * from extracto_ocasional where veh_placa = ? and con_id = ?";

            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, placa);
            pstate.setInt(2, contrato);
    
            resultado = pstate.executeQuery();
    
            if(resultado.next()){
                return resultado.getInt(2);
            }else{
                throw new SQLException("No se encontraron resultados");
            }
        }finally{
            resultado.close();
            pstate.close();
        }
        
    }
    public String[] consultar_uno_extracto_mensual(String placa, int consecutivo)throws SQLException{
        dato = new String[7];

        consultar = "select * from extracto_mensual where veh_placa = ? and ext_consecutivo = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, placa);
        pstate.setInt(2, consecutivo);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            
            dato[0] = resultado.getString(1);
            dato[1] = resultado.getString(2);
            dato[2] = resultado.getString(3);
            dato[3] = resultado.getString(4);
            dato[4] = resultado.getString(5);
            dato[5] = resultado.getString(6);
            dato[6] = resultado.getString(7);
        }
        return dato;
    }

    public void actualizar_extracto_mensual(String placa, int consecutivo, int contrato, String fecha_inicial, String fecha_final, int origen, int destino)throws SQLException{

        actualizar = "update extracto_mensual set con_id = ?, ext_fecha_inicial = ?, ext_fecha_final = ?, ext_origen = ?, ext_destino = ? where veh_placa = ? and ext_consecutivo = ?";

        pstate = coneccion.prepareStatement(actualizar);

        pstate.setInt(1, contrato);
        pstate.setString(2, fecha_inicial);
        pstate.setString(3, fecha_final);
        pstate.setInt(4,origen);
        pstate.setInt(5, destino);
        pstate.setString(6, placa);
        pstate.setInt(7, consecutivo);
        

        pstate.executeUpdate();
        pstate.close();

    }

    public void actualizar_todos_extractos_mensuales(String fecha_inicial, String fecha_final)throws SQLException{
        String consultar_consecutivo;
        String actualizar_consecutivo;
        String placa = "";
        int consecutivo_antes = 0;
        int consecutivo_despues = 0;
        PreparedStatement pstate2;
        PreparedStatement pstate3;
        ResultSet resultado2;

        consultar = "select * from extracto_mensual";
        consultar_consecutivo = "select con_numero from consecutivo_extracto_mensual where con_placa = ?";
        actualizar_consecutivo = "update consecutivo_extracto_mensual set con_numero = ?+1 where con_placa = ?";
        actualizar = "update extracto_mensual set ext_consecutivo = ? + 1, ext_fecha_inicial = ?, ext_fecha_final = ? where veh_placa = ? and ext_consecutivo = ?";
        
        state = coneccion.createStatement();
        pstate = coneccion.prepareStatement(actualizar);
        pstate2 = coneccion.prepareStatement(consultar_consecutivo);
        pstate3 = coneccion.prepareStatement(actualizar_consecutivo);

        resultado = state.executeQuery(consultar);

        try{
            
            while (resultado.next()) {
                // se consulta la placa del extracto a evaluar y el consecutivo a actualizar
                placa = resultado.getString(1);
                consecutivo_antes = resultado.getInt(2);
    
                // Se consulta el consecutivo actual del vehiculo
                pstate2.setString(1, placa);
                resultado2 = pstate2.executeQuery();
    
                // se aplica lo consultado si existe
                if(resultado2.next()){
    
                    // Se establece el valor de consecutvio despues
                    consecutivo_despues = resultado2.getInt(1);
    
                    // se remplazan los valores del update para el extracto_mensual
                    pstate.setInt(1,consecutivo_despues);
                    pstate.setString(2, fecha_inicial);
                    pstate.setString(3, fecha_final);
                    pstate.setString(4, placa);
                    pstate.setInt(5, consecutivo_antes);
    
                    // se actualiza el consecutivo de los extractos mensuales para el vehiculo
                    pstate3.setInt(1, consecutivo_despues);
                    pstate3.setString(2, placa);
    
                    try{
                        // ejectua los updates tanto para extracto, como para consecutvio
                        coneccion.setAutoCommit(false);
                        pstate.executeUpdate();
                        pstate3.executeUpdate();
    
                        coneccion.commit();
                    }catch(SQLException ex){
                        coneccion.rollback();
                        throw ex;
                    }finally{
                        coneccion.setAutoCommit(true);
                        resultado2.close();
                    }
                }
    
                
            }
    
            state.close();
            pstate2.close();
            pstate3.close();
            pstate.close();
            resultado.close();
        }catch(SQLException ex){
            state.close();
            pstate2.close();
            pstate3.close();
            pstate.close();
            resultado.close();
            throw ex;
        }
        

    }

    public void insertar_extracto_mensual(String placa, int contrato, String fecha_inicial, String fecha_final, int origen, int destino, int consecutivo)throws SQLException{

        String accion_auxiliar = "";
        insertar = "insert into extracto_mensual values (?,?,?,?,?,?,?)";
        try{

        
        state = coneccion.createStatement();
        resultado = state.executeQuery("select con_numero from consecutivo_extracto_mensual where con_placa = \'"+placa+"\'");
        if(resultado.next()){
            if(consecutivo < resultado.getInt(1)){
                consecutivo = resultado.getInt(1);
                
            }

            accion_auxiliar = "update consecutivo_extracto_mensual set con_numero = ? where con_placa = ?"; 

        }else{
            accion_auxiliar = "insert into consecutivo_extracto_mensual (con_numero, con_placa) values (?,?)";
            if(consecutivo <= 0){
                consecutivo = 1;
            }
        }

        

        coneccion.setAutoCommit(false);
        pstate = coneccion.prepareStatement(insertar);

        pstate.setString(1, placa);
        pstate.setInt(2, consecutivo);
        pstate.setInt(3, contrato);
        pstate.setString(4, fecha_inicial);
        pstate.setString(5, fecha_final);
        pstate.setInt(6, origen);
        pstate.setInt(7, destino);

        pstate.executeUpdate();

        pstate = coneccion.prepareStatement(accion_auxiliar);

        pstate.setInt(1, consecutivo);
        pstate.setString(2, placa);

        pstate.executeUpdate();
        
        coneccion.commit();
        }catch(SQLException e){
            coneccion.rollback();
            SQLException ex = new SQLException("No fue posible insertar el extracto");
            throw ex;
        }finally{
            coneccion.setAutoCommit(true);
        }
        
    }

    public void eliminar_extracto_mensual(String placa, int consecutivo)throws SQLException{

        borrar = "delete from extracto_mensual where veh_placa = ? and ext_consecutivo = ?";

        pstate = coneccion.prepareStatement(borrar);

        pstate.setString(1, placa);
        pstate.setInt(2, consecutivo);

        pstate.executeUpdate();


    }
    
    // Metodo para consultar el tipo de contrato
    public String[] consultar_tipo_contrato()throws SQLException{

        dato = new String[4];

        state = coneccion.createStatement();
        resultado = state.executeQuery("select tc_nombre from tipo_contrato");

        for (int i = 0; resultado.next(); i++) {

            dato[i] = resultado.getString(1);

        }

        state.close();
        resultado.close();
        return dato;

    }


    // Metodos relacionados con extractos ocasionales

    public int consultar_consecutivo_ocasional(String buscar)throws SQLException{

        int consecutivo = 0;

        consultar = "select * from consecutivo_extracto_ocasional where con_placa = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, buscar);

        resultado = pstate.executeQuery();
        if(resultado.next()){
            consecutivo = resultado.getInt(2); 
            return consecutivo;
        }else{
            return 0;
        }

    }
    
    /**
     * Se encarga de incertar un extracto ocasional con los
     * parametros:
     * 
     * {@code placa} el cual identifica el vehiculo.
     * {@code consecutivo} determina el consecutivo del extracto.
     * {@code contrato} determina el numero de contrato vinculado al extracto.
     * 
     * Dependiendo del numero consecutivo se determina si se actualiza o no en
     * la tabla de los numero consecutivos para los extractos ocasionales
     * 
     * @param placa
     * @param consecutivo
     * @param contrato
     * @throws SQLException
     */
    public void insertar_extracto_ocasional(String placa, int consecutivo, int contrato)throws SQLException{

        String accion_auxiliar = "";
        int consecutivo2 = 0;
        insertar = "insert into extracto_ocasional values (?,?,?)";
        
        try{

            /*Busca en la tabla de consecutivos para los extractos ocasionales si hay uno asosciado a la placa ingresada*/
            state = coneccion.createStatement();
            resultado = state.executeQuery("select con_numero from consecutivo_extracto_ocasional where con_placa = \'"+placa+"\'");

            // Si existe un registro
            if(resultado.next()){
                // Determina si el consecutivo ingresado es mayor que el que existe en la tabla
                if(consecutivo < resultado.getInt(1)){ /* Si el consecutivo ingresado es menor que el de la tabla
                                                                      guardamos el consecutivo de la tabla en consecutivo2
                                                                      de esta forma no altermos el orden el consecutivo como tal
                                                                      y seguimos manteniendo el registro, permitiendonos hacer
                                                                      registros hacia atras*/
                    consecutivo2 = resultado.getInt(1);
                    
                }

                // En este caso se define una actualizacion del registro, puesto que ya hay uno existente
                accion_auxiliar = "update consecutivo_extracto_ocasional set con_numero = ? where con_placa = ?";

            }else{  // En caso de no encontrar un regsitro en la tabla, creara un asociado a la placa del vehiculo
                accion_auxiliar = "insert into consecutivo_extracto_ocasional (con_numero, con_placa) values (?,?)";
                if(consecutivo <= 0){   // En este caso verifica que el consecutivo no sea menor o igual que cero
                    consecutivo = 1;
                }
            }

            

            // Define el auto comit como false en caso de requerir hacer un roleback
            coneccion.setAutoCommit(false);

            // Perara los datos a insertar en el extracto ocasional
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, placa);
            pstate.setInt(2, consecutivo);
            pstate.setInt(3, contrato);


            pstate.executeUpdate();
            pstate.close();

            // Prepara los datos a insertar en el consecutivo extracto mensual
            pstate = coneccion.prepareStatement(accion_auxiliar);

            pstate.setInt(1, consecutivo2);
            pstate.setString(2, placa);

            pstate.executeUpdate();

            // Si todo ah salido bien hace un commit
            coneccion.commit();
        }catch(SQLException e){ // Si algo fallo hace el roleback
            coneccion.rollback();
            throw e;
        }finally{
            coneccion.setAutoCommit(true);
            resultado.close();
            pstate.close();
        }

    }

    public String[][] consultar_vw_extracto_ocasional(String buscar) throws SQLException{
        datos = new String[1][13];
        int cantidad = 0;
        int i = 1;

        String placa = "\'" +buscar+"%\'";
        String contrato = "\'" +buscar + "%\'";
        String persona = "\'%" + buscar + "%\'";

        consultar = "select * from vw_extracto_ocasional where veh_placa like " + placa +" or con_id like "+ contrato +" or con_contratante like " + persona;
        
        // consultando la cantidad de registros para reservar en memoria
        state = coneccion.createStatement();

        resultado = state.executeQuery("select count(*) as total from vw_extracto_ocasional where veh_placa like " + placa +" or con_id like "+ contrato +" or con_contratante like " + persona);


        if(resultado.next()){
            cantidad = resultado.getInt(1);
        }
        
        resultado.close();

        datos = new String[cantidad+1][13];
        
        datos[0][0] = "PLACA";
        datos[0][1] = "CONSECUTIVO";
        datos[0][2] = "N. CONTRATO";
        datos[0][3] = "CONTRATANTE";
        datos[0][4] = "TIPO ID CONT";
        datos[0][5] = "NOMBRE CONTRATANTE";
        datos[0][6] = "FECHA INICIAL";
        datos[0][7] = "FECHA FINAL";
        datos[0][8] = "C. ORIGEN";
        datos[0][9] = "D. ORIGEN";
        datos[0][10] = "C. DESTINO";
        datos[0][11] = "D. DESTINO";
        datos[0][12] = "TIPO CONTRATO";

        if(cantidad == 0){
            return datos;
        }

        resultado = state.executeQuery(consultar);

        while(resultado.next()){

            datos[i][0] = resultado.getString(1);
            datos[i][1] = resultado.getString(2);
            datos[i][2] = resultado.getString(3);
            datos[i][3] = resultado.getString(4);
            datos[i][4] = resultado.getString(5);
            datos[i][5] = resultado.getString(6);
            datos[i][6] = resultado.getString(7);
            datos[i][7] = resultado.getString(8);
            datos[i][8] = resultado.getString(9);
            datos[i][9] = resultado.getString(10);
            datos[i][10] = resultado.getString(11);
            datos[i][11] = resultado.getString(12);
            datos[i][12] = resultado.getString(13);
            i++;
        }

        state.close();
        resultado.close();
        return datos;

    }

    public void eliminar_extracto_ocasional(String placa, int consecutivo)throws SQLException{

        borrar = "delete from extracto_ocasional where veh_placa like ? and ext_consecutivo = ?";

        pstate = coneccion.prepareStatement(borrar);

        pstate.setString(1, placa);
        pstate.setInt(2, consecutivo);

        pstate.executeUpdate();
        pstate.close();
    }
    
    public void actualizar_extracto_ocasional(String placa, int consecutivo, int contrato)throws SQLException{

        actualizar = "update extracto_ocasional set con_id = ? where veh_placa = ? and ext_consecutivo = ?";

        pstate = coneccion.prepareStatement(actualizar);

        pstate.setInt(1, contrato);
        pstate.setString(2, placa);
        pstate.setInt(3, consecutivo);

        pstate.executeUpdate();
        pstate.close();
    }
    // Metodos relacionados con contratos ocasionales

    public int consultar_tipo_contrato_ocasional(int id) throws SQLException{
        int tipo_contrato = 0;
        consultar = "select tc_id from contrato_ocasional where con_id = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setInt(1, id);

        resultado = pstate.executeQuery();
        

        if(resultado.next()){
            tipo_contrato = resultado.getInt(1);
            resultado.close();
            pstate.close();
            return tipo_contrato;
        }else{
            pstate.close();
            SQLException ex = new SQLException("No hay resultados para tu consulta sobre tipo contrato ocasional");
            throw ex;
        }
    }

    /**
     * Retorna un arreglo de placas asociados a un numero de contrato
     * ejemplo {"SXT696", "SXT705"}, si estas dos estan asociadas a
     * un contrato
     * @param id
     * @return Array whit data from veh_placa atribute
     * @throws SQLException
     */
    public String[] consultar_placas_contrato_ocasional(int id)throws SQLException{
        dato = new String[1];
        consultar = "select veh_placa from extracto_ocasional where con_id = ?";
        int cantidad = 0;

        // Creacion del pstate para realizar la consulta
        pstate = coneccion.prepareStatement("select count(*) from extracto_ocasional where con_id = ?");
        pstate.setInt(1, id);
        resultado = pstate.executeQuery();

        cantidad = (resultado.next())?resultado.getInt(1):0;

        if(cantidad == 0){
            SQLException ex = new SQLException("No hay resultados para tu consulta");
            throw ex;
        }else{
            
            resultado.close();
            pstate.close();
            dato = new String[cantidad];
        }

        pstate = coneccion.prepareStatement(consultar);
        pstate.setInt(1, id);

        resultado = pstate.executeQuery();

        for(int i = 0; resultado.next(); i++){
            dato[i] = resultado.getString(1);
        }

        resultado.close();
        pstate.close();
        return dato;
        
    }

    public String[] consultar_uno_contrato_ocasional(int id)throws SQLException{
        dato = new String[8];
        consultar = "select * from contrato_ocasional where con_id = ?";

        pstate = coneccion.prepareStatement(consultar);
        
        pstate.setInt(1, id);

        resultado = pstate.executeQuery();
        if(resultado.next()){
            for(int i = 0; i < dato.length; i++){

                dato[i] = resultado.getString(i+1);
            }
        }else{
            for(int i = 0; i < 7; i++){

                dato[i] = "null";
            }
        }
        pstate.close();
        return dato;
    }
    public int consultar_maximo_contrato_ocasional()throws SQLException{

        int max = 0;

        consultar = "select max(con_id) from contrato_ocasional";

        state = coneccion.createStatement();

        resultado = state.executeQuery(consultar);

        if(resultado.next()){
            max = resultado.getInt(1);
            return max;
        }
        return max;

    }

    public String[][] consultar_contrato_ocasional(String buscar)throws SQLException{
        datos = new String[1][12];
        String con_id = buscar + "%";
        String con_contratante = buscar + "%";
        String con_nombre = "%" + buscar + "%";
        String consultar_cantidad;
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_contrato_ocasional where con_id like ? or con_contratante like ? or con_nombre like ?";
        consultar_cantidad = "select count(*) from vw_contrato_ocasional where con_id like ? or con_contratante like ? or con_nombre like ?";

        pstate = coneccion.prepareStatement(consultar_cantidad);

        pstate.setString(1, con_id);
        pstate.setString(2, con_contratante);
        pstate.setString(3, con_nombre);

        resultado = pstate.executeQuery();


        if(resultado.next()){
            cantidad = resultado.getInt(1);
        }


        datos = new String[cantidad+1][12];
            
        datos[0][0] = "N. CONTRATO";
        datos[0][1] = "ID CONTRATANTE";
        datos[0][2] = "TIPO ID CONT";
        datos[0][3] = "NOMBRE CONTRATANTE";
        datos[0][4] = "FECHA INICIAL";
        datos[0][5] = "FECHA FINAL";
        datos[0][6] = "CIU ORIGEN";
        datos[0][7] = "DEP ORIGEN";
        datos[0][8] = "CIU DESTINO";
        datos[0][9] = "DEP DESTINO";
        datos[0][10] = "VALOR CONTRATO";
        datos[0][11] = "TIPO CONTRATO";

        if(cantidad == 0){
            return datos;
        }

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, con_id);
        pstate.setString(2, con_contratante);
        pstate.setString(3, con_nombre);

        resultado = pstate.executeQuery();

        while(resultado.next()){

            datos[i][0] = resultado.getString(1);
            datos[i][1] = resultado.getString(2);
            datos[i][2] = resultado.getString(3);
            datos[i][3] = resultado.getString(4);
            datos[i][4] = resultado.getString(5);
            datos[i][5] = resultado.getString(6);
            datos[i][6] = resultado.getString(7);
            datos[i][7] = resultado.getString(8);
            datos[i][8] = resultado.getString(9);
            datos[i][9] = resultado.getString(10);
            datos[i][10] = resultado.getString(11);
            datos[i][11] = resultado.getString(12);
            i++;
        }

        return datos;
    }

    public void insertar_contrato_ocasional(int numero_contrato, String contratante, String fecha_inical, String fecha_final, int origen, int destino, double valor, int tipo_contrato)throws SQLException{

        insertar = "insert into contrato_ocasional values (?,?,?,?,?,?,?,?);";

        pstate = coneccion.prepareStatement(insertar);

        pstate.setInt(1, numero_contrato);
        pstate.setString(2, contratante);
        pstate.setString(3, fecha_inical);
        pstate.setString(4, fecha_final);
        pstate.setInt(5, origen);
        pstate.setInt(6, destino);
        pstate.setDouble(7, valor);
        pstate.setInt(8, tipo_contrato);

        pstate.executeUpdate();
        pstate.close();


    }

    public void actualizar_contrato_ocasional(int numero_contrato, String contratante, String fecha_inical, String fecha_final, int origen, int destino, double valor, int tipo_contrato)throws SQLException{

        actualizar = "update contrato_ocasional set con_contratante = ?, con_fecha_inicial = ?, con_fecha_final = ?, con_origen = ?, con_destino = ?, con_valor = ?, tc_id = ? where con_id = ?";

        pstate = coneccion.prepareStatement(actualizar);

        pstate.setString(1, contratante);
        pstate.setString(2, fecha_inical);
        pstate.setString(3, fecha_final);
        pstate.setInt(4, origen);
        pstate.setInt(5, destino);
        pstate.setDouble(6, valor);
        pstate.setInt(7, tipo_contrato);
        pstate.setInt(8, numero_contrato);
        

        pstate.executeUpdate();
        pstate.close();
        
    }

    public void eliminar_contrato_ocasional(int numero_contrato) throws SQLException{

        borrar = "delete from contrato_ocasional where con_id = ?";

        pstate = coneccion.prepareStatement(borrar);

        pstate.setInt(1, numero_contrato);

        pstate.executeUpdate();
        pstate.close();
    }


    // metodos relacionados con contratos mensuales
    public int consultar_tipo_contrato_mensual(int contrato)throws SQLException{

        consultar = "select * from contrato_mensual where con_id = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setInt(1, contrato);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            return resultado.getInt(3);
        }
        return 0;
    }
    public String[] consultar_uno_contrato_mensual(int contrato)throws SQLException{
        dato = new String[9];

        consultar = "select * from vw_contrato_mensual where con_id = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setInt(1, contrato);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            
            dato[0] = resultado.getString(1);
            dato[1] = resultado.getString(2);
            dato[2] = resultado.getString(3);
            dato[3] = resultado.getString(4);
            dato[4] = resultado.getString(5);
            dato[5] = resultado.getString(6);
            dato[6] = resultado.getString(7);   
            dato[7] = resultado.getString(8);
            dato[8] = resultado.getString(9);
        }
        return dato;
    }

    public String[][] consultar_contratos_mensuales(String buscar) throws SQLException{
        datos = new String[1][9];
        int cantidad = 0;
        int i = 1;

        
        consultar = "select * from vw_contrato_mensual where con_contratante like \'" + buscar + "%\' or con_id like \'%" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'";

        try{
            
            state = coneccion.createStatement();
            resultado = state.executeQuery("select count(*) as total from vw_contrato_mensual where con_contratante like \'" + buscar + "%\' or con_id like \'%" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'");
            

            if(resultado.next()){
                cantidad = resultado.getInt(1);
            }


            datos = new String[cantidad+1][9];

            
            
            datos[0][0] = "N. CONTRATO";
            datos[0][1] = "ID CONTRATANTE";
            datos[0][2] = "TIPO ID CONT";
            datos[0][3] = "NOMBRE CONTRATANTE";
            datos[0][4] = "ID RESPONSABLE";
            datos[0][5] = "NOMBRE RESPONSABLE";
            datos[0][6] = "CELULAR RESPONSABLE";
            datos[0][7] = "DIRECCION RESPONSABLE";
            datos[0][8] = "OBJETO CONTRATO";

            if(cantidad == 0){
                return datos;
            }

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                datos[i][6] = resultado.getString(7);
                datos[i][7] = resultado.getString(8);
                datos[i][8] = resultado.getString(9);
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }

    public void eliminar_contrato_mensual(int id)throws SQLException{

        borrar = "delete from contrato_mensual where con_id = ?";

        pstate = coneccion.prepareStatement(borrar);

        pstate.setInt(1, id);

        pstate.executeUpdate();

    }

    public String consultar_ultimo_contrato_mensual()throws SQLException{
        consultar = "select max(con_id) from contrato_mensual";

        state = coneccion.createStatement();

        resultado = state.executeQuery(consultar);
        resultado.next();
        
        return "" + resultado.getInt(1);
    }

    public void insertar_contrato_mensual(int id, String contratante, int tipo_contrato)throws SQLException{

        insertar = "insert into contrato_mensual values (?,?,?)";

        pstate = coneccion.prepareStatement(insertar);

        pstate.setInt(1, id);
        pstate.setString(2, contratante);
        pstate.setInt(3, tipo_contrato);

        pstate.executeUpdate();
    }
    
    // Funcion para consultar los datos del vehiuclo para el extracto

    public String[] consultar_uno_vw_vehiculo_extracto(String placa)throws SQLException{
        dato = new String[6];

        consultar = "select * from vw_vehiculo_extracto where veh_placa = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, placa);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            
            dato[0] = resultado.getString(1);
            dato[1] = resultado.getString(2);
            dato[2] = resultado.getString(3);
            dato[3] = resultado.getString(4);
            dato[4] = resultado.getString(5);
            dato[5] = resultado.getString(6);
 
        }
        return dato;
    }

    

    // Funciones para conlutar actualizar y eliminar contratantes
    
    public void insertar_contratante(String contratante, String responsable)throws SQLException{

        insertar = "insert into contratante values (?,?)";

        pstate = coneccion.prepareStatement(insertar);

        pstate.setString(1, contratante);
        pstate.setString(2, responsable);

        pstate.executeUpdate();
    }
    
    public String[] consultar_uno_contratante(String contratante_id) throws SQLException{
        dato = new String[9];
        
        consultar = "select * from vw_contratante where con_contratante like ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, contratante_id);

        resultado = pstate.executeQuery();
        

        if(resultado.next()){
            for(int i = 0; i < dato.length; i++){
                dato[i] = resultado.getString(i+1);
            }
            resultado.close();
            pstate.close();
        }else{
            resultado.close();
            pstate.close();
            SQLException ex = new SQLException("No hay resultados para tu consulta sobre contratante");
            throw ex;
        }

        return dato;
    }
    public String[][] consultar_contratante(String buscar) throws SQLException{

        datos = new String[1][9];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_contratante where con_contratante like \'" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'";

        try{
            
            state = coneccion.createStatement();
            resultado = state.executeQuery("select count(*) as total from vw_contratante where con_contratante like \'" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'");
            

            if(resultado.next()){
                cantidad = resultado.getInt(1);
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][9];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID CONTRATANTE";
            datos[0][1] = "TIPO ID CONT";
            datos[0][2] = "NOMBRE CONTRATANTE";
            datos[0][3] = "ID RESPONSABLE";
            datos[0][4] = "NOMBRE RESPONSABLE";
            datos[0][5] = "CELULAR RESPONSABLE";
            datos[0][6] = "DIRECCION RESPONSABLE";
            datos[0][7] = "MUNICIPIO";
            datos[0][8] = "DEPARTAMENTO";
            

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                datos[i][6] = resultado.getString(7);
                datos[i][7] = resultado.getString(8);
                datos[i][8] = resultado.getString(9);
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }

    public void eliminar_contratante(String id)throws SQLException{

        borrar = "delete from contratante where con_contratante = ?";

        try{

            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, id);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
        
    }

    public void actualizar_contratante(String id, String dato)throws SQLException{

        actualizar = "update contratante set con_responsable = ? where con_contratante = ?";

        pstate = coneccion.prepareStatement(actualizar);

        pstate.setString(1, dato);
        pstate.setString(2, id);

        pstate.executeUpdate();
    }
    
    // Metodo para consultar el convenio al que pertenece un vehiculo externo
    public String[]consultar_uno_vehiculo_externo(String placa)throws SQLException{
        dato = new String[3];
        pstate = coneccion.prepareStatement("select * from vw_vehiculo_externo where veh_placa = ?");

        pstate.setString(1, placa);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            dato[0] = resultado.getString(1);
            dato[1] = resultado.getString(2);
            dato[2] = resultado.getString(3);
        }

        pstate.close();
        resultado.close();
        return dato;
    }

    public void insertar_documento2(String placa, String nombre, byte[] datos)throws SQLException{
        
        insertar = "insert into documento2 values (?,?,?)";

        pstate = coneccion.prepareStatement(insertar);

        pstate.setString(1, placa);
        pstate.setString(2, nombre);
        pstate.setBytes(3, datos);

        pstate.executeUpdate();

        pstate.close();
    }

    public byte[] consultar_documento2(String placa)throws SQLException{

        byte[] baytes = null;
        consultar = "select * from documento2 where veh_placa = ?";

        pstate = coneccion.prepareStatement(consultar);

        pstate.setString(1, placa);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            baytes = resultado.getBytes(3);
        }

        return baytes;
    }
    // Hacer un metodo para limpiar las tablas cuando las llenen con datos vacios
}
