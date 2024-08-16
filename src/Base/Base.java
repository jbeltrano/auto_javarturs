package Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

import Utilidades.Capitalizar_Strings;

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
     * Se encarga de consultar el
     * tipo de servicio vehicular
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

    /**
     * Inserta el tipo de id para una persona o entidad
     * dijitando el nombre a ocupar como tipo de id
     * @param tip_nombre
     * @throws SQLException
     */
    public void insertar_tipo_id(String tip_nombre)throws SQLException{
        insertar = "insert into tipo_id (tip_nombre) values (?)";
        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, tip_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Actualiza el tipo de id, especificamente
     * el nombre del tipo de id.
     * Utiliza a {@code id} para identificar el tipo de id
     * y lo remplaza con {@code tip_nombre} en la base de datos
     * @param id
     * @param tip_nombre
     * @throws SQLException
     */
    public void actualizar_tipo_id(int id, String tip_nombre)throws SQLException{
        actualizar = "update tipo_id set tip_nombre = ? where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, tip_nombre);
            pstate.setInt(2, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Elimina el registro en la base de datos
     * identificado con el parametro {@code id}
     * @param id
     * @throws SQLException
     */
    public void eliminar_tipo_id(int id)throws SQLException{
        borrar = "delete from tipo_id where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setInt(1, id);

            pstate.executeQuery();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Retorna un {@code String[]} dependiendo
     * de la columna que se pase en el parametro {@code columna}
     * siempre y cuando la columna exista en la base de datos
     * @param columna
     * @return
     * @throws SQLException
     */
    public String[] consultar_tipo_id(int columna)throws SQLException{
        
        datos = consultar_tipo_id();
        dato = new String[datos.length-1];
        for(int i = 0; i < datos.length-1; i++){
            dato[i] = datos[i+1][columna];
        }
        return dato;
    }

    /**
     * Retorna una matrix con los registros
     * que se encuentren en la base de datos
     * @return String[][]
     * @throws SQLException
     */
    public String[][] consultar_tipo_id()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;
        
        consultar = "select * from tipo_id";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from tipo_id");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO DOCUMENTO";

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("tip_id"));
                datos[i][1] = resultado.getString("tip_nombre");
                
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
     * Retorna un arreglo con un registro
     * completo de la base de datos identificado
     * con el parametro {@code id}
     * @param id
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_uno_tipo_id(int id)throws SQLException{
        dato = new String[2];
        dato[0] = null;
        dato[1] = null;

        consultar = "select * from tipo_id where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(consultar);

            pstate.setInt(1, id);

            resultado = pstate.executeQuery();

            if (resultado.next()){
                dato[0] = "" + resultado.getInt("tip_id");
                dato[1] = resultado.getString("tip_id");
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
            
            datos[0][0] = "ID";
            datos[0][1] = "CIUDAD";
            datos[0][2] = "DEPARTAMENTO";

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
            
            datos[0][0] = "ID";
            datos[0][1] = "CIUDAD";
            datos[0][2] = "DEPARTAMENTO";

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
            
            datos[0][0] = "ID";
            datos[0][1] = "CIUDAD";
            datos[0][2] = "DEPARTAMENTO";

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

    /**
     * Retorna un {@code String[]} con cont
     * todos los nombres de los departamentos
     * @return String[]
     * @throws SQLException
     */
    public String[] consultar_departamento()throws SQLException{
        dato = new String[1];
        int cantidad = 0;
        int i = 0;

        consultar = "select * from departamento";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from departamento");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return dato;
            }

            dato = new String[cantidad];

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                dato[i] = resultado.getString(2);
                
                i++;
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
     * Retorna una matrix de {@code String[][]},
     * donde aparecen los nombre y id de los
     * diferentes departamentos registrados en la
     * base de datos
     * @return
     * @throws SQLException
     */
    public String[][] consultar_departamentos()throws SQLException{
        datos = new String[1][2];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from departamento";

        try{

            

            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from departamento");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad +1][2];

            datos[0][0] = "ID";
            datos[0][1] = "DEPARTAMENTO";

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                
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
     * Retorna una matrix de {@code String[][]},
     * con los id y nombres de los departamentos, usando
     * como parametro de busqueda {@code buscar}
     * @param buscar
     * @return
     * @throws SQLException
     */
    public String[][] consultar_departamentos(String buscar)throws SQLException{
        datos = new String[1][2];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from departamento where dep_nombre like \'" + buscar + "%\'";

        try{

            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from departamento where dep_nombre like \'" + buscar + "%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                resultado.close();
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad +1][2];

            datos[0][0] = "ID";
            datos[0][1] = "DEPARTAMENTO";

            resultado = state.executeQuery(consultar);

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                
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
     * Retorna el id del tepartamento con un
     * dato de tipo {@code int}, utilizando
     * como parametro de busqueda el nombre exacto del
     * departamento usando el parametro {@code departamento}
     * @param departamento
     * @return int
     * @throws SQLException
     */
    public int consultar_id_departamento(String departamento) throws SQLException{

        consultar = "select dep_id from departamento where dep_nombre = ?";
        try{

            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, departamento);

            resultado = pstate.executeQuery();

            if(resultado.next()){
                return resultado.getInt(1);
            }else{
                SQLException ex = new SQLException("No hay resultados para tu consulta sobre departamento");
                throw ex;
            }
        }catch(SQLException ex){
            throw ex;
        }finally{
            resultado.close();
            pstate.close();
        }
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
    


    /**
     * Esta funcion se encarga de insertar
     * los tipos de empleados utilizando el
     * {@code nombre} como el nombre o tipo
     * de empleado.
     * @param nombre
     * @throws SQLException
     */
    public void insertar_tipo_empleado(String nombre)throws SQLException{
        insertar = "insert into tipo_empleado (tiem_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Este metodo se encarga de eliminar el
     * tipo de empleado pasado por el parametro
     * {@code id} que es de tipo {@code int}
     * @param id
     * @throws SQLException
     */
    public void eliminar_tipo_empleado(int id)throws SQLException{
        borrar = "delete from tipo_empleado where tiem_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setInt(1, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    /**
     * Retorna una matrix de tipo {@code String[][]},
     * la cual contiene todos los datos del tipo empleado
     * que estan almacenados en la base de datos
     * @return
     * @throws SQLException
     */
    public String [][] consultar_tipo_empleado()throws SQLException{
    
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from tipo_empleado";

        try{
            state = coneccion.createStatement();
 
            // Obtiene la cantidad de elementos que vamos a necesitar declarar para nuestra matrix
            resultado = state.executeQuery("select count() as total from tipo_empleado");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            resultado.close();

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";

            while(resultado.next()){

                datos[i][0] = "" + resultado.getInt("tiem_id");
                datos[i][1] = resultado.getString("tiem_nombre");

                
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
     * Se encarga de insertar las diferentes
     * eps en las que puede estar afiliado
     * un empleado utilizando el parametro
     * {@code eps_nombre} el cual contiene
     * el nombre de la eps.
     * 
     * @param eps_nombre
     * @throws SQLException
     */
    public void insertar_eps(String eps_nombre)throws SQLException{
        insertar = "insert into eps (eps_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, eps_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro de
     * eps en la base de datos identificandolo
     * con el parametro {@code id} que es de tipo
     * {@code int}.
     * 
     * @param id
     * @throws SQLException
     */
    public void eliminar_eps(int id)throws SQLException{
        borrar = "delete from eps where eps_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);
            
            pstate.setInt(1, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Retorna una matrix de tipo {@code String[][]},
     * la cual contiene los diferentes registros que hay
     * en la base de datos sobre las eps.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_eps()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from eps";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from eps");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }
            resultado.close();

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "EPS";


            while(resultado.next()){

                datos[i][0] = "" + resultado.getInt("eps_id");
                datos[i][1] = resultado.getString("eps_nombre");
                
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
     * Se encarga de insertar los diferentes
     * tipos de pensiones en los que los empleados
     * pueden estar afiliados utilizando el parametro
     * {@code pen_nombre} como el nombre de pension.
     * @param pen_nombre
     * @throws SQLException
     */
    public void insertar_pension(String pen_nombre)throws SQLException{
        insertar = "insert into pension (pen_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, pen_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de eliminar un registro de
     * la base de datos de las pensiones,
     * utilizando el registro {@code id},
     * de tipo {@code int}, el cual identifica
     * el tipo o nombre de pension.
     * 
     * @param id
     * @throws SQLException
     */
    public void eliminar_pension(int id)throws SQLException{
        borrar = "delete from pension where pen_id = " + id;

        try{
            state = coneccion.createStatement();
            
            state.executeUpdate(borrar);
            
        }catch(SQLException ex){
            throw ex;
        }finally{
            state.close();
        }
    }
    
    /**
     * Retorna una matrix de tipo {@code String[][]},
     * la cual contiene los diferentes registros de
     * pension en la base de datos.
     * 
     * @return
     * @throws SQLException
     */
    public String[][] consultar_pension()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from pension";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from pension");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
                
            }
            resultado.close();

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "AFP";

            while(resultado.next()){

                datos[i][0] = resultado.getString("pen_id");
                datos[i][1] = resultado.getString("pen_nombre");

                
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


    // Metodos para Insertar, eliminar, actualizar y consultar CESANTIAS
    public void insertar_cesantias(String ces_nombre)throws SQLException{
        insertar = "insert into cesantias (ces_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, ces_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_cesantias(int id)throws SQLException{
        borrar = "delete from cesantias where ces_id = " + id;

        try{
            state = coneccion.createStatement();
            state.executeUpdate(borrar);

        }catch(SQLException ex){
            throw ex;
        }

    }

    public String[][] consultar_cesantias()throws SQLException{
        
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from cesantias";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from cesantias");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "CESANTIAS";

            while(resultado.next()){

                datos[i][0] = resultado.getString("ces_id");
                datos[i][1] = resultado.getString("ces_nombre");

                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }


    // Metodos para insertar, eliminar, actualizar y consultar BANCOS
    public void insertar_banco(String ban_nombre)throws SQLException{
        insertar = "insert into banco(ban_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, ban_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_banco(int id)throws SQLException{
        borrar = "delete from banco where ban_id = " + id;

        try{
            state = coneccion.createStatement();
            state.executeUpdate(borrar);
        }catch(SQLException ex){
            throw ex;
        }
    }

    public String[][] consultar_banco()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from banco";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from banco");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "CESANTIAS";

            while(resultado.next()){

                datos[i][0] = resultado.getString("ban_id");
                datos[i][1] = resultado.getString("ban_nombre");

                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }


    // Metodos para insertar, eliminar, actualizar y cunsultar TIPO_NOVEDAD
    public void insertar_tipo_novedad(String nombre, String nombre_largo)throws SQLException{
        insertar = "insert into tipo_novedad(tin_nombre, tin_nombre2) values (?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, nombre);
            pstate.setString(2, nombre_largo);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    public void insertar_tipo_novedad(String nombre)throws SQLException{
        insertar = "insert into tipo_novedad (tin_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_tipo_novedad(int id)throws SQLException{
        borrar = "delete from tipo_novedad where tin_id = "+id;

        try{
            state = coneccion.createStatement();
            state.executeUpdate(borrar);
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    public String[][] consultar_tipo_novedad()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from tipo_novedad";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from tipo_novedad");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";

            while(resultado.next()){

                datos[i][0] = resultado.getString("tin_id");
                datos[i][1] = resultado.getString("tin_nombre");

                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }

    
    // Metodos para insertar, eliminar, actualizar y consultar NOVEDAD
    public void insertar_novedad(String persona, int tipo_novedad, int numeor_dias, Date fecha_inicial, Date fecha_final)throws SQLException{
        insertar = "insert into novedad (per_id, tin_id, nov_dias, nov_fecha_inicial, nov_fecha_final) values (?,?,?,?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, persona);
            pstate.setInt(2, tipo_novedad);
            pstate.setDate(3, fecha_inicial);
            pstate.setDate(4, fecha_final);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public String[][] consultar_novedad()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from novedad";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from novedad");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][7];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "NOMBRE";
            datos[0][2] = "NOV";
            datos[0][3] = "NOVEDAD";
            datos[0][4] = "CANTIDAD DIAS";
            datos[0][5] = "ESTADO";
            datos[0][6] = "FECHA INICIAL";
            datos[0][7] = "FECHA FINAL";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("per_nombre");
                datos[i][2] = resultado.getString("tin_nombre");
                datos[i][3] = resultado.getString("tin_nombre2");
                datos[i][4] = resultado.getString("nov_dias");
                datos[i][5] = resultado.getString("nov_estado");
                datos[i][6] = resultado.getString("nov_fecha_inicial");
                datos[i][6] = resultado.getString("nov_fecha_final");
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }

    // Metodos para insertar, eliminar, actualizar y eliminar VEHICULO
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
        }
    }

    public void eliminar_vehiculo(String id)throws SQLException{
        borrar = "delete from vehiculo where veh_placa = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }

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
            SQLException e = new SQLException("No es posible realizar la actualizacion a vehiculos.\n Error 3");
            throw e;
        }
    }
    
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

    public int consultar_capacidad_vehiculo(String buscar)throws SQLException{
        int auxiliar = 0;
        consultar = "select veh_cantidad from vehiculo where veh_placa = ?";

        pstate = coneccion.prepareStatement(consultar);
        pstate.setString(1, buscar);

        resultado = pstate.executeQuery();

        if(resultado.next()){
           auxiliar = resultado.getInt(1); 
        }else{
            resultado.close();
            pstate.close();
            throw new SQLException("No hay resultados para tu busqueda");
        }

        resultado.close();
        pstate.close();

        return auxiliar;
    }

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
    
    public String[] consultar_uno_vehiculo(String id)throws SQLException{

        dato = new String[17];
        for(int i = 0; i < dato.length; i++){
            dato[i] = null;
        }

        consultar = "select * from vw_vehiculo where veh_placa like ?";
        String consutlar2 = "select veh_parque_automotor from vehiculo where veh_placa like ?";
        ResultSet resultado2;

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
