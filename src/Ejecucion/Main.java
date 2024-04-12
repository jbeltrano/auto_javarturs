package Ejecucion;

import Front.Principal;

public class Main {
    
    public static void main(String[] args) {


        new Principal("src\\DB\\base.db");
        try{
            System.out.println(getDigitoDian("1111111"));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }

    public static int getDigitoDian(String nitParam) throws Exception
    {   
        int digitos_especificos[] = {3,7,13,17,19,23,29,37,41,43,47,53,59,67,71};
        if( nitParam == null || ( nitParam.trim() ).isEmpty() )
        {
            throw new Exception("La cadena del NIT es nula o vacía.");
        }
         
        nitParam = nitParam.trim();
         
        int indiceRaya = nitParam.indexOf("-");
         
        String nitInterno = indiceRaya > 0 ? nitParam.substring(0, indiceRaya): nitParam;
         
 
        String nitVector[] = nitInterno.split("(?<=.)");
 
        int valorCalculado = 0;
        int aux = nitVector.length -1;
 
        for( int i = 0; i < nitVector.length; i++ )
        {

            valorCalculado += digitos_especificos[i] * Integer.parseInt(nitVector[aux-i]);
            

        }
         
        int modulo = valorCalculado % 11;
         
        if( modulo >= 2 )
        {
            modulo = 11 - modulo;
        }
         
        return modulo;
    }

}
