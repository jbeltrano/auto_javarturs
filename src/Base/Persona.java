package Base;

import java.sql.SQLException;

import Utilidades.Capitalizar_Strings;

public class Persona extends Base{
    
    public Persona(String url){
        super(url);
    }

    /**
     * Realiza la insercion de una persona en la base de datos
     * utilizando los diversos parametros dependiendo del tipo de
     * dato a insertar en la base de datos
     * 
     * @param per_id Es el id o numero de documento de la persona
     * @param tip_id Es el tipo de documento de la persona
     * @param per_nombre Es el nombre de la persona
     * @param per_celular Es el numero de celular de la persona
     * @param ciu_id Es el id de la ciudad de la persona
     * @param per_direccion Es la direccion de la persona
     * @param per_correo Es el correo de la persona
     * 
     * @throws SQLException
     */
    public void insertar_persona(String per_id, int tip_id, String per_nombre, String per_celular, int ciu_id, String per_direccion, String per_correo)throws SQLException{
        insertar = "insert into persona values (?,?,?,?,?,?,?)";
        per_nombre = Capitalizar_Strings.capitalizarNombre(per_nombre);
        per_direccion = Capitalizar_Strings.capitalizar_primera(per_direccion);
        per_correo = per_correo.toLowerCase();
        
        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, per_id);
            pstate.setInt(2, tip_id);
            pstate.setString(3, per_nombre);
            pstate.setString(4, per_celular);
            pstate.setInt(5, ciu_id);
            pstate.setString(6, per_direccion);
            pstate.setString(7, per_correo);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro de la base de datos
     * cuando se le pase como parametro el id de la persona
     * @param per_id
     * @throws SQLException
     */
    public void eliminar_persona(String per_id)throws SQLException{

        borrar = "delete from persona where per_id = ?";

        try{

            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, per_id);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Actualiza la informacion de una persona, siempre y cuando
     * el {@code per_id} coincida con el id de la persona en la
     * base de datos. Y el resto de parametros actualizan el registro.
     * @param per_id
     * @param tip_id
     * @param per_nombre
     * @param per_celular
     * @param ciu_id
     * @param per_direccion
     * @param per_correo
     * @throws SQLException
     */
    public void actualizar_persona(String per_id, int tip_id, String per_nombre, String per_celular, int ciu_id, String per_direccion, String per_correo) throws SQLException{
        actualizar = "update persona set  tip_id = ?, per_nombre = ?, per_celular = ?, ciu_id = ?, per_direccion = ?, per_correo = ? where per_id = ?";

        per_nombre = Capitalizar_Strings.capitalizarNombre(per_nombre);
        per_direccion = Capitalizar_Strings.capitalizar_primera(per_direccion);

        try{
            pstate = coneccion.prepareStatement(actualizar);

            
            pstate.setInt(1, tip_id);
            pstate.setString(2, per_nombre);
            pstate.setString(3, per_celular);
            pstate.setInt(4, ciu_id);
            pstate.setString(5, per_direccion);
            pstate.setString(6, per_correo);
            pstate.setString(7, per_id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    } 

    /**
     * Retorna una matrix de tipo {@code String[][]}
     * con los diferentes registros que en cuentre con el
     * parametro {@code buscar}, el filtro lo hace por nombres
     * apellidos, incluso el id de la persona
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[][] consultar_persona(String buscar)throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_persona where per_nombre like \'%" + buscar + "%\' or per_id like \'" + buscar + "%\'";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_persona where per_nombre like \'%" + buscar + "%\' or per_id like \'" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][8];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";
            datos[0][2] = "NOMBRE";
            datos[0][3] = "CELULAR";
            datos[0][4] = "CIUDAD";
            datos[0][5] = "DEPARTAMENTO";
            datos[0][6] = "DIRECCION";
            datos[0][7] = "CORREO ELECTRONICO";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("tip_nombre");
                datos[i][2] = resultado.getString("per_nombre");
                datos[i][3] = resultado.getString("per_celular");
                datos[i][4] = resultado.getString("ciu_nombre");
                datos[i][5] = resultado.getString("dep_nombre");
                datos[i][6] = resultado.getString("per_direccion");
                datos[i][7] = resultado.getString("per_correo");
                
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
     * Retorna una matrix de tipo {@code String[][]},
     * con los registros de las personas que no sean
     * contratantes, haciendo el filtro con el parametro
     * {@code buscar}
     * 
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[][] consultar_no_contratante(String buscar)throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_no_contratante where per_nombre like \'%" + buscar + "%\' or per_id like \'" + buscar + "%\'";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_no_contratante where per_nombre like \'%" + buscar + "%\' or per_id like \'" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                
            }
            resultado.close();

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][8];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";
            datos[0][2] = "NOMBRE";
            datos[0][3] = "CELULAR";
            datos[0][4] = "CIUDAD";
            datos[0][5] = "DEPARTAMENTO";
            datos[0][6] = "DIRECCION";
            datos[0][7] = "CORREO ELECTRONICO";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("tip_nombre");
                datos[i][2] = resultado.getString("per_nombre");
                datos[i][3] = resultado.getString("per_celular");
                datos[i][4] = resultado.getString("ciu_nombre");
                datos[i][5] = resultado.getString("dep_nombre");
                datos[i][6] = resultado.getString("per_direccion");
                datos[i][7] = resultado.getString("per_correo");
                
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
     * Se encarga de retornar una matrix de
     * tipo {@code String[][]}, con todos los
     * registros de la base de datos sobre la
     * tabla persona
     * @return
     * @throws SQLException
     */
    public String[][] consultar_persona()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_persona";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_persona");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }
            resultado.close();

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][8];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";
            datos[0][2] = "NOMBRE";
            datos[0][3] = "CELULAR";
            datos[0][4] = "CIUDAD";
            datos[0][5] = "DEPARTAMENTO";
            datos[0][6] = "DIRECCION";
            datos[0][7] = "CORREO ELECTRONICO";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("tip_nombre");
                datos[i][2] = resultado.getString("per_nombre");
                datos[i][3] = resultado.getString("per_celular");
                datos[i][4] = resultado.getString("ciu_nombre");
                datos[i][5] = resultado.getString("dep_nombre");
                datos[i][6] = resultado.getString("per_direccion");
                datos[i][7] = resultado.getString("per_correo");
                
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
     * Retorna un {@code String[]}, con el resgistro
     * completo de la base de datos de la primer persona
     * encontrada por el parametro {@code buscar}, el cual
     * es el id de la persona.
     * 
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[] consultar_uno_persona(String buscar)throws SQLException{

        dato = new String[8];
        dato[0] = null;
        dato[1] = null;
        dato[3] = null;
        dato[4] = null;
        dato[5] = null;
        dato[6] = null;
        dato[7] = null;

        consultar = "select * from vw_persona where per_id like \'" + buscar +"\'";

        try{
            state = coneccion.createStatement();

            resultado = state.executeQuery(consultar);


            if (resultado.next()){
                dato[0] = resultado.getString("per_id");
                dato[1] = resultado.getString("tip_nombre");
                dato[2] = resultado.getString("per_nombre");
                dato[3] = resultado.getString("per_celular");
                dato[4] = resultado.getString("ciu_nombre");
                dato[5] = resultado.getString("dep_nombre");
                dato[6] = resultado.getString("per_direccion");
                dato[7] = resultado.getString("per_correo");
                
            }
            

        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            state.close();
        }

        return dato;

    }
    
    
    /**
     * Retorna una matrix de tipo {@code String[][]},
     * con los diferentes registros de las personas que
     * no sean persona juridica, utilizando tambien el filtro
     * {@code buscar} para filtrar los diferentes registros.
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[][] consultar_persona_natural(String buscar)throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_persona_natural where per_nombre like \'%" + buscar + "%\' or per_id like \'" + buscar + "%\'";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_persona_natural where per_nombre like \'%" + buscar + "%\' or per_id like \'" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }
            

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][8];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";
            datos[0][2] = "NOMBRE";
            datos[0][3] = "CELULAR";
            datos[0][4] = "CIUDAD";
            datos[0][5] = "DEPARTAMENTO";
            datos[0][6] = "DIRECCION";
            datos[0][7] = "CORREO";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("tip_nombre");
                datos[i][2] = resultado.getString("per_nombre");
                datos[i][3] = resultado.getString("per_celular");
                datos[i][4] = resultado.getString("ciu_nombre");
                datos[i][5] = resultado.getString("dep_nombre");
                datos[i][6] = resultado.getString("per_direccion");
                datos[i][7] = resultado.getString("per_correo");
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
