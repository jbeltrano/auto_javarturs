package Utilidades;

public class Convertir_numero_texto {
    
    // Atributos especiales para hacer conversiones de numeros a nuemeros en letras
    private static final String[] UNIDADES = {"", "un", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private static final String[] DECENAS = {"", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
    private static final String[] CENTENAS = {"", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};

    /**
     * Convierte un numero entero a texto en un
     * String que lo describe en plabras
     * @param numero
     * @return
     */
    public static String convertir_numero_texto(int numero){
        if (numero == 0) {
            return "cero";
        }

        String texto = "";

        // Procesar millones
        int millones = numero / 1000000;
        if (millones > 0) {

            if( millones == 1){
                texto += "un millón ";
            }
            else{
                texto += convertir_numero_texto(millones) + " millones ";
            }
            
            numero %= 1000000;
            if(numero == 0){
                texto += "de ";
            }
        }

        // Procesar miles
        int miles = numero / 1000;
        if (miles > 0) {
            texto += convertir_numero_texto(miles) + " mil ";
            numero %= 1000;
        }

        // Procesar centenas
        int centenas = numero / 100;
        if (centenas > 0) {
            if (centenas == 1 && numero % 100 != 0) {
                texto += "ciento ";
            } else if(centenas ==1 && numero % 100 == 0){
                texto += "cien";
            }
            else{
                texto += CENTENAS[centenas] + " ";
            }
            numero %= 100;
        }

        // Procesar decenas y unidades
        if (numero > 0) {
            if (numero < 10) {
                texto += UNIDADES[numero];
            } else if(numero < 30 && numero >20){

                if( numero == 20){
                    texto += "veinte";
                }else{
                    texto += "veinti" + UNIDADES[numero%10];
                }
            } else if (numero < 20) {
                if (numero == 10) {
                    texto += "diez";
                } else {
                    texto += "dieci" + UNIDADES[numero % 10];
                }
            } else {
                int decena = numero / 10;
                texto += DECENAS[decena];
                int unidad = numero % 10;
                if (unidad > 0) {
                    texto += " y " + UNIDADES[unidad];
                }
            }
        }

        
        return texto.trim();
    }
}