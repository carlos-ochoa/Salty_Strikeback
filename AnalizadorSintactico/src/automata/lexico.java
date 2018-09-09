/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automata;

import java.util.ArrayList;

/**
 *
 * @author Erik
 */
public class lexico {
    public lexico(){
        
    }
    public char identificar(char letra){
        char identificador = 0;
        ArrayList<Character> sim_mat = new ArrayList<>();
        sim_mat.add('+');
        sim_mat.add('-');
        sim_mat.add('*');
        sim_mat.add('/');
        sim_mat.add('(');
        sim_mat.add(')');
        sim_mat.add('.');
        
        if(letra>47 && letra<58){
            identificador = 'D';
        }
        else if((letra>64 && letra<91)||(letra>96 && letra<123)){
            identificador = 'L';
        }
        else if(sim_mat.contains(letra)){
            identificador = letra;
        }
        else if(letra == ' '){
            identificador = 'S';
        }
        else
            System.out.println("No se encuentra la letra en el alfabeto");
        return identificador;
    }
    
    public void analisis(ArrayList<ArrayList<Integer>> tabla, String cadena, ArrayList<Character> alfabeto){
        //boolean actual = true;  //Bandera para saber si existe un estado para el caracter analizado
        int fila = 0;       //Numero de la fila en la que estamos
        int columna = 0;    //Numero de la columna en la que estamos
        //int estado = -1;    //Entero para almacenar los estados
        int contador = 0;   //Auxiliares de contadores
        int aceptacion = -1; //Entero que guarda el token
        char identificador = 0; //Letra del identificador
        ArrayList<Integer> auxfila = new ArrayList<>();    //Array para obtener la fila de la tabla
        ArrayList lexema = new ArrayList<>();    //Array para mostrar el lexema actual
        
        System.out.println("Tabla "+tabla);
        System.out.println("String "+cadena);
        System.out.println("Alfabeto "+alfabeto);
        /*for(ArrayList<Integer> k:tabla){
            System.out.println("fila "+k);
        }*/
        
        //Mientras no acabemos de analizar la cadena
        for(int i = 0; i<cadena.length(); i++){
            //Vemos si podemos procesar la cadena
            identificador = identificar(cadena.charAt(i));
            //System.out.println("identificador "+identificador);
            if(alfabeto.contains(identificador)){
                //En caso de que si podemos, obtenemos el estado al que tenemos que ir
                for(contador = 0; contador<alfabeto.size(); contador++){
                    if(identificador == alfabeto.get(contador)){
                        columna = contador;
                        contador = alfabeto.size()+1;
                    }
                }
                //Una vez obtenido la columna obtenemos la fila a la que ir
                auxfila = tabla.get(fila);
                if(auxfila.get(columna) != -1){
                    //Si existe la transicion
                    fila = auxfila.get(columna);    //Obtenemos la fila
                    aceptacion = auxfila.get(auxfila.size()-1); //Obtenemos el token
                    lexema.add(cadena.charAt(i));   //Y ponemos el caracter en el lexema
                    
                }
                else if(fila == 0 && auxfila.get(columna)==-1){
                    //Si la cadena tiene un error
                    System.out.println("Caracter "+identificador);       //Imprimimos el lexema
                    System.out.println("Token "+aceptacion);    //Imprimimos el token
                    //lexema = new ArrayList<>();    //Reiniciamos el lexema
                }
                else{
                    //En caso de no existir
                    fila = 0;   //Fila en cero porque nos vamos al EdoInicial
                    System.out.println("Lexema "+lexema);       //Imprimimos el lexema
                    System.out.println("Token "+aceptacion);    //Imprimimos el token
                    i--;    //Retrocedemos para no saltarnos ese caracter
                    lexema = new ArrayList<>();    //Reiniciamos el lexema
                }
            }
            else{
                //Si no existe el caracter salimos del proceso porque no se puede analizar
                System.out.println("No se puede procesar tu cadena");
                i = cadena.length()+1;
            }
        }
        //Al final imprimimos el ultimo lexema
        if(fila != 0){
            System.out.println("Lexema "+lexema);       //Imprimimos el lexema
            System.out.println("Token "+aceptacion);    //Imprimimos el token
        }
        
    }
    
}
