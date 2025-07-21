package Base;

import java.io.IOException;
import java.sql.SQLException;

public class Contrato_mensual extends Base{
    
    public Contrato_mensual() throws IOException, SQLException{
        super();
    }

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
}
