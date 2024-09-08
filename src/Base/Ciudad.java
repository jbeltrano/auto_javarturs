package Base;

import java.sql.SQLException;

import Utilidades.Capitalizar_Strings;

public class Ciudad extends Base{
    
    private String[] HEAD_TABLA = {"ID", "CIUDAD", "DEPARTAMENTO"};

    public Ciudad(String url){
        super(url);
    }
    /**
     * Inserta una ciudad donde {@code dep_id}
     * es el departamento al que va a pertenecer
     * la ciudad y {@code ciu_nombre} es el nombre
     * de la ciudad a registrar
     * @param dep_id
     * @param ciu_nombre
     * @throws SQLException
     */
    public void insertar_ciudad(int dep_id, String ciu_nombre)throws SQLException{
        
        insertar = "insert into ciudad (ciu_nombre, dep_id) values (?,?)";
        String nombre = Capitalizar_Strings.capitalizarNombre(ciu_nombre);

        try{
            pstate = coneccion.prepareStatement(insertar);
            
            pstate.setString(1, nombre);
            pstate.setInt(2, dep_id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Elimina el registro de una ciudad identificada
     * con el parametro {@code ciu_id}
     * @param ciu_id
     * @throws SQLException
     */
    public void eliminar_ciudad(String ciu_id)throws SQLException{
        borrar = "delete from ciudad where ciu_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);
            
            pstate.setString(1, ciu_id);

            pstate.executeUpdate();
        
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Actualiza el nombre de la ciudad
     * identificandola con {@code ciu_id},
     * mientras que el nombre a actualizar
     * se pasa en el parametro {@code ciu_nombre}
     * @param ciu_id
     * @param ciu_nombre
     * @throws SQLException
     */
    public void actualizar_ciudad(int ciu_id, String ciu_nombre)throws SQLException{

        actualizar = "update ciudad set ciu_nombre = ? where ciu_id = ?";
        String nombre = Capitalizar_Strings.capitalizarNombre(ciu_nombre);
        

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, nombre);
            pstate.setInt(2, ciu_id);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Retorna todos los registros de
     * ciudades que se encuentren en la
     * base de datos
     * @return String[][]
     * @throws SQLException
     */
    public String [][] consultar_ciudad()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad natural join departamento";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from ciudad");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][3];

            resultado = state.executeQuery(consultar);
            
            datos[0] = HEAD_TABLA;

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("ciu_id"));
                datos[i][1] = resultado.getString("ciu_nombre");
                datos[i][2] = resultado.getString("dep_nombre");
                
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
     * Retorna una matrix con todos los
     * registros filtrados por el parametros
     * {@code buscar}, este puede realizar la
     * busqueda como un departamento o una ciudad
     * @param buscar
     * @return String[][]
     * @throws SQLException
     */
    public String [][] consultar_ciudad(String buscar)throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad natural join departamento where dep_nombre like \'%"+buscar+"%\'";

        try{
            state = coneccion.createStatement();
            
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from ciudad natural join departamento where dep_nombre like \'%"+buscar+"%\'");
            
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][3];

            resultado = state.executeQuery(consultar);
            
            datos[0] = HEAD_TABLA;

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("ciu_id"));
                datos[i][1] = resultado.getString("ciu_nombre");
                datos[i][2] = resultado.getString("dep_nombre");
                
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
     * Retorna un {@code String[]} buscando
     * con el parametro {@code ciudad}, y con
     * el parametro {@code columna} se define
     * la columna a retornar
     * @param buscar
     * @param columna
     * @return
     * @throws SQLException
     */
    public String [] consultar_ciudad(String buscar, int columna)throws SQLException{
        
        dato = new String[1];
        try{
            datos = consultar_ciudad(buscar);
            dato = new String[datos.length-1];
            for(int i = 0;i < datos.length-1; i++){
            dato[i] = datos[i+1][columna];
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
        
        return dato;
    }

    /**
     * Retorna una matrix de las diferentes ciudades
     * bucando los registros con el parametro {@code buscar}
     * @param buscar
     * @return String[][]
     * @throws SQLException
     */
    public String [][] consultar_ciudades(String buscar)throws SQLException{
        datos = new String[1][3];
        int cantidad = 0;
        int i = 1;

        consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad natural join departamento where ciu_id like \'" + buscar + "\' or ciu_nombre like \'%"+buscar+"%\' or dep_nombre like \'"+buscar+"%\'";

        try{
            
            state = coneccion.createStatement();
            
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from ciudad natural join departamento where ciu_id like \'" + buscar + "\' or ciu_nombre like \'%"+buscar+"%\' or dep_nombre like \'"+buscar+"%\'");
            
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][3];

            resultado = state.executeQuery(consultar);
            
            datos[0] = HEAD_TABLA;

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("ciu_id"));
                datos[i][1] = resultado.getString("ciu_nombre");
                datos[i][2] = resultado.getString("dep_nombre");
                
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
     * Retorna un {@code String[]} el cual contiene
     * un registro completo de una ciudad buscada
     * mediante el parametro {@code buscar}, el cual
     * puede ser el id o el nombre a buscar
     * 
     * Retorna el registro en bruto, es decir sin hacer
     * joins dentro de la base de datos
     * @param buscar
     * @return String[]
     * @throws SQLException
     */
    public String [] consultar_uno_ciudad(String buscar)throws SQLException{

        dato = new String[3];
        dato[0] = null;
        dato[1] = null;
        dato[2] = null;
        try{
            consultar = "select * from ciudad where ciu_id = "+ Integer.parseInt(buscar);
        }catch(NumberFormatException e){
            consultar = "select * from ciudad where ciu_nombre like \'"+buscar +"\'";
        }
        

        try{
            state = coneccion.createStatement();

            resultado = state.executeQuery(consultar);

            if (resultado.next()){
                dato[0] = "" + resultado.getInt("ciu_id");
                dato[1] = resultado.getString("ciu_nombre");
                dato[2] = resultado.getString("dep_id");
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
     * Retorna un registro de la base de datos en {@code String[]} 
     * en un formato especifico donde aparecen los nombres en ves
     * de los identificadores. Utilizando el parametro {@code buscar}
     * 
     * @param buscar
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_uno_ciudades(String buscar)throws SQLException{

        dato = new String[3];
        dato[0] = null;
        dato[1] = null;
        dato[2] = null;
        try{
            consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad natural join departamento where ciu_id = "+ Integer.parseInt(buscar);
        }catch(NumberFormatException e){
            consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad where ciu_nombre like \'"+buscar +"\'";
        }
        

        try{
            state = coneccion.createStatement();

            resultado = state.executeQuery(consultar);

            if (resultado.next()){
                dato[0] = "" + resultado.getInt(1);
                dato[1] = resultado.getString(2);
                dato[2] = resultado.getString(3);
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
     * Se encarga de insertar un registro en la base de datos
     * donde se pasa como parametros la {@code ciudad} y el
     * {@code departamento} en valores de tipo {@code String}
     * @param ciudad
     * @param departamento
     * @throws SQLException
     */
    public void insertar_ciudad(String ciudad, String departamento) throws SQLException{

        insertar = "insert into ciudad (ciu_nombre, dep_id) values (?, (select dep_id from departamento where dep_nombre = ?))";
        ciudad = Capitalizar_Strings.capitalizarNombre(ciudad);
        try{

            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, ciudad);
            pstate.setString(2, departamento);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }

    }
}
