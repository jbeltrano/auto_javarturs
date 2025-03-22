package Base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Extractos extends Base{
    
    public Extractos(String url){
        super(url);
    }

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        String fecha_resultado;
        LocalDate date1;
        LocalDate date2;

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
                // Verifica que las fechas sean correctas
                fecha_resultado = resultado.getString(5);

                try {
                    // Convertir las cadenas a LocalDate
                    date1 = LocalDate.parse(fecha_resultado, formatter);
                    date2 = LocalDate.parse(fecha_final, formatter);
        
                    // Comparar las fechas
                    if (date1.isAfter(date2)) {
                        continue;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido: " + e.getMessage());
                }

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
        int consecutivo2 = consecutivo;
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
                    consecutivo2 = consecutivo = 1;

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
            state.close();
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

    public String[]consultar_uno_vehiculo_externo(String placa)throws SQLException{
        dato = new String[4];
        pstate = coneccion.prepareStatement("select * from vw_vehiculo_externo where veh_placa = ?");

        pstate.setString(1, placa);

        resultado = pstate.executeQuery();

        if(resultado.next()){
            dato[0] = resultado.getString(1);
            dato[1] = resultado.getString(2);
            dato[2] = resultado.getString(3);
            dato[3] = resultado.getString(4);
        }

        pstate.close();
        resultado.close();
        return dato;
    }

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
    
}
