package Base;

import java.util.Vector;

import org.apache.poi.sl.draw.geom.SqrtExpression;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

import Utilidades.Capitalizar_Strings;

public class Base extends Base_datos{

    
    private String[] dato;
    private String[][] datos;


    /* Construtor basico ingresando url base de datos con la plantilla estandar */
    public Base(String url){
        super(url);
    }

    // Metodos para insertar, borrar, actualizar y eliminar CLASE VEHICULO
    public void insertar_clase_vehiculo(String cla_nombre)throws SQLException{
        insertar = "insert into clase_vehiculo (cla_nombre) values (?)";
        try{

            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, cla_nombre);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    public void actualizar_clase_vehiculo(int id, String nombre)throws SQLException{
        
        actualizar = "update clase_vehiculo set cla_nombre = ? where cla_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, nombre);
            pstate.setInt(2, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }

    public void actualizar_clase_vehiculo(String nombre_inicial, String nombre_final)throws SQLException{
        actualizar = "update clase_vehiculo set cla_nombre = ? where cla_nombre like ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, nombre_final);
            pstate.setString(2, nombre_inicial);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    public void eliminar_clase_vehiculo(int id)throws SQLException{
        borrar = "delete from clase_vehiculo where cla_id = ?";
        consultar = "select count(cla_id) as total from vehiculo where cla_id = ?";

        try{
            // consultando cuantos elementos existen en la tabla vehiculo
            pstate = coneccion.prepareStatement(consultar);
            pstate.setInt(1, id);

            resultado = pstate.executeQuery();
            System.out.println(resultado.getInt("total"));
            if(resultado.getInt("total") > 0){
                SQLException exception = new SQLException("No es posible eliminar este registro. Error 1\nConsultar documento");
                throw exception;
            }else{
                pstate = coneccion.prepareStatement(borrar);
                
                pstate.setInt(1, id);

                pstate.executeUpdate();
            }
        }catch(SQLException ex){
            throw ex;
        }
    }

    public Vector <String> consultar_clase_vehiculo(int columna)throws SQLException{
        Vector<String> vector = new Vector<>();

        int cantidad = 0;
        
        consultar = "select * from clase_vehiculo";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from clase_vehiculo");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return vector;
            }

            resultado = state.executeQuery(consultar);

            if(columna == 1){
                while(resultado.next()){
                    vector.add(""+resultado.getInt("cla_id"));
                }
            }
            else if(columna == 2){
                while(resultado.next()){
                    vector.add(resultado.getString("cla_nombre"));
                }
            }
            else{
                while(resultado.next()){
                    vector.add(resultado.getInt("cla_id") + "|" + resultado.getString("cla_nombre"));
                }
            }

        }catch(SQLException ex){
            throw ex;
        }


        return vector;
    }

    public String[][] consultar_clase_vehiculo()throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;
        
        consultar = "select * from clase_vehiculo";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from clase_vehiculo");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][2];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO VEHICULO";

            while(resultado.next()){

                datos[i][0] = new String("" + resultado.getInt("cla_id"));
                datos[i][1] = resultado.getString("cla_nombre");
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;
    }

    public String[] consultar_uno_clase_vehiculo(String id)throws SQLException{
        dato = new String[2];
        dato[0] = null;
        dato[1] = null;

        consultar = "select * from clase_vehiculo where cla_id = ?";

        try{
            pstate = coneccion.prepareStatement(consultar);
            try{
                pstate.setInt(1, Integer.parseInt(id));
            }catch(NumberFormatException ex){
                consultar = "select * from clase_vehiculo where cla_nombre like ?";
                pstate = coneccion.prepareStatement(consultar);
                pstate.setString(1, id);
            }

            resultado = pstate.executeQuery();

            if (resultado.next()){
                dato[0] = "" + resultado.getInt(1);
                dato[1] = resultado.getString(2);
            }
            

        }catch(SQLException ex){
            throw ex;
        }
        return dato;
    }

    // metodos para SERVICIO
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
            }
            
            resultado = state.executeQuery(consultar);

