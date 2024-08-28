package Base;

import java.sql.SQLException;

public class Base extends Base_datos{

    
    protected String[] dato;
    protected String[][] datos;


    /* Construtor basico ingresando url base de datos con la plantilla estandar */
    public Base(String url){
        super(url);
    }

    /**
     * Metodo que debe ser heredado para modificar o 
     * establecer las cabeceras de las tablas.
     */
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
    
    
    // Metodos relacionados con contratos ocasionales

    // Metodo para consultar el tipo de contrato
    


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
