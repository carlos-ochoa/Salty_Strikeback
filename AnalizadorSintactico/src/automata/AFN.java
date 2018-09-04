/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automata;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Charly
 */
public class AFN {
    protected ArrayList<Estado> estados;
    protected ArrayList<Character> alfabeto;
    protected Estado estadoInicial;
    protected ArrayList<Estado> estadosFinales;
    protected ArrayList<Transicion> transiciones;

    public AFN(ArrayList<Estado> estados, ArrayList<Character> alfabeto, Estado estadoInicial, ArrayList<Estado> estadosFinales) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }

    @Override
    public String toString() {
        /*for(Estado e : estados){
            this.transiciones.addAll(e.transiciones);
        }*/
        return "AFN{" + "estados=" + estados + ", alfabeto=" + alfabeto + ", estadoInicial=" + estadoInicial + ", estadosFinales=" + estadosFinales + '}';
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public ArrayList<Estado> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public void setAlfabeto(ArrayList<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void setEstadosFinales(ArrayList<Estado> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }
    
    public AFN unirAEstadoBasico(ArrayList<AFN> afn, int id){
        Estado e; //Instanciamos al nuevo estado del afn
        ArrayList<Transicion> at = new ArrayList<>(); 
        e = new Estado(id,false,at);
        //Añadimos al nuevo estado al conjunto de estados del autómata
        this.estados.add(e);
        //Para cada autómata recibido...
        for(AFN a : afn){
            //Añadiremos una transición Epsilon a el estado inicial del afn recorrido
            e.getTransiciones().add(new Transicion(a.getEstadoInicial()));
            //Añadimos todos sus estados al autómata actual
            this.estados.addAll(a.getEstados());
            //Añadir alfabeto luego del afn analizado al nuevo autómata creado
            for(char c : a.getAlfabeto()){
                if(!this.alfabeto.contains(c))
                    this.alfabeto.add(c);
            }
            this.estadosFinales.addAll(a.getEstadosFinales());
        }
        this.estadoInicial = e;
        return this;
    }
    
    public AFN unirAEstadoBasico(AFN afn){
        //Si queremos añadir sólo un autómata más al actual hacemos lo mismo que el
        //método anterior pero sólo una vez jaja
        this.getEstadoInicial().getTransiciones().add(new Transicion(afn.getEstadoInicial()));
        for(char c : afn.getAlfabeto()){
                if(!this.alfabeto.contains(c))
                    this.alfabeto.add(c);
            }
        this.estados.addAll(afn.getEstados());
        this.estadosFinales.addAll(afn.getEstadosFinales());
        return this;
    }
    
    public AFN crearAFNBasico(char s, int id){
        AFN f; //Creamos una instancia de la clase AFN
        Estado e1, e2; //Estados que conforman el nuevo autómata
        Transicion t;
        ArrayList <Transicion> at =  new ArrayList <>();
        ArrayList <Character> ac = new ArrayList <>();
        ArrayList <Estado> ef = new ArrayList <>();
        ArrayList <Estado> es = new ArrayList<>();
        Estado ei = new Estado();
        //Inicializamos las instancias de cada clase
        f = new AFN(es,ac,ei,ef);
        e1 = new Estado(id,false,at);
        e2 = new Estado();
        t = new Transicion(s,e2);
        //Al estado uno le añadimos la transición a e2 con el caracter s
        e1.transiciones.add(t);
        //Establecemos a e2 como el estado final
        e2.setEsFinal(true);
        e2.setId(id+1);
        //Añadimos los estados al conjunto de estados del automata
        f.estados.add(e1);
        f.estados.add(e2);
        //Establecemos al estado e1 como el inicial del nuevo autómata
        f.estadoInicial = e1;
        //Añadimos al alfabeto del nuevo autómata el caracter s
        f.alfabeto.add(s);
        //FInalmente en la colección de estados finales colocamos a e2
        f.estadosFinales.add(e2);
        return f;
    }
    
    public AFN unir(AFN f2, int id){
        Estado e1,e2;
        ArrayList <Transicion> at =  new ArrayList <Transicion>();
        ArrayList <Character> ac = new ArrayList <Character>();
        ArrayList <Estado> ef = new ArrayList <Estado>();
        //Creamos los dos estados que nos permitirán unir al nuevo autómata
        e1 = new Estado(id, false, at);
        e2 = new Estado();
        //Establecemos al estado e2 como el final
        e2.setEsFinal(true);
        e2.setId(id + 1);
        //Creamos una transición de e1 al estado inicial del autómata actual
        e1.transiciones.add(new Transicion(this.estadoInicial));
        //Creamos otra transición de e2 al estado inicial del segundo autómata
        e1.transiciones.add(new Transicion(f2.getEstadoInicial()));
        //Para cada estado final en el autómata actual le añadimos una trancisión a e2
        System.out.println(this.getEstados().toString());
        for (Estado e : this.estadosFinales){
            e.transiciones.add(new Transicion(e2));
            e.esFinal = false;
        }
        //Hacemos lo mismo con los estados finales del segundo autómata
        for (Estado e : f2.estadosFinales){
            e.transiciones.add(new Transicion(e2));
            e.esFinal = false;
        }
        //Agregamos los nuevos estados a la coleccion de estados del automata
        for(Estado e : f2.getEstados()){
            this.estados.add(e);
        }
        this.estados.add(e1);
        this.estados.add(e2);
        //Eliminamos los estados finales del autómata actual
        this.estadosFinales.clear();
        //Establecemos a e2 como el nuevo estado inicial
        this.estadoInicial = e1;
        //Añadimos a e2 como nuevo estado final
        this.estadosFinales.add(e2);
        //Al alfabeto del nuevo autómata le añadimos el del número 2
        this.alfabeto.addAll(f2.getAlfabeto());
        System.out.println(e1.toString());
        System.out.println(e2.toString());
        return this;
    }
    
    public AFN concatenar(AFN f2, int id){
        /*
            Para cada estado en los finales del segundo autómata
            les retiramos el atributo de aceptación y para cada transición 
            en el estado inicial las añadimos a los finales del autómata
        */
        for (Estado e : this.estadosFinales){
            e.setEsFinal(false);
            for(Transicion t : f2.getEstadoInicial().getTransiciones())
                e.getTransiciones().add(t);
        }
        for(Estado e : f2.getEstados()){
            if(f2.getEstadoInicial() == e)
                continue;
            this.estados.add(e);
        }
        //Añadimos al alfabeto del autómata actual el de f2
        this.alfabeto.addAll(f2.getAlfabeto());
        this.estadosFinales.clear();
        //Hacemos lo mismo con los estados finales
        this.estadosFinales.addAll(f2.getEstadosFinales());
        //Borramos el segundo autómata
        f2 = null;
        return this;
    }
    
    public AFN cerraduraKleene(int id){
        //Creamos los dos estado auxiliares
        Estado e1,e2;
        ArrayList <Transicion> at =  new ArrayList <>();
        e1 = new Estado(id, false, at);
        e2 = new Estado();
        e2.setEsFinal(true);
        e2.setId(id + 1);
        /*
            Para cada estado final del autómata actual le añadimos
            transiciones epsilon hacia el estado e2 y al estado inicial
            y luego les quitamos el estatus de final
        */
        for (Estado e : this.estadosFinales){
            e.transiciones.add(new Transicion(e2));
            e.transiciones.add(new Transicion(this.estadoInicial));
            e.esFinal = false;
        }
        //Añadimos nuevas transiciones epsilon al estado inicial desde e1
        e1.transiciones.add(new Transicion(this.estadoInicial));
        //Hacemos lo mismo hacia e2
        e1.transiciones.add(new Transicion(e2));
        //Agregamos los nuevos estados a la coleccion de estados del automata
        this.estados.add(e1);
        this.estados.add(e2);
        //Añadimos a la colección de estados finales a e2
        this.estadosFinales.clear();
        this.estadosFinales.add(e2);
        //Establecemos a e1 como el inicial del autómata
        this.estadoInicial = e1;
        System.out.println(this.estadoInicial);
        return this;
    }
         
    public AFN cerraduraPositiva(int id){
        //Creamos los dos estado auxiliares
        Estado e1,e2;
        ArrayList <Transicion> at =  new ArrayList <>();
        e1 = new Estado(id, false, at);
        e2 = new Estado();
        e2.setEsFinal(true);
        e2.setId(id + 1);
        /*
            Para cada estado final del autómata actual le añadimos
            transiciones epsilon hacia el estado e2 y al estado inicial
            y luego les quitamos el estatus de final
        */
        for (Estado e : this.estadosFinales){
            e.transiciones.add(new Transicion(e2));
            e.transiciones.add(new Transicion(this.estadoInicial));
            e.esFinal = false;
        }
        //Añadimos nuevas transiciones epsilon al estado inicial desde e1
        e1.transiciones.add(new Transicion(this.estadoInicial));
        //Agregamos los nuevos estados a la coleccion de estados del automata
        this.estados.add(e1);
        this.estados.add(e2);
        //Añadimos a la colección de estados finales a e2
        this.estadosFinales.clear();
        this.estadosFinales.add(e2);
        //Establecemos a e1 como el inicial del autómata
        this.estadoInicial = e1;
        System.out.println(this.estadoInicial);
        return this;
    }
    
    public AFN interrogacion(int id){
        //Creamos los dos estado auxiliares
        Estado e1,e2;
        ArrayList <Transicion> at =  new ArrayList <>();
        e1 = new Estado(id, false, at);
        e2 = new Estado();
        e2.setEsFinal(true);
        e2.setId(id + 1);
        /*
            Para cada estado final del autómata actual le añadimos
            transiciones epsilon hacia el estado e2 y al estado inicial
            y luego les quitamos el estatus de final
        */
        for (Estado e : this.estadosFinales){
            e.transiciones.add(new Transicion(e2));
            e.esFinal = false;
        }
        //Añadimos nuevas transiciones epsilon al estado inicial desde e1
        e1.transiciones.add(new Transicion(this.estadoInicial));
        //Hacemos lo mismo hacia e2
        e1.transiciones.add(new Transicion(e2));
        //Agregamos los nuevos estados a la coleccion de estados del automata
        this.estados.add(e1);
        this.estados.add(e2);
        //Añadimos a la colección de estados finales a e2
        this.estadosFinales.clear();
        this.estadosFinales.add(e2);
        //Establecemos a e1 como el inicial del autómata
        this.estadoInicial = e1;
        System.out.println(this.estadoInicial);
        return this;
    }
    
    public ArrayList<Estado> Cerradura_E(Estado e){
        ArrayList<Estado> aux = new ArrayList<>(); //Conjunto de estados a regresar
        Estado a, rep;   //Estado que guarda los estados sacados de la pila
        boolean repetido = false;   //Bandera para verificar repetidos
        Stack<Estado> pila = new Stack<>(); //Creamos la pila
        pila.push(e);   //Metemos el Estado de entrada a la pila
        //Mientras que la pila no esta vacia
        while(!pila.empty()){
            //Sacamos un elemento de la pila
            a = pila.pop();
            //Si este no se encuentra en el conjunto resultado
            for(int i = 0; i<aux.size();i++){
                rep = aux.get(i);
                if(a.getId() == rep.getId())
                  repetido = true;  
            }
            if(repetido == false){
                //Lo añadimos a este
                aux.add(a);
            }
            //Para cada transicion t del estado a verificamos
            for(Transicion t : a.getTransiciones()){
                //Si su simbolo es Epsilon, metemos el estado a la pila
                if(t.simbolo == '☼'){
                    pila.push(t.getDestino());
                }
            }  
        }
        return aux;
    }
    
    public ArrayList<Estado> Cerradura_E(ArrayList<Estado> e){
        ArrayList<Estado> R = new ArrayList<>();    //Conjunto de estados a regresar
        ArrayList<Estado> aux = new ArrayList<>();  //Conjunto de estados a regresar
        Estado rep, auxiliar;                     //Estado para verificar repeticiones
        int cambio = 1;                 //Bandera para verificar la burbuja
        boolean repetido = false;       //Bandera para indicar repeticiones
        //Limpiamos los arreglos
        aux.clear();
        R.clear();
        //Para cada estado a en el conjunto e de estados
        for(Estado a: e){
            aux = Cerradura_E(a);   //Hacemos la cerradura Epsilon
            //Verificamos que que no existan elementos repetidos
            for(int i = 0; i<aux.size();i++){
                rep = aux.get(i);
                for(int j = 0; j<R.size();j++){
                    //Si este se encuentra en el conjunto resultado marcamos la bandera
                    if(R.get(j).getId() == rep.getId()){
                        repetido = true;
                        j = R.size()+2;
                    }
                }
                //Si no esta repetido lo añadimos
                if(repetido == false){
                    R.add(rep);
                }
                repetido = false;   //Reiniciamos la bandera
            }
        }
        //Ordenamos el arreglo
        for(int cont2 = 0; cont2 < R.size()-1 && cambio != 0; cont2++){	
            cambio = 0; //Se hace la revision del metodo burbuja.
            for(int cont = 0; cont < R.size()-1-cont2; cont++)	//Recorre todo el arreglo.
            {
                    if(R.get(cont).getId() > R.get(cont+1).getId() )	//Si el numero es mayor al siguiente se cambia
                    {
                            auxiliar = R.get(cont+1);
                            R.set(cont+1,R.get(cont));
                            R.set(cont,auxiliar);
                            cambio = 1;
                    }
            }
	}
        //System.out.println(R);
        return R;
    }
    
    public ArrayList<Estado> Mover(Estado e, char s){
        ArrayList<Estado> R = new ArrayList<>(); //Conjunto de estados a regresar
        R.clear(); //Limpiamos el arreglo
        //Para cada transicion del estado e verificamos
        for(Transicion t:e.getTransiciones()){
            //si el simbolo es igual al de entrada
            if(t.simbolo == s){
                //si lo es, añadimos el estado R al conjunto resultado
                R.add(t.destino);
            }
        }
        //System.out.println(R);
        return R;
    }
    
    public ArrayList<Estado> Mover(ArrayList<Estado> e, char s){
        ArrayList<Estado> R = new ArrayList<>();    //Conjunto de estados a regresar
        ArrayList<Estado> aux = new ArrayList<>();  //Conjunto de estados a regresar
        Estado rep, auxiliar;                     //Estado para verificar repeticiones
        int cambio = 1;                 //Bandera para verificar la burbuja
        boolean repetido = false;       //Bandera para indicar repeticiones
        
        //Limpiamos los arreglos
        aux.clear();
        R.clear();
        //Para cada estado a en el conjunto e de estados:
        for(Estado a:e){
            aux = Mover(a, s);
            //Verificamos que que no existan elementos repetidos
            for(int i = 0; i<aux.size();i++){
                rep = aux.get(i);
                for(int j = 0; j<R.size();j++){
                    //Si este se encuentra en el conjunto resultado marcamos la bandera
                    if(R.get(j).getId() == rep.getId()){
                        repetido = true;
                        j = R.size()+2;
                    }
                }
                //Si no esta repetido lo añadimos
                if(repetido == false){
                    R.add(rep);
                }
                repetido = false;   //Reiniciamos la bandera
            }
        }
        //Ordenamos el arreglo
        for(int cont2 = 0; cont2 < R.size()-1 && cambio != 0; cont2++){	
            cambio = 0; //Se hace la revision del metodo burbuja.
            for(int cont = 0; cont < R.size()-1-cont2; cont++)	//Recorre todo el arreglo.
            {
                    if(R.get(cont).getId() > R.get(cont+1).getId() )	//Si el numero es mayor al siguiente se cambia
                    {
                            auxiliar = R.get(cont+1);
                            R.set(cont+1,R.get(cont));
                            R.set(cont,auxiliar);
                            cambio = 1;
                    }
            }
	}
        //System.out.println(R);
        return R;
    }
    
    public ArrayList<Estado> Ir_A(Estado e, char s){
        return Cerradura_E(Mover(e, s));
    }
    
    public ArrayList<Estado> Ir_A(ArrayList<Estado> e, char s){
        ArrayList<Estado> R = new ArrayList<>();    //Conjunto de estados a regresar
        ArrayList<Estado> aux = new ArrayList<>();  //Conjunto de estados a regresar
        Estado rep, auxiliar;                     //Estado para verificar repeticiones
        int cambio = 1;                 //Bandera para verificar la burbuja
        boolean repetido = false;       //Bandera para indicar repeticiones
        //Limpiamos los arreglos
        aux.clear();
        R.clear();
        for(Estado a: e){   //Para cada estado a en el conjunto e de estados:
            aux = Ir_A(a, s);   
            //Verificamos que que no existan elementos repetidos
            for(int i = 0; i<aux.size();i++){
                rep = aux.get(i);
                for(int j = 0; j<R.size();j++){
                    //Si este se encuentra en el conjunto resultado marcamos la bandera
                    if(R.get(j).getId() == rep.getId()){
                        repetido = true;
                        j = R.size()+2;
                    }
                }
                //Si no esta repetido lo añadimos
                if(repetido == false){
                    R.add(rep);
                }
                repetido = false;   //Reiniciamos la bandera
            }
        }
        
        //Ordenamos el arreglo
        for(int cont2 = 0; cont2 < R.size()-1 && cambio != 0; cont2++){	
            cambio = 0; //Se hace la revision del metodo burbuja.
            for(int cont = 0; cont < R.size()-1-cont2; cont++)	//Recorre todo el arreglo.
            {
                    if(R.get(cont).getId() > R.get(cont+1).getId() )	//Si el numero es mayor al siguiente se cambia
                    {
                            auxiliar = R.get(cont+1);
                            R.set(cont+1,R.get(cont));
                            R.set(cont,auxiliar);
                            cambio = 1;
                    }
            }
	}
        //System.out.println(R);
        return R;
    }
}