            for(int i = 0; resultado.next(); i++){
                dato[i] = resultado.getString(2);
            }
            

        }catch(SQLException ex){
            throw ex;
        }
        return dato;
    }

    // Metodos para insertar, eliminar, actualizar y consultar TIPO ID
    public void insertar_tipo_id(String tip_nombre)throws SQLException{
        insertar = "insert into tipo_id (tip_nombre) values (?)";
        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, tip_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void actualizar_tipo_id(int id, String tip_nombre)throws SQLException{
        actualizar = "update tipo_id set tip_nombre = ? where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, tip_nombre);
            pstate.setInt(2, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_tipo_id(int id)throws SQLException{
        borrar = "delete from tipo_id where tip_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);

            pstate.setInt(1, id);

            pstate.executeQuery();
        }catch(SQLException ex){
            throw ex;
        }
    }

    public String[] consultar_tipo_id(int columna)throws SQLException{
        
        datos = consultar_tipo_id();
        dato = new String[datos.length-1];
        for(int i = 0; i < datos.length-1; i++){
            dato[i] = datos[i+1][columna];
        }
        return dato;
    }

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
        }

        return datos;
    }

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
        }
        return dato;
    }

    // Metodos para insertar, eliminar, actualizar y consultar CIUDAD
    public void insertar_ciudad(int dep_id, String ciu_nombre)throws SQLException{
        
        insertar = "insert into ciudad (ciu_nombre, dep_id) values (?,?)";
        ciu_nombre = Capitalizar_Strings.capitalizarNombre(ciu_nombre);

        try{
            pstate = coneccion.prepareStatement(insertar);
            
            pstate.setString(1, ciu_nombre);
            pstate.setInt(2, dep_id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_ciudad(String ciu_id)throws SQLException{
        borrar = "delete from ciudad where ciu_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);
            
            pstate.setString(1, ciu_id);

            pstate.executeUpdate();
        
        }catch(SQLException ex){
            throw ex;
        }
    }

    public void actualizar_ciudad(int ciu_id, String ciu_nombre)throws SQLException{

        actualizar = "update ciudad set ciu_nombre = ? where ciu_id = ?";
        ciu_nombre = Capitalizar_Strings.capitalizarNombre(ciu_nombre);

        try{
            pstate = coneccion.prepareStatement(actualizar);

            pstate.setString(1, ciu_nombre);
            pstate.setInt(2, ciu_id);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    

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
        }

        return datos;
    }

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
    
    public String [][] consultar_ciudad(String buscar)throws SQLException{
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad natural join departamento where dep_nombre like ?";

        try{
            pstate = coneccion.prepareStatement(consultar);
            state = coneccion.createStatement();
            
            pstate.setString(1, buscar);
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from ciudad natural join departamento where dep_nombre like \'"+buscar+"\'");
            
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][3];

            resultado = pstate.executeQuery();
            
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
        }

        return datos;
    }

    public String [][] consultar_ciudades(String buscar)throws SQLException{
        datos = new String[1][3];
        int cantidad = 0;
        int i = 1;

        consultar = "select ciu_id, ciu_nombre, dep_nombre from ciudad natural join departamento where ciu_nombre like \'"+buscar+"%\' or dep_nombre like \'"+buscar+"%\'";

        try{
            
            state = coneccion.createStatement();
            
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from ciudad natural join departamento where ciu_nombre like \'"+buscar+"%\' or dep_nombre like \'"+buscar+"%\'");
            
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
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
        }

        return datos;
    }

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
        }

        return dato;
    }

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
        }

        return dato;
    }

    public void insertar_ciudad(String ciudad, String departamento) throws SQLException{

        insertar = "insert into ciudad (ciu_nombre, dep_id) values (?, (select dep_id from departamento where dep_nombre = ?))";

        try{

            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, ciudad);
            pstate.setString(2, departamento);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }

    }

    // Metodos para departamento
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
        }

        return dato;
    }

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
        }

        return datos;
    }

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
        }

        return datos;
    }

    public int consultar_id_departamento(String departamento) throws SQLException{

        consultar = "select dep_id from departamento where dep_nombre = ?";
        try{

            pstate = coneccion.prepareStatement(consultar);
            pstate.setString(1, departamento);

            resultado = pstate.executeQuery();

            if(resultado.next()){
                return resultado.getInt(1);
            }else{
                SQLException ex = new SQLException("No hay resultados para tu consulta");
                throw ex;
            }
        }catch(SQLException ex){
            throw ex;
        }
    }

    // Metodos para insertar, eliminar, actualizar y consultar PERSONA
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
        }
    }

    public void eliminar_persona(String per_id)throws SQLException{

        borrar = "delete from persona where per_id = ?";

        try{

            pstate = coneccion.prepareStatement(borrar);

            pstate.setString(1, per_id);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

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
        }
    } 

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
        }

        return datos;

    
    }
    
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
        }

        return datos;

    
    }

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
        }

        return datos;
    }

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
        }

        return dato;

    }
    
    // Metodos para consultar a personas naturales
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
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][7];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";
            datos[0][2] = "NOMBRE";
            datos[0][3] = "CELULAR";
            datos[0][4] = "CIUDAD";
            datos[0][5] = "DEPARTAMENTO";
            datos[0][6] = "DIRECCION";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("tip_nombre");
                datos[i][2] = resultado.getString("per_nombre");
                datos[i][3] = resultado.getString("per_celular");
                datos[i][4] = resultado.getString("ciu_nombre");
                datos[i][5] = resultado.getString("dep_nombre");
                datos[i][6] = resultado.getString("per_direccion");
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    
    }
    
    public String[][] consultar_persona_natural()throws SQLException{

        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from vw_persona_natural";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from vw_persona_natural");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            if(cantidad == 0){
                return datos;
            }

            datos = new String[cantidad+1][7];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID";
            datos[0][1] = "TIPO";
            datos[0][2] = "NOMBRE";
            datos[0][3] = "CELULAR";
            datos[0][4] = "CIUDAD";
            datos[0][5] = "DEPARTAMENTO";
            datos[0][6] = "DIRECCION";

            while(resultado.next()){

                datos[i][0] = resultado.getString("per_id");
                datos[i][1] = resultado.getString("tip_nombre");
                datos[i][2] = resultado.getString("per_nombre");
                datos[i][3] = resultado.getString("per_celular");
                datos[i][4] = resultado.getString("ciu_nombre");
                datos[i][5] = resultado.getString("dep_nombre");
                datos[i][6] = resultado.getString("per_direccion");
                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;
    }

    // Metodo para insertar, eliminar, actualizar y consultar TIPO EMPLEADO
    public void insertar_tipo_empleado(String nombre)throws SQLException{
        insertar = "insert into tipo_empleado (tiem_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

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
    
    public String [][] consultar_tipo_empleado()throws SQLException{
    
        datos = new String[1][20];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from tipo_empleado";

        try{
            state = coneccion.createStatement();
 
            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from tipo_empleado");
            
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

                datos[i][0] = "" + resultado.getInt("tiem_id");
                datos[i][1] = resultado.getString("tiem_nombre");

                
                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }

    // Metodos para inertar, eliminar, actualizar y consultar EPS
    public void insertar_eps(String eps_nombre)throws SQLException{
        insertar = "insert into eps (eps_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, eps_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_eps(int id)throws SQLException{
        borrar = "delete from eps where eps_id = ?";

        try{
            pstate = coneccion.prepareStatement(borrar);
            
            pstate.setInt(1, id);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }
    }

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

            if(cantidad == 0){
                return datos;
            }

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
        }

        return datos;

    }
    
    
    // Metodos para insertar, eliminar, actualizar y consultar PENSION
    public void insertar_pension(String pen_nombre)throws SQLException{
        insertar = "insert into pension (pen_nombre) values (?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, pen_nombre);

            pstate.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }
    }

    public void eliminar_pension(int id)throws SQLException{
        borrar = "delete from pension where pen_id = " + id;

        try{
            state = coneccion.createStatement();
            
            state.executeUpdate(borrar);
            
        }catch(SQLException ex){
            throw ex;
        }
    }
    
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

            if(cantidad == 0){
                return datos;
            }

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

    public String[][] consultar_documentos()throws SQLException{

        datos = new String[1][8];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from documento";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from documento");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            datos = new String[cantidad+1][8];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "NUMERO INTERNO";
            datos[0][2] = "FECHA SOAT";
            datos[0][3] = "FECHA RTM";
            datos[0][4] = "FECHA RCC";
            datos[0][5] = "FECHA RCE";
            datos[0][6] = "TOP";
            datos[0][7] = "FECHA TOP";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                datos[i][6] = resultado.getString(7);
                datos[i][7] = resultado.getString(8);
                

                i++;
            }

        }catch(SQLException ex){
            throw ex;
        }

        return datos;

    }
    
    public String[][] consultar_documentos(String buscar)throws SQLException{

        datos = new String[1][8];
        int cantidad = 0;
        int i = 1;

        consultar = "select * from documento where veh_placa like \'" + buscar +"%\'";

        try{
            state = coneccion.createStatement();

            // Se obtiene la cantidad de elementos a retornar y inicializar la matriz
            resultado = state.executeQuery("select count() as total from documento where veh_placa like \'" + buscar +"%\'");
            
            if(resultado.next()){
                cantidad = resultado.getInt("total");
            }

            datos = new String[cantidad+1][8];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "PLACA";
            datos[0][1] = "NUMERO INTERNO";
            datos[0][2] = "FECHA SOAT";
            datos[0][3] = "FECHA RTM";
            datos[0][4] = "FECHA RCC";
            datos[0][5] = "FECHA RCE";
            datos[0][6] = "TOP";
            datos[0][7] = "FECHA TOP";



            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                datos[i][6] = resultado.getString(7);
                datos[i][7] = resultado.getString(8);
                

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

                dato[0] = resultado.getString(1);
                dato[1] = resultado.getString(2);
                dato[2] = resultado.getString(3);
                dato[3] = resultado.getString(4);
                dato[4] = resultado.getString(5);
                dato[5] = resultado.getString(6);
                dato[6] = resultado.getString(7);
                dato[7] = resultado.getString(8);
                
            }
            

        }catch(SQLException ex){
            throw ex;
        }
        return dato;

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
        datos = new String[1][13];
        int cantidad = 0;
        int i = 1;

        
        consultar = "select * from vw_extracto_mensual where veh_placa like \'%" + buscar + "%\'";

        
            
        state = coneccion.createStatement();
        resultado = state.executeQuery("select count(*) as total from vw_extracto_mensual where veh_placa like \'%" + buscar + "%\'");
            

        if(resultado.next()){
            cantidad = resultado.getInt(1);
        }

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
        datos[0][12] = "TP CONTRATO";

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

        return datos;

    }

    public String[] consultar_uno_extracto_mensual(String placa, int consecutivo)throws SQLException{
        dato = new String[8];

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
            dato[7] = resultado.getString(8);   
        }
        return dato;
    }

    public void actualizar_extracto_mensual(String placa, int consecutivo, int contrato, String fecha_inicial, String fecha_final, int origen, int destino, int tipo_contrato)throws SQLException{

        actualizar = "update extracto_mensual set con_id = ?, ext_fecha_incial = ?, est_fecha_final = ?, ext_origen = ?, ext_destino = ?, tc_id = ? where veh_placa = ? and ext_consecutivo = ?";

        pstate = coneccion.prepareStatement(actualizar);

            pstate.setInt(1, contrato);
        pstate.setString(2, fecha_inicial);
        pstate.setString(3, fecha_final);
        pstate.setInt(4,origen);
        pstate.setInt(5, destino);
        pstate.setInt(6, tipo_contrato);
        pstate.setString(7, placa);
        pstate.setInt(8, destino);
        

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

    public void insertar_extracto_mensual(String placa, int contrato, String fecha_inicial, String fecha_final, int origen, int destino, int consecutivo, int tipo_contrato)throws SQLException{

        String accion_auxiliar = "";
        insertar = "insert into extracto_mensual values (?,?,?,?,?,?,?,?)";
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
        pstate.setInt(8, tipo_contrato);

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

    public String[][] consultar_vw_extracto_ocasional(String buscar) throws SQLException{
        datos = new String[1][12];
        int cantidad = 0;
        int i = 1;

        String placa = buscar+"%";
        String contrato = buscar + "%";
        String persona = "%" + buscar + "%";

        consultar = "select * from vw_extracto_ocasional where veh_placa like ? or con_id like ? or con_contratante like ?";
        
        // consultando la cantidad de registros para reservar en memoria
        pstate = coneccion.prepareStatement("select count(*) as total from vw_extracto_ocasional where veh_placa like ? or con_id like ? or con_contratante like ?");

        pstate.setString(1, placa);
        pstate.setString(2, contrato);
        pstate.setString(3, persona);

        resultado = pstate.executeQuery();
        pstate.close();

        if(resultado.next()){
            cantidad = resultado.getInt(1);
        }
        resultado.close();

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

        pstate = coneccion.prepareStatement(consultar);
        
        pstate.setString(1, placa);
        pstate.setString(2, contrato);
        pstate.setString(3, persona);

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

        pstate.close();
        resultado.close();
        return datos;

    }

    // Metodos relacionados con contratos ocasionales
    public String[] consultar_uno_contrato_ocasional(int id)throws SQLException{
        dato = new String[7];
        consultar = "select * from contrato_ocasional where con_id = ?";

        pstate = coneccion.prepareStatement(consultar);
        
        pstate.setInt(1, id);

        resultado = pstate.executeQuery();
        if(resultado.next()){
            for(int i = 0; i < 7; i++){

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
        datos = new String[1][11];
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


        datos = new String[cantidad+1][11];
            
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
            i++;
        }

        return datos;
    }

    public void insertar_contrato_ocasional(int numero_contrato, String contratante, String fecha_inical, String fecha_final, int origen, int destino, double valor)throws SQLException{

        insertar = "insert into contrato_ocasional values (?,?,?,?,?,?,?);";

        pstate = coneccion.prepareStatement(insertar);

        pstate.setInt(1, numero_contrato);
        pstate.setString(2, contratante);
        pstate.setString(3, fecha_inical);
        pstate.setString(4, fecha_final);
        pstate.setInt(5, origen);
        pstate.setInt(6, destino);
        pstate.setDouble(7, valor);

        pstate.executeUpdate();
        pstate.close();


    }

    public void actualizar_contrato_ocasional(int numero_contrato, String contratante, String fecha_inical, String fecha_final, int origen, int destino, double valor)throws SQLException{

        actualizar = "update contrato_ocasional set con_contratante = ?, con_fecha_inicial = ?, con_fecha_final = ?, con_origen = ?, con_destino = ?, con_valor = ? where con_id = ?";

        pstate = coneccion.prepareStatement(actualizar);

        pstate.setString(1, contratante);
        pstate.setString(2, fecha_inical);
        pstate.setString(3, fecha_final);
        pstate.setInt(4, origen);
        pstate.setInt(5, destino);
        pstate.setDouble(6, valor);
        pstate.setInt(7, numero_contrato);

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

    public String[] consultar_uno_contrato_mensual(int contrato)throws SQLException{
        dato = new String[8];

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
        }
        return dato;
    }

    public String[][] consultar_contratos_mensuales(String buscar) throws SQLException{
        datos = new String[1][8];
        int cantidad = 0;
        int i = 1;

        
        consultar = "select * from vw_contrato_mensual where con_contratante like \'" + buscar + "%\' or con_id like \'%" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'";

        try{
            
            state = coneccion.createStatement();
            resultado = state.executeQuery("select count(*) as total from vw_contrato_mensual where con_contratante like \'" + buscar + "%\' or con_id like \'%" + buscar + "%\' or con_nombre like \'%" + buscar + "%\'");
            

            if(resultado.next()){
                cantidad = resultado.getInt(1);
            }


            datos = new String[cantidad+1][8];

            
            
            datos[0][0] = "N. CONTRATO";
            datos[0][1] = "ID CONTRATANTE";
            datos[0][2] = "TIPO ID CONT";
            datos[0][3] = "NOMBRE CONTRATANTE";
            datos[0][4] = "ID RESPONSABLE";
            datos[0][5] = "NOMBRE RESPONSABLE";
            datos[0][6] = "CELULAR RESPONSABLE";
            datos[0][7] = "DIRECCION RESPONSABLE";

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

    public void insertar_contrato_mensual(int id, String contratante)throws SQLException{

        insertar = "insert into contrato_mensual values (?,?)";

        pstate = coneccion.prepareStatement(insertar);

        pstate.setInt(1, id);
        pstate.setString(2, contratante);

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
    
    public String[][] consultar_contratante(String buscar) throws SQLException{

        datos = new String[1][7];
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

            datos = new String[cantidad+1][7];

            resultado = state.executeQuery(consultar);
            
            datos[0][0] = "ID CONTRATANTE";
            datos[0][1] = "TIPO ID CONT";
            datos[0][2] = "NOMBRE CONTRATANTE";
            datos[0][3] = "ID RESPONSABLE";
            datos[0][4] = "NOMBRE RESPONSABLE";
            datos[0][5] = "CELULAR RESPONSABLE";
            datos[0][6] = "DIRECCION RESPONSABLE";
            

            while(resultado.next()){

                datos[i][0] = resultado.getString(1);
                datos[i][1] = resultado.getString(2);
                datos[i][2] = resultado.getString(3);
                datos[i][3] = resultado.getString(4);
                datos[i][4] = resultado.getString(5);
                datos[i][5] = resultado.getString(6);
                datos[i][6] = resultado.getString(7);
                
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

    // Hacer un metodo para limpiar las tablas cuando las llenen con datos vacios
}
