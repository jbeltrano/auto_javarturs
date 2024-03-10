package Utilidades;

public class Capitalizar_Strings {

    public static String capitalizarNombre(String nombre) {
        String[] palabras = nombre.toLowerCase().split(" ");
        StringBuilder resultado = new StringBuilder();
        
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        
        return resultado.toString().trim();
    }

    public static String capitalizar_primera(String str){
        String[] palabras = str.toLowerCase().split(" ");
        StringBuilder resultado = new StringBuilder();
        
        if (!palabras[0].isEmpty()) {
            resultado.append(Character.toUpperCase(palabras[0].charAt(0)))
                    .append(palabras[0].substring(1))
                    .append(" ");
        }
        
        for(int i = 1; i < palabras.length; i++){
            resultado.append(palabras[i].toUpperCase())
            .append(" ");
        }
        
        return resultado.toString().trim();
    }

}
