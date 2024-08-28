package Base;

import java.sql.SQLException;

public class Contrato_ocasional extends Base {
    
    public Contrato_ocasional(String url){
        super(url);
    }

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
}
