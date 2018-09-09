/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.tabla;

/**
 *
 * @author Erik
 */
public class AFD {
    protected ArrayList<ArrayList<Estado>> estados;
    protected ArrayList<Character> alfabeto;
    protected ArrayList<Estado> estadoInicial;
    protected ArrayList<Estado> estadosFinales;
    protected ArrayList<Transicion> transiciones;
    
    public AFD(ArrayList<ArrayList<Estado>> estados, ArrayList<Character> alfabeto, ArrayList<Estado> estadoInicial, ArrayList<Estado> estadosFinales) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
    }
    public AFD() {
        
    }

    public ArrayList<ArrayList<Estado>> getEstados() {
        return estados;
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }

    public ArrayList<Estado> getEstadoInicial() {
        return estadoInicial;
    }

    public ArrayList<Estado> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstados(ArrayList<ArrayList<Estado>>  estados) {
        this.estados = estados;
    }

    public void setAlfabeto(ArrayList<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setEstadoInicial(ArrayList<Estado> estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void setEstadosFinales(ArrayList<Estado> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }
    
    public boolean Repetidos(ArrayList<ArrayList<Estado>> E, ArrayList<Estado> meter){
        boolean repetido = true;
        ArrayList <Estado> aux = new ArrayList<>();
        //System.out.println("En repetir");
        for(int i = 0; i<E.size();i++){
            aux = E.get(i);
            //System.out.println("aux "+ i + " " + aux);
            //System.out.println("meter "+meter);
            if(aux.size() == meter.size()){
                for(int j = 0; j<aux.size(); j++){
                    //System.out.println("aux ID "+ aux.get(j).getId() + " meter ID " + meter.get(j).getId());
                    if(aux.get(j).getId() == meter.get(j).getId())
                        repetido = true;
                    else{
                        repetido = false;
                        j = aux.size()+2;
                    }
                    //System.out.println("repetido " + repetido);
                }
                //Si encontramos un conjunto que si existe saliemos del metodo
                if(repetido == true)
                    i = E.size()+2;
            }
            else{
                repetido = false;
            }
        }
        return repetido;
    }
    
    public int Lugar(ArrayList<ArrayList<Estado>> E, ArrayList<Estado> meter){
        boolean repetido = true;
        int lugar = 0;
        ArrayList <Estado> aux = new ArrayList<>();
        for(int i = 0; i<E.size();i++){
            aux = E.get(i);
            //Verificamos si son del mismo tamaño
            if(aux.size() == meter.size()){
                //Para cada conjunto de estados de E verificamos que no sea el conjunto a meter
                for(int j = 0; j<aux.size(); j++){
                    //System.out.println("aux ID "+ aux.get(j).getId() + " meter ID " + meter.get(j).getId());
                    //Si son los ID iguales 1:1 entonces sabremos que es repetido
                    if(aux.get(j).getId() == meter.get(j).getId())
                        repetido = true;
                    else{
                        repetido = false;
                        j = aux.size()+2;
                    }
                }
                //Si encontramos el conjunto repetido, obtenemos su ID y nos salimos del metodo
                if(repetido == true){
                    lugar = i;
                    i = E.size()+2;
                }
                    
            }
            else{
                //Si no es del mismo tamaño sabemos que no son iguales
                repetido = false;
            }
        }
        return lugar;
    }
    
    public boolean Verificar(ArrayList<Estado> aux, ArrayList<Estado> estadosfinales){
        boolean finales = true; //Bandera que verifica los estados finales en un conjunto de estados
        //ArrayList <Estado> aux = new ArrayList<>();
        //Verificamos cada uno de los estados con los estados finales
        for(int i = 0; i<aux.size();i++){
            for(int j = 0; j < estadosfinales.size(); j++){
                //Si existe uno
                if(aux.get(i).getId() == estadosfinales.get(j).getId()){
                    finales = true; //Ponemos la bandera en true y salimos del metodo
                    j = estadosfinales.size()+2;
                    i = aux.size()+2;
                }
                else
                    finales = false;
            }
        }
        
        return finales;
    }
    
    public int ObtenerToken(ArrayList<Estado> aux, ArrayList<Estado> estadosfinales, ArrayList<Integer> tokens){
        int token = 0;
        //ArrayList <Estado> aux = new ArrayList<>();
        //Verificamos cada uno de los estados con los estados finales
        for(int i = 0; i<aux.size();i++){
            for(int j = 0; j < estadosfinales.size(); j++){
                //Si existe uno
                if(aux.get(i).getId() == estadosfinales.get(j).getId()){
                    token = tokens.get(j); //Ponemos el token correspondiente
                    j = estadosfinales.size()+2;
                    i = aux.size()+2;
                }
            }
        }
        
        return token;
    }
    
    public void EscribirArchivo( ArrayList<ArrayList<Integer>> tabla, int automata){
        String nombre = "Tabla_AFD"+automata+".txt";          //Variable para el nombre del txt
        PrintWriter salida = null;     //Variable para hacer el archivo de la tabla
        
        //Creamos el archivo
        try {
            salida = new PrintWriter(nombre);
        } catch (Exception e) {
            //En caso de no poderse, se indica
            System.out.println("Error al crear el archivo");
        }
        
        for(int i = 0; i<tabla.size(); i++){
            salida.println(tabla.get(i));
        }
        salida.close();
    }
    
    public ArrayList<ArrayList<Integer>> LeerTabla (String nombre){
        ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();
        File archivo = new File("F:\\AnalizadorSintactico\\"+nombre);
        Scanner lector = null;
        ArrayList<Integer> numeros = new ArrayList<>();
        String[] transformar;
        String linea;
        ArrayList<Integer> fila = new ArrayList<>();
        
        try {
            lector = new Scanner(archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mientras no acabemos de leer todas las lineas
        while (lector.hasNextLine()) {
            linea = lector.nextLine();  //Leemos la linea
            //Quitamos todos los caracteres del string que no nos sirven
            linea = linea.replace(',', ' ');
            linea = linea.replace('[', ' ');
            linea = linea.replace(']', ' ');
            transformar = linea.split("\\s+");  //Separamos todos los numeros para meterlos a la fila
            //System.out.println("linea "+linea);
            for(int j=1; j<transformar.length; j++){
                fila.add(Integer.parseInt(transformar[j]));
                //System.out.println("linea "+transformar[j]);
            }
            tabla.add(fila);
            fila = new ArrayList<>();
        }
        //System.out.println("Tabla "+tabla);
        return tabla;
    }
    
    public ArrayList<ArrayList<Integer>> Transformar_AFN(AFN f){
        int id = 0; //Id del estado
        int lugar = 0; //Entero para obtener la transicion de los repetidos
        int contador = 0; //Entero que nos ayuda a poner -1 en la tabla
        boolean repetir;
        ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();    //Tabla de estados del AFD
        ArrayList<Integer> destino = new ArrayList<>();
        //{{S0->a,b,c,d}{S1->a,b,c,d}{S2->a,b,c,d}...}, o tambien se ve como
        //{{fila 1}{fila 2}{fila 3}}
        ArrayList<ArrayList<Estado>> E = new ArrayList<>();         //Conjunto de estados del AFD
        ArrayList<Estado> meter = new ArrayList<>();    //Conjunto auxiliar para meter conjuntos a E
        ArrayList<Estado> aux = new ArrayList<>();      //Conjunto auxiliar para el metodo Ir_A
        Queue<ArrayList<Estado>> cola = new LinkedList<>();     //Creamos la cola que contiene a los estados a analizar
        
        if(f.tokens.isEmpty()){
            f.calcularToken();
        }
        meter = f.Cerradura_E(f.estadoInicial);   //Obtenemos el estado S0 (C_E del Est. Inicial)
        E.add(meter);     //Ponemos el Estado en el conjunto  final
        cola.add(meter); //Agregamos el estado en la cola para analizarlo
        
        //Mientras los estados no acaben de ser revisados
        while(!cola.isEmpty()){
            System.out.println("hola "+E);
            System.out.println("cola "+cola);
            aux = cola.poll();  //Sacamos un estado
            contador = 0;
            //Para cada simbolo del alfabeto
            for(char c : f.getAlfabeto()){
                meter = f.Ir_A(aux, c); //Obtenemos Ir_A del conjunto de un elemento de la cola
                //Si el elemento no esta en el conjunto E
                //System.out.println("meter "+meter);
                
                if(!meter.isEmpty()){
                    repetir = Repetidos(E, meter);
                    //System.out.println("repetir "+repetir);
                    if(repetir == false){
                        cola.add(meter);    //Metemos el nuevo estado en la cola para analizarlo
                        E.add(meter);       //y al conjunto E
                        id++;               //Ponemos el id en el siguiente nuevo 0->1, 1->2 ...
                        //System.out.println("ID "+id);
                        destino.add(id-1,id);    //Ponemos el id del estado en el caracter correspondiente
                        //System.out.println("Destino "+destino);
                    }
                    else{
                        //Si el elemento ya existe vamos a buscarlo para saber su id
                        lugar = Lugar(E, meter);
                        destino.add(lugar);    //Ponemos el id del estado en el caracter correspondiente
                        //System.out.println("Destino "+destino);
                    }
                }
                else if(contador < f.getAlfabeto().size())
                    destino.add(-1); 
                contador++;
            }
            
            //Verificamos si el estado contiene el estado final
            if(Verificar(aux, f.estadosFinales) == true){
                //Si lo tiene, agregamos 1
                destino.add(ObtenerToken(aux, f.estadosFinales, f.tokens));
                //destino.add(1);
                tabla.add(destino); //Agregamos la primera fila a la tabla
            }
            else{
                //Caso contrario, agregamos -1
                destino.add(-1);
                tabla.add(destino); //Agregamos la primera fila a la tabla
            }
            //System.out.println("Destino "+destino);
            //System.out.println("Antes "+tabla);
            destino = new ArrayList<>();
            //System.out.println("Cola "+cola);
            //System.out.println("Tabla "+tabla);
            
        }
        System.out.println(tabla);
        
        return tabla;
    }
    
}
