
package AnalisisSintactico;

import java.util.ArrayList;

public class Pila {
    // la pila
    private ArrayList<Object> pila = new ArrayList<>();
    
    
    public void push(Object objeto){//agregar valores a la pila
        pila.add(objeto);
    }
    public Object pop(){// mostrar el ultimo valor y lo elimina
        if(!(pila.isEmpty())){
            Object objeto = pila.get(pila.size()-1);
            pila.remove(pila.size()-1);
            return objeto;
        }else{
            return null;
        }
    }
    public Object peek(){//muestra el ultimo valor
        if(!(pila.isEmpty())){
            return pila.get(pila.size()-1);
        }else{
            return null;
        }
        
    }
    public boolean empty(){//dice si la pila esta llena o vacia
        return pila.isEmpty();
    }
    
    public ArrayList<Object> getPila(){
        return this.pila;
    }
// para asignar una pila
    public void setPila(ArrayList<Object> pila) {
        this.pila = (ArrayList<Object>) pila.clone();
    }
    // quita y pone un elemento 
    public void quitarPoner(Object objeto){
        pop();
        push(objeto);
    }
    // vacia la pila
    public void clear(){
        pila.clear();
    }
}
