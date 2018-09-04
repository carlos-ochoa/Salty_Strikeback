/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automata;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
        for(int i = 0; i<E.size();i++){
            aux = E.get(i);
            if(aux.size() == meter.size()){
                for(int j = 0; j<aux.size(); j++){
                    if(aux.get(j).getId() == meter.get(j).getId())
                        repetido = true;
                    else{
                        repetido = false;
                        j = aux.size()+2;
                    }  
                }
            }
            else{
                repetido = false;
            }
        }
        
        return repetido;
    }
    public boolean Verificar(ArrayList<Estado> aux, ArrayList<Estado> estadosfinales){
        boolean finales = true;
        //ArrayList <Estado> aux = new ArrayList<>();
        for(int i = 0; i<aux.size();i++){
            for(int j = 0; j < estadosfinales.size(); j++){
                if(aux.get(i).getId() == estadosfinales.get(j).getId()){
                    finales = true;
                    j = estadosfinales.size()+2;
                    i = aux.size()+2;
                }
                else
                    finales = false;
            }
        }
        
        return finales;
    }
    
    public void Transformar_AFN(AFN f){
        int id = 0; //Id del estado
        int contador = 0; //Contador
        boolean repetir;
        ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();    //Tabla de estados del AFD
        ArrayList<Integer> destino = new ArrayList<>();
        //{{S0->a,b,c,d}{S1->a,b,c,d}{S2->a,b,c,d}...}, o tambien se ve como
        //{{fila 1}{fila 2}{fila 3}}
        ArrayList<ArrayList<Estado>> E = new ArrayList<>();         //Conjunto de estados del AFD
        ArrayList<Estado> meter = new ArrayList<>();    //Conjunto auxiliar para meter conjuntos a E
        ArrayList<Estado> aux = new ArrayList<>();      //Conjunto auxiliar para el metodo Ir_A
        ArrayList<Estado> copia = new ArrayList<>();    //Conjunto auxiliar para identificar copias
        Queue<ArrayList<Estado>> cola = new LinkedList<>();     //Creamos la cola que contiene a los estados a analizar
        meter = f.Cerradura_E(f.estadoInicial);   //Obtenemos el estado S0 (C_E del Est. Inicial)
        E.add(meter);     //Ponemos el Estado en el conjunto  final
        cola.add(meter); //Agregamos el estado en la cola para analizarlo
        System.out.println("jajas");
        //Mientras los estados no acaben de ser revisados
        while(!cola.isEmpty()){
            System.out.println("hola"+E);
            aux = cola.poll();  //Sacamos un estado
            //Para cada simbolo del alfabeto
            for(char c : f.getAlfabeto()){
                meter = f.Ir_A(aux, c); //Obtenemos Ir_A del conjunto de un elemento de la cola
                //Si el elemento no esta en el conjunto E
                if(!meter.isEmpty()){
                    if((repetir = Repetidos(E, meter)) == false){
                        cola.add(meter);    //Metemos el nuevo estado en la cola para analizarlo
                        E.add(meter);       //y al conjunto E
                        id++;               //Ponemos el id en el siguiente nuevo 0->1, 1->2 ...
                        destino.add(id-1,id);    //Ponemos el id del estado en el caracter correspondiente
                    }
                    else{
                        //Si el elemento ya existe vamos a buscarlo para saber su id
                        for(contador = 0; contador<E.size(); contador++){
                            copia = E.get(contador);  //Obtenemos el Estado n
                            //Si es igual al que estamos buscando
                            if(meter == copia){
                                destino.add(contador+1);    //Ponemos el id del estado en el caracter correspondiente
                            }
                        }
                    }
                }
            }
            //Verificamos si el estado contiene el estado final
            if(Verificar(aux, f.estadosFinales) == true){
                //Si lo tiene, agregamos 1
                destino.add(1);
            }
            else{
                //Caso contrario, agregamos -1
                destino.add(-1);
            }
            tabla.add(destino); //Agregamos la primera fila a la tabla
            destino.clear();    //Limpiamos la fila
        }
        //ta
        System.out.println("antes");
        System.out.println(destino);
        return;
    }
    
}
