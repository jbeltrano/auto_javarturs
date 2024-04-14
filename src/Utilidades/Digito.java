package Utilidades;

public class Digito {

    public static int get_digito_nit(String nitParam)
    {   
        int digitos_especificos[] = {3,7,13,17,19,23,29,37,41,43,47,53,59,67,71};
         
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

