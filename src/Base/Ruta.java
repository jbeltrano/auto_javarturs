package Base;

import java.sql.SQLException;
import Estructuras_datos.Graph;

public class Ruta extends Base{
    
    public Ruta(String url){
        super(url);
    }

    
    public String[][] consultar_ruta(String buscar)throws SQLException{
        datos = new String[1][5];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_ruta where origen like \'" + buscar + "%\' or destino like \'" + buscar + "%\'";

        try{

            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_ruta where origen like \'" + buscar + "%\' or destino like \'" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            

            datos = new String[cantidad +1][5];

            datos[0][0] = "ID ORIGEN";
            datos[0][1] = "ORIGEN";
            datos[0][2] = "ID DESTINO";
            datos[0][3] = "DESTINO";
            datos[0][4] = "DISTANCIA";

            if(cantidad == 0){
                return datos;
            }
            
            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                for(int j = 0; j < datos[0].length; j++){
                    datos[i][j] = resultado.getString(j+1);
                }
                
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

    public void eliminar_ruta(int id_origen, int id_destino)throws SQLException{

        borrar = "delete from ruta where rut_origen_id = ? and rut_destino_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);
            
            pstate.setInt(1, id_origen);
            pstate.setInt(2, id_destino);

            pstate.executeUpdate();

            pstate.setInt(1, id_destino);
            pstate.setInt(2, id_origen);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }
    
    public void insertar_ruta(int id_origen, int id_destino, int distancia)throws SQLException{
        insertar = "insert into ruta values(?,?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setInt(1, id_origen);
            pstate.setInt(2, id_destino);
            pstate.setInt(3, distancia);

            pstate.executeUpdate();

            pstate.setInt(1, id_destino);
            pstate.setInt(2, id_origen);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    public void actualizar_ruta(int id_origen, int id_destino, int id_origen_final, int id_destino_final, int distancia)throws SQLException{

        actualizar = "update ruta set rut_origen_id = ?, rut_destino_id = ?, rut_distancia = ? where rut_origen_id = ? and rut_destino_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setInt(1, id_origen_final);
            pstate.setInt(2, id_destino_final);
            pstate.setInt(3, distancia);
            pstate.setInt(4, id_origen);
            pstate.setInt(5, id_destino);

            pstate.executeUpdate();

            
            pstate.setInt(1, id_destino_final);
            pstate.setInt(2, id_origen_final);
            pstate.setInt(3, distancia);
            pstate.setInt(4, id_destino);
            pstate.setInt(5, id_origen);
            

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    public void cargarRutasGrafo(Graph grafo) throws SQLException{
        consultar = "SELECT rut_origen_id, rut_destino_id, rut_distancia FROM ruta";

        try {

            state = coneccion.createStatement();
            resultado = state.executeQuery(consultar);

            while (resultado.next()) {
                int origen = resultado.getInt("rut_origen_id");
                int destino = resultado.getInt("rut_destino_id");
                int distancia = resultado.getInt("rut_distancia");

                // Agregamos la ruta de origen a destino
                grafo.setNodo(origen, destino, distancia);
                
            }
        } catch (SQLException e) {
            throw e;
        }finally{
            resultado.close();
            state.close();
        }
    }
}
