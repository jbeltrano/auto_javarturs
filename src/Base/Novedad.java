package Base;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class Novedad extends Base{
    
    public Novedad() throws IOException, SQLException{
        super();
    }

    /**
     * Se encarga de insertar un tipo de novedad
     * a la base de datos mediante los parametros
     * {@code nombre} y {@code nombre_largo}
     * 
     * @param nombre pasa un nombre corto para registrar la novedad
     * es basicamente como una clase de codigo
     * para identificarla mas facilmente
     * @param nombre_largo es un nombre mas largo y especifico
     * que se utiliza para ver la novedad de una forma mas especifica
     * @throws SQLException
     */
    public void insertar_tipo_novedad(String nombre, String nombre_largo)throws SQLException{
        insertar = "insert into tipo_novedad(tin_nombre, tin_nombre2) values (?,?)";

        try{
            pstate = coneccion.prepareStatement(insertar);

            pstate.setString(1, nombre);
            pstate.setString(2, nombre_largo);

            pstate.executeUpdate();
        }catch(SQLException ex){
            throw ex;
        }finally{
            pstate.close();
        }
    }
    
    /**
     * Se encarga de insertar una novedad unicamente con el
     * nombre corto o nombre en clave del tipo de novedad
     * 
     * @param nombre 
     * Pasa el nombre corto del tipo de
     * novedad a insertar en la base de datos
     * @throws SQLException
     */
    public void insertar_tipo_novedad(String nombre)throws SQLException{
        insertar = "insert into tipo_novedad (tin_nombre) values (?)";

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
     * Se encarga de eliminar un registro de la
     * base de datos, el cual se pasa en el parametro
     * {@code id}
     * 
     * @param id Es el id o identificador
     * del registro a eliminar en la tabla
     * tipo de novedad en la base de datos
     * @throws SQLException
     */
    public void eliminar_tipo_novedad(int id)throws SQLException{
        borrar = "delete from tipo_novedad where tin_id = "+id;

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
     * Consulta y retorna un {@code String[][]} con
     * todos los registro de la tabla tipo_novedad 
     * en la base de datos.
     * 
     * @return
     * @throws SQLException
     */
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

            resultado.close();

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
        }finally{
            resultado.close();
            state.close();
        }

        return datos;

    }

    /**
     * Inserta una novedad relacionada a un empleado
     * en la base de datos.
     * 
     * @param persona Es el id de la persona
     * a la cual se le va a insertar la novedad.
     * 
     * @param tipo_novedad Es el id del tipo
     * de novedad que se va a relacionar al
     * empleado.
     * 
     * @param numeor_dias Es el numero o cantidad
     * de dias que le va a durar la novedad al
     * empleado.
     * 
     * @param fecha_inicial Es la fecha inicial
     * de la novedad.
     * 
     * @param fecha_final Es la fecha final de
     * la novedad.
     * 
     * @throws SQLException
     */
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
        }finally{
            pstate.close();
        }
    }

    /**
     * Se encarga de consultar todos los registros
     * que hay en la tabla novedad de la base de datos,
     * retornando un {@code String[][]} con dichos registros.
     * 
     * @return
     * @throws SQLException
     */
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

            resultado.close();

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
        }finally{
            resultado.close();
            state.close();
        }

        return datos;

    }

}
